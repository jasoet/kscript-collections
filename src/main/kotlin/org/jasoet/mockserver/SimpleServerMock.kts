@file:CompilerOpts("-Xuse-experimental=io.ktor.locations.Location")
@file:CompilerOpts("-jvm-target 1.8")
@file:DependsOn("io.ktor:ktor-server-netty:1.1.3")
@file:DependsOn("io.ktor:ktor-server-core:1.1.3")
@file:DependsOn("io.ktor:ktor-gson:1.1.3")
@file:DependsOn("info.picocli:picocli:3.9.6")
@file:DependsOn("org.yaml:snakeyaml:1.24")
@file:DependsOn("org.slf4j:slf4j-simple:1.7.26")
@file:Include("MockServerConfig.kt")

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.logging.Level
import java.util.logging.Logger

Logger.getGlobal().level = Level.OFF

fun startServer(port: Int, yamlConfig: String) {
    val configs = readConfig(yamlConfig)
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

@Command(name = "Simple Server Mock", version = ["1.0"], mixinStandardHelpOptions = true)
inner class Cli : Runnable {

    @Option(names = ["-p", "--port"], required = true, description = ["Server Port"])
    var port: Int = 8080

    @Option(names = ["-c", "--config"], description = ["Base64 JSON Content that will be returned as response"])
    var yamlConfig: String = ""

    override fun run() {
        startServer(port, yamlConfig)
    }
}

CommandLine.run(Cli(), *args)

