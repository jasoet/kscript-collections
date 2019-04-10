@file:CompilerOpts("-jvm-target 1.8")
@file:DependsOn("io.ktor:ktor-server-netty:1.1.3")
@file:DependsOn("io.ktor:ktor-server-core:1.1.3")
@file:DependsOn("io.ktor:ktor-gson:1.1.3")
@file:DependsOn("info.picocli:picocli:3.9.6")
@file:DependsOn("org.yaml:snakeyaml:1.24")
@file:DependsOn("org.slf4j:slf4j-simple:1.7.26")
@file:Include("ServerConfig.kt")

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jasoet.mockserver.Cli
import org.jasoet.mockserver.loadStream
import org.jasoet.mockserver.readYaml
import picocli.CommandLine
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess

Logger.getGlobal().level = Level.OFF

fun startServer(port: Int, configLocation: String = "") {
    val stream = loadStream(configLocation)
    val configs = readYaml(stream) ?: exitProcess(1)
    embeddedServer(Netty, port = port) {
        routing {
            configs.forEach {
                val config = it
                get(config.path) {
                    call.respondText(text = config.content,
                            contentType = ContentType.parse(config.contentType),
                            status = HttpStatusCode.fromValue(config.statusCode))
                }
            }
        }
    }.start(true)
}

val cli = Cli { port, yamlConfig ->
    startServer(port, yamlConfig)
}

CommandLine.run(cli, *args)

