package dialogs;

/*
 * This software is licensed under the MIT License.
 * Copyright 2018, Maurice Koch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import algorithms.Sort;
import main.Controller;
import main.MathFunc;
import main.SortVisualisationPanel;
import main.Statics;
import main.Window;

/**
 * This class is responsible for editing the sorting list.
 * You can either set the number of elements that should contain the list, then 
 * every value is randomly determined, or you can set every value invidually.
 * 
 * 
 * @author Maurice Koch
 * @category Dialogs
 * @version BETA
 * 
 * 
 */

public class EnterDialog extends OptionDialog {

	private DefaultListModel<Integer> listModel;
	private JList<Integer> elements;
	private JTextField value;
	private JSpinner values;
	private JButton enterValue, ok, remove, crNmb;
	private JRadioButton crRandom, setMan;
	private SortVisualisationPanel svp;
	private JPanel btnWrp1,btnWrp2; 
	
	private static EnterDialog instance;
	private Controller controller;
	
	private static int width,height;

	private EnterDialog(Controller controller,int width, int height) {
		super("sortlist", width, height);
		this.controller = controller;
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
					listModel.addElement(MathFunc.getRandomNumber(1, temp));

				
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

					try {

						temp = Integer.parseInt(value.getText());
						listModel.add(0, temp);

						int[] tmp = new int[listModel.getSize()];
						for (int i = 0; i < listModel.getSize(); i++){
							tmp[i] = listModel.get(i);
						}
						
						Sort.setElements(tmp);
						svp.setElements(tmp);
						svp.updateBarSize();
						svp.updatePanelSize();
						
					} catch (NumberFormatException ex) {
						JOptionPane
								.showMessageDialog(
										null,
										langXML.getValue("error0"),
										"Ungueltige Eingabe",
										JOptionPane.ERROR_MESSAGE);
					}


					value.setText("");
					value.requestFocus();
	
				}

			}

			else {

				int l = (int) (values.getValue());
				listModel.removeAllElements();
				
				int[] tmp = new int[l];
				for (int i = 0; i < l; i++){
					tmp[i] = MathFunc.getRandomNumber(1, l/3);
				    listModel.addElement(tmp[i]);
				}
				
				Sort.setElements(tmp);
				svp.setElements(tmp);
				svp.updateBarSize();
				svp.updatePanelSize();
				revalidate();
				repaint();
			}
			
			
		}

		else if (e.getActionCommand() == Statics.ELEMENTS_SET) {

			controller.actionPerformed(e);
			dispose();
		}

	}

	
	/**
	 * {@inheritDoc} overridden method
	 * @Override
	 */
	public void updateComponentsLabel() {

		super.updateComponentsLabel();

		crNmb.setName(langXML.getValue("rnumber"));
		enterValue.setName(langXML.getValue("add"));
		ok.setName(langXML.getValue("exit"));
		crRandom.setText(langXML.getValue("setList"));
		setMan.setText(langXML.getValue("setManual"));
		btnWrp1.setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.GRAY),langXML.getValue("selection")));

	}

	/**
	 * {@inheritDoc} overridden method
	 */
	
	@Override
	protected void initComponents() {
		listModel = new DefaultListModel<Integer>();
		elements = new JList<Integer>(listModel);
		value = new JTextField();
		values = new JSpinner();
		values.setFont(Window.getComponentFont(15f));
		setLayout(new GridBagLayout());
		
		JTabbedPane tp = new JTabbedPane();
		


		JScrollPane sp = new JScrollPane(elements);
		svp = new SortVisualisationPanel(width,height);
		svp.setElements(Sort.getElements());
		svp.updateBarSize();
		svp.updatePanelSize();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		int tempElements[] = Sort.getElements();
		for (int i = 0; i < tempElements.length; i++) {
			listModel.addElement(new Integer(tempElements[i]));
		}
		values.setValue(listModel.getSize());
		
		tp.addTab("List",sp);
		tp.addTab("Graphic",svp);
		
		GridBagConstraints tcnt = new GridBagConstraints();
		tcnt.fill = GridBagConstraints.BOTH;
		tcnt.gridx = 0;
		tcnt.gridy = 0;
		tcnt.gridwidth = 4;
		tcnt.gridheight = 3;
		tcnt.weightx = 1;
		tcnt.weighty = 7;

		crRandom = new JRadioButton(langXML.getValue("setList"));
		crRandom.addActionListener(this);
		crRandom.setSelected(true);
		setMan = new JRadioButton(langXML.getValue("setManual"));
		setMan.addActionListener(this);

		btnWrp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		TitledBorder tb= BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY), langXML.getValue("selection"));
				
		btnWrp1.setBorder(tb);
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
				langXML.getValue("rnumber"));
		crNmb.addActionListener(this);
		
		
		enterValue = new JButton(
				langXML.getValue("add"));
		enterValue.addActionListener(this);

		ok = new JButton(langXML.getValue("exit"));
		ok.setActionCommand(Statics.ELEMENTS_SET);
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

		// sp
		
		
		add(tp, tcnt);
		add(btnWrp1, btnWrpc1);
		add(btnWrp2, btnWrpc2);
		
		
		//setResizable(false);
	
	}

	/**
	 * 
	 * @param width width of the frame
	 * @param height height of the frame
	 * @return an instance of EnterDialog, if the wasn't requested before,
	 * it will be created. For more info see 'Singleton' (Design Pattern) 
	 * @category Singleton (Design Pattern)
	 */
	public static EnterDialog getInstance(Controller controller,int width,
			int height) {

		if (instance == null){
			EnterDialog.width = 470;
			EnterDialog.height = 120;
			instance = new EnterDialog(controller,width, height);
		}

		instance.setVisible(true);
		return instance;
	}



}
