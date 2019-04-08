@file:Suppress("EXPERIMENTAL_API_USAGE")
@file:CompilerOpts("-Xuse-experimental=io.ktor.locations.Location")
@file:CompilerOpts("-jvm-target 1.8")
@file:DependsOn("io.ktor:ktor-server-netty:1.1.3")
@file:DependsOn("io.ktor:ktor-server-core:1.1.3")
@file:DependsOn("io.ktor:ktor-gson:1.1.3")
@file:DependsOn("info.picocli:picocli:3.9.6")
@file:DependsOn("org.slf4j:slf4j-simple:1.7.26")

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.Option
import java.util.Base64
import java.util.logging.Level
import java.util.logging.Logger

Logger.getGlobal().level = Level.OFF

fun String.decode(): String {
    return String(Base64.getDecoder().decode(this))
}

fun startServer(port: Int, path: String, content: String) {
    embeddedServer(Netty, port = port) {
        routing {
            get(path) {
                call.respondText(content.decode(), ContentType.Application.Json)
            }
        }
    }.start(true)
}

@Command(name = "Simple Server Mock", version = ["1.0"], mixinStandardHelpOptions = true)
inner class Cli : Runnable {

    @Option(names = ["-p", "--port"], required = true, description = ["Server Port"])
    var port: Int = 8080

    @Option(names = ["-u", "--url"], description = ["URL to be Mocked"])
    var path: String = "/v2/configurations/{name}/latest"

    @Option(names = ["-c", "--content"], required = true, description = ["Base64 JSON Content that will be returned as response"])
    var jsonContent: String = ""

    override fun run() {
        startServer(port, path, jsonContent)
    }
}

CommandLine.run(Cli(), *args)

