package org.jasoet.mockserver

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import picocli.CommandLine
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

data class Configs(var configs: List<Config> = emptyList()) {
    constructor() : this(emptyList())
}

data class Config(var path: String, var content: String = "", var contentType: String = "application/json", var statusCode: Int = 200) {
    constructor() : this("", content = "", contentType = "application/json", statusCode = 2000)
}

private val yaml by lazy {
    Yaml(Constructor(Configs::class.java))
}

fun loadStream(fileName: String = ""): InputStream {
    return if (fileName.isNotBlank()) {
        FileInputStream(File(fileName))
    } else {
        System.`in`
    }
}

fun readYaml(inputStream: InputStream): List<Config>? {
    return try {
        yaml.load<Configs>(inputStream).configs.distinctBy(Config::path)
    } catch (e: Exception) {
        System.err.println("Failed to load Yaml Config! Cause: ${e.message}")
        null
    }
}

@CommandLine.Command(name = "Simple Server Mock", version = ["1.0"], mixinStandardHelpOptions = true)
class Cli(private val operation: (Int, String) -> Unit) : Runnable {

    @CommandLine.Option(names = ["-p", "--port"], required = true, description = ["Server Port"])
    var port: Int = 8080

    @CommandLine.Option(names = ["-c", "--config"], description = ["Base64 JSON Content that will be returned as response"])
    var yamlConfig: String = ""

    override fun run() {
        operation(port, yamlConfig)
    }
}
