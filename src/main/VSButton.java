package main;

import com.bulenkov.iconloader.IconLoader;

import javax.swing.*;

public class VSButton extends JButton {

    public VSButton(String iconResourcePath) {
        Icon icon = IconLoader.getIcon(iconResourcePath);

        this.setIcon(IconLoader.getIcon(iconResourcePath));
        this.setRolloverEnabled(true);
        this.setRolloverIcon(new RolloverIcon(icon));
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}
