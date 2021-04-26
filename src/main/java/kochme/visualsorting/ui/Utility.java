package kochme.visualsorting.ui;

import javax.swing.*;

public class Utility {
    public static JButton createButton(String iconPath) {
        JButton btn = new JButton();
        Icon icon = new ImageIcon(Utility.class.getResource(iconPath));
        btn.setRolloverEnabled(true);
        btn.setRolloverIcon(new RolloverIcon(icon));
        btn.setDisabledIcon(new DisabledIcon(icon));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setIcon(new ImageIcon(Utility.class.getResource(iconPath)));
        return btn;
    }
}