package kochme.visualsorting.dialogs;

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

import kochme.visualsorting.ui.MainWindow;
import kochme.visualsorting.ui.ElementsCanvas;
import kochme.visualsorting.app.*;

/**
 * This class is responsible for editing the sorting list.
 * You can either set the number of elements that should contain the list, then
 * every value is randomly determined, or you can set every value invidually.
 *
 * @author Maurice Koch
 * @version BETA
 * @category Dialogs
 */

public class ElementEditorDialog extends OptionDialog implements ActionListener {

    private static int[] listOfElements;

    private DefaultListModel<Integer> listModel;
    private JSpinner nofValues;
    private JButton exit;
    private JRadioButton random, reversed, sorted;
    private ElementsCanvas svp;

    private static ElementEditorDialog instance;
    private final Controller controller;
    private static int width, height;

    private ElementEditorDialog(Controller controller, int width, int height) {
        super("Element Editor", width, height, false);
        this.controller = controller;
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
                tmp = Utils.getRandomSequence(l);
            }
            if (sorted.isSelected()) {
                tmp = Utils.getSortedSequence(l);
            }
            if (reversed.isSelected()) {
                tmp = Utils.getReversedSequence(l);
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
        listModel = new DefaultListModel<>();
        JList<Integer> elements = new JList<Integer>(listModel);

        for (int listOfElement : listOfElements) {
            listModel.addElement(listOfElement);
        }

        nofValues = new JSpinner();
        nofValues.setFont(MainWindow.getComponentFont(15f));
        setLayout(new GridBagLayout());

        JTabbedPane tp = new JTabbedPane();
        JScrollPane sp = new JScrollPane(elements);

        JButton update = new JButton("Update");
        update.addActionListener(this);
        ButtonGroup listTypeGroup = new ButtonGroup();

        svp = new ElementsCanvas(width, height);
        svp.setElements(listOfElements);
        svp.updateBarSize();
        svp.updatePanelSize();
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        nofValues.setValue(listModel.getSize());

        tp.addTab("Numeric List", sp);
        tp.addTab("Bar Graphic", svp);

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

        JPanel btnWrp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
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

        JPanel btnWrp2 = new JPanel();
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
    }

    public static void setElements(int[] elements) {
        ElementEditorDialog.listOfElements = new int[elements.length];
        System.arraycopy(elements, 0, ElementEditorDialog.listOfElements, 0, elements.length);
        System.out.println(ElementEditorDialog.listOfElements.length);
    }

    public static ElementEditorDialog getInstance(Controller controller, int width,
                                                  int height) {

        if (instance == null) {
            ElementEditorDialog.width = 470;
            ElementEditorDialog.height = 120;
            instance = new ElementEditorDialog(controller, width, height);
        }
        instance.setVisible(true);
        return instance;
    }


}
