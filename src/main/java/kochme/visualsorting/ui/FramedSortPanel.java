package kochme.visualsorting.ui;
/*
 * This software is licensed under the MIT License.
 * Copyright 2018, Maurice Koch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import kochme.visualsorting.dialogs.InfoDialog;
import kochme.visualsorting.app.Consts;
import kochme.visualsorting.ui.Utility;

/**
 * This class is used to display the animation in a panel. The animation is
 * based on bars with different heights. Each bar is representing a different
 * value in the sorting list.
 *
 * @author maurice koch
 * @version beta
 * @category graphics
 **/
public class FramedSortPanel extends SortPanel {

    private TitledBorder leftBorder;
    private JButton remove;
    private Consts.SortAlgorithm algorithm;

    public FramedSortPanel(Consts.SortAlgorithm algorithm, int width, int height) {
        super(width, height);

        this.algorithm = algorithm;

        leftBorder = BorderFactory.createTitledBorder("");
        leftBorder.setTitleJustification(TitledBorder.ABOVE_TOP);
        leftBorder.setTitleColor(Color.darkGray);
        leftBorder.setTitleFont(Window.getComponentFont(12f));
        setInfo(0, 0);

        setBorder(leftBorder);
        manageButtons();
    }

    public void setActionListenerForRemoveAction(ActionListener listener) {
        remove.addActionListener(e -> listener.actionPerformed(new ActionEvent(this, e.getID(), e.getActionCommand())));
    }

    private void manageButtons() {

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.insets = new Insets(-7, 0, 0, 2);
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        remove = Utility.createButton("/icons/remove.png");
        remove.setActionCommand(Consts.REMOVE_SORT);
        remove.setPreferredSize(new Dimension(16, 16));

        JButton info = Utility.createButton("/icons/info_rect.png");

        info.addActionListener(e -> {
            new InfoDialog(algorithm, algorithm.toString(), 600, 370);
        });
        info.setActionCommand(Consts.INFO);
        info.setPreferredSize(new Dimension(16, 16));
        GridBagConstraints gbc2 = (GridBagConstraints) gbc.clone();
        gbc2.gridx = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;

        add(remove, gbc);
        add(info, gbc2);
    }

    public void enableRemoveButton(boolean enable) {
        remove.setEnabled(enable);
    }

    public void setInfo(int accesses, int comparisons) {
        String info = "<html> <b>" +
                algorithm.toString() + ("</b> - ( ") + String.valueOf(comparisons)
                + (" comparisons | ") + String.valueOf(accesses) + (" accesses") + " ) </html>";

        leftBorder.setTitle(info);
    }

    public static int getMax(int values[]) {
        int max = 0;

        for (int i = 0; i < values.length; i++) {
            if (values[i] > max)
                max = values[i];
        }
        return max;
    }

    public void setDuration(int sec, int msec) {
        String durInfo = " in ".concat(String.valueOf(sec).concat(":")).concat(String.valueOf(msec)).concat(" sec.");
        leftBorder.setTitle(leftBorder.getTitle().concat(durInfo));
        repaint();
    }

}
