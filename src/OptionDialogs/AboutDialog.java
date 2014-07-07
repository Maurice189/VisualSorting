package OptionDialogs;

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
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.Statics;


public class AboutDialog extends OptionDialog{

	public AboutDialog(int width, int height) {
		super("about", width, height);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void initComponents() {
		
		
		JLabel cpr = new JLabel();
		ImageIcon bg = null;
		
		cpr.setText("(C) 2014 M.Koch - ".concat(Statics.getVersion()).concat(" - Clicke here to visit on GitHub"));
		cpr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		cpr.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                   
	                 try {
						Desktop.getDesktop().browse(new URI("https://github.com/Maurice189/VisualSorting"));
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

			addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					dispose();

				}

				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub

				}

			});

			add(BorderLayout.CENTER, new JLabel(bg));
			add(BorderLayout.SOUTH, cpr);
			setSize(bg.getIconWidth(), bg.getIconHeight() + 65);
		}
	}

}
