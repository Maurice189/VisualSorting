package OptionDialogs;

import java.awt.event.ActionListener;

import javax.swing.JDialog;

import main.Controller;
import main.LanguageFileXML;



public abstract class OptionDialog extends JDialog implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	protected Controller controller;
	protected String titleXMLTag;
	protected static LanguageFileXML langXML;
	
	public OptionDialog(Controller controller,String titleXMLTag,int width, int height) {
		
		this.controller = controller;
		this.titleXMLTag = titleXMLTag;
		initComponents();
		setSize(width, height);
		setTitle(langXML.getValue(titleXMLTag));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public OptionDialog(){
		
	}
	


	public static void setLanguageFileXML(LanguageFileXML langXML){
		OptionDialog.langXML = langXML;
	}
	
	public void updateComponentsLabel(){
		setTitle(langXML.getValue(titleXMLTag));
	}
	protected abstract void initComponents();
		

}
