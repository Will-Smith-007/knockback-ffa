package de.will_smith_007.knockback_ffa.kit

import com.google.inject.Singleton
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

@Singleton
class KitHandler {

    /**
     * Adds all standard items for the player.
     */
    fun setPlayerKit(player: Player) {
        val knockbackStick = ItemStack(Material.STICK)
        knockbackStick.editMeta { itemMeta: ItemMeta ->
            with(itemMeta) {
                displayName(
                    Component.text("Knockback Stick", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)
                )
                addEnchant(Enchantment.KNOCKBACK, 2, false)
            }
        }

        val fishingRod = ItemStack(Material.FISHING_ROD)
        fishingRod.editMeta { itemMeta: ItemMeta ->
            with(itemMeta) {
                displayName(
                    Component.text("Grappling hook", NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false)
                )
            }
        }


        val playerInventory = player.inventory
        playerInventory.clear()
        playerInventory.addItem(knockbackStick, fishingRod)
    }
}