package OptionDialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Controller;
import main.Statics;

public class InfoDialog extends OptionDialog{
	
	private final static int SIZE = 5;
	
	private JPanel btnPanel;
	private JButton selAlg[],nextRight,nextLeft;

	public InfoDialog(Controller controller,String infoName,int width, int height) {
		super(controller,Statics.COMPONENT_TITLE.INFO, width, height);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == nextRight) {
		// test
		}
		
		else if (e.getSource() == nextLeft) {
			
		}
		
	}

	@Override
	protected void initComponents() {
		
		System.out.println("INFO");
		setLayout(new BorderLayout());

		final int length = Statics.SORT_ALGORITHMNS.length;
		
		JPanel btnPanel = new JPanel(new GridLayout(0,SIZE));
		JButton selAlg[] = new JButton[SIZE];
		nextLeft = new JButton("->");
		nextLeft.addActionListener(this);
		nextRight = new JButton("<-");
		nextRight.addActionListener(this);
		
		for(int i = 0; i<SIZE;i++){
			selAlg[i] = new JButton(Statics.SORT_ALGORITHMNS[i]);
			selAlg[i].setBorder(BorderFactory.createEmptyBorder());
			btnPanel.add(selAlg[i]);
			
		}
		
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
		
		add(BorderLayout.NORTH,btnPanel);
		add(BorderLayout.CENTER,editorScrollPane);
		
		setVisible(true);
		
		
	}

}
