package main;

import javax.swing.*;
import java.awt.*;


public class DisabledIcon implements Icon {
    protected Icon icon;

    public DisabledIcon(Icon icon) {
        this.icon = icon;
    }

    public int getIconHeight() {
        return icon.getIconHeight();
    }

    public int getIconWidth() {
        return icon.getIconWidth();
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setComposite(AlphaComposite.SrcAtop.derive(0.5f));
        icon.paintIcon(c, g, x, y);
    }

}