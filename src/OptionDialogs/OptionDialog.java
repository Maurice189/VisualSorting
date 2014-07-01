package OptionDialogs;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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
		
		java.net.URL helpURL = OptionDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		setVisible(true);
	}
	
	public OptionDialog(){
		
		setLocationRelativeTo(null);
		java.net.URL helpURL = OptionDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
	}
	


	public static void setLanguageFileXML(LanguageFileXML langXML){
		OptionDialog.langXML = langXML;
	}
	
	public void updateComponentsLabel(){
		setTitle(langXML.getValue(titleXMLTag));
	}
	protected abstract void initComponents();
		

}
