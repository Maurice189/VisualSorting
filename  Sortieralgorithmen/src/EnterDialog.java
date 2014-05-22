import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;


public class EnterDialog extends OptionDialog{
	
	
	
	private DefaultListModel<Integer> listModel;
	private JList<Integer> elements;
	private JTextField value;
	private JSpinner values;
	private JButton enterValue, ok, remove, crNmb;
	private JPanel list;
	private JPanel list2;
	private JScrollPane pane;
	
	public EnterDialog(Controller controller,int width, int height) {
		super(controller,Statics.COMPONENT_TITLE.SORTLIST, width, height);
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == remove){
			if (!elements.isSelectionEmpty()) {

				int selIndices[] = elements.getSelectedIndices();
				for (int i = 0; i < selIndices.length; i++)
					listModel.remove(selIndices[i]);

			}
		}
		
		else if (e.getSource() == crNmb){
			
			int temp = 0;

			if (value.getText() != null) {

				temp = (int) (values.getValue());
				listModel.removeAllElements();
				for (int i = 0; i < temp; i++)
					listModel.addElement(Controller
							.getRandomNumber(0, temp));

			}

		}
		
		else if (e.getSource() == enterValue){
			
			int temp = 0;

			if (value.getText() != null) {

				boolean error = false;

				try {

					temp = Integer.parseInt(value.getText());
				} catch (NumberFormatException ex) {

					error = true;
					JOptionPane.showMessageDialog(null, Statics
							.getNamebyXml(Statics.COMPONENT_TITLE.ERROR0),
							"Ungueltige Eingabe", JOptionPane.ERROR_MESSAGE);
				}

				if (!error)
					listModel.add(0, temp);

				value.setText("");
				value.requestFocus();
			}
		}
		
		else if(e.getSource() == ok){
			int[] temp = new int[listModel.size()];
			for (int i = 0; i < listModel.size(); i++)
				temp[i] = listModel.get(i);
			Sort.setElements(temp);

			dispose();
		}
		
	}

	@Override
	public void updateComponentsLabel() {
		
		super.updateComponentsLabel();
		
		remove.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.REMOVE));
		crNmb.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RNUMBERS));
		enterValue.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD));
		ok.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
		
	}

	@Override
	protected void initComponents() {
	    listModel = new DefaultListModel<Integer>();
		elements = new JList<Integer>(listModel);
	    value = new JTextField();
		values = new JSpinner();
	
		list = new JPanel();
		list2 = new JPanel();
		pane = new JScrollPane(elements);

		list.setLayout(new BoxLayout(list, BoxLayout.X_AXIS));
		list2.setLayout(new BoxLayout(list2, BoxLayout.X_AXIS));

		remove = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.REMOVE));
		
		crNmb = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.RNUMBERS));
		enterValue = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD));
		
		ok = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
		setLayout(new BoxLayout(getContentPane(),
				BoxLayout.Y_AXIS));

		int tempElements[] = Sort.getElements();
		for (int i = 0; i < tempElements.length; i++) {
			listModel.addElement(new Integer(tempElements[i]));
		}

		list.add(crNmb);
		list.add(Box.createHorizontalStrut(5));
		list.add(values);
		list.add(Box.createHorizontalStrut(5));
		list.add(ok);
		list2.add(enterValue);
		list2.add(value);
		list2.add(remove);

		java.net.URL helpURL = Dialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");

		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}

		add(pane);
		add(Box.createVerticalStrut(7));
		add(list);
		add(list2);
		
	}

}
