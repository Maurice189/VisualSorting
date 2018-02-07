package main;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelUI{
	
	private static int counter = 0,releasedID;
	private static LanguageFileXML langXML;
	
	private TitledBorder leftBorder;
	private JButton remove;
	private JPanel panel;
	private int ID;
	
	
	public PanelUI(ActionListener listener, JPanel panel, String selectedSort){
		
		
		ID = PanelUI.counter++;
		
		this.panel = panel;
		leftBorder = BorderFactory.createTitledBorder("");
		leftBorder.setTitleJustification(TitledBorder.ABOVE_TOP);
		leftBorder.setTitleColor(Color.darkGray);
		leftBorder.setTitleFont(Window.getComponentFont(12f));
		setInfo(selectedSort,0,0);

		
		panel.setBorder(leftBorder);
		manageButtons(listener);
	}
	
	public static void setLanguageFileXML(LanguageFileXML langXML){
		PanelUI.langXML = langXML;
	}
	
	private void manageButtons(final ActionListener listener){
		
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.insets = new Insets(-7, 0, 0, 2);
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		remove = new JButton();
		remove.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PanelUI.releasedID = ID;
				listener.actionPerformed(e);
			}
			
		});
		remove.setActionCommand(Statics.REMOVE_SORT);
		remove.setIcon(new ImageIcon(Statics.class.getResource("/resources/delete_visualsort_1.png")));
		remove.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/delete_visualsort_rollover_1.png")));
		remove.setBorder(BorderFactory.createEmptyBorder());
		remove.setPreferredSize(new Dimension(16,16));
		
		JButton info = new JButton();
		info.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PanelUI.releasedID = ID;
				listener.actionPerformed(e);
			}
			
		});
		info.setActionCommand(Statics.INFO);
		info.setIcon(new ImageIcon(Statics.class.getResource("/resources/info_visualsort_1.png")));
		info.setRolloverIcon(new ImageIcon(Statics.class.getResource("/resources/info_visualsort_rollover_1.png")));
		info.setBorder(BorderFactory.createEmptyBorder());
		info.setPreferredSize(new Dimension(16,16));
		GridBagConstraints gbc2 = (GridBagConstraints) gbc.clone();
		gbc2.gridx = 0;
		gbc2.weightx = 1;
		gbc2.weighty = 1;
		
		panel.add(remove,gbc);
		panel.add(info,gbc2);
	}
	
	public void enableRemoveButton(boolean enable){
		remove.setEnabled(enable);
	}

	public void setInfo(String info) {
		leftBorder.setTitle(info);
	}
	
	public void setInfo(String algoname,int accesses,int comparisons) {
		
		String info = 
		algoname+(" - ( ")+String.valueOf(comparisons)+(" ")+
		langXML.getValue("cmp")+(" | ")+String.valueOf(accesses)+(" ")+langXML.getValue("access")+" )";
		
		leftBorder.setTitle(info);

	}
	
	public void setDuration(int sec, int msec){
		
		String durInfo = " in ".concat(String.valueOf(sec).concat(":")).concat(String.valueOf(msec)).concat(" sec.");
		leftBorder.setTitle(leftBorder.getTitle().concat(durInfo));
		panel.repaint();
	}
	
	public void updateID(){
		if(ID > PanelUI.releasedID) ID--;
	}
	
	public static void updateCounter(){
		counter--;
	}
	
	public static int getReleasedID(){
		return PanelUI.releasedID;
	}
	
	public int getID(){
		return ID;
	}
}