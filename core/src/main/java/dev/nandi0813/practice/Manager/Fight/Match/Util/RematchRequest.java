package dev.nandi0813.practice.Manager.Fight.Match.Util;

import dev.nandi0813.practice.Manager.Backend.ConfigManager;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Duel.DuelManager;
import dev.nandi0813.practice.Manager.Duel.DuelRequest;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.MatchManager;
import dev.nandi0813.practice.Manager.Inventory.Inventories.LobbyInventory;
import dev.nandi0813.practice.Manager.Inventory.Inventory;
import dev.nandi0813.practice.Manager.Inventory.InventoryManager;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Profile.Enum.ProfileStatus;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Manager.Profile.ProfileManager;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.ZonePractice;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RematchRequest {

    @Getter
    private final List<Player> players = new ArrayList<>();
    @Getter
    private final Ladder ladder;
    private final int rounds;

    private boolean isRequested = false;

    public RematchRequest(Match match) {
        this.players.addAll(match.getPlayers());
        this.ladder = match.getLadder();
        this.rounds = match.getWinsNeeded();

        setInventories();
        startRunnable();
    }

    public void sendRematchRequest(Player sender) {
        Player target = getOtherPlayer(sender);
        if (!target.isOnline()) {
            Common.sendMMMessage(sender, LanguageManager.getString("MATCH.REMATCH-REQUEST.TARGET-OFFLINE"));
            return;
        }

        Profile targetProfile = ProfileManager.getInstance().getProfile(target);

        if (isRequested) {
            Common.sendMMMessage(sender, LanguageManager.getString("MATCH.REMATCH-REQUEST.ALREADY-SENT"));
            return;
        }

        if ((targetProfile.getStatus().equals(ProfileStatus.LOBBY) || targetProfile.getStatus().equals(ProfileStatus.EDITOR) || targetProfile.getStatus().equals(ProfileStatus.SPECTATE)) && !targetProfile.isParty()) {
            if (!targetProfile.isDuelRequest()) {
                Common.sendMMMessage(sender, LanguageManager.getString("MATCH.REMATCH-REQUEST.TARGET-DONT-ACCEPT").replaceAll("%target%", target.getName()));
                return;
            }

            DuelRequest request = new DuelRequest(sender, target, ladder, null, rounds);
            DuelManager.getInstance().sendRequest(request);

            isRequested = true;
        } else
            Common.sendMMMessage(sender, LanguageManager.getString("MATCH.REMATCH-REQUEST.CANT-SEND-ANYMORE").replaceAll("%target%", target.getName()));
    }

    public Player getOtherPlayer(Player player) {
        for (Player player1 : players)
            if (player1 != player)
                return player1;
        return null;
    }

    public void setInventories() {
        Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () ->
        {
            for (Player player : this.players) {
                if (!player.isOnline()) return;

                Inventory inventory = InventoryManager.getInstance().getPlayerInventory(player);
                if (inventory instanceof LobbyInventory lobbyInventory) {
                    lobbyInventory.addRematchItem(player);
                }
            }
        }, 5L);
    }

    public void startRunnable() {
        Bukkit.getScheduler().runTaskLater(ZonePractice.getInstance(), () ->
                {
                    MatchManager.getInstance().getRematches().remove(this);

                    for (Player player : players) {
                        if (!player.isOnline()) return;

                        Profile profile = ProfileManager.getInstance().getProfile(player);
                        if (!profile.getStatus().equals(ProfileStatus.LOBBY)) return;

                        InventoryManager.getInstance().setLobbyInventory(player, false);
                    }
                },
                ConfigManager.getInt("MATCH-SETTINGS.REMATCH.EXPIRE-TIME") * 20L);
    }

}
