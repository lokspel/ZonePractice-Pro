package dev.nandi0813.practice.Manager.PlayerKit;

import dev.nandi0813.practice.Manager.Ladder.Abstraction.PlayerCustom.CustomLadder;
import dev.nandi0813.practice.Manager.PlayerKit.Items.KitItem;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerKitEditing {

    private final CustomLadder customLadder;
    @Setter
    private KitItem kitItem;

    public PlayerKitEditing(CustomLadder customLadder) {
        this.customLadder = customLadder;
    }

}
