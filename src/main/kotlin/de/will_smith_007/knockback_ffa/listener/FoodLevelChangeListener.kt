package de.will_smith_007.knockback_ffa.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class FoodLevelChangeListener : Listener {

    @EventHandler
    fun onFoodLevelChange(foodLevelChangeEvent: FoodLevelChangeEvent) {
        foodLevelChangeEvent.isCancelled = true
    }
}