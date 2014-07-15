package algorithms;

// copied from http://puzzlersworld.com/interview-questions/sort-the-array-radix-sort-java/

/**
 * 
 * Implementation of the respective sort algorithm.
 * 
 * @author maurice
 * @version BETA
 * @category Sort
 */

import java.util.ArrayList;

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;


public class RadixSort extends Sort{
	
	
	public RadixSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public RadixSort() {
		// TODO Auto-generated constructor stub
		super();

	}

 
    public void radixSort(int maxDigits){
        int exp = 1;//10^0;
        for(int i =0; i < maxDigits; i++){
            ArrayList<Integer> bucketList[] = new ArrayList[10];
            for(int k=0; k < 10; k++){
                bucketList[k] = new ArrayList<Integer>();
            }
            for(int j =0; j < elements.length; j++){
                int number = (elements[j]/exp)%10;
                bucketList[number].add(elements[j]);
                svp.setInfo("Radixsort",accesses++,comparisons);
     
            }
            exp *= 10;
            int index =0;
            for(int k=0; k < 10; k++){
                for(int num: bucketList[k]){
                	
                	svp.visualInsert(index,num);
                	//svp.setInfo("Radixsort",iterates++);
                	svp.setInfo("Radixsort",accesses++,comparisons);

                	
                    elements[index] = num;
                   
 
                	checkRunCondition();
                    index++;
                }
            }
        }

    }
 


	public void run() {
		// TODO Auto-generated method stub
	
		radixSort(3);
	
		if(flashing) svp.flashing();
		setChanged();
		notifyObservers();
	}
	
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Radixsort;
	}
}