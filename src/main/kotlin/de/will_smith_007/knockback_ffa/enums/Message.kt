package de.will_smith_007.knockback_ffa.enums

enum class Message(
    private val message: String
) {

    PREFIX("§f[§eKnockback-FFA§f] §7");

    override fun toString(): String {
        return message
    }
}