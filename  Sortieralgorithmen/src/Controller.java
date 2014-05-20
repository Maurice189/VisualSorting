import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import javax.swing.JDialog;

/**
 * @author Maurice Koch
 * @version BETA
 */

/* Strategy Design Pattern vom feinsten */
/*
 * TODO: General Functions
 * 
 * - Hinzufügen von Sortieralgorithmen nach Sprachänderung nicht mehr möglich
 * - Entfernfunktion verbessern Skalierung feinjustierung Grafikausgabe
 * - Merken welche Sprache ausgewählt wurde. ggf. in config.xml abspeichern
 * - Optimierung Parameter in SortVisualtionPanel wenn möglich statisch implementieren
 * - Implementierung weiterer Sortierverfahren
 */

public class Controller implements Observer, ActionListener, ComponentListener,
		MouseListener {

	private ArrayList<Sort> sortList;
	private Window window;
	private int runningThreads, vspIndex;
	private ExecutorService executor;

	public Controller() {
		// TODO Auto-generated constructor stub

		int size = 123;
		int[] elements = new int[size];

		sortList = new ArrayList<Sort>();
		// executor better that
		executor = Executors.newCachedThreadPool();

		for (int i = 0; i < size; i++)
			elements[i] = Controller.getRandomNumber(0, size);

		Sort.setElements(elements);

	}

	public ArrayList<Sort> getList() {
		return sortList;
	}

	public void setView(Window window) {

		this.window = window;
		SortVisualtionPanel.setBackgroundColor(window.getBackground());
	}

	public static int getRandomNumber(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getActionCommand() == Statics.ADD_SORT) {

			Sort sort;
			String selectedSort;

			if (runningThreads != 0) {

				Sort.resume();
				for (int i = 0; i < sortList.size(); i++) {
					sortList.get(i).resetElements();
					runningThreads = 0;
				}
			}

			selectedSort = window.getSelectedSort();

			if (selectedSort.equals(Statics.SORT_ALGORITHMNS[0]))
				sort = new HeapSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[1]))
				sort = new BubbleSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[2]))
				sort = new QuickSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[3]))
				sort = new BinaryTreeSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[4]))
				sort = new CombSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[5]))
				sort = new GnomeSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[6]))
				sort = new ShakerSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[7]))
				sort = new MergeSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[8]))
				sort = new BitonicSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[9]))
				sort = new RadixSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[10]))
				sort = new ShellSort();
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[11]))
				sort = new InsertionSort();
			else
				sort = new HeapSort();

			sort.addObserver(this);
			sortList.add(sort);
			window.addNewSort(sort, selectedSort);

		}

		else if (e.getActionCommand() == Statics.START) {

			if (runningThreads != 0) {
				if (Sort.isStopped()) {

					Sort.resume();
					for (int i = 0; i < sortList.size(); i++) {

						Lock l = sortList.get(i).getLock();
						Condition c = sortList.get(i).getCondition();

						try {
							l.lock();
							c.signal();
						} finally {
							l.unlock();
						}
					}

					window.unlockManualIteration(false);
				} else {
					Sort.stop();
					window.unlockManualIteration(true);

				}

			}

			else {

				System.out.println("OK");
				for (int i = 0; i < sortList.size(); i++) {

					Sort.resume();
					executor.execute(sortList.get(i));
					runningThreads++;

				}

				window.unlockManualIteration(false);
			}

			window.toggleStartStop();
		}

		else if (e.getActionCommand() == Statics.RESET) {

			//Sort.resume();

			for (int i = 0; i < sortList.size(); i++) {
				sortList.get(i).resetElements();
				runningThreads = 0;
			}
		}

		else if (e.getActionCommand() == Statics.MANUAL) {

			new ManualDialog(this,"Bedienungsanleitung",300,700);
			
			
		}

		else if (e.getActionCommand() == Statics.NEW_ELEMENTS) {

			new EnterDialog(this, 500, 200);
		}

		else if (e.getActionCommand() == Statics.INFO) {

			new InfoDialog(this,300,150);
		}

		else if (e.getActionCommand() == Statics.LANG_DE) {

			// FIXME hinzufügen nicht mehr möglich !
			Statics.readLang("resources/lang_de.xml", "German");

			window.dispose();
			window = new Window(this, "Visual Sorting - Beta", 800, 550);
			runningThreads = 0;


		}

		else if (e.getActionCommand() == Statics.LANG_EN) {

			// FIXME hinzufügen nicht mehr möglich !
			Statics.readLang("resources/lang_en.xml", "English");
			window.dispose();
			window = new Window(this, "Visual Sorting - Beta", 800, 550);
			runningThreads = 0;

	
			
			
			
		}

		else if (e.getActionCommand() == Statics.LANG_FR) {

			// FIXME hinzufügen nicht mehr möglich !
			Statics.readLang("resources/lang_fr.xml", "France");
			window.dispose();
			window = new Window(this, "Visual Sorting - Beta", 800, 550);
			runningThreads = 0;

		}

		else if (e.getActionCommand() == Statics.NEXT_ITERATION) {

			Lock l;
			Condition c;

			for (int i = 0; i < sortList.size(); i++) {

				l = sortList.get(i).getLock();
				c = sortList.get(i).getCondition();

				try {
					l.lock();
					c.signal();
				} finally {
					l.unlock();
				}
			}

		}

		else if (e.getActionCommand() == Statics.POPUP_REMOVE) {

			if (sortList.size() > 0) {

				window.removeSort(vspIndex);
				sortList.remove(vspIndex);

			}
		}
		
		else if (e.getActionCommand() == Statics.REPORT) {

			if (sortList.size() > 0) {

				window.removeSort(vspIndex);
				sortList.remove(vspIndex);

			}
		}

		else if (e.getActionCommand() == Statics.DELAY) {

			  new DelayDialog(this,320, 200);
		}

	}

	public void componentResized(ComponentEvent e) {

		for (int i = 0; i < sortList.size(); i++) {

			sortList.get(i).getSortVisualtionPanel().updatePanelSize();

		}

	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentHidden(ComponentEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		for (int i = 0; i < sortList.size(); i++) {

			if (e.getSource() == sortList.get(i).getSortVisualtionPanel()) {

				if (Sort.isStopped() && e.getButton() == 3)
					window.showPopupMenu(e.getX(), e.getY(), i);
				vspIndex = i;

			}

		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		if (--runningThreads == 0) {
			System.out.println("ALL THREADS STOPPED");

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					window.toggleStartStop();
				}
			});
		}

	}

}
