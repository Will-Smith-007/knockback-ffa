package de.will_smith_007.knockback_ffa.scheduler.interfaces

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitScheduler

interface Scheduler {

    val bukkitScheduler: BukkitScheduler
        get() = Bukkit.getScheduler()

    /**
     * Starts the scheduler.
     */
    fun start()

    /**
     * Stops the scheduler.
     */
    fun stop()
}