import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import kotlin.system.exitProcess

data class Configs(var configs: List<Config> = emptyList()) {
    constructor() : this(emptyList())
}

data class Config(var path: String, var content: String = "", var contentType: String = "application/json", var statusCode: Int = 200) {
    constructor() : this("", content = "", contentType = "application/json", statusCode = 2000)
}

private val yaml by lazy {
    val constructor = Constructor(Configs::class.java).apply {
        val typeDescription = TypeDescription(Configs::class.java)
        typeDescription.addPropertyParameters("wheels", Config::class.java)
        addTypeDescription(typeDescription)
    }
    Yaml(constructor)
}

fun readConfig(yamlConfig: String): List<Config> {
    val inputStream: InputStream = if (yamlConfig.isBlank()) {
        System.`in`
    } else {
        FileInputStream(File(yamlConfig))
    }
    return try {
        yaml.load<Configs>(inputStream).configs.distinctBy(Config::path)
    } catch (e: Exception) {
        System.err.println("Failed to load Yaml Config! Cause: ${e.message}")
        exitProcess(1)
    }
}

