package dev.nandi0813.practice.Manager.GUI.GUIs.Selectors;

import dev.nandi0813.practice.Manager.Arena.Arenas.Arena;
import dev.nandi0813.practice.Manager.Backend.GUIFile;
import dev.nandi0813.practice.Manager.Backend.LanguageManager;
import dev.nandi0813.practice.Manager.Duel.DuelManager;
import dev.nandi0813.practice.Manager.Duel.DuelRequest;
import dev.nandi0813.practice.Manager.Fight.Match.Enum.MatchType;
import dev.nandi0813.practice.Manager.Fight.Match.Match;
import dev.nandi0813.practice.Manager.Fight.Match.Type.Duel.Duel;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PartyFFA.PartyFFA;
import dev.nandi0813.practice.Manager.Fight.Match.Type.PlayersVsPlayers.PartySplit.PartySplit;
import dev.nandi0813.practice.Manager.GUI.GUI;
import dev.nandi0813.practice.Manager.GUI.GUIItem;
import dev.nandi0813.practice.Manager.GUI.GUIManager;
import dev.nandi0813.practice.Manager.GUI.GUIType;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Ladder;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.Normal.NormalLadder;
import dev.nandi0813.practice.Manager.Ladder.Abstraction.PlayerCustom.CustomLadder;
import dev.nandi0813.practice.Manager.Ladder.LadderManager;
import dev.nandi0813.practice.Manager.Ladder.Util.LadderUtil;
import dev.nandi0813.practice.Manager.Party.MatchRequest.PartyRequest;
import dev.nandi0813.practice.Manager.Party.Party;
import dev.nandi0813.practice.Manager.Party.PartyManager;
import dev.nandi0813.practice.Manager.Profile.Profile;
import dev.nandi0813.practice.Module.Util.ClassImport;
import dev.nandi0813.practice.Util.Common;
import dev.nandi0813.practice.Util.InventoryUtil;
import dev.nandi0813.practice.ZonePractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LadderSelectorGui extends GUI {

    private final Profile profile;

    private final MatchType matchType;
    private final Map<Integer, Ladder> ladderSlots = new HashMap<>();

    private static final ItemStack FILLER_ITEM = GUIFile.getGuiItem("GUIS.SELECTORS.LADDER-SELECTOR.ICONS.FILLER-ITEM").get();
    private static final GUIItem CUSTOM_PLAYER_KIT_ITEM = GUIFile.getGuiItem("GUIS.SELECTORS.LADDER-SELECTOR.ICONS.BASE-CUSTOM-PLAYER-KIT-ICON");

    public LadderSelectorGui(Profile profile, MatchType matchType) {
        super(GUIType.Ladder_Selector);
        this.profile = profile;
        this.matchType = matchType;
        this.gui.put(1, InventoryUtil.createInventory(GUIFile.getString("GUIS.SELECTORS.LADDER-SELECTOR.TITLE").replace("%matchType%", this.matchType.getName(false)), 6));

        build();
    }


    @Override
    public void build() {
        update();
    }

    @Override
    public void update() {
        Bukkit.getScheduler().runTaskAsynchronously(ZonePractice.getInstance(), () ->
        {
            Inventory inventory = gui.get(1);
            inventory.clear();
            ladderSlots.clear();

            for (int i = 45; i < 54; i++)
                inventory.setItem(i, FILLER_ITEM);

            for (NormalLadder ladder : LadderManager.getInstance().getLadders()) {
                if (ladder.isEnabled() && ladder.isUnranked() && ladder.getMatchTypes().contains(matchType)) {
                    List<String> lore = new ArrayList<>();
                    for (String line : GUIFile.getStringList("GUIS.SELECTORS.LADDER-SELECTOR.ICONS.LADDER.LORE"))
                        lore.add(line.replaceAll("%ladder%", ladder.getDisplayName()));

                    ItemStack icon = ClassImport.getClasses().getItemCreateUtil().createItem(ladder.getIcon(), GUIFile.getString("GUIS.SELECTORS.LADDER-SELECTOR.ICONS.LADDER.NAME").replace("%ladder%", ladder.getDisplayName()), lore);

                    int slot = inventory.firstEmpty();
                    gui.get(1).setItem(slot, icon);
                    ladderSlots.put(slot, ladder);
                }
            }

            if (profile.getSelectedCustomLadder() != null) {
                GUIItem customPlayerKitItem = CUSTOM_PLAYER_KIT_ITEM.cloneItem();
                ItemStack ladderIcon = profile.getSelectedCustomLadder().getIcon();
                if (ladderIcon != null) {
                    // TODO: Custom ladder icon with name
                    if (customPlayerKitItem.getName() == null)
                        customPlayerKitItem.setName(CUSTOM_PLAYER_KIT_ITEM.getName());

                    customPlayerKitItem.setMaterial(ladderIcon.getType());
                    customPlayerKitItem.setDamage(ladderIcon.getDurability());
                }

                inventory.setItem(53, customPlayerKitItem.get());
                ladderSlots.put(53, profile.getSelectedCustomLadder());
            }
        });
    }

    @Override
    public void handleClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Party party = PartyManager.getInstance().getParty(player);
        Inventory inventory = e.getView().getTopInventory();
        int slot = e.getRawSlot();

        e.setCancelled(true);

        if (inventory.getSize() <= slot) return;
        if (!ladderSlots.containsKey(slot)) return;

        Ladder ladder = ladderSlots.get(slot);

        if (ladder instanceof NormalLadder) {
            if (!ladder.isEnabled() || !ladder.getMatchTypes().contains(matchType)) {
                Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.LADDER-NOT-AVAILABLE"));
                update();
                return;
            } else if (((NormalLadder) ladder).isFrozen()) {
                Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.LADDER-FROZEN"));
                return;
            }
        } else if (ladder instanceof CustomLadder) {
            if (!ladder.isEnabled()) {
                Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.CUSTOM-LADDER-NOT-READY"));
                return;
            } else if (!ladder.getMatchTypes().contains(matchType)) {
                Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.CUSTOM-LADDER-CANT-PLAY-MATCHTYPE"));
                return;
            }
        }

        /*
         * Duel games ladder selector
         */
        if (party == null) {
            if (player.hasPermission("zpp.duel.selectarena")) {
                new ArenaSelectorGui(ladder, matchType, this).open(player);
            } else if (player.hasPermission("zpp.duel.selectrounds") && ladder instanceof NormalLadder) {
                new DuelRoundSelectorGui(matchType, ladder, null, this).open(player);
            } else {
                Player target = DuelManager.getInstance().getPendingRequestTarget().get(player);
                DuelRequest request = new DuelRequest(player, target, ladder, null, ladder.getRounds());

                if (target.isOnline())
                    DuelManager.getInstance().sendRequest(request);
                else {
                    Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.DUEL.TARGET-LEFT"));
                    player.closeInventory();
                }
            }
        }
        /*
         * Party games ladder selector
         */
        else {
            /*
             * Own party game ladder selector
             */
            if (!this.matchType.equals(MatchType.PARTY_VS_PARTY)) {
                if (party.getMembers().size() < 2) {
                    player.closeInventory();
                    Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.PARTY.NOT-ENOUGH-PLAYERS"));
                    return;
                }

                if (player.hasPermission("zpp.party.selectarena")) {
                    new ArenaSelectorGui(ladder, matchType, this).open(player);
                } else if (player.hasPermission("zpp.party.selectrounds") && ladder instanceof NormalLadder) {
                    new DuelRoundSelectorGui(matchType, ladder, null, this).open(player);
                } else {
                    Arena arena = LadderUtil.getAvailableArena(ladder);
                    if (arena == null) {
                        Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.PARTY.NO-AVAILABLE-ARENA"));
                        return;
                    }

                    player.closeInventory();

                    Match match = getMatch(party, ladder, arena, ladder.getRounds());
                    if (match == null) {
                        Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.PARTY.ERROR"));
                        return;
                    }

                    party.setMatch(match);
                    match.startMatch();
                }
            }
            /*
             * Party vs party ladder selector
             */
            else {
                Party target = PartyManager.getInstance().getRequestManager().getPendingRequestTarget().get(party);

                if (!PartyManager.getInstance().getParties().contains(target)) {
                    Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.PARTY.TARGET-PARTY-DISBANDED"));
                    GUIManager.getInstance().searchGUI(GUIType.Party_OtherParties).open(player);
                    return;
                }

                if (player.hasPermission("zpp.party.selectarena")) {
                    new ArenaSelectorGui(ladder, matchType, this).open(player);
                } else if (player.hasPermission("zpp.party.selectrounds") && ladder instanceof NormalLadder) {
                    new DuelRoundSelectorGui(matchType, ladder, null, this).open(player);
                } else {
                    player.closeInventory();

                    Arena arena = LadderUtil.getAvailableArena(ladder);
                    if (arena == null) {
                        Common.sendMMMessage(player, LanguageManager.getString("LADDER.SELECTOR.PARTY.NO-AVAILABLE-ARENA"));
                        return;
                    }

                    // Send the game request
                    PartyRequest partyRequest = new PartyRequest(party, target, ladder, arena, ladder.getRounds());
                    partyRequest.sendRequest();
                }
            }
        }
    }

    @Nullable
    private Match getMatch(Party party, Ladder ladder, Arena arena, int rounds) {
        Match match = null;

        if (party.getMembers().size() == 2)
            match = new Duel(ladder, arena, party.getMembers(), false, rounds);
        else {
            if (matchType.equals(MatchType.PARTY_FFA))
                match = new PartyFFA(ladder, arena, party, rounds);
            else if (matchType.equals(MatchType.PARTY_SPLIT))
                match = new PartySplit(ladder, arena, party, rounds);
        }
        return match;
    }

}
