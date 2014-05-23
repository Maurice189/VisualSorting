package main;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import OptionDialogs.DelayDialog;
import OptionDialogs.EnterDialog;
import OptionDialogs.InfoDialog;
import OptionDialogs.ManualDialog;
import OptionDialogs.OptionDialog;
import sorting_algorithms.BinaryTreeSort;
import sorting_algorithms.BitonicSort;
import sorting_algorithms.BubbleSort;
import sorting_algorithms.CombSort;
import sorting_algorithms.GnomeSort;
import sorting_algorithms.HeapSort;
import sorting_algorithms.InsertionSort;
import sorting_algorithms.MergeSort;
import sorting_algorithms.QuickSort;
import sorting_algorithms.RadixSort;
import sorting_algorithms.ShakerSort;
import sorting_algorithms.Sort;

/**
 * @author Maurice Koch
 * @version BETA
 */

/*
 * TODO: General Functions
 * 
 * - Merken welche Sprache ausgewählt wurde. ggf. in config.xml abspeichern
 * - Optimierung Parameter in SortVisualtionPanel wenn möglich statisch implementieren
 * - Implementierung weiterer Sortierverfahren
 */

public class Controller implements Observer, ActionListener, WindowListener {

	private ArrayList<Sort> sortList;
	private LinkedList<OptionDialog> dialogs;
	
	private Window window;
	private int runningThreads, vspIndex;
	
	private boolean byUserStopped = false;
	private ExecutorService executor;

	public Controller() {

		int size = 110;
		int[] elements = new int[size];

		sortList = new ArrayList<Sort>();
		dialogs = new LinkedList<OptionDialog>(); 
		
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
			/*
			else if (selectedSort.equals(Statics.SORT_ALGORITHMNS[10]))
				sort = new ShellSort();*/
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

					byUserStopped = false;
					window.unlockManualIteration(false);
				} else {
					Sort.stop();
					window.unlockManualIteration(true);
					byUserStopped = true;
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

			for (int i = 0; i < sortList.size(); i++) {
				sortList.get(i).resetElements();
				runningThreads = 0;
			}
		}

		else if (e.getActionCommand() == Statics.MANUAL) {

			dialogs.add(new ManualDialog(this,"Bedienungsanleitung",300,700));
			
			
		}

		else if (e.getActionCommand() == Statics.NEW_ELEMENTS) {

			dialogs.add(new EnterDialog(this, 500, 200));
		}

		else if (e.getActionCommand() == Statics.INFO) {

			dialogs.add(new InfoDialog(this,300,150));
		}

		else if (e.getActionCommand() == Statics.LANG_DE) {

			Statics.readLang("resources/lang_de.xml", "German");
			window.updateLanguage();
			for(OptionDialog temp:dialogs) temp.updateComponentsLabel();

		}

		else if (e.getActionCommand() == Statics.LANG_EN) {

			Statics.readLang("resources/lang_en.xml", "English");
			window.updateLanguage();
			for(OptionDialog temp:dialogs) temp.updateComponentsLabel();

			
		}

		else if (e.getActionCommand() == Statics.LANG_FR) {

			Statics.readLang("resources/lang_fr.xml", "France");
			window.updateLanguage();
			for(OptionDialog temp:dialogs) temp.updateComponentsLabel();
	
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

			dialogs.add(new DelayDialog(this,320, 150));
		}

	}

	// this method will be called by an EDT foreign thread
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

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
		for(OptionDialog temp: dialogs) temp.dispose();
		
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
		
		if (runningThreads != 0 && byUserStopped == false) {
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
				
			}
		window.appReleased();

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		Sort.stop();
		if (runningThreads != 0 && byUserStopped == false) window.appStopped();
	}

}
