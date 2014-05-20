
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JDialog;



public abstract class OptionDialog extends JDialog implements WindowListener,ActionListener{

	
	private static final long serialVersionUID = 1L;
	protected Controller controller;
	
	public OptionDialog(Controller controller,Statics.COMPONENT_TITLE title,int width, int height) {
		
		this.controller = controller;
		this.addWindowListener(this);
		
		initComponents();
		setSize(width, height);
		setTitle(Statics.getNamebyXml(title));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}


	
	public abstract void updateComponentsLabel();
	protected abstract void initComponents();
	
		

}
