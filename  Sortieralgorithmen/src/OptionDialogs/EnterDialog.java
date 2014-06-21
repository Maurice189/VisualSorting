package OptionDialogs;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	private JPanel btnWrp2;
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
			
			 btnWrp2.removeAll();
			 btnWrp2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	         btnWrp2.add(enterValue);
	         btnWrp2.add(Box.createHorizontalStrut(10));
	         btnWrp2.add(value);
	         btnWrp2.add(Box.createHorizontalGlue());
	         btnWrp2.add(ok);
			
		}
		
		else if (e.getSource() == setMan){
			
			 btnWrp2.removeAll();
			 btnWrp2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	         btnWrp2.add(enterValue);
	         btnWrp2.add(Box.createHorizontalStrut(10));
	         values.setMinimumSize(value.getMaximumSize());
	         btnWrp2.add(values);
	         btnWrp2.add(Box.createHorizontalGlue());
	         btnWrp2.add(ok);
			
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
		
		setLayout(new GridBagLayout());
		
         JScrollPane sp = new JScrollPane(elements);
         sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         
         int tempElements[] = Sort.getElements();
         for (int i = 0; i < tempElements.length; i++) {
			listModel.addElement(new Integer(tempElements[i]));
         }
         
         GridBagConstraints tcnt = new GridBagConstraints();
         tcnt.fill = GridBagConstraints.BOTH;
         tcnt.gridx = 0;
         tcnt.gridy = 0;
         tcnt.gridwidth = 4;
         tcnt.gridheight = 3;
         tcnt.weightx = 1;
         tcnt.weighty = 7;
         
         
         crRandom = new JRadioButton("Zufallszahlen");
 		 crRandom.addActionListener(this);
 		 crRandom.setSelected(true);
 		 setMan = new JRadioButton("Manuell");
 		 setMan.addActionListener(this);
 		
 	

         JPanel btnWrp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
         btnWrp1.setBorder(BorderFactory.createTitledBorder(
         BorderFactory.createLineBorder(Color.GRAY), "Type"));
         btnWrp1.add(crRandom);
         btnWrp1.add(setMan);
         
         ButtonGroup bg = new ButtonGroup();
         bg.add(crRandom);
 			bg.add(setMan);
       
         
         GridBagConstraints btnWrpc1 = new GridBagConstraints();
         btnWrpc1.fill = GridBagConstraints.HORIZONTAL;
         btnWrpc1.gridx = 0;
         btnWrpc1.gridy = 4;
         btnWrpc1.gridwidth = 4;
         btnWrpc1.gridheight = 1;
         btnWrpc1.weightx = 1;
         btnWrpc1.weighty = 1;
         btnWrpc1.anchor = GridBagConstraints.NORTH;
         btnWrpc1.insets = new Insets(4, 4, 4, 4 );
         
         crNmb = new JButton(
  				Statics.getNamebyXml(Statics.COMPONENT_TITLE.RNUMBERS));
  		 enterValue = new JButton(
  				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD));
  		
  		 ok = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
         
         btnWrp2 = new JPanel();
         btnWrp2.setLayout(new BoxLayout(btnWrp2,BoxLayout.X_AXIS));
         value.setMaximumSize(values.getMaximumSize());

         //btnWrp2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
         btnWrp2.add(enterValue);
         btnWrp2.add(Box.createHorizontalStrut(10));
         btnWrp2.add(value);
         btnWrp2.add(Box.createHorizontalGlue());
         btnWrp2.add(ok);
         
       
         
         GridBagConstraints btnWrpc2 = new GridBagConstraints();
         btnWrpc2.fill = GridBagConstraints.HORIZONTAL;
         btnWrpc2.gridx = 0;
         btnWrpc2.gridy = 5;
         btnWrpc2.gridwidth = 4;
         btnWrpc2.gridheight = 1;
         btnWrpc2.weightx = 1;
         btnWrpc2.weighty = 1;
         btnWrpc2.anchor = GridBagConstraints.SOUTH;
         btnWrpc2.insets = new Insets(4, 4, 4, 4 );
		
         
         add(sp, tcnt );
         add( btnWrp1, btnWrpc1 );
         add( btnWrp2, btnWrpc2 );
	
		/*
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
		list2.add(remove);*/

		java.net.URL helpURL = EnterDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");

		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		/*
		add(pane);
		add(Box.createVerticalStrut(5));
		add(buttonWrapper);
		add(Box.createVerticalGlue());

		add(list);
		add(list2);
		
		list2.setVisible(false);
		*/
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
