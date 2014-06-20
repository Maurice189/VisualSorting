package sorting_algorithms;
import main.SortVisualtionPanel;


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
			
				try {
					if (Sort.stop) {
						lock.lock();
						condition.await();
						lock.unlock();
					} else

						Thread.sleep(Sort.delayMs,Sort.delayNs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("INFO: INTERRUPTED WHILE SLEEPING"); //e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				
				j--;
			}
			
			svp.visualInsert(j, temp);
			svp.setInfo("Insertionsort",iterates++);
			
			elements[j] = temp;
			try {
				if (Sort.stop) {
					lock.lock();
					condition.await();
					lock.unlock();
				} else

					Thread.sleep(Sort.delayMs,Sort.delayNs);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("INFO: INTERRUPTED WHILE SLEEPING"); //e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
		setChanged();
		notifyObservers();
		
	}

}