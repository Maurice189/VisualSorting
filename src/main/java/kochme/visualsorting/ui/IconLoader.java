package kochme.visualsorting.ui;

import javax.swing.ImageIcon;

public class IconLoader {
    public static ImageIcon getIcon(String iconPath) {
        java.net.URL url = IconLoader.class.getResource(iconPath);
        ImageIcon icon = new ImageIcon(url);
        System.out.println("Width: " + icon.getIconWidth() + " - Height: " + icon.getIconHeight());
        return icon;
    }
}
