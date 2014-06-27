package OptionDialogs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
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
 * 
 * @author maurice
 * 
 */

public class EnterDialog extends OptionDialog {

	private DefaultListModel<Integer> listModel;
	private JList<Integer> elements;
	private JTextField value;
	private JSpinner values;
	private JButton enterValue, ok, remove, crNmb;
	private JRadioButton crRandom, setMan;

	private JPanel btnWrp1,btnWrp2; 
	private static EnterDialog instance;

	private EnterDialog(Controller controller, int width, int height) {
		super(controller, Statics.COMPONENT_TITLE.SORTLIST, width, height);

	}



	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == remove) {
			if (!elements.isSelectionEmpty()) {

				int selIndices[] = elements.getSelectedIndices();
				for (int i = 0; i < selIndices.length; i++)
					listModel.remove(selIndices[i]);

			}
		}

		else if (e.getSource() == crNmb) {

			int temp = 0;

			if (value.getText() != null) {

				temp = (int) (values.getValue());
				listModel.removeAllElements();
				for (int i = 0; i < temp; i++)
					listModel.addElement(Controller.getRandomNumber(0, temp));

			}

		}

		else if (e.getSource() == crRandom) {

			btnWrp2.removeAll();
			btnWrp2.add(enterValue);
			btnWrp2.add(Box.createHorizontalStrut(10));
			btnWrp2.add(values);
			values.setValue(listModel.getSize());
			btnWrp2.add(Box.createHorizontalGlue());
			btnWrp2.add(ok);
			btnWrp2.revalidate();
			btnWrp2.repaint();

		}

		else if (e.getSource() == setMan) {

			btnWrp2.removeAll();
			btnWrp2.add(enterValue);
			btnWrp2.add(Box.createHorizontalStrut(10));
			btnWrp2.add(value);
			btnWrp2.add(Box.createHorizontalGlue());
			btnWrp2.add(ok);
			btnWrp2.revalidate();
			btnWrp2.repaint();
		}

		else if (e.getSource() == enterValue) {

			int temp = 0;
			if (setMan.isSelected()) {
				if (value.getText() != null) {

					boolean error = false;

					try {

						temp = Integer.parseInt(value.getText());
					} catch (NumberFormatException ex) {

						error = true;
						JOptionPane
								.showMessageDialog(
										null,
										Statics.getNamebyXml(Statics.COMPONENT_TITLE.ERROR0),
										"Ungueltige Eingabe",
										JOptionPane.ERROR_MESSAGE);
					}

					if (!error)
						listModel.add(0, temp);

					value.setText("");
					value.requestFocus();
				}

			}

			else {

				temp = (int) (values.getValue());
				listModel.removeAllElements();
				for (int i = 0; i < temp; i++)
					listModel.addElement(Controller.getRandomNumber(0, temp/3));

			}
		}

		else if (e.getSource() == ok) {
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

		//remove.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.REMOVE));
		crNmb.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RNUMBERS));
		enterValue.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD));
		ok.setName(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
		crRandom.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETLIST));
		setMan.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETMANUAL));
		btnWrp1.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY), Statics.getNamebyXml(Statics.COMPONENT_TITLE.SELECTION)));

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
		values.setValue(listModel.getSize());
		
		GridBagConstraints tcnt = new GridBagConstraints();
		tcnt.fill = GridBagConstraints.BOTH;
		tcnt.gridx = 0;
		tcnt.gridy = 0;
		tcnt.gridwidth = 4;
		tcnt.gridheight = 3;
		tcnt.weightx = 1;
		tcnt.weighty = 7;

		crRandom = new JRadioButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETLIST));
		crRandom.addActionListener(this);
		crRandom.setSelected(true);
		setMan = new JRadioButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETMANUAL));
		setMan.addActionListener(this);

		btnWrp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnWrp1.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY), Statics.getNamebyXml(Statics.COMPONENT_TITLE.SELECTION)));
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
		btnWrpc1.insets = new Insets(4, 4, 4, 4);

		crNmb = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.RNUMBERS));
		crNmb.addActionListener(this);

		enterValue = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD));
		enterValue.addActionListener(this);

		ok = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.EXIT));
		ok.addActionListener(this);

		btnWrp2 = new JPanel();
		btnWrp2.setLayout(new BoxLayout(btnWrp2, BoxLayout.X_AXIS));
		value.setMaximumSize(values.getMaximumSize());

		btnWrp2.add(enterValue);
		btnWrp2.add(Box.createHorizontalStrut(10));
		btnWrp2.add(values);
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
		btnWrpc2.insets = new Insets(4, 4, 4, 4);

		add(sp, tcnt);
		add(btnWrp1, btnWrpc1);
		add(btnWrp2, btnWrpc2);

		java.net.URL helpURL = EnterDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");

		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
	
	}

	public static EnterDialog getInstance(Controller controller, int width,
			int height) {

		if (instance == null)
			instance = new EnterDialog(controller, width, height);

		instance.setVisible(true);
		return instance;
	}

	public static EnterDialog getInstance() {
		return instance;
	}

}
