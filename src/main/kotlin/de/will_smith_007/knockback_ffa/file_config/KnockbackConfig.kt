package de.will_smith_007.knockback_ffa.file_config

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.file_config.interfaces.IDatabaseConfig
import de.will_smith_007.knockback_ffa.file_config.interfaces.IWorldConfig
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.InputStream
import java.nio.file.Files

@Singleton
class KnockbackConfig @Inject constructor(
    javaPlugin: JavaPlugin
) : IDatabaseConfig, IWorldConfig {

    private var yamlConfiguration: YamlConfiguration
    private var config: File

    init {
        val logger = javaPlugin.logger

        val configDirectory = javaPlugin.dataFolder
        val configName = "config.yml"

        config = File(configDirectory, configName)

        if (configDirectory.mkdirs()) {
            logger.info("Knockback-FFA configuration has been created.")
        }

        if (!config.exists()) {
            val inputStream: InputStream? = javaPlugin.getResource(configName)
            if (inputStream != null) {
                Files.copy(inputStream, config.toPath())
            }
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(config)
    }

    override fun getSQLHost(): String? {
        return yamlConfiguration.getString("sqlHost")
    }

    override fun getSQLPort(): Int {
        return yamlConfiguration.getInt("sqlPort")
    }

    override fun getSQLDatabaseName(): String? {
        return yamlConfiguration.getString("sqlDatabaseName")
    }

    override fun getSQLUsername(): String? {
        return yamlConfiguration.getString("sqlUsername")
    }

    override fun getSQLSecret(): String? {
        return yamlConfiguration.getString("sqlSecret")
    }

    override fun addWorld(world: World) {
        val worldList: List<String> = getWorlds()

        worldList.plus(world.name)
        yamlConfiguration.set("Maps", worldList)

        saveFile()
    }

    override fun removeWorld(worldName: String) {
        val worldList: List<String> = getWorlds()

        worldList.minus(worldName)
        yamlConfiguration.set("Maps", worldList)
        yamlConfiguration.set(worldName, null)

        saveFile()
    }

    override fun setWorldSpawn(worldName: String, spawnLocation: Location) {
        yamlConfiguration.set("$worldName.x", spawnLocation.x)
        yamlConfiguration.set("$worldName.y", spawnLocation.y)
        yamlConfiguration.set("$worldName.z", spawnLocation.z)
        yamlConfiguration.set("$worldName.yaw", spawnLocation.yaw)
        yamlConfiguration.set("$worldName.pitch", spawnLocation.pitch)

        saveFile()
    }

    override fun setDeathHeight(worldName: String, deathHeight: Int) {
        yamlConfiguration.set("$worldName.deathHeight", deathHeight)

        saveFile()
    }

    override fun getWorlds(): List<String> {
        return yamlConfiguration.getStringList("Maps")
    }

    override fun isConfiguredWorld(worldName: String): Boolean {
        return getWorlds().contains(worldName)
    }

    private fun saveFile() {
        yamlConfiguration.save(config)
    }
}