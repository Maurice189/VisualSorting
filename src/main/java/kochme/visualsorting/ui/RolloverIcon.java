package kochme.visualsorting.ui;

import javax.swing.*;
import java.awt.*;

public class RolloverIcon implements Icon {
    protected Icon icon;

    public RolloverIcon(Icon icon) {
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
        graphics2d.setColor(new Color(0, 0, 0, 20));
        graphics2d.fillRect(x, y, getIconWidth(), getIconHeight());
        graphics2d.setColor(new Color(0, 0, 0, 50));
        graphics2d.drawRect(x, y, getIconWidth(), getIconHeight());
        icon.paintIcon(c, g, x, y);
    }

}