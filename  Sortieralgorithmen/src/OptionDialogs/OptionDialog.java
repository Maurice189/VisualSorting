package OptionDialogs;

import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import main.Controller;
import main.Statics;



public abstract class OptionDialog extends JDialog implements WindowListener,ActionListener{

	
	private static final long serialVersionUID = 1L;
	protected Controller controller;
	protected Statics.COMPONENT_TITLE title;
	
	public OptionDialog(Controller controller,Statics.COMPONENT_TITLE title,int width, int height) {
		
		this.controller = controller;
		this.addWindowListener(this);
		this.title = title;
		initComponents();
		setSize(width, height);
		setTitle(Statics.getNamebyXml(title));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}


	
	public void updateComponentsLabel(){
		setTitle(Statics.getNamebyXml(title));
	}
	protected abstract void initComponents();
		

}
