package OptionDialogs;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import main.Controller;
import main.Statics;


public class ManualDialog extends OptionDialog{

	public ManualDialog(Controller controller,String title,int width, int height) {
		super(controller,Statics.COMPONENT_TITLE.MANUAL, width, height);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateComponentsLabel() {
		super.updateComponentsLabel();
		
	}

	@Override
	protected void initComponents() {
		
		JEditorPane manual = new JEditorPane();
		manual.setEditable(false);

		java.net.URL helpURL = ManualDialog.class.getClassLoader().getResource(
				"resources/manual.html");
		if (helpURL != null) {
			try {
				manual.setPage(helpURL);
			} catch (IOException e) {
				System.err.println("Attempted to read a bad URL: " + helpURL);
			}
		} else {
			System.err.println("Couldn't find file: TextSamplerDemoHelp.html");
		}

		JScrollPane editorScrollPane = new JScrollPane(manual);
		editorScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));

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
		add(editorScrollPane);
		helpURL = ManualDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		
	}

}
