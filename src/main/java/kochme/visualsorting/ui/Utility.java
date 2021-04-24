package kochme.visualsorting.ui;

import javax.swing.*;
import kochme.visualsorting.ui.IconLoader;

public class Utility {
    public static JButton createButton(String iconPath) {
        JButton btn = new JButton();
        Icon icon = IconLoader.getIcon(iconPath);
        btn.setRolloverEnabled(true);
        btn.setRolloverIcon(new RolloverIcon(icon));
        btn.setDisabledIcon(new DisabledIcon(icon));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setIcon(IconLoader.getIcon(iconPath));
        return btn;
    }
}