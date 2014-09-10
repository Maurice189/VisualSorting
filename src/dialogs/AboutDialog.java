package dialogs;

/*
Visualsorting
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

/**
 * This class is responsible for displaying the another dialog.
 * The 'AboutDialog' is just used for informations about the author as well as the applications.
 * 
 * 
 * @author Maurice Koch
 * @category Dialogs
 * @version BETA
 * 
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import main.Window;


public class AboutDialog extends OptionDialog{
	
	
	private static AboutDialog instance;

	public AboutDialog(int width, int height) {
		super("about", width, height);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void initComponents() {
		
		
		JLabel hyperlinkGitHub = new JLabel(),hyperlinkGNU = new JLabel();
		JTextArea cpr = new JTextArea();
		JPanel hyperlinks = new JPanel(new GridLayout(2,0));
		TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
		"Visual Sorting - Copyright 2014 by Maurice Koch");
		tb.setTitleFont(Window.getComponentFont(10f));
		
		cpr.setFont(Window.getComponentFont(10f));
		cpr.setBackground(UIManager.getColor("Panel.background"));
		cpr.setEditable(false);
		hyperlinks.setBorder(BorderFactory.createEmptyBorder(0, 6, 2, 0));
		cpr.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(11, 5, 5, 5),tb));
		hyperlinkGitHub.setFont(Window.getComponentFont(10f));
		hyperlinkGNU.setFont(Window.getComponentFont(10f));
		ImageIcon bg = null;
		hyperlinkGitHub.setText("Click here to visit on GitHub (for comments/bug report/etc.)");
		hyperlinkGNU.setText("Click here to visit http://www.gnu.org/licenses/");
		 cpr.setText(
		 "\nThis program is free software: you can redistribute it and/or modify\n"+
		 "it under the terms of the GNU General Public License as published by\n"+
		 "the Free Software Foundation, either version 3 of the License, or\n"+
		 "(at your option) any later version.\n"+

		 "This program is distributed in the hope that it will be useful,\n"+
		 "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"+
		 "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n"+
		 "GNU General Public License for more details.\n"+

		 "You should have received a copy of the GNU General Public License\n"+
		 "along with this program.  If not, see http://www.gnu.org/licenses/");
		
		hyperlinkGitHub.setCursor(new Cursor(Cursor.HAND_CURSOR));
		hyperlinkGitHub.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                   
	                 try {
						Desktop.getDesktop().browse(new URI("https://github.com/Maurice189/VisualSorting"));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
	                   
	            }
	    });
		
		hyperlinkGNU.setCursor(new Cursor(Cursor.HAND_CURSOR));
		hyperlinkGNU.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                   
	                 try {
						Desktop.getDesktop().browse(new URI("http://www.gnu.org/licenses/"));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
	                   
	            }
	    });
	    

		setLayout(new BorderLayout());

		java.net.URL helpURL = AboutDialog.class.getClassLoader().getResource(
				"resources/VisualSorting_Logo_small_transparent.png");
		if (helpURL != null) {
			bg = new ImageIcon(helpURL);

			addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					dispose();

				}

			});
			
			hyperlinks.add(hyperlinkGNU);
			hyperlinks.add(hyperlinkGitHub);

			add(BorderLayout.PAGE_START,new JLabel(bg));
			add(BorderLayout.CENTER,cpr);
			add(BorderLayout.SOUTH,hyperlinks);
			setResizable(false);
		}
	}
	
	public static AboutDialog getInstance(int width,
			int height) {

		if (instance == null)
			instance = new AboutDialog(width, height);

		instance.setVisible(true);
		return instance;
	}

}
