package main;

/*
Visualsorting
Copyright (C) 2014  Maurice Koch

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import algorithms.BinaryTreeSort;
import algorithms.BitonicSort;
import algorithms.BogoSort;
import algorithms.BubbleSort;
import algorithms.CombSort;
import algorithms.GnomeSort;
import algorithms.HeapSort;
import algorithms.InsertionSort;
import algorithms.IntroSort;
import algorithms.MergeSort;
import algorithms.QuickSort;
import algorithms.RadixSort;
import algorithms.ShakerSort;
import algorithms.ShellSort;
import algorithms.Sort;
import dialogs.AboutDialog;
import dialogs.DelayDialog;
import dialogs.EnterDialog;
import dialogs.InfoDialog;
import dialogs.OptionDialog;
import main.InternalConfig.LANG;
import main.Statics.SORTALGORITHMS;


/**
 * 
 * 
 * This class respresents, as the name implies, the controller in the
 * MVC pattern. Moreover this class implements the observer pattern,
 * so the controller can be informed, if a visualsation thread ends
 * 
 * @author Maurice Koch
 * @version beta
 * @category MVC
 * 
 */
public class Controller implements Observer,ComponentListener,ActionListener, WindowListener {

	private ArrayList<Sort> sortList; 
	private LinkedList<OptionDialog> dialogs; 

	private Window window;
	private LanguageFileXML langXMLInterface;
	private boolean byUserStopped = false;
	private ExecutorService executor; 
	private javax.swing.Timer appTimer;
	private int leftMs,leftSec,threadsAlive;

	/**
	 * 
	 * @param langXMLInterface
	 */
	public Controller(LanguageFileXML langXMLInterface) {

		this.langXMLInterface = langXMLInterface;
		
		sortList = new ArrayList<Sort>();
		dialogs = new LinkedList<OptionDialog>();

		executor = Executors.newCachedThreadPool();
		createTimer();
	
	}
	
	/**
	 * 
	 * @param window
	 */
	public void setView(Window window) {

		this.window = window;
		SortVisualisationPanel.setBackgroundColor(window.getBackground());
		window.addComponentListener(this);
		window.updateNumberOfElements(Sort.getElements().length);
	}
	
	/**
	 * 
	 */
	public void reset(){
		
		if (Sort.isStopped()) {

			Sort.setInterruptFlag(true);
			Sort.resume();
			Sort.setFlashingAnimation(false);

			for (Sort temp : sortList) {

				temp.deleteObservers();
				temp.unlockSignal();	
				temp.getPanelUI().enableRemoveButton(true);
		 	}
			
			try {
			  executor.shutdown();
		        if (!executor.awaitTermination(1200, TimeUnit.MILLISECONDS)) { //optional *
		            System.out.println("Executor did not terminate in the specified time."); //optional *
		            List<Runnable> droppedTasks = executor.shutdownNow(); //optional **
		            System.out.println("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed."); //optional **
		        }
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if (!executor.isTerminated())
				System.out.println("executor service isn't terminated !");

			for (Sort temp : sortList) {

				temp.initElements();
				temp.addObserver(this);

			}
			Sort.setInterruptFlag(false);
			Sort.setFlashingAnimation(true);
			createTimer();
			
		}
		
		else {
			for (Sort temp : sortList) {
				temp.getPanelUI().enableRemoveButton(true);
				temp.initElements();

			}
		}

		
		window.unlockAddSort(true);
		threadsAlive = 0;
		
		
	}
	
	/**
	 * 
	 */
	private void createTimer(){
		
		leftMs = 0;
		leftSec = 0;
		
		appTimer = new javax.swing.Timer(10, new ActionListener() {

			  public void actionPerformed( ActionEvent e ) {
				  
				  leftMs+=10;
				  if (leftMs == 1000) {
					leftMs = 0;
					leftSec++;
				  }
				  
				  window.setClockParam(leftSec, leftMs);
			  }
			  
		}); 
		if(window!=null) window.setClockParam(0,0);
	}
	
	
	
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == Statics.ADD_SORT) {

			Sort sort;
			String selectedSort;

			Sort.resume();
			for (Sort temp : sortList) {
				temp.initElements();
				threadsAlive = 0;
			}

			selectedSort = window.getSelectedSort();

			if (selectedSort.equals(SORTALGORITHMS.Heapsort.toString()))
				sort = new HeapSort();
			else if (selectedSort.equals(SORTALGORITHMS.Bubblesort.toString()))
				sort = new BubbleSort();
			else if (selectedSort.equals(SORTALGORITHMS.Quicksort.toString()))
				sort = new QuickSort();
			else if (selectedSort.equals(SORTALGORITHMS.BTS.toString()))
				sort = new BinaryTreeSort();
			else if (selectedSort.equals(SORTALGORITHMS.Combsort.toString()))
				sort = new CombSort();
			else if (selectedSort.equals(SORTALGORITHMS.Gnomesort.toString()))
				sort = new GnomeSort();
			else if (selectedSort.equals(SORTALGORITHMS.Shakersort.toString()))
				sort = new ShakerSort();
			else if (selectedSort.equals(SORTALGORITHMS.Mergesort.toString()))
				sort = new MergeSort();
			else if (selectedSort.equals(SORTALGORITHMS.Bitonicsort.toString())){
				sort = new BitonicSort();
				if(Integer.bitCount(Sort.getElements().length) != 1){
					JOptionPane.showMessageDialog(window,
					langXMLInterface.getValue("info0l0")+"\n"+langXMLInterface.getValue("info0l1"),
					"Information",JOptionPane.INFORMATION_MESSAGE);		
				}
			}
				
			else if (selectedSort.equals(SORTALGORITHMS.Radixsort.toString()))
				sort = new RadixSort();
			else if (selectedSort.equals(SORTALGORITHMS.Shellsort.toString()))
				sort = new ShellSort();
			else if (selectedSort.equals(SORTALGORITHMS.Insertionsort
					.toString()))
				sort = new InsertionSort();
			else if (selectedSort.equals(SORTALGORITHMS.Bogosort
					.toString()))
				sort = new BogoSort();
			else if (selectedSort.equals(SORTALGORITHMS.Introsort
					.toString()))
				sort = new IntroSort();
			else
				sort = new HeapSort();

			sort.addObserver(this);
			sortList.add(sort);
			window.addNewSort(sort);
			resize();

		}

		else if (e.getActionCommand() == Statics.START) {
			if (threadsAlive != 0) {
				if (Sort.isStopped()) {
					Sort.resume();
					for (Sort temp : sortList) {
						temp.unlockSignal();
					}

					byUserStopped = false;
					window.unlockManualIteration(false);
					appTimer.start();

					
				} else {
					Sort.stop();
				
					window.unlockManualIteration(true);
					byUserStopped = true;
					appTimer.stop();
					

				}

			} else {

				if (executor.isTerminated()){
					executor = Executors.newCachedThreadPool();
				}
				Sort.resume();
				for (Sort temp : sortList) {

					temp.initElements();
					temp.getPanelUI().enableRemoveButton(false);
					executor.execute(temp);
					threadsAlive++;
				}

				if (byUserStopped){
					byUserStopped = false;
				}
				window.unlockManualIteration(false);
				window.unlockAddSort(false);
				
				createTimer();
				appTimer.start();
			}

			window.toggleStartStop();
			
		}

		else if (e.getActionCommand() == Statics.RESET) {

			reset();
		}
		
		else if (e.getActionCommand() == Statics.AUTO_PAUSE) {

			InternalConfig.toggleAutoPause();
		}


		else if (e.getActionCommand() == Statics.NEW_ELEMENTS) {

			dialogs.add(EnterDialog.getInstance(this,500, 300));
		}

		else if (e.getActionCommand() == Statics.ABOUT) {

			dialogs.add(AboutDialog.getInstance(370, 415));
		}

		else if (e.getActionCommand() == Statics.INFO) {

			SORTALGORITHMS selAlgorithm = sortList.get(
					PanelUI.getReleasedID()).getAlgorithmName();

			dialogs.add(new InfoDialog(selAlgorithm, selAlgorithm
					.toString(), 600, 370));
		}

		else if (e.getActionCommand() == Statics.LANG_DE) {

			InternalConfig.setLanguage(LANG.de);
			langXMLInterface.readXML(InternalConfig.getLanguageSetPath());
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		else if (e.getActionCommand() == Statics.LANG_EN) {

			InternalConfig.setLanguage(LANG.en);
			langXMLInterface.readXML(InternalConfig.getLanguageSetPath());
			window.updateLanguage();
			// update language on every open dialog
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel();
			

		}

		else if (e.getActionCommand() == Statics.LANG_FR) {

			InternalConfig.setLanguage(LANG.fr);
			langXMLInterface.readXML(InternalConfig.getLanguageSetPath());
			window.updateLanguage();
			for (OptionDialog temp : dialogs)
				temp.updateComponentsLabel(); // update language on every open
												// dialog

		}

		// just execute one more step
		else if (e.getActionCommand() == Statics.NEXT_ITERATION) {

			for (Sort temp : sortList) {
				temp.unlockSignal();
			}

		}
		/**
		 * It is quite inconvenient to figure out which
		 * panel was removed. It's even not a good idea, to create 
		 * for each new panel a new handler. So all remove requests (fired events)
		 * are redirected to this handler. 
		 */
		else if (e.getActionCommand() == Statics.REMOVE_SORT) {

			if (sortList.size() > 0) {

				int selPanel = PanelUI.getReleasedID();

				if (threadsAlive != 0) {
					Sort.setFlashingAnimation(false);
					sortList.get(selPanel).unlockSignal();
					sortList.get(selPanel).deleteObservers();
					Future<?> f = executor.submit(sortList.get(selPanel));
					f.cancel(true);
					threadsAlive--;
					

				}

				window.removeSort(selPanel);
				sortList.remove(selPanel);
				
				for (Sort temp : sortList)
					temp.getPanelUI().updateID();
				
				PanelUI.updateCounter();
				Sort.setFlashingAnimation(true);
				resize();
			}
		}


		else if (e.getActionCommand() == Statics.DELAY) {

			dialogs.add(DelayDialog.getInstance(320, 150));
		}
		
		else if (e.getActionCommand() == Statics.ELEMENTS_SET) {

			window.updateNumberOfElements(Sort.getElements().length);
			for(Sort temp: sortList){
				
				if(temp.getAlgorithmName() == SORTALGORITHMS.Bitonicsort
				&& Integer.bitCount(Sort.getElements().length) != 1){
					
					JOptionPane.showMessageDialog(window,
					langXMLInterface.getValue("info0l0")+"\n"+langXMLInterface.getValue("info0l1"),
					"Information",JOptionPane.INFORMATION_MESSAGE);		
				}
				
				temp.getSortVisualisationPanel().updateBarSize();
			}
			reset();
		}
	}
	
	
	/*
	 * Called when a algorithm terminates
	 * (Oberserver) 
	 */
	@Override
	public void update(Observable o, Object arg) {

	    	/*
	    	 * When all proccess has been terminated,
	    	 * then the gui has to be updated (enable buttons etc.).
	    	 * 
	    	 * Hence we're using swing (no sync. , no concurrent operations), 
	    	 * we need to invoke the gui update later,
	    	 * instead of using the calling thread.
	    	 */
		if (--threadsAlive == 0 && byUserStopped == false) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					window.toggleStartStop();
					window.unlockAddSort(true);
					window.unlockManualIteration(true);
				}
			});
			
			appTimer.stop();
			
			for (Sort temp : sortList){
				temp.getPanelUI().enableRemoveButton(true);
			}
			
		}
		
		
			
		sortList.get((int)arg).getPanelUI().setDuration(leftSec, leftMs);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		appTimer.stop();

		for (OptionDialog temp : dialogs){
			temp.dispose();
		}

		InternalConfig.saveChanges();
		
	}

	 @Override
	public void windowActivated(WindowEvent e) {

		if(InternalConfig.isAutoPauseEnabled()){
			if (threadsAlive != 0 && byUserStopped == false) {
			 	Sort.resume();
			 	appTimer.start();
			 	for (Sort temp : sortList) {
			 		temp.unlockSignal();
			 	}

			}
			
			window.appReleased();
		 
	 	}
		
	}

	 @Override
	public void windowDeactivated(WindowEvent e) {
		
		if(InternalConfig.isAutoPauseEnabled()){
			Sort.stop();
			if (threadsAlive != 0 && byUserStopped == false) {
				window.appStopped();
				appTimer.stop();
			}
		}
	}
	 
	
	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {} 
	/**
	 *  
	 */
	private void resize(){
		 
		 if(!sortList.isEmpty()){				
			for(Sort tmp:sortList){
			    tmp.getSortVisualisationPanel().updateSize();
			}
		 }
	 }

	@Override
	public void componentResized(ComponentEvent e) {
		resize();
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
	

	
	

}
