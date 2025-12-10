package dev.nandi0813.practice.Manager.PlayerKit.Items;

import dev.nandi0813.practice.Manager.GUI.GUIItem;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditorIcon extends GUIItem {

    private int slot = -1;

    public boolean equals(EditorIcon editorIcon) {
        return this.getMaterial() == editorIcon.getMaterial() && this.getSlot() == editorIcon.getSlot();
    }

}
