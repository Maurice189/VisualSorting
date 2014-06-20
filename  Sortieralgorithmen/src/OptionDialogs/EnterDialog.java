package OptionDialogs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import main.Controller;
import main.Statics;
import sorting_algorithms.Sort;

/**
 * <b>Used Pattern: Singleton</b>  
 * @author maurice
 *
 */

public class EnterDialog extends OptionDialog{
	
	
	
	private DefaultListModel<Integer> listModel;
	private JList<Integer> elements;
	private JTextField value;
	private JSpinner values;
	private JButton enterValue, ok, remove, crNmb;
	private JRadioButton crRandom,setMan;
	private JPanel list;
	private JPanel list2;
	private JScrollPane pane;
	
	private static EnterDialog instance;
	
	private EnterDialog(Controller controller,int width, int height) {
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
		
		else if (e.getSource() == crRandom){
			list.setVisible(true);
			list2.setVisible(false);
		}
		
		else if (e.getSource() == setMan){
			list2.setVisible(true);
			list.setVisible(false);
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
		
	
		
		JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonWrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		crRandom = new JRadioButton("Zufallszahlen");
		crRandom.addActionListener(this);
		crRandom.setSelected(true);
		setMan = new JRadioButton("Manuell");
		setMan.addActionListener(this);
		buttonWrapper.add(crRandom);
		buttonWrapper.add(setMan);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(crRandom);
		bg.add(setMan);
		
	
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
		
		value.setMaximumSize(new Dimension(250,remove.getPreferredSize().height));

		list.add(crNmb);
		list.add(Box.createHorizontalStrut(5));
		list.add(values);
		list.add(Box.createHorizontalStrut(5));
		list.add(ok);
		list2.add(enterValue);
		list2.add(Box.createHorizontalStrut(5));
		list2.add(value);
		list2.add(Box.createHorizontalGlue());
		list2.add(remove);

		java.net.URL helpURL = EnterDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");

		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}

		add(pane);
		add(Box.createVerticalStrut(5));
		add(buttonWrapper);
		add(Box.createVerticalGlue());

		add(list);
		add(list2);
		
		list2.setVisible(false);
		
	}
	
	
	public static EnterDialog getInstance(Controller controller,int width, int height){
		
		if(instance == null) instance = new EnterDialog(controller,width,height);
		
		instance.setVisible(true);
		return instance;
	}
	
	public static EnterDialog getInstance(){
		return instance;
	}

}