package sorting_algorithms;
import java.util.ArrayList;

import main.SortVisualtionPanel;


public class RadixSort extends Sort{
	
	
	public RadixSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public RadixSort() {
		// TODO Auto-generated constructor stub
		super();

	}

 
    public void radixSort(int maxDigits) throws InterruptedException{
        int exp = 1;//10^0;
        for(int i =0; i < maxDigits; i++){
            ArrayList<Integer> bucketList[] = new ArrayList[10];
            for(int k=0; k < 10; k++){
                bucketList[k] = new ArrayList<Integer>();
            }
            for(int j =0; j < elements.length; j++){
                int number = (elements[j]/exp)%10;
                bucketList[number].add(elements[j]);
     
            }
            exp *= 10;
            int index =0;
            for(int k=0; k < 10; k++){
                for(int num: bucketList[k]){
                	
                	svp.visualInsert(index,num);
                	svp.setInfo(("RadixSort [ " + elements.length
     						+ " Elemente ] - Iterationen: " + iterates++));
                    elements[index] = num;
                   
    				
    				
    				if (Sort.stop) {
    					lock.lock();
    					condition.await();
    					lock.unlock();
    				}

    				else
    					Thread.sleep(Sort.delayMs,Sort.delayNs);
                    index++;
                }
            }
        }

    }
 


	public void run() {
		// TODO Auto-generated method stub
		try {
			radixSort(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("INFO: INTERRUPTED WHILE SLEEPING"); //e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		setChanged();
		notifyObservers();
	}
}