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
		"Visual Sorting - Copyright 2018, Maurice Koch");
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
		hyperlinkGNU.setText("Click here to visit https://opensource.org/licenses/MIT");
		 cpr.setText(
		 "\nPermission is hereby granted, free of charge, to any person obtaining a copy of\n" +
                 "this software and associated documentation files (the \"Software\"), to\n" +
                 "deal in the Software without restriction, including without limitation\n" +
                 "the rights to use, copy, modify, merge, publish, distribute, sublicense,\n" +
                 "and/or sell copies of the Software, and to permit persons to whom the\n" +
                 "Software is furnished to do so, subject to the following conditions:\n\n"+

		 "The above copyright notice and this permission notice shall be included\n" +
				 "in all copies or substantial portions of the Software.\n\n"+

		 "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND,\n" +
                 "EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE\n" +
                 "WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE\n" +
                 "AND NONINFRINGEMENT IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT\n" +
                 "HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,\n" +
                 "WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING\n" +
                 "FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE\n" +
                 "OR OTHER DEALINGS IN THE SOFTWARE.");
		
		hyperlinkGitHub.setCursor(new Cursor(Cursor.HAND_CURSOR));
		hyperlinkGitHub.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                   
	                 try {
						Desktop.getDesktop().browse(new URI("https://github.com/Maurice189/VisualSorting"));
					 } catch (IOException e2) {
						 e2.printStackTrace();
					 } catch ( URISyntaxException e1){}
	                   
	            }
	    });
		
		hyperlinkGNU.setCursor(new Cursor(Cursor.HAND_CURSOR));
		hyperlinkGNU.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                   
	                 try {
						Desktop.getDesktop().browse(new URI("https://opensource.org/licenses/MIT"));
					} catch (IOException e2) {
						e2.printStackTrace();
					} catch ( URISyntaxException e1){}

	            }
	    });
	    

		setLayout(new BorderLayout());

		java.net.URL helpURL = AboutDialog.class.getClassLoader().getResource(
				"resources/logo.png");
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
