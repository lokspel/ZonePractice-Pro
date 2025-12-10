package dev.nandi0813.practice.Manager.Ladder.Type;

import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.RoundStatus;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.TeamEnum;
import dev.nandi0813.practice.Manager.Fight.Match.Interface.PlayerWinner;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Round;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PlayersVsPlayers;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface.CustomConfig;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Interface.LadderHandle;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.Enum.LadderType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Setter
@Getter
public class Boxing extends NormalLadder implements CustomConfig, LadderHandle {

    private int boxingWinHit;
    private static final String BOXING_WINHIT_PATH = "boxing-winhit";

    public Boxing(String name, LadderType type) {
        super(name, type);
    }

    @Override
    public boolean handleEvents(Event e, Match match) {
        if (e instanceof EntityDamageEvent) {
            onPlayerDamage((EntityDamageEvent) e, match);

            if (e instanceof EntityDamageByEntityEvent)
                onPlayerDamagePlayer((EntityDamageByEntityEvent) e, match, this);

            return true;
        }
        return false;
    }

    @Override
    public void setCustomConfig(YamlConfiguration config) {
        config.set(BOXING_WINHIT_PATH, this.boxingWinHit);
    }

    @Override
    public void getCustomConfig(YamlConfiguration config) {
        if (config.isInt(BOXING_WINHIT_PATH)) {
            this.boxingWinHit = config.getInt(BOXING_WINHIT_PATH);
            if (this.boxingWinHit < 40 || this.boxingWinHit > 600)
                this.boxingWinHit = 100;
        } else
            this.boxingWinHit = 100;
    }

    private static void onPlayerDamagePlayer(final @NotNull EntityDamageByEntityEvent e, final @NotNull Match match, final @NotNull Boxing ladder) {
        Player attacker = (Player) e.getDamager();
        int requiredStrokes = ladder.getBoxingWinHit();
        requiredStrokes--;

        MatchType matchType = match.getType();
        TeamEnum attackerTeam;

        Round round = match.getCurrentRound();

        switch (matchType) {
            case DUEL:
            case PARTY_FFA:
                if (match.getCurrentStat(attacker).getHit() == requiredStrokes && round instanceof PlayerWinner) {
                    PlayerWinner playerWinner = (PlayerWinner) match.getCurrentRound();

                    if (!match.getCurrentStat(attacker).isSet()) {
                        playerWinner.setRoundWinner(attacker);
                        round.endRound();
                    }
                }
                break;
            case PARTY_SPLIT:
            case PARTY_VS_PARTY:
                if (match instanceof PlayersVsPlayers playersVsPlayers) {
                    attackerTeam = playersVsPlayers.getTeam(attacker);

                    if (getTeamBoxingStrokes(match, playersVsPlayers.getTeamPlayers(attackerTeam)) == requiredStrokes) {
                        if (!match.getCurrentStat(attacker).isSet()) {
                            playersVsPlayers.getCurrentRound().setRoundWinner(attackerTeam);
                            round.endRound();
                        }
                    }
                }
                break;
        }
    }

    private static void onPlayerDamage(final @NotNull EntityDamageEvent e, final @NotNull Match match) {
        if (!(e.getEntity() instanceof Player player)) return;

        if (match.getCurrentRound().getRoundStatus().equals(RoundStatus.LIVE)) {
            e.setDamage(0);
            player.setHealth(20);
        }
    }

    public static int getTeamBoxingStrokes(Match match, List<Player> team) {
        int strokes = 0;
        for (Player player : team)
            strokes += match.getCurrentStat(player).getHit();
        return strokes;
    }

}
