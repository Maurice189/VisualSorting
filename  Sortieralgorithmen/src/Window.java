import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	private ArrayList<Sort> sortList;

	private JButton next, newSort, nextStep, reset;
	private JPanel content;
	private Controller controller;
	private JLabel info;
	private JComboBox<String> sortChooser;
	private JMenuItem about, list, delay, manual,report;
	private JRadioButtonMenuItem de, en, fr;
	private JMenu help, settings, languages;
	private JToolBar toolBar;

	public Window(Controller controller, String title, int width, int height) {
		// TODO Auto-generated constructor stub

		JMenuBar menuBar;
		ButtonGroup bg = new ButtonGroup();

		this.controller = controller;
		info = new JLabel(Statics.getNamebyXml(Statics.COMPONENT_TITLE.INFO),
				JLabel.CENTER);
		info.setFont(Statics.getDefaultFont(30f));
		info.setForeground(Color.GRAY);

		setTitle(title);
		setSize(width, height);
		sortList = controller.getList();
		addComponentListener(controller);
		addWindowListener(controller);

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);

		menuBar = new JMenuBar();

		languages = new JMenu(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.LANG));
		settings = new JMenu(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETTINGS));
		help = new JMenu(Statics.getNamebyXml(Statics.COMPONENT_TITLE.HELP));
		list = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.SORTLIST));
		list.addActionListener(controller);
		list.setActionCommand(Statics.NEW_ELEMENTS);

		delay = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.DELAY));
		delay.addActionListener(controller);
		delay.setActionCommand(Statics.DELAY);

		about = new JMenuItem(Statics.getNamebyXml(
				Statics.COMPONENT_TITLE.ABOUT).concat(" ").concat(title));
		about.addActionListener(controller);
		about.setActionCommand(Statics.INFO);

		manual = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.MANUAL));
		manual.addActionListener(controller);
		manual.setActionCommand(Statics.MANUAL);
		
		report = new JMenuItem(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.REPORT));
		manual.addActionListener(controller);
		manual.setActionCommand(Statics.REPORT);

		de = new JRadioButtonMenuItem("German");
		de.addActionListener(controller);
		de.setActionCommand(Statics.LANG_DE);

		en = new JRadioButtonMenuItem("English");
		en.addActionListener(controller);
		en.setActionCommand(Statics.LANG_EN);

		fr = new JRadioButtonMenuItem("France");
		fr.addActionListener(controller);
		fr.setActionCommand(Statics.LANG_FR);

		bg.add(de);
		bg.add(en);
		bg.add(fr);

		String tmp = Statics.getLanguageSet();
		if (tmp.equals(de.getText()))
			de.setSelected(true);
		else if (tmp.equals(en.getText()))
			en.setSelected(true);
		else if (tmp.equals(fr.getText()))
			fr.setSelected(true);

		languages.add(en);
		languages.add(de);
		languages.add(fr);

		help.add(manual);
		help.add(report);
		help.add(about);
		settings.add(list);
		settings.add(languages);
		settings.add(delay);

		menuBar.add(settings);
		menuBar.add(help);
		setJMenuBar(menuBar);

		sortChooser = new JComboBox<String>(Statics.SORT_ALGORITHMNS);
		content = new JPanel();
		content.setLayout(new BorderLayout());

		next = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI));
		next.addActionListener(controller);
		next.setActionCommand(Statics.START);

		newSort = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD_SORT));
		newSort.addActionListener(controller);
		newSort.setActionCommand(Statics.ADD_SORT);

		nextStep = new JButton(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.ITERATION));
		nextStep.addActionListener(controller);
		nextStep.setActionCommand(Statics.NEXT_ITERATION);

		reset = new JButton(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RESET));
		reset.addActionListener(controller);
		reset.setActionCommand(Statics.RESET);

		content.add(BorderLayout.CENTER, info);

		toolBar.add(next);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(nextStep);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(reset);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(newSort);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(sortChooser);

		reset.setEnabled(false);
		next.setEnabled(false);
		nextStep.setEnabled(false);

		java.net.URL helpURL = Dialog.class.getClassLoader().getResource(
				"resources/frameIcon2.png");
		if (helpURL != null) {
			setIconImage(new ImageIcon(helpURL).getImage());
		}
		setLayout(new BorderLayout());
		add(BorderLayout.PAGE_START, toolBar);
		add(BorderLayout.CENTER, content);
		// setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		System.out.println("W: " + newSort.getWidth() + "|H: "
				+ newSort.getHeight());

	}

	
	  public void updateLanguage(){
	  
	  info.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.INFO));
	  
	  
	  if (next.getText().equals(
	  Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI)))
	  next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI));
	  
	  else next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STOPANI));
	  
	  nextStep.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ITERATION));
	  newSort.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ADD_SORT));
	  reset.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.RESET));
	  about.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.ABOUT));
	  list.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SORTLIST));
	  delay.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.DELAY));
	  manual.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.MANUAL));
	  help.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.HELP));
	  settings.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.SETTINGS));
	  languages.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.LANG)); }
	 

	public void showPopupMenu(int x, int y, int sortIndex) {

		sortList.get(sortIndex).getSortVisualtionPanel().showPopUpMenu(x, y);

	}

	public void toggleStartStop() {

		if (next.getText().equals(
				Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI))) {
			next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STOPANI));
			newSort.setEnabled(false);
			reset.setEnabled(false);
		} else {
			next.setText(Statics.getNamebyXml(Statics.COMPONENT_TITLE.STARTANI));
			newSort.setEnabled(true);
			reset.setEnabled(true);
		}
	}

	public void addNewSort(Sort sort, String selectedSort) {

		Dimension minSize = null;
		
		if (sortList.size() == 1) {
			content.remove(info);
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			content.add(Box.createVerticalStrut(20));
			next.setEnabled(true);
			reset.setEnabled(true);
		}
		SortVisualtionPanel temp = new SortVisualtionPanel(controller,
				selectedSort, this.getWidth(), this.getHeight());
		sort.setSortVisualtionPanel(temp);

		content.add(temp);
		content.add(Box.createVerticalStrut(50));
		revalidate();

		for (int i = 0; i < sortList.size(); i++)
			minSize = sortList.get(i).getSortVisualtionPanel().updatePanelSize();
		
		
		if(minSize.getHeight() != 0 || minSize.getHeight() != 0 ){
			System.out.println("SD");
			//setSize(minSize);
		}

	}

	public String getSelectedSort() {

		return (String) sortChooser.getSelectedItem();
	}

	public void unlockManualIteration(boolean lock) {

		nextStep.setEnabled(lock);
	}

	public void removeSort(int index) {

		content.remove(sortList.get(index).getSortVisualtionPanel());

		if (sortList.size() == 1) {

			System.out.println("REMOVE");
			content.removeAll();
			content.setLayout(new BorderLayout());
			content.add(info);
			content.repaint();
			next.setEnabled(false);
			reset.setEnabled(false);
			nextStep.setEnabled(false);
		}
		
		revalidate();
		repaint();
		
		for (int i = 0; i < sortList.size(); i++) {
			if (i != index)
				sortList.get(i).getSortVisualtionPanel().updatePanelSize();
		}


		

	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		Statics.loadConfig("resources/config.xml");
		Statics.readLang("resources/lang_de.xml", "German");

		Statics.initDefaultFont("resources/OxygenFont/Oxygen-Regular.ttf");
		Controller controller = new Controller();
		Window window = new Window(controller,
				"Visual Sorting - ".concat(Statics.getVersion()), 800, 550);
		controller.setView(window);

	}

}
