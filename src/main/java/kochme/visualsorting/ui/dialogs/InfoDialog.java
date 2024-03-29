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
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import kochme.visualsorting.app.Constants.SortAlgorithm;

public class InfoDialog extends JDialog {

    private static HashMap<SortAlgorithm, String> infoPageRes;

    private JEditorPane manual;
    private JList<String> algorithmList;
    private final SortAlgorithm algorithm;

    public InfoDialog(SortAlgorithm SortAlgorithm, String title, int width,
                      int height) {

        this.algorithm = SortAlgorithm;

        setTitle(title);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        initComponents();
        setVisible(true);
    }

    private void setPage(SortAlgorithm algorithm) {
        java.net.URL helpURL = InfoDialog.class.getResource("/pages/".concat(infoPageRes.get(algorithm)));

        if (helpURL != null) {
            try {
                manual.setPage(helpURL);
                setTitle(algorithm.toString());
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        }
    }

    private void setPage(String infoPageTitle) {
        SortAlgorithm algorithm = SortAlgorithm.Bubblesort;

        if (infoPageTitle.endsWith("bubblesort.html")) {
            algorithm = SortAlgorithm.Bubblesort;
        } else if (infoPageTitle.endsWith("combsort.html")) {
            algorithm = SortAlgorithm.Combsort;
        } else if (infoPageTitle.endsWith("heapsort.html")) {
            algorithm = SortAlgorithm.Heapsort;
        } else if (infoPageTitle.endsWith("insertionsort.html")) {
            algorithm = SortAlgorithm.Insertionsort;
        }
        else if (infoPageTitle.endsWith("mergesort.html")) {
            algorithm = SortAlgorithm.Mergesort;
        } else if (infoPageTitle.endsWith("quicksort_fixed.html")) {
            algorithm = SortAlgorithm.Quicksort_FIXED;
        } else if (infoPageTitle.endsWith("quicksort_mo3.html")) {
            algorithm = SortAlgorithm.Quicksort_MO3;
        } else if (infoPageTitle.endsWith("quicksort_random.html")) {
            algorithm = SortAlgorithm.Quicksort_RANDOM;
        } else if (infoPageTitle.endsWith("shakersort.html")) {
            algorithm = SortAlgorithm.Shakersort;
        } else if (infoPageTitle.endsWith("shellsort.html")) {
            algorithm = SortAlgorithm.Shellsort;
        } else if (infoPageTitle.endsWith("selectionsort.html")) {
            algorithm = SortAlgorithm.Selectionsort;
        }
        algorithmList.setSelectedIndex(Arrays.asList(SortAlgorithm.values()).indexOf(algorithm));
        setPage(algorithm);
    }

    protected void initComponents() {
        setLayout(new BorderLayout());

        manual = new JEditorPane();
        manual.setFont(new Font("OpenSans-Regular", 0, 12));
        manual.setEditable(false);
        manual.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        manual.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String description = e.getDescription();
                if (description.startsWith("infopage")) {
                    setPage(description);
                } else {
                    try {
                        Desktop.getDesktop().browse(new URL(description).toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JScrollPane editorScrollPane = new JScrollPane(manual);
        editorScrollPane.setBorder(BorderFactory.createEtchedBorder());
        editorScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        algorithmList = new JList<>();
        algorithmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        algorithmList.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return SortAlgorithm.values().length;
            }

            @Override
            public Object getElementAt(int index) {
                return SortAlgorithm.values()[index].toString();
            }
        });
        algorithmList.addListSelectionListener(e -> {
            setPage(SortAlgorithm.values()[(algorithmList.getSelectedIndex())]);
        });

        algorithmList.setSelectedIndex(Arrays.asList(SortAlgorithm.values()).indexOf(algorithm));

        add(BorderLayout.WEST, algorithmList);
        add(BorderLayout.CENTER, editorScrollPane);

    }

    public static void initInfoPageResolver(
            HashMap<SortAlgorithm, String> infoPageRes) {
        InfoDialog.infoPageRes = infoPageRes;
    }
}
