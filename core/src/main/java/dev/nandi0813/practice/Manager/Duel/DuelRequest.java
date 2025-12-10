package dev.nandi0813.practice.Manager.Duel;

import dev.nandi0813.practice.Manager.Arena.Arenas.Arena;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Fight.Match.Type.Duel.Duel;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Ladder.Util.LadderUtil;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.Common;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Getter
@Setter
public class DuelRequest {

    private Player sender;
    private Player target;
    private Ladder ladder;
    private Arena arena;
    private int rounds;

    public DuelRequest(Player sender, Player target, Ladder ladder, Arena arena, int rounds) {
        this.sender = sender;
        this.target = target;
        this.ladder = ladder;
        this.arena = arena;
        this.rounds = rounds;
    }

    public void sendRequest() {
        String arenaName;
        if (arena != null)
            arenaName = arena.getDisplayName();
        else
            arenaName = LanguageManager.getString("COMMAND.DUEL.REQUEST-MESSAGE.RANDOM-ARENA-NAME");

        for (String line : LanguageManager.getList("COMMAND.DUEL.REQUEST-MESSAGE.SENDER")) {
            Common.sendMMMessage(sender, line
                    .replaceAll("%ladder%", ladder.getDisplayName())
                    .replaceAll("%arena%", arenaName)
                    .replaceAll("%rounds%", String.valueOf(rounds))
                    .replaceAll("%target%", target.getName())
                    .replaceAll("%targetPing%", String.valueOf(ClassImport.getClasses().getPlayerUtil().getPing(target)))
            );
        }

        for (String line : LanguageManager.getList("COMMAND.DUEL.REQUEST-MESSAGE.TARGET")) {
            Common.sendMMMessage(target, line
                    .replaceAll("%ladder%", ladder.getDisplayName())
                    .replaceAll("%arena%", arenaName)
                    .replaceAll("%rounds%", String.valueOf(rounds))
                    .replaceAll("%sender%", sender.getName())
                    .replaceAll("%senderPing%", String.valueOf(ClassImport.getClasses().getPlayerUtil().getPing(sender)))
            );
        }
    }

    public void acceptRequest() {
        DuelManager.getInstance().getRequests().get(target).remove(this);

        Arena arena;
        if (this.getArena() != null) {
            arena = this.getArena();

            if (arena.getAvailableArena() == null) {
                Common.sendMMMessage(sender, LanguageManager.getString("COMMAND.DUEL.ARENA-BUSY").replaceAll("%arena%", this.getArena().getDisplayName()));
                arena = LadderUtil.getAvailableArena(ladder);
            }
        } else
            arena = LadderUtil.getAvailableArena(ladder);

        if (arena != null) {
            Duel duel = new Duel(ladder, arena, Arrays.asList(sender, target), false, rounds);
            duel.startMatch();
        } else {
            Common.sendMMMessage(sender, LanguageManager.getString("COMMAND.DUEL.NO-AVAILABLE-ARENA"));
            Common.sendMMMessage(target, LanguageManager.getString("COMMAND.DUEL.NO-AVAILABLE-ARENA"));
        }
    }

}
