package de.will_smith_007.knockback_ffa.file_config.interfaces

import org.bukkit.Location
import org.bukkit.World

interface IWorldConfig {

    fun addWorld(world: World)

    fun removeWorld(worldName: String)

    fun setWorldSpawn(worldName: String, spawnLocation: Location)

    fun setDeathHeight(worldName: String, deathHeight: Int)

    fun getWorlds(): List<String>

    fun isConfiguredWorld(worldName: String): Boolean
}