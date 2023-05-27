package de.will_smith_007.knockback_ffa.fileConfig.interfaces

import org.bukkit.Location
import org.bukkit.World

interface WorldConfig {

    fun addWorld(world: World)

    fun removeWorld(worldName: String)

    fun setWorldSpawn(worldName: String, spawnLocation: Location)

    fun setDeathHeight(worldName: String, deathHeight: Int)

    fun getWorlds(): MutableList<String>

    fun isConfiguredWorld(worldName: String): Boolean

    fun getDeathHeight(worldName: String): Int

    fun getWorldSpawnLocation(worldName: String): Location?
}