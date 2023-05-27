package de.will_smith_007.knockback_ffa.fileConfig.interfaces

import org.bukkit.Location
import org.bukkit.World

interface WorldConfig {

    /**
     * Adds a world to the config.
     * @param world The [World] which should be added to the config.
     */
    fun addWorld(world: World)

    /**
     * Removed a world from the config file.
     * @param worldName The name of world which should be removed from the config.
     */
    fun removeWorld(worldName: String)

    /**
     * Sets the world spawn [Location] for the specified name of world.
     * @param worldName The name of world for which the [Location] should be set.
     * @param spawnLocation The [Location] which should be set.
     */
    fun setWorldSpawn(worldName: String, spawnLocation: Location)

    /**
     * Sets the death height for the specified name of world.
     * @param worldName Name of world for which the death height should be set.
     * @param deathHeight death height which should be set.
     */
    fun setDeathHeight(worldName: String, deathHeight: Int)

    /**
     * Gets the configured worlds from the config file.
     * @return A [MutableList] which contains the configured world names.
     */
    fun getWorlds(): MutableList<String>

    /**
     * Checks if the name of world is in the config file.
     * @param worldName Name of world which should be checked.
     * @return True if the name of world is in the config file.
     */
    fun isConfiguredWorld(worldName: String): Boolean

    /**
     * Gets the configured death height of the name of world from the config file.
     * @param worldName The name of world from which you want to get the death height.
     */
    fun getDeathHeight(worldName: String): Int

    /**
     * Gets the spawn location of a configured name of world.
     * @param worldName Name of world from which you want to get the spawn location.
     */
    fun getWorldSpawnLocation(worldName: String): Location?
}