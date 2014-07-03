package sorting_algorithms;

// copied from http://www.iti.fh-flensburg.de/lang/algorithmen/sortieren/insert/insertion.htm

/**
 * 
 * Implementation of the respective sort algorithm.
 * 
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;


public class InsertionSort extends Sort{

	public InsertionSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public InsertionSort() {
		// TODO Auto-generated constructor stub
		super();

	}
	

	public void run() {
		
		int temp;
		
		for (int i = 1; i < elements.length; i++) {
			temp = elements[i];
			int j = i;
			while (j > 0 && elements[j - 1] > temp) {
				
				svp.visualInsert(j, elements[j - 1]);
				svp.setInfo("Insertionsort",iterates++);
				
				elements[j] = elements[j - 1];
				checkRunCondition();
			
				
				j--;
			}
			
			svp.visualInsert(j, temp);
			svp.setInfo("Insertionsort",iterates++);
			
			elements[j] = temp;
			checkRunCondition();
		
		}
		
		if(flashing) svp.flashing();
		setChanged();
		notifyObservers();
		
	}
	
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Insertionsort;
	}

}
