package OptionDialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Controller;
import main.Statics;

public class InfoDialog extends OptionDialog{
	
	private final static int SIZE = 3;
	
	private JPanel btnPanel;
	private JLabel selAlg[];
	private JButton nextRight,nextLeft;
	private int currentIndex = 0;

	public InfoDialog(Controller controller,String infoName,int width, int height) {
		super(controller,Statics.COMPONENT_TITLE.INFO, width, height);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == nextRight) {
			
			
				if(currentIndex+SIZE < Statics.SORT_ALGORITHMNS.length){
					
					btnPanel.removeAll();
					btnPanel.add(nextLeft);
					
					if(currentIndex+SIZE == Statics.SORT_ALGORITHMNS.length){
						selAlg[currentIndex-1].setEnabled(false);
						selAlg[currentIndex].setEnabled(false);
						selAlg[currentIndex+1].setEnabled(true);
						btnPanel.add(selAlg[currentIndex-1]);
						btnPanel.add(selAlg[currentIndex]);
						btnPanel.add(selAlg[currentIndex+1]);
					}
					else{
					
					
						for(int i = currentIndex;i<currentIndex+SIZE;i++){
						
							if(i!=currentIndex+1)selAlg[i].setEnabled(false);
							else 				 selAlg[i].setEnabled(true);
							btnPanel.add(selAlg[i]);
						
						}
					
						currentIndex++;
					}
					
					btnPanel.add(nextRight);
					revalidate();
					repaint();
				}
				
		}
		
		else if (e.getSource() == nextLeft) {
			
			
			if(currentIndex > 0){
				
				btnPanel.removeAll();
				currentIndex--;
				btnPanel.add(nextLeft);
				for(int i = currentIndex; i<currentIndex+SIZE;i++){
					
					if(i!=currentIndex+1)selAlg[i].setEnabled(false);
					else 				 selAlg[i].setEnabled(true);
					btnPanel.add(selAlg[i]);
					
				}
				
				btnPanel.add(nextRight);
				revalidate();
				repaint();
			}
		}
		
	}

	@Override
	protected void initComponents() {
		
		setLayout(new BorderLayout());

		final int length = Statics.SORT_ALGORITHMNS.length;
		
		btnPanel = new JPanel(new GridLayout(0,SIZE+2));
		selAlg = new JLabel[length];
		nextLeft = new JButton("<-");
		nextLeft.addActionListener(this);
		nextLeft.setBorder(BorderFactory.createEmptyBorder());
		nextRight = new JButton("->");
		nextRight.setBorder(BorderFactory.createEmptyBorder());
		nextRight.addActionListener(this);
		
		btnPanel.add(nextLeft);
		for(int i = 0; i<length;i++){
			selAlg[i] = new JLabel(Statics.SORT_ALGORITHMNS[i]);
			if(i!=currentIndex+1)selAlg[i].setEnabled(false);
			if(i<SIZE)btnPanel.add(selAlg[i]);
			
		}
		
		btnPanel.add(nextRight);
		
		JEditorPane manual = new JEditorPane();
		manual.setEditable(false);

		java.net.URL helpURL = InfoDialog.class.getClassLoader().getResource(
				"resources/infopage_qsort.html");
		if (helpURL != null) {
			try {
				manual.setPage(helpURL);
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		} else {
			System.err.println("Couldn't find file: infopage_qsort.html");
		}

		JScrollPane editorScrollPane = new JScrollPane(manual);
		editorScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		
		add(BorderLayout.NORTH,btnPanel);
		add(BorderLayout.CENTER,editorScrollPane);
		
		setVisible(true);
		
		
	}

}
