package algorithms;

import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.PanelUI;
import main.SortVisualisationPanel;
import main.Statics.SORTALGORITHMS;

/**
 * 
 * Used Design Patterns
 * 
 * 		Model-View-Controller
 * 		Strategy Design Pattern
 * 		Observer Design Pattern
 * 
 * Abstract
 * 
 * As the Strategy Design Pattern declares, every single sort algorithm is
 * implemented as a own class. This abstract class defines the interface and
 * implements Runnable in order to execute every single sort algorithm
 * in a own thread. Moreover every sort object has its reference to a own
 * canvas 
 * Even we need to know(for the GUI) when the sort procedure has terminated.
 * Thats the reason, why we need the Observable interface 
 * After a thread has terminated, it notifys the Controller 
 * 
 * 
 * 
 * @author Maurice Koch
 * @version beta
 */

public abstract class Sort extends Observable implements Runnable {

	protected static int gElement[];
	protected static long delayMs = 5;
	protected static int delayNs = 0;
	protected static boolean stop = true,flashing = true,interrupt;
	

	protected int elements[];
	protected int iterates,accesses,comparisons;
	protected SortVisualisationPanel svp;
	protected PanelUI panelUI;
	protected Lock lock = new ReentrantLock();
	protected Condition condition = lock.newCondition();


	/*
	 * Each object hold its own SortVisualisationPanel and redirect every 
	 * changes that are done on the sortlist
	 */
	public Sort(SortVisualisationPanel svp) {

		this.svp = svp;


	}

	public void initElements() {

		this.elements = new int[Sort.gElement.length];
		System.arraycopy(Sort.gElement, 0, elements, 0, Sort.gElement.length);

		iterates = 0;
		accesses = 0;
		comparisons = 0;
		svp.setElements(this.elements);
	}

	/*
	 * 
	 * Sorting list, that can accessed from every object, in order to create a copy
	 */
	public static void setElements(int elements[]) {
		Sort.gElement = elements;

	}

	/*
	 * a copy of the static sortlist is created 
	 */
	public Sort() {
		// TODO Auto-generated constructor stub

		this.elements = new int[Sort.gElement.length];
		System.arraycopy(Sort.gElement, 0, elements, 0, Sort.gElement.length);

	}
	
	/*
	 * The paused thread resume
	 */
	public void unlockSignal(){
		try {
			lock.lock();
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

	/*
	 * This method is called in the run method from every algorithm that is running
	 * This is needed to provide the start/stop functionality that is based
	 * on locks and condition. 
	 * 
	 */
	protected void checkRunCondition() {

		try {
			if (Sort.stop) {
				lock.lock();
				condition.await();
				lock.unlock();
			}
			
			else if(Sort.interrupt)
				Thread.currentThread().interrupt();

			else
				Thread.sleep(Sort.delayMs, Sort.delayNs);

		} catch (InterruptedException e) {
			//System.out.println("INFO: INTERRUPTED WHILE SLEEPING"); //e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	/*
	 * 
	 * is used for displaying the sorting list in 'EnterDialog'
	 * and for saving the number of elements in the configuration file
	 */
	public static int[] getElements() {
		return Sort.gElement;
	}

	public void setSortVisualisationPanel(SortVisualisationPanel svp,PanelUI panelUI) {

		this.svp = svp;
		this.panelUI = panelUI;
		svp.setElements(elements);

	}
	
	public PanelUI getPanelUI(){
		return panelUI;
	}
	
	
	public static void setInterruptFlag(boolean interrupt){
		Sort.interrupt = interrupt;
	}
	
	/*
	 * the specific algorithm name (identifying is needed for the info dialog)
	 */
	public abstract SORTALGORITHMS getAlgorithmName();

	public static void stop() {
		stop = true;

	}
	public static void resume() {
		stop = false;

	}
	
	/*
	 * flashing decides whether to animated the ending of a sorting proceedure
	 */
	public static void setFlashingAnimation(boolean flashing){
		
		Sort.flashing = flashing;
	}

	
	/*
	 * is especially needed for the controller
	 */
	public static boolean isStopped() {
		return stop;
	}

	/**
	 * is used to apply modifications on the 'SortVisualisationPanel' object
	 * for e.g enable the remove button
	 */
	public SortVisualisationPanel getSortVisualisationPanel() {

		return svp;
	}

	/*
	 * delayNs  set the delay(nanoseconds) for all threads
	 */
	public static void setDelayNs(int delayNs) {

		Sort.delayNs = delayNs;

	}


	/*
	 * delayNs  set the delay(milliseconds) for all threads
	 */
	public static void setDelayMs(long delayMs) {

		Sort.delayMs = delayMs;

	}

	/*
	 *  The delay is saved in the configuration file
	 */
	public static long getDelayMs() {
		return Sort.delayMs;
	}

	/*
	 *  The delay is saved in the configuration file
	 */
	public static long getDelayNs() {
		return Sort.delayNs;
	}

}
