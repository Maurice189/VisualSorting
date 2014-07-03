package sorting_algorithms;

/*
 *  copied from http://www.javabeginners.de/Algorithmen/Sortieralgorithmen/Mergesort.php
 *  Copyright Jörg Czeschla
 */

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


public class MergeSort extends Sort{

	public MergeSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public MergeSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public void run() {
		// TODO Auto-generated method stub
	   
	
		sort(0,elements.length-1);
		
		if(flashing) svp.flashing();
		setChanged();
		notifyObservers();
		
		
	}
	

	    public void sort(int l, int r) {
	        
	        if (l < r) {
	            int q = (l + r) / 2;
	            
	            sort(l, q);
	            sort(q + 1, r);
	            merge(l, q, r);
	        }
	        
	    }

	    private void merge(int l, int q, int r) {
	        int[] arr = new int[elements.length];
	        int i, j;
	        for (i = l; i <= q; i++) {
	            arr[i] = elements[i];
	        }
	        for (j = q + 1; j <= r; j++) {
	            arr[r + q + 1 - j] = elements[j];
	  
	        }
	        i = l;
	        j = r;
	        for (int k = l; k <= r; k++) {
	        	
	        	
	            if (arr[i] <= arr[j]) {
	            	svp.visualInsert(k, arr[i]);
	                elements[k] = arr[i];
	                svp.setInfo("Mergesort",iterates++);
	                
	                i++;
	            } else {
	            	
	            	svp.visualInsert(k, arr[j]);
	                elements[k] = arr[j];
	                svp.setInfo("Mergesort",iterates++);
	                
	                j--;
	            }
	            

	        	checkRunCondition();

	        }
	    }
	    
	    
	    @Override
		public SORTALGORITHMS getAlgorithmName() {
			// TODO Auto-generated method stub
			return SORTALGORITHMS.Mergesort;
		}

	   
	

}
