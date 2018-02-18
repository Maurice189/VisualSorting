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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import main.Statics;
import main.Window;
import main.Statics.SortAlgorithm;

public class InfoDialog extends OptionDialog {


	private static HashMap<SortAlgorithm, String> infoPageRes;
    private static HashMap<SortAlgorithm, String> toTitle;

    private JEditorPane manual;
	private JScrollPane editorScrollPane;
	private JList<String> algorithmList;
	private SortAlgorithm algorithm;

	/**
	 * 
	 * @param SortAlgorithm
	 *            a enumertion list of all existing sort algoritms, is used for
	 *            displaying
	 * @param title
	 *            {@inheritDoc}
	 * @param width
	 *            {@inheritDoc}
	 * @param height
	 *            {@inheritDoc}
	 */
	public InfoDialog(SortAlgorithm SortAlgorithm, String title, int width,
			int height) {

		super();

		final JLabel loadGif = new JLabel(new ImageIcon(
				Statics.class.getResource("/resources/icons/loading.gif")),
				JLabel.CENTER);
		this.algorithm = SortAlgorithm;

		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		add(loadGif);
		setVisible(true);

		new Thread(() -> {
            initComponents();
            remove(loadGif);

            EventQueue.invokeLater(() -> setVisible(true));

        }).start();

	}

	private void setPage(SortAlgorithm algorithm) {

        java.net.URL helpURL = InfoDialog.class.getClassLoader()
                .getResource("resources/pages/".concat(infoPageRes.get(algorithm)));

        if (helpURL != null) {
            try {
                manual.setPage(helpURL);
                setTitle(toTitle.get(algorithm));
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        }
	}

    private void setPage(String infoPageTitle) {
        SortAlgorithm algorithm = SortAlgorithm.Bogosort;

        if (infoPageTitle.endsWith("bogosort.html")) {
            algorithm = SortAlgorithm.Bogosort;
        } else if (infoPageTitle.endsWith("bubblesort.html")) {
            algorithm = SortAlgorithm.Bubblesort;
        } else if (infoPageTitle.endsWith("combsort.html")) {
            algorithm = SortAlgorithm.Combsort;
        } else if (infoPageTitle.endsWith("heapsort.html")) {
            algorithm = SortAlgorithm.Heapsort;
        } else if (infoPageTitle.endsWith("insertionsort.html")) {
            algorithm = SortAlgorithm.Insertionsort;
        } else if (infoPageTitle.endsWith("introsort.html")) {
            algorithm = SortAlgorithm.Introsort;
        } else if (infoPageTitle.endsWith("mergesort.html")) {
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
        }
        setPage(algorithm);
    }

	@Override
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

		editorScrollPane = new JScrollPane(manual);
        editorScrollPane.setBorder(BorderFactory.createEtchedBorder());
        editorScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        algorithmList = new JList<>();
        List<SortAlgorithm> ids = new ArrayList<>(infoPageRes.keySet());
        ids.sort(Comparator.comparing(o -> toTitle.get(o)));

        algorithmList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        algorithmList.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return ids.size();
            }

            @Override
            public Object getElementAt(int index) {
                return toTitle.get(ids.get(index));
            }
        });
        algorithmList.addListSelectionListener(e -> {
            System.out.println(algorithmList.getSelectedIndex());
            setPage(ids.get(algorithmList.getSelectedIndex()));
        });
        algorithmList.setSelectedIndex(ids.indexOf(algorithm));

        add(BorderLayout.WEST, algorithmList);
		add(BorderLayout.CENTER, editorScrollPane);

	}

	/**
	 * 
	 * HashMap: key = 'name of sort algorithm' value = 'path of the respective
	 * html-file'
	 * 
	 * @param infoPageRes
	 *            map for resolving each sort algorithm into the respective
	 *            html-file path
	 */
	public static void initInfoPageResolver(
			HashMap<SortAlgorithm, String> infoPageRes) {
		InfoDialog.infoPageRes = infoPageRes;

	}

    public static void initTitleResolver (
            HashMap<SortAlgorithm, String> toTitle) {
        InfoDialog.toTitle = toTitle;

    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
