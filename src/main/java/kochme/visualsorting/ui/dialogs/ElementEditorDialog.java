package kochme.visualsorting.ui.dialogs;

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
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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

public class ElementEditorDialog extends JDialog implements ActionListener {

    private static int[] listOfElements;

    private DefaultListModel<Integer> listModel;
    private JSpinner nofValues;
    private JButton exit;
    private JRadioButton random, reversed, sorted;
    private ElementsCanvas previewCanvas;

    private static ElementEditorDialog instance;
    private final Controller controller;
    private static int width, height;

    private ElementEditorDialog(Controller controller, int width, int height) {
        this.controller = controller;

        setTitle("Elements Editor");
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initComponents();
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
                tmp = Utility.getRandomSequence(l);
            }
            if (sorted.isSelected()) {
                tmp = Utility.getSortedSequence(l);
            }
            if (reversed.isSelected()) {
                tmp = Utility.getReversedSequence(l);
            }

            for (int i = 0; i < l; i++) {
                listModel.addElement(tmp[i]);
            }

            controller.elementsChanged(tmp);
            previewCanvas.setElements(tmp);
            previewCanvas.updateBarSize();
            previewCanvas.updatePanelSize();
            revalidate();
            repaint();

        }
    }

    protected void initComponents() {
        listModel = new DefaultListModel<>();
        JList<Integer> elements = new JList<>(listModel);

        for (int listOfElement : listOfElements) {
            listModel.addElement(listOfElement);
        }

        //SpinnerNumberModel model1 = new SpinnerNumberModel(5, 0, 256, 1);
        nofValues = new JSpinner();
        //nofValues.setFont(MainWindow.getComponentFont(15f));
        setLayout(new GridBagLayout());

        JTabbedPane tabs = new JTabbedPane();
        JScrollPane elementScrollList = new JScrollPane(elements);

        JButton updateBtn = new JButton("Set");
        updateBtn.addActionListener(this);
        ButtonGroup listTypeGroup = new ButtonGroup();

        previewCanvas = new ElementsCanvas(width, height);
        previewCanvas.setElements(listOfElements);
        previewCanvas.updateBarSize();
        previewCanvas.updatePanelSize();
        elementScrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        nofValues.setValue(listModel.getSize());

        tabs.addTab("List of Elements", elementScrollList);
        tabs.addTab("Bar Preview", previewCanvas);

        GridBagConstraints tabsConstraints = new GridBagConstraints();
        tabsConstraints.fill = GridBagConstraints.BOTH;
        tabsConstraints.gridx = 0;
        tabsConstraints.gridy = 0;
        tabsConstraints.gridwidth = 4;
        tabsConstraints.gridheight = 3;
        tabsConstraints.weightx = 1;
        tabsConstraints.weighty = 7;

        random = new JRadioButton("Random");
        random.addActionListener(this);
        random.setSelected(true);

        sorted = new JRadioButton("Ascending");
        sorted.addActionListener(this);

        reversed = new JRadioButton("Descending");
        reversed.addActionListener(this);

        listTypeGroup.add(random);
        listTypeGroup.add(sorted);
        listTypeGroup.add(reversed);

        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.X_AXIS));
        //TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Settings");
        Border tb = BorderFactory.createLineBorder(Color.GRAY);

        orderPanel.setBorder(tb);
        orderPanel.add(Box.createHorizontalStrut(5));
        orderPanel.add(new JLabel("Number of elements:"));
        orderPanel.add(Box.createHorizontalStrut(5));
        orderPanel.add(nofValues);
        orderPanel.add(Box.createHorizontalGlue());
        orderPanel.add(random);
        orderPanel.add(sorted);
        orderPanel.add(reversed);

        JPanel nofPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TitledBorder tb1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Number of Elements");
        nofPanel.setBorder(tb1);

        GridBagConstraints orderConstraints = new GridBagConstraints();
        orderConstraints.fill = GridBagConstraints.HORIZONTAL;
        orderConstraints.gridx = 0;
        orderConstraints.gridy = 4;
        orderConstraints.gridwidth = 4;
        orderConstraints.gridheight = 1;
        orderConstraints.weightx = 1;
        orderConstraints.weighty = 1;
        orderConstraints.anchor = GridBagConstraints.NORTH;
        orderConstraints.ipadx = 15;
        orderConstraints.ipady = 15;
        orderConstraints.insets = new Insets(5, 4, 10, 4);

        exit = new JButton("Exit");
        exit.setActionCommand(Constants.ELEMENTS_SET);
        exit.addActionListener(this);

        JPanel elementPanel = new JPanel();
        elementPanel.setLayout(new BoxLayout(elementPanel, BoxLayout.X_AXIS));

        elementPanel.add(updateBtn);
        elementPanel.add(Box.createHorizontalGlue());
        elementPanel.add(exit);

        GridBagConstraints elementConstraints = new GridBagConstraints();
        elementConstraints.fill = GridBagConstraints.HORIZONTAL;
        elementConstraints.gridx = 0;
        elementConstraints.gridy = 5;
        elementConstraints.gridwidth = 4;
        elementConstraints.gridheight = 1;
        elementConstraints.weightx = 1;
        elementConstraints.weighty = 1;
        elementConstraints.anchor = GridBagConstraints.SOUTH;
        elementConstraints.insets = new Insets(4, 4, 4, 4);

        add(tabs, tabsConstraints);
        add(orderPanel, orderConstraints);
        add(elementPanel, elementConstraints);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                previewCanvas.updateSize();
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
