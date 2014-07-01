package OptionDialogs;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.Controller;
import main.Statics;


public class AboutDialog extends OptionDialog{

	public AboutDialog(Controller controller,int width, int height) {
		super(controller,"about", width, height);
		
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
