package OptionDialogs;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.Controller;
import main.Statics;


public class InfoDialog extends OptionDialog{

	public InfoDialog(Controller controller,int width, int height) {
		super(controller,Statics.COMPONENT_TITLE.ABOUT, width, height);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComponentsLabel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initComponents() {
		
		
		JLabel cpr = new JLabel("©2014 M.Koch - ".concat(Statics.getVersion()));
		ImageIcon bg = null;

		cpr.setFont(Statics.getDefaultFont(12f));
		setLayout(new BorderLayout());

		java.net.URL helpURL = InfoDialog.class.getClassLoader().getResource(
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
			helpURL = InfoDialog.class.getClassLoader().getResource(
					"resources/frameIcon2.png");
			if (helpURL != null) {
				setIconImage(new ImageIcon(helpURL).getImage());
			}
			setSize(bg.getIconWidth(), bg.getIconHeight() + 65);
		}
	}

}
