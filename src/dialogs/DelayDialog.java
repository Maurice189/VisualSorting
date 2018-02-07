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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algorithms.Sort;
import main.Window;


public final class DelayDialog extends OptionDialog{
	
	private static DelayDialog instance;
	private JLabel delay;
	private JSlider slider;
	private JRadioButton ms,ns;
	private boolean active = true;
	
	private DelayDialog(int width, int height) {
		super("delay", width, height);
		
	}
	
	/**
	 * {@inheritDoc} overriden method
	 * @Override
	 */
	protected void initComponents() {
		
		delay = new JLabel();
		slider = new JSlider(0, 300, 50);
		ms = new JRadioButton("ms");
		ms.addActionListener(this);
	    ns = new JRadioButton("ns");
	    ns.addActionListener(this);
	
		 
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

		panel2.add(ms);
		panel2.add(ns);

		slider.setValue((int) Sort.getDelayMs());
		delay.setText((String.valueOf((int) Sort.getDelayMs())).concat(" ms : ")
				.concat(String.valueOf((int) Sort.getDelayNs())).concat(" ns"));
		ms.setSelected(true);

		panel3.add(Box.createHorizontalGlue());
		panel3.add(delay);
		panel3.add(Box.createHorizontalGlue());
		panel3.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		setLayout(new BoxLayout(getContentPane(),
				BoxLayout.Y_AXIS));
	
		
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(10);
		slider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				if (active) {
					if (ms.isSelected()) {

						delay.setText((String
								.valueOf(slider.getValue())
								.concat(" ms : ")
								.concat(String.valueOf((int) Sort.getDelayNs()))
								.concat(" ns")));
						Sort.setDelayMs(slider.getValue());

					} else {

						delay.setText(String
								.valueOf((int) Sort.getDelayMs())
								.concat(" ms : ")
								.concat(String.valueOf(slider.getValue())
										.concat(" ns")));
						Sort.setDelayNs(slider.getValue());
					}
				}

			}

		});
	

		add(panel3);
		add(Box.createVerticalStrut(4));
		add(panel2);
		add(Box.createVerticalStrut(8));
		add(slider);
		add(Box.createVerticalStrut(25));
		
		setResizable(false);
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == ns){
			
			active = false;
			ms.setSelected(false);
			slider.setMajorTickSpacing(10);
			slider.setMaximum(999);
			slider.setMinimum(0);
			slider.setValue((int) Sort.getDelayNs());
			active = true;

			
		}
		
		else if(e.getSource() == ms){

			active = false;
			ns.setSelected(false);
			slider.setMajorTickSpacing(5);
			slider.setMaximum(300);
			slider.setMinimum(0);
			slider.setValue((int) Sort.getDelayMs());
			active = true;
			
		
		}
		
		
	}

	/**
	 * 
	 * @param width width of the frame
	 * @param height height of the frame
	 * @return an instance of EnterDialog, if the wasn't requested before,
	 * it will be created. For more info see 'Singleton' (Design Pattern) 
	 * @category Singelton (Design Pattern)
	 */
	public static DelayDialog getInstance(int width, int height){
		
		if(instance == null) instance = new DelayDialog(width,height);
		
		instance.setVisible(true);
		return instance;
	}


	
}