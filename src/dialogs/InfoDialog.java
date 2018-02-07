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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Statics;
import main.Window;
import main.Statics.SORTALGORITHMS;

public class InfoDialog extends OptionDialog {

	private final static int SIZE = 3, CENTER_PLUS = 1;
	private final static Font NFONT_SIZE = Window.getComponentFont(14f),
			SFONT_SIZE = Window.getComponentFont(10f);

	private static HashMap<SORTALGORITHMS, String> infoPageRes;

	private JEditorPane manual;
	private JPanel btnPanel;
	private JLabel selAlg[];
	private JButton nextRight, nextLeft;
	private int currentIndex = 0, activeIndex;
	private JScrollPane editorScrollPane;

	/**
	 * 
	 * @param sortAlgorithms
	 *            a enumertion list of all existing sort algoritms, is used for
	 *            displaying
	 * @param title
	 *            {@inheritDoc}
	 * @param width
	 *            {@inheritDoc}
	 * @param height
	 *            {@inheritDoc}
	 */
	public InfoDialog(SORTALGORITHMS sortAlgorithms, String title, int width,
			int height) {

		super();
		

		final JLabel loadGif = new JLabel(new ImageIcon(
				Statics.class.getResource("/resources/loading.gif")),
				JLabel.CENTER);
		this.activeIndex = sortAlgorithms.ordinal();

		setTitle(title);
		setSize(width, height);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		add(loadGif);
		setVisible(true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				
				long t = System.currentTimeMillis();
				initComponents();
				//System.out.println("NEEDED TIME : "+(System.currentTimeMillis()-t));
				remove(loadGif);
			
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						setVisible(true);
					}
				});

			}

		}).start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nextRight) {

			btnPanel.removeAll();
			btnPanel.add(nextLeft);
			btnPanel.add(Box.createHorizontalGlue());

			if (activeIndex < currentIndex + CENTER_PLUS)
				activeIndex++;

			else if (currentIndex + SIZE < SORTALGORITHMS.length()) {
				currentIndex++;
				activeIndex = currentIndex + CENTER_PLUS;
			} else if (activeIndex < SORTALGORITHMS.length() - 1)
				activeIndex++;

			for (int i = currentIndex; i < currentIndex + SIZE; i++) {

				if (i != activeIndex) {
					selAlg[i].setEnabled(false);
					selAlg[i].setFont(SFONT_SIZE);
				} else {
					selAlg[i].setEnabled(true);
					selAlg[i].setFont(NFONT_SIZE);
				}
				btnPanel.add(selAlg[i]);
				btnPanel.add(Box.createHorizontalGlue());

			}

			setPage(activeIndex);

			btnPanel.add(nextRight);
			revalidate();
			repaint();

		}

		else if (e.getSource() == nextLeft) {

			if (activeIndex > currentIndex + CENTER_PLUS)
				activeIndex--;
			else if (currentIndex > 0) {
				currentIndex--;
				activeIndex = currentIndex + CENTER_PLUS;
			} else if (activeIndex > 0)
				activeIndex--;

			btnPanel.removeAll();
			btnPanel.add(nextLeft);
			btnPanel.add(Box.createHorizontalGlue());
			for (int i = currentIndex; i < currentIndex + SIZE; i++) {

				if (i != activeIndex) {
					selAlg[i].setEnabled(false);
					selAlg[i].setFont(SFONT_SIZE);
				} else {
					selAlg[i].setEnabled(true);
					selAlg[i].setFont(NFONT_SIZE);
				}
				btnPanel.add(selAlg[i]);
				btnPanel.add(Box.createHorizontalGlue());

			}
			setPage(activeIndex);

			btnPanel.add(nextRight);
			revalidate();
			repaint();
		}

	}

	/**
	 * The value of the index in SORTALGORITHMS enumertion resolved into the
	 * respective html-file path.
	 * 
	 * @see initInfoPageResolver(HashMap<SORTALGORITHMS,String> infoPageRes)
	 * 
	 * @param index
	 *            the page of the index in the enumertionlist of SORTALGORITHMS
	 *            is shown
	 */
	private void setPage(int index) {

		java.net.URL helpURL = InfoDialog.class.getClassLoader()
				.getResource(
						"resources/".concat(infoPageRes.get(SORTALGORITHMS
								.values()[index])));
		
		if (helpURL != null) {
			try {
				manual.setPage(helpURL);
				setTitle(SORTALGORITHMS.values()[index].toString());
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		}

	}

	@Override
	protected void initComponents() {

		setLayout(new BorderLayout());

		final int length = SORTALGORITHMS.length();

		btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		selAlg = new JLabel[length];
		nextLeft = new JButton(new ImageIcon(
				Statics.class
						.getResource("/resources/next_left_visualsort.png")));
		nextLeft.setRolloverIcon(new ImageIcon(Statics.class
				.getResource("/resources/next_left_visualsort_rollover.png")));
		nextLeft.setRolloverEnabled(true);
		nextLeft.addActionListener(this);
		nextLeft.setBorder(BorderFactory.createEmptyBorder());
		nextLeft.setOpaque(false);
		nextLeft.setContentAreaFilled(false);
		nextLeft.setBorderPainted(false);

		nextRight = new JButton(new ImageIcon(
				Statics.class
						.getResource("/resources/next_right_visualsort.png")));
		nextRight.setRolloverIcon(new ImageIcon(Statics.class
				.getResource("/resources/next_right_visualsort_rollover.png")));
		nextRight.setRolloverEnabled(true);
		nextRight.setBorder(BorderFactory.createEmptyBorder());
		nextRight.setOpaque(false);
		nextRight.setContentAreaFilled(false);
		nextRight.setBorderPainted(false);
		nextRight.addActionListener(this);

		btnPanel.add(nextLeft);
		btnPanel.add(Box.createHorizontalGlue());
		currentIndex = activeIndex - CENTER_PLUS;
		if (currentIndex < 0)
			currentIndex = 0;
		else if (currentIndex + SIZE > SORTALGORITHMS.length())
			currentIndex = SORTALGORITHMS.length() - SIZE;

		for (int i = 0; i < length; i++) {
			selAlg[i] = new JLabel(SORTALGORITHMS.values()[i].toString());
			if (i != activeIndex) {
				selAlg[i].setEnabled(false);
				selAlg[i].setFont(SFONT_SIZE);
			} else
				selAlg[i].setFont(NFONT_SIZE);

			if (i >= currentIndex && i < currentIndex + SIZE) {
				btnPanel.add(selAlg[i]);
				btnPanel.add(Box.createHorizontalGlue());
			}

		}
		

		btnPanel.add(nextRight);

		manual = new JEditorPane();
		manual.setEditable(false);
		manual.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		setPage(activeIndex);

		editorScrollPane = new JScrollPane(manual);
		editorScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		editorScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

		add(BorderLayout.NORTH, btnPanel);
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
			HashMap<SORTALGORITHMS, String> infoPageRes) {
		InfoDialog.infoPageRes = infoPageRes;

	}

}
