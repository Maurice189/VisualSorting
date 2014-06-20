package sorting_algorithms;
import main.SortVisualtionPanel;


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
	   
		try {
			sort(0,elements.length-1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("INFO: INTERRUPTED WHILE SLEEPING"); //e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		setChanged();
		notifyObservers();
		
		
	}
	

	    public void sort(int l, int r) throws InterruptedException {
	        
	        if (l < r) {
	            int q = (l + r) / 2;
	            
	            sort(l, q);
	            sort(q + 1, r);
	            merge(l, q, r);
	        }
	        
	    }

	    private void merge(int l, int q, int r) throws InterruptedException {
	        int[] arr = new int[elements.length];
	        int i, j;
	        for (i = l; i <= q; i++) {
	            arr[i] = elements[i];
	        }
	        for (j = q + 1; j <= r; j++) {
	            arr[r + q + 1 - j] = elements[j];
	            /*
	            //svp.visualCmp((r + q + 1 - j), j, false);
	            svp.visualInsert((r + q + 1 - j), elements[j]);
	           
				svp.setInfo(("Mergesort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

	        	if (Sort.stop) {
					lock.lock();
					condition.await();
					lock.unlock();
				}

				else
					Thread.sleep(Sort.delayMs,Sort.delayNs);
				*/
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
	            

	        	if (Sort.stop) {
					lock.lock();
					condition.await();
					lock.unlock();
				}

				else
					Thread.sleep(Sort.delayMs,Sort.delayNs);

	        }
	    }

	   
	

}