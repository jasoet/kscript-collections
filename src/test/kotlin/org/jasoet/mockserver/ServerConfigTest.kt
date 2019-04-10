package org.jasoet.mockserver

import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import kotlin.test.assertNotNull

@TestInstance(Lifecycle.PER_CLASS)
class ServerConfigTest {

    @Nested
    inner class YamlConfig {

        @Test
        fun `it should able to load stream from filename`() {
            val fileLocation = "./src/test/resources/org/jasoet/mockserver/SampleConfig.yaml"
            val stream = loadStream(fileLocation)
            val config = readYaml(stream)
            config.shouldNotBeNull()
            assertNotNull(config, "Yaml Config should not null")
        }

        @Test
        fun `it should able to load std in`() {
            val stream = loadStream()
            stream.shouldNotBeNull()
        }

        @Test
        fun `it should able to read correct yaml`() {
            val configStream = this.javaClass.getResourceAsStream("/org/jasoet/mockserver/SampleConfig.yaml")
            configStream.shouldNotBeNull()
            val configs = readYaml(configStream)
            configs.shouldNotBeNull()
            assertNotNull(configs, "Yaml Config should not null")
            configs.shouldNotBeEmpty()
            configs.size.shouldEqualTo(3)
        }

        @Test
        fun `it should not able to read incorrect yaml`() {
            val configStream = this.javaClass.getResourceAsStream("/org/jasoet/mockserver/InvalidConfig.yaml")
            configStream.shouldNotBeNull()
            val config = readYaml(configStream)
            config.shouldBeNull()
        }

    }

    @Nested
    inner class CliTest {

    }
}