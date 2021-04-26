package kochme.visualsorting.ui;

import javax.swing.ImageIcon;

public class IconLoader {
    public static ImageIcon getIcon(String iconPath) {
        return new ImageIcon(IconLoader.class.getResource(iconPath));
    }
}
