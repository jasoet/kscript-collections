@file:CompilerOpts("-jvm-target 1.8")
@file:DependsOn("id.jasoet:fun-gson:1.0.0")
@file:DependsOn("id.jasoet:scripts-commons:1.0.3")
@file:DependsOn("com.github.salomonbrys.kotson:kotson:2.5.0")
@file:DependsOn("io.ktor:ktor-client-apache:1.2.2")
@file:DependsOn("io.ktor:ktor-client-gson:1.2.2")

import com.github.salomonbrys.kotson.string
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import id.jasoet.gson.Json
import id.jasoet.gson.loadRemote
import id.jasoet.gson.query
import id.jasoet.scripts.Execute
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.put

data class NodeDetail(val node: String, val checkId: String, val serviceId: String, val serviceName: String, val dataCenter: String = "dc1", val status: String)

val result = Execute {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = Json(
            config = {
                gson
            }
    )

    val nodeName = "ip-10-14-23-71.ap-southeast-1.compute.internal"
    val nodeHealths = loadRemote("http://localhost:8500/v1/health/node/$nodeName")
    val nodesWithCriticalStatus = json.query<JsonArray>(nodeHealths, "$.[?(@.Status == 'critical')]")
    val allNodes = nodesWithCriticalStatus.mapNotNull {
        if (it.isJsonObject) {
            val node = it.asJsonObject
            NodeDetail(
                    node = node.get("Node").string,
                    checkId = node.get("CheckID").string,
                    serviceId = node.get("ServiceID").string,
                    serviceName = node.get("ServiceName").string,
                    dataCenter = "dc1",
                    status = node.get("Status").string
            )
        } else {
            null
        }
    }

    val httpClient = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = GsonSerializer {}
        }
    }

    println(allNodes.size)
    println(allNodes)

    allNodes.forEach {
        val deregResult = httpClient.put<String>("http://localhost:8500/v1/agent/service/deregister/${it.serviceId}") {}
        println("Dereg ${it.checkId} from node ${it.node}, serviceName ${it.serviceName}.  success = $deregResult")
    }

}

result.print()
