package de.will_smith_007.knockback_ffa.scheduler.interfaces

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitScheduler

interface Scheduler {

    val bukkitScheduler: BukkitScheduler
        get() = Bukkit.getScheduler()

    fun start()

    fun stop()
}