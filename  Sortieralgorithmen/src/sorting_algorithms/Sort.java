package sorting_algorithms;
import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import main.SortVisualtionPanel;

/**
 * <b>Used Pattern: Strategy Design Pattern/Observer Design Pattern</b>  
 * 
 * As the Strategy Design Pattern declares, every single sort algorithmn is 
 * implemented as a own class. 
 * This abstract class defines the interface and implements <b>Runnable</b>
 * in order to execute every single sort algorithmn in a own thread.
 * Moreover every sort object has its reference to a own canvas(named: <b>SortVisualtionPanel</b>)
 * <br><br>
 * Even we need to know(for the GUI) when the sort proceedure has terminated. Thats the reason, why we
 * need the Obserable interface
 * 
 * 
 * @author maurice
 * @version BETA
 *  
 */



public abstract class Sort extends Observable implements Runnable{
	
	
	protected static int gElement[]; /** Statisches Referenzelement fuer die Sortierelemente*/
	protected int elements[];	
	protected int iterates;
	protected SortVisualtionPanel svp;
	protected static long delayMs = 110;
    protected static int delayNs = 0; 
	
	
	protected Lock lock = new ReentrantLock();
	protected Condition condition = lock.newCondition();
	protected static boolean stop = true;
	
	/** @param <b>svp</b>  */
	public Sort(SortVisualtionPanel svp) { // permite write only per copy
		// TODO Auto-generated constructor stub
		
	
		this.svp = svp;
		
		/* this block is redundant, if 'initElements()' is called always before the animation launchs
		 * 
		 * 
		this.elements = new int[Sort.gElement.length];
		System.arraycopy( Sort.gElement, 0, elements, 0, Sort.gElement.length );
		
		
		svp.setElements(this.elements);
		svp.drawElements();
		*/

	}
	
	public void initElements(){
		
		this.elements = new int[Sort.gElement.length];
		System.arraycopy( Sort.gElement, 0, elements, 0, Sort.gElement.length );
		
		iterates = 0;
		svp.setElements(this.elements);
		svp.drawElements();
	}
	
	public static void setElements(int elements[]){
		Sort.gElement = elements;	
	}

	public Sort() {
		// TODO Auto-generated constructor stub
		
		this.elements = new int[Sort.gElement.length];
		System.arraycopy( Sort.gElement, 0, elements, 0, Sort.gElement.length );
		

	}
	
	public static int[] getElements(){
		return Sort.gElement;
	}
	
	
	
	public void setSortVisualtionPanel(SortVisualtionPanel svp){
		
		this.svp = svp;
		svp.setElements(elements);
		
	}
	
	public static void stop(){
		stop = true;
		
		
	}
	
	public static void resume(){
		stop = false;
		
	}
	
	public static boolean isStopped(){
		return stop;
	}
	
	public SortVisualtionPanel getSortVisualtionPanel(){
		
		return svp;
	}
	
	
	public Condition getCondition(){
		return condition;
	}
	
	public Lock getLock(){
		return lock;
	}
	
	public static void setDelayNs(int delayNs){

		if(Sort.delayMs == 0 && delayNs == 0) Sort.delayNs = 1;
		else Sort.delayNs = delayNs;
		
	}
	
	public static void setDelayMs(long delayMs){
		
		if(Sort.delayNs == 0  && delayMs == 0) Sort.delayMs = 1;
		else Sort.delayMs = delayMs;
		
	}
	
	public static long getDelayMs(){
		return Sort.delayMs;
	}
	
	public static long getDelayNs(){
		return Sort.delayNs;
	}


}
