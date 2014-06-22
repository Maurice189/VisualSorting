package sorting_algorithms;
import main.SortVisualtionPanel;

public class ShellSort extends Sort {

	public ShellSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public ShellSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public void run() {
		// TODO Auto-generated method stub

		int i, j, increment, temp, number_of_elements = elements.length;

		/* Shell Sort Program */
		for (increment = number_of_elements / 2; increment > 0; increment /= 2) {
			for (i = increment; i < number_of_elements; i++) {
				temp = elements[i];
				for (j = i; j >= increment; j -= increment) {

					if (temp < elements[j - increment]) {
					
						svp.visualInsert(j, elements[j - increment]);
						svp.setInfo("Shellsort",iterates++);
						
						elements[j] = elements[j - increment];
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

					} else {

						svp.visualCmp(i, j - increment, false);
						svp.setInfo("Shellsort",iterates++);
						
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
						break;
					}
				}
				
				
				svp.visualInsert(j, temp);
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
		}

		setChanged();
		notifyObservers();

	}
}
