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

import javax.swing.*;
import javax.swing.border.TitledBorder;

import kochme.visualsorting.ui.dialogs.InfoDialog;
import kochme.visualsorting.app.Constants;

/**
 * This class is used to display the animation in a panel. The animation is
 * based on bars with different heights. Each bar is representing a different
 * value in the sorting list.
 *
 * @author maurice koch
 * @version beta
 * @category graphics
 **/
public class FramedElementsCanvas extends ElementsCanvas {
    private final TitledBorder leftBorder;
    private final Constants.SortAlgorithm algorithm;
    private JButton remove;

    public FramedElementsCanvas(Constants.SortAlgorithm algorithm, int width, int height) {
        super(width, height);
        this.algorithm = algorithm;

        leftBorder = BorderFactory.createTitledBorder("");
        leftBorder.setTitleJustification(TitledBorder.ABOVE_TOP);
        leftBorder.setTitleColor(Color.darkGray);
        leftBorder.setTitleFont(MainWindow.getComponentFont(12f));
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
        remove = Utility.createButton("/icons/remove-rect.png");
        remove.setActionCommand(Constants.REMOVE_SORT);
        remove.setPreferredSize(new Dimension(16, 16));
        remove.setToolTipText("Remove.");

        JButton info = Utility.createButton("/icons/info-rect.png");

        info.addActionListener(e -> {
            new InfoDialog(algorithm, algorithm.toString(), 800, 500);
        });
        info.setActionCommand(Constants.INFO);
        info.setPreferredSize(new Dimension(16, 16));
        info.setToolTipText("Detailed information.");
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
        leftBorder.setTitle(info);
    }

    public void setDuration(int sec, int milliSeconds) {
        String durInfo = " in ".concat(String.valueOf(sec).concat(":")).concat(String.valueOf(milliSeconds)).concat(" sec.");
        leftBorder.setTitle(leftBorder.getTitle().concat(durInfo));
        repaint();
    }
}
