package OptionDialogs;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Controller;
import main.Statics;
import sorting_algorithms.Sort;


/**
 * <b>Used Pattern: Singleton</b>  
 * @author maurice
 *
 */

public final class DelayDialog extends OptionDialog{
	
	private static DelayDialog instance;
	private JLabel delay;
	private JSlider slider;
	private JRadioButton ms,ns;
	private boolean active = true;
	
	private DelayDialog(Controller controller,int width, int height) {
		super(controller,Statics.COMPONENT_TITLE.DELAY, width, height);
		
	}
	
	@Override
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
						System.out.println(slider.getValue());

					} else {

						delay.setText(String
								.valueOf((int) Sort.getDelayMs())
								.concat(" ms : ")
								.concat(String.valueOf(slider.getValue())
										.concat(" ns")));
						Sort.setDelayNs(slider.getValue());
						System.out.println(slider.getValue());
					}
				}

			}

		});
		
		java.net.URL helpURL = DelayDialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}

		add(panel3);
		add(Box.createVerticalStrut(4));
		add(panel2);
		add(Box.createVerticalStrut(8));
		add(slider);
		add(Box.createVerticalStrut(25));
		
	}
	
	
	@Override
	public void updateComponentsLabel() {
		
		super.updateComponentsLabel();

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

	
	
	public static DelayDialog getInstance(Controller controller,int width, int height){
		
		if(instance == null) instance = new DelayDialog(controller,width,height);
		
		instance.setVisible(true);
		return instance;
	}


	
}