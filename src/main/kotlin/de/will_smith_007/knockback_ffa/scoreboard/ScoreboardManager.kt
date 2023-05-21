package de.will_smith_007.knockback_ffa.scoreboard

import com.google.inject.Inject
import com.google.inject.Singleton
import de.will_smith_007.knockback_ffa.game_assets.GameAssets
import de.will_smith_007.knockback_ffa.game_data.GameData
import de.will_smith_007.knockback_ffa.scoreboard.interfaces.IScoreboardManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team
import java.text.SimpleDateFormat
import java.util.*

@Singleton
class ScoreboardManager @Inject constructor(
    private val gameAssets: GameAssets
) : IScoreboardManager {

    private val defaultSchema = SimpleDateFormat("mm:ss")

    override fun createScoreboard(player: Player) {
        val scoreboard: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = scoreboard.registerNewObjective(
            "aaa", Criteria.DUMMY,
            Component.text("KNOCKBACK-FFA", NamedTextColor.WHITE)
        )

        objective.displaySlot = DisplaySlot.SIDEBAR
        objective.displayName(Component.text("KNOCKBACK-FFA", NamedTextColor.WHITE))

        val gameData: GameData? = gameAssets.gameData

        run {
            val team = scoreboard.registerNewTeam("x14")
            team.prefix(Component.text("§0"))
            team.addEntry("§0")
            objective.getScore("§0").score = 14
        }

        run {
            val team = scoreboard.registerNewTeam("x13")
            team.prefix(Component.text("§1"))
            team.suffix(Component.text("Map:", NamedTextColor.GRAY))
            team.addEntry("§1")
            objective.getScore("§1").score = 13
        }

        run {
            val worldName: String = gameData?.world?.name ?: "Unknown"
            val team = scoreboard.registerNewTeam("x12")
            team.prefix(Component.text("§2"))
            team.suffix(Component.text(worldName, NamedTextColor.YELLOW))
            team.addEntry("§2")
            objective.getScore("§2").score = 12
        }

        run {
            val team = scoreboard.registerNewTeam("x11")
            team.prefix(Component.text("§3"))
            team.addEntry("§3")
            objective.getScore("§3").score = 11
        }

        run {
            val team = scoreboard.registerNewTeam("x10")
            team.prefix(Component.text("§4"))
            team.suffix(Component.text("Map switch on:", NamedTextColor.GRAY))
            team.addEntry("§4")
            objective.getScore("§4").score = 10
        }

        run {
            val currentTimeMillis: Long = System.currentTimeMillis()
            val lastMapSwitchMillis: Long = gameData?.worldSwitchMillis ?: 0L
            val formattedTime = defaultSchema.format(Date((currentTimeMillis - lastMapSwitchMillis)))
            val team = scoreboard.registerNewTeam("x9")
            team.prefix(Component.text(formattedTime, NamedTextColor.YELLOW))
            team.suffix(Component.text("/", NamedTextColor.DARK_GRAY)
                .append(Component.text("10:00", NamedTextColor.RED)))
            team.addEntry("§5")
            objective.getScore("§5").score = 9
        }

        player.scoreboard = scoreboard
    }

    override fun updateScoreboard(player: Player) {
        val scoreboard: Scoreboard = player.scoreboard
        val gameData: GameData? = gameAssets.gameData

        if (scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) createScoreboard(player)

        val mapTeam: Team? = scoreboard.getTeam("x12")
        val mapSwitchTeam: Team? = scoreboard.getTeam("x9")

        if (mapTeam != null) {
            val worldName: String = gameData?.world?.name ?: "Unknown"
            mapTeam.suffix(Component.text(worldName, NamedTextColor.YELLOW))
        }

        if (mapSwitchTeam != null) {
            val currentTimeMillis: Long = System.currentTimeMillis()
            val lastMapSwitchMillis: Long = gameData?.worldSwitchMillis ?: 0L
            val formattedTime = defaultSchema.format(Date((currentTimeMillis - lastMapSwitchMillis)))
            mapSwitchTeam.prefix(Component.text(formattedTime, NamedTextColor.YELLOW))
        }
    }

    override fun setTablist(player: Player) {
        val scoreboard: Scoreboard = player.scoreboard

        var playerTeam: Team? = scoreboard.getTeam("player")
        if (playerTeam == null) {
            playerTeam = scoreboard.registerNewTeam("player")
        }
        playerTeam.color(NamedTextColor.GRAY)

        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            playerTeam.addEntry(onlinePlayer.name)
        }
    }

    override fun setScoreboardAndTablist(player: Player) {
        createScoreboard(player)
        setTablist(player)
    }
}