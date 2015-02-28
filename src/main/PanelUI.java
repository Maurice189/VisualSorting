package main;
/*
VisualSorting
Copyright (C) 2014  Maurice Koch

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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