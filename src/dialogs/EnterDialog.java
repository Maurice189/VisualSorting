package dialogs;

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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import main.*;
import gui.SortVisualisationPanel;
import gui.Window;

/**
 * This class is responsible for editing the sorting list.
 * You can either set the number of elements that should contain the list, then
 * every value is randomly determined, or you can set every value invidually.
 *
 * @author Maurice Koch
 * @version BETA
 * @category Dialogs
 */

public class EnterDialog extends OptionDialog implements ActionListener {

    private DefaultListModel<Integer> listModel;
    private JList<Integer> elements;
    private JSpinner nofValues;
    private JButton update, exit;
    private ButtonGroup listTypeGroup;
    private JRadioButton random, reversed, sorted;
    private SortVisualisationPanel svp;
    private JPanel btnWrp1, btnWrp2;

    private static EnterDialog instance;
    private Controller controller;
    private static int width, height;

    private EnterDialog(Controller controller, int width, int height) {
        super("sortlist", width, height, false);
        this.controller = controller;
    }

    public int getRandomNumber(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }


    private int[] getRandomSequence(int n) {
        List<Integer> s1 = new LinkedList<>();
        int[] randomSequence = new int[n];

        for (int i = 0; i < n; i++) {
            s1.add(i);
        }

        for (int i = 0; i < n; i++) {
            int r = getRandomNumber(0, n - i);
            randomSequence[i] = s1.remove(r);
        }

        return randomSequence;
    }

    private int[] getSortedSequence(int n) {
        int[] sortedSequence = new int[n];

        for (int i = 0; i < n; i++) {
            sortedSequence[i] = i;
        }

        return sortedSequence;
    }

    private int[] getReversedSequence(int n) {
        int[] reversedSequence = new int[n];

        for (int i = 0; i < n; i++) {
            reversedSequence[i] = n - 1 - i;
        }

        return reversedSequence;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exit) {
            controller.actionPerformed(e);
            this.dispose();
        } else {
            int l = (int) (nofValues.getValue());
            listModel.removeAllElements();

            int[] tmp = new int[l];

            if (random.isSelected()) {
                tmp = getRandomSequence(l);
            }
            if (sorted.isSelected()) {
                tmp = getSortedSequence(l);
            }
            if (reversed.isSelected()) {
                tmp = getReversedSequence(l);
            }

            for (int i = 0; i < l; i++) {
                listModel.addElement(tmp[i]);
            }

            controller.elementsChanged(tmp);
            svp.setElements(tmp);
            svp.updateBarSize();
            svp.updatePanelSize();
            revalidate();
            repaint();

        }

    }

    @Override
    protected void initComponents() {
        listModel = new DefaultListModel<Integer>();
        elements = new JList<Integer>(listModel);
        nofValues = new JSpinner();
        nofValues.setFont(Window.getComponentFont(15f));
        setLayout(new GridBagLayout());

        JTabbedPane tp = new JTabbedPane();
        JScrollPane sp = new JScrollPane(elements);

        update = new JButton("Update");
        update.addActionListener(this);
        listTypeGroup = new ButtonGroup();

        svp = new SortVisualisationPanel(Consts.SortAlgorithm.Quicksort_MO3, width, height, null);
        svp.setElements(InternalConfig.getElements());
        svp.updateBarSize();
        svp.updatePanelSize();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        int tempElements[] = InternalConfig.getElements();
        for (int i = 0; i < tempElements.length; i++) {
            listModel.addElement(new Integer(tempElements[i]));
        }
        nofValues.setValue(listModel.getSize());

        tp.addTab("List", sp);
        tp.addTab("Graphic", svp);

        GridBagConstraints tcnt = new GridBagConstraints();
        tcnt.fill = GridBagConstraints.BOTH;
        tcnt.gridx = 0;
        tcnt.gridy = 0;
        tcnt.gridwidth = 4;
        tcnt.gridheight = 3;
        tcnt.weightx = 1;
        tcnt.weighty = 7;

        random = new JRadioButton("Random");
        random.addActionListener(this);
        random.setSelected(true);

        sorted = new JRadioButton("Sorted");
        sorted.addActionListener(this);

        reversed = new JRadioButton("Reversed");
        reversed.addActionListener(this);

        listTypeGroup.add(random);
        listTypeGroup.add(sorted);
        listTypeGroup.add(reversed);

        btnWrp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Selection");

        btnWrp1.setBorder(tb);
        btnWrp1.add(random);
        btnWrp1.add(sorted);
        btnWrp1.add(reversed);

        GridBagConstraints btnWrpc1 = new GridBagConstraints();
        btnWrpc1.fill = GridBagConstraints.HORIZONTAL;
        btnWrpc1.gridx = 0;
        btnWrpc1.gridy = 4;
        btnWrpc1.gridwidth = 4;
        btnWrpc1.gridheight = 1;
        btnWrpc1.weightx = 1;
        btnWrpc1.weighty = 1;
        btnWrpc1.anchor = GridBagConstraints.NORTH;
        btnWrpc1.insets = new Insets(4, 4, 4, 4);

        exit = new JButton("Exit");
        exit.setActionCommand(Consts.ELEMENTS_SET);
        exit.addActionListener(this);

        btnWrp2 = new JPanel();
        btnWrp2.setLayout(new BoxLayout(btnWrp2, BoxLayout.X_AXIS));

        btnWrp2.add(update);
        btnWrp2.add(Box.createHorizontalStrut(10));
        btnWrp2.add(nofValues);
        btnWrp2.add(Box.createHorizontalGlue());
        btnWrp2.add(exit);

        GridBagConstraints btnWrpc2 = new GridBagConstraints();
        btnWrpc2.fill = GridBagConstraints.HORIZONTAL;
        btnWrpc2.gridx = 0;
        btnWrpc2.gridy = 5;
        btnWrpc2.gridwidth = 4;
        btnWrpc2.gridheight = 1;
        btnWrpc2.weightx = 1;
        btnWrpc2.weighty = 1;
        btnWrpc2.anchor = GridBagConstraints.SOUTH;
        btnWrpc2.insets = new Insets(4, 4, 4, 4);

        add(tp, tcnt);
        add(btnWrp1, btnWrpc1);
        add(btnWrp2, btnWrpc2);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                svp.updateSize();
            }
        });

        //setResizable(false);

    }

    public static EnterDialog getInstance(Controller controller, int width,
                                          int height) {

        if (instance == null) {
            EnterDialog.width = 470;
            EnterDialog.height = 120;
            instance = new EnterDialog(controller, width, height);
        }

        instance.setVisible(true);
        return instance;
    }


}
