package algorithms;

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

This sort algorithm is based on:
http://www.javabeginners.de/Algorithmen/Sortieralgorithmen/Mergesort.php
(C) Jörg Czeschla

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
		
		setChanged();
		notifyObservers(svp.getID());
		
		if(flashing) svp.flashing();
		
		
		
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
	            svp.setInfo("Mergesort",accesses++,comparisons);
	        }
	        for (j = q + 1; j <= r; j++) {
	            arr[r + q + 1 - j] = elements[j];
	            svp.setInfo("Mergesort",accesses++,comparisons);
	  
	        }
	        i = l;
	        j = r;
	        for (int k = l; k <= r; k++) {
	        	
	        	
	            if (arr[i] <= arr[j]) {
	            	svp.visualInsert(k, arr[i]);
	                elements[k] = arr[i];
	                //svp.setInfo("Mergesort",iterates++);
	                svp.setInfo("Mergesort",accesses++,comparisons++);
	                
	                i++;
	            } else {
	            	
	            	svp.visualInsert(k, arr[j]);
	                elements[k] = arr[j];
	                //svp.setInfo("Mergesort",iterates++);
	                svp.setInfo("Mergesort",accesses++,comparisons++);
	                
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
