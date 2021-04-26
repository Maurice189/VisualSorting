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
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import kochme.visualsorting.app.Consts;
import kochme.visualsorting.app.Consts.SortAlgorithm;
import kochme.visualsorting.app.Controller;
import kochme.visualsorting.app.InternalConfig;

public class MainWindow extends JFrame {
    private static Font componentFont = new Font("Monospace", Font.BOLD, 13);
    private static Font infoFont = new Font("Monospace", Font.BOLD, 43);

    public enum State {EMPTY, RUNNING, PAUSED, STOPPED}

    private JLabel algorithmInfo;
    private final String title;

    private JButton addAlgorithmBtn;
    private JButton nextInstructionBtn;
    private JButton resetBtn;
    private JButton listOfElementsBtn;
    private PlayPauseToggle playPauseToggle;
    private JPanel panelContainer;
    private final Controller controller;
    private JLabel centeredInfoLabel, executionTimeLabel, numberOfElementsLabel;
    private JComboBox<SortAlgorithm> algorithmSelection;

    private List<FramedElementsCanvas> vsPanel;

    public MainWindow(Controller controller, String title, int width, int height) {
        this.title = title;
        this.controller = controller;
        initComponents(width, height);
    }

    private void initComponents(int width, int height) {
        JMenuBar menuBar;

        centeredInfoLabel = new JLabel("Add some sort algorithm",
                JLabel.CENTER);
        centeredInfoLabel.setFont(infoFont);
        centeredInfoLabel.setForeground(Color.GRAY);

        executionTimeLabel = new JLabel();
        executionTimeLabel.setToolTipText("Execution time.");
        executionTimeLabel.setForeground(Color.black);
        executionTimeLabel.setIcon(IconLoader.getIcon("/icons/timer.png"));

        JCheckBoxMenuItem switchIntPause = new JCheckBoxMenuItem("Automatic pause enabled");
        switchIntPause.addActionListener(controller);
        switchIntPause.setActionCommand(Consts.AUTO_PAUSE);
        switchIntPause.setState(InternalConfig.isAutoPauseEnabled());

        JMenu programmFunctions = new JMenu("Options");

        // TODO : Add this again when fixed - programmFunctions.add(switchIntPause);

        numberOfElementsLabel = new JLabel();
        numberOfElementsLabel.setToolTipText("Number of elements in list.");
        numberOfElementsLabel.setForeground(Color.black);

        setTitle(title);
        setSize(width, height);
        vsPanel = new ArrayList<>();
        addWindowListener(controller);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        menuBar = new JMenuBar();

        Border border = BorderFactory.createEtchedBorder();
        Border margin = new EmptyBorder(5, 1, 10, 1);
        toolBar.setBorder(new CompoundBorder(border, margin));

        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.setBackground(bottomBar.getBackground().darker());
        bottomBar.setBorder(BorderFactory.createEtchedBorder());
        bottomBar.add(executionTimeLabel);
        bottomBar.add(Box.createHorizontalGlue());
        bottomBar.add(numberOfElementsLabel);

        JMenu settings = new JMenu("Settings");
        JMenu help = new JMenu("Help");
        JMenuItem list = new JMenuItem("List of elements");
        list.addActionListener(controller);
        list.setActionCommand(Consts.NEW_ELEMENTS);

        JMenuItem delay = new JMenuItem("Speed");
        delay.addActionListener(controller);
        delay.setActionCommand(Consts.DELAY);

        JMenuItem about = new JMenuItem("About " + title);
        about.addActionListener(controller);
        about.setActionCommand(Consts.ABOUT);

        help.add(about);
        settings.add(programmFunctions);
        settings.add(list);
        settings.add(delay);

        menuBar.add(settings);
        menuBar.add(help);
        setJMenuBar(menuBar);

        algorithmInfo = new JLabel();
        algorithmInfo.setForeground(Color.GRAY);
        algorithmSelection = new JComboBox<>();

        algorithmSelection.addItemListener(itemEvent -> {
            SortAlgorithm algorithm = (SortAlgorithm) algorithmSelection.getSelectedItem();

            if (algorithm == SortAlgorithm.Bogosort) {
                algorithmInfo.setText("<html><b>Bogo sort</b> - Generate random permutations until it finds on that sorted.</html>");
            } else if (algorithm == SortAlgorithm.Bubblesort) {
                algorithmInfo.setText("<html><b>Bubble sort</b> - Compare elements pairwise and perform swaps if necessary.</html>\"");
            } else if (algorithm == SortAlgorithm.Combsort) {
                algorithmInfo.setText("<html><b>Comb sort</b> - Improvement of bubble sort with dynamic gap sizes.</html>\"");
            } else if (algorithm == SortAlgorithm.Mergesort) {
                algorithmInfo.setText("<html><b>Merge sort</b> - Divide unsorted lists and repeatedly merge sorted sub-lists.</html>\"");
            } else if (algorithm == SortAlgorithm.Insertionsort) {
                algorithmInfo.setText("<html><b>Insertion sort</b> - In each iterations finds correct insert position of element</html>\"");
            } else if (algorithm == SortAlgorithm.Shakersort) {
                algorithmInfo.setText("<html><b>Shaker sort</b> - Bidirectional bubble sort, also known as cocktail sort.</html>\"");
            } else if (algorithm == SortAlgorithm.Selectionsort) {
                algorithmInfo.setText("<html><b>Selection sort</b> - Repeatedly finds the minimum element and append to sorted list.</html>\"");
            } else if (algorithm == SortAlgorithm.Shellsort) {
                algorithmInfo.setText("<html><b>Shell sort</b> - Generalization of insertion or bubble sort.</html>\"");
            } else if (algorithm == SortAlgorithm.Heapsort) {
                algorithmInfo.setText("<html><b>Heap sort</b> - Builds a heap and repeatedly extracts the current maximum.</html>\"");
            } else if (algorithm == SortAlgorithm.Quicksort_FIXED) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Partitioning of lists by choosing a fixed pivot element.</html>\"");
            } else if (algorithm == SortAlgorithm.Quicksort_RANDOM) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Partitioning of lists by choosing a random pivot element.</html>\"");
            } else if (algorithm == SortAlgorithm.Quicksort_MO3) {
                algorithmInfo.setText("<html><b>Quick sort</b> - Partitioning of lists by choosing a pivot element based on median of three.</html>\"");
            } else {
                algorithmInfo.setText("-");
            }
            int prefWidth = algorithmInfo.getPreferredSize().width;
            algorithmInfo.setMaximumSize(new Dimension(prefWidth, 70));
        });

        Arrays.stream(SortAlgorithm.values()).forEach(algorithm -> algorithmSelection.addItem(algorithm));
        algorithmSelection.setMaximumSize(new Dimension(300, 100));

        panelContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        getBackground(), 0, getHeight(),
                        getBackground().darker());
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panelContainer.setLayout(new BorderLayout());

        playPauseToggle = new PlayPauseToggle();
        playPauseToggle.setPlayIcon("/icons/start.png");
        playPauseToggle.setPauseIcon("/icons/pause.png");
        playPauseToggle.setState(PlayPauseToggle.State.PLAY);
        playPauseToggle.setToolTipText("Start/Pause sorting process.");
        playPauseToggle.addActionListener(controller);
        playPauseToggle.setActionCommand(Consts.START);

        addAlgorithmBtn = Utility.createButton("/icons/add.png");
        addAlgorithmBtn.setToolTipText("Add selected sort algorithm.");
        addAlgorithmBtn.addActionListener(controller);
        addAlgorithmBtn.setActionCommand(Consts.ADD_SORT);

        JButton adjustSpeedBtn = Utility.createButton("/icons/speed.png");
        adjustSpeedBtn.setToolTipText("Adjust the sorting speed.");
        adjustSpeedBtn.addActionListener(controller);
        adjustSpeedBtn.setActionCommand(Consts.DELAY);

        listOfElementsBtn = Utility.createButton("/icons/elements.png");
        listOfElementsBtn.setToolTipText("Edit elements in list.");
        listOfElementsBtn.addActionListener(controller);
        listOfElementsBtn.setActionCommand(Consts.NEW_ELEMENTS);

        nextInstructionBtn = Utility.createButton("/icons/next_instruction.png");
        nextInstructionBtn.setToolTipText("Execute playPauseToggle instruction.");
        nextInstructionBtn.addActionListener(controller);
        nextInstructionBtn.setActionCommand(Consts.NEXT_ITERATION);

        resetBtn = Utility.createButton("/icons/reset.png");
        resetBtn.setToolTipText("Reset to unsorted state.");
        resetBtn.addActionListener(controller);
        resetBtn.setActionCommand(Consts.RESET);

        panelContainer.add(BorderLayout.CENTER, centeredInfoLabel);

        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        Dimension size = new Dimension(separator.getPreferredSize().width,
                separator.getMaximumSize().height);
        separator.setMaximumSize(size);

        algorithmInfo.setToolTipText("Short description of currently selected sort algorithm.");
        algorithmInfo.setIcon(new ImageIcon(Consts.class.getResource("/icons/info_round.png")));
        algorithmInfo.setIconTextGap(10);

        toolBar.add(Box.createHorizontalStrut(3));
        toolBar.add(playPauseToggle);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(resetBtn);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(separator);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(nextInstructionBtn);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(adjustSpeedBtn);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(listOfElementsBtn);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(algorithmInfo);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(addAlgorithmBtn);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(algorithmSelection);
        toolBar.add(Box.createHorizontalStrut(3));

        java.net.URL icon = MainWindow.class.getResource(
                "/icons/icon.png");
        java.net.URL icon2x = MainWindow.class.getResource(
                "/icons/icon@2x.png");
        java.net.URL icon3x = MainWindow.class.getResource(
                "/icons/icon@3x.png");
        java.net.URL icon4x = MainWindow.class.getResource(
                "/icons/icon@4x.png");
        java.net.URL icon5x = MainWindow.class.getResource(
                "/icons/icon@5x.png");
        java.net.URL icon6x = MainWindow.class.getResource(
                "/icons/icon@6x.png");

        List<Image> icons = new ArrayList<Image>();

        icons.add(new ImageIcon(icon).getImage());
        icons.add(new ImageIcon(icon2x).getImage());
        icons.add(new ImageIcon(icon3x).getImage());
        icons.add(new ImageIcon(icon4x).getImage());
        icons.add(new ImageIcon(icon5x).getImage());
        icons.add(new ImageIcon(icon6x).getImage());

        setIconImages(icons);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(toolBar);
        add(panelContainer);
        add(bottomBar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setState(State.EMPTY);
    }

    public void setState(State state) {
        switch (state) {
            case EMPTY:
                resetBtn.setEnabled(false);
                playPauseToggle.setEnabled(false);
                nextInstructionBtn.setEnabled(false);
                listOfElementsBtn.setEnabled(true);
                addAlgorithmBtn.setEnabled(true);
                playPauseToggle.setState(PlayPauseToggle.State.PLAY);
                panelContainer.removeAll();
                panelContainer.setLayout(new BorderLayout());
                panelContainer.add(centeredInfoLabel);
                panelContainer.repaint();
                revalidate();
                repaint();
                break;
            case RUNNING:
                resetBtn.setEnabled(false);
                playPauseToggle.setEnabled(true);
                nextInstructionBtn.setEnabled(false);
                listOfElementsBtn.setEnabled(false);
                addAlgorithmBtn.setEnabled(false);
                vsPanel.forEach(v -> v.enableRemoveButton(false));
                playPauseToggle.setState(PlayPauseToggle.State.PAUSE);
                this.setTitle(title);
                break;
            case PAUSED:
                resetBtn.setEnabled(true);
                playPauseToggle.setEnabled(true);
                nextInstructionBtn.setEnabled(true);
                listOfElementsBtn.setEnabled(false);
                addAlgorithmBtn.setEnabled(false);
                vsPanel.forEach(v -> v.enableRemoveButton(false));
                playPauseToggle.setState(PlayPauseToggle.State.PLAY);
                this.setTitle(title.concat(" - Paused"));
                break;
            case STOPPED:
                resetBtn.setEnabled(true);
                playPauseToggle.setEnabled(true);
                nextInstructionBtn.setEnabled(false);
                listOfElementsBtn.setEnabled(true);
                addAlgorithmBtn.setEnabled(true);
                vsPanel.forEach(v -> v.enableRemoveButton(true));
                setExecutionTime(0);
                playPauseToggle.setState(PlayPauseToggle.State.PLAY);
                break;
        }
    }

    public void updateSize() {
        revalidate();
        vsPanel.forEach(ElementsCanvas::updateSize);
        repaint();
    }

    public void addSortVisualizationPanel(FramedElementsCanvas temp) {
        if (vsPanel.size() == 0) {
            panelContainer.remove(centeredInfoLabel);
            panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
            panelContainer.add(Box.createVerticalStrut(20));
        }

        vsPanel.add(temp);
        panelContainer.add(temp);
        revalidate();
    }

    public void removeSortVisualizationPanel(FramedElementsCanvas temp) {
        if (!vsPanel.contains(temp) || !panelContainer.isAncestorOf(temp)) {
            throw new IllegalArgumentException("Variable 'temp' was never added");
        }
        panelContainer.remove(temp);
        vsPanel.remove(temp);
        updateSize();
    }

    public void updateNumberOfElements(int nof) {
        numberOfElementsLabel.setText(String.valueOf(nof).concat(" ").concat("number of elements"));
    }

    public void setExecutionTime(int milliSeconds) {
        SimpleDateFormat dataFormat = new SimpleDateFormat("mm:ss:SSS");
        executionTimeLabel.setText(dataFormat.format(new Date(milliSeconds)));
    }

    public SortAlgorithm getSelectedSort() {
        return (SortAlgorithm) algorithmSelection.getSelectedItem();
    }

    public static void setComponentFont(String source) {
        try {
            InputStream in = MainWindow.class.getResourceAsStream(source);
            if (in != null) {
                componentFont = Font.createFont(Font.TRUETYPE_FONT, in);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static Font getComponentFont(float size) {
        return componentFont.deriveFont(size);
    }

    public static void setInfoFont(String source, float size) {
        try {
            InputStream in = MainWindow.class.getResourceAsStream(source);
            if (in != null) {
                infoFont = Font.createFont(Font.TRUETYPE_FONT, in);
                infoFont = infoFont.deriveFont(size);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
