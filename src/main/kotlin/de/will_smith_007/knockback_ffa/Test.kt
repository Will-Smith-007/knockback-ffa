package de.will_smith_007.knockback_ffa

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun CommandSender.isPlayer(): Boolean {
    contract {
        returns(true) implies (this@isPlayer is Player)
    }
    return this is Player
}