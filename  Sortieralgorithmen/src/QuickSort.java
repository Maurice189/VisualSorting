public class QuickSort extends Sort {

	public QuickSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public QuickSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	private void qSort(int x[], int links, int rechts)
			throws InterruptedException {
		if (links < rechts) {
			int i = partition(elements, links, rechts);
			qSort(x, links, i - 1);
			qSort(x, i + 1, rechts);
		}

	}

	private int partition(int x[], int links, int rechts)
			throws InterruptedException {
		int pivot, i, j, help;
		pivot = x[rechts];
		i = links;
		j = rechts - 1;
		while (i <= j) {
			
			if (x[i] > pivot) {
				// tausche x[i] und x[j]
				help = x[i];
				x[i] = x[j];
				x[j] = help;
				
				//svp.drawElements(i, j, true);
				svp.fastExchange(i, j);
				svp.setInfo(("QuickSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

				j--;
			} else{
				
				
				//svp.drawElements(i, rechts, false);
				svp.fastExchange(i, rechts);
				svp.setInfo(("QuickSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));
				
				
				i++;
			}
			
			if (Sort.stop) {
				lock.lock();
				condition.await();
				lock.unlock();
			}

			else
				Thread.sleep(Sort.delay);
				
		}
		// tausche x[i] und x[rechts]
		help = x[i];
		x[i] = x[rechts];
		x[rechts] = help;

		//svp.drawElements(i, rechts, true);
		svp.fastExchange(i, rechts);
		svp.setInfo(("QuickSort [ " + elements.length
				+ " Elemente ] - Iterationen: " + iterates++));

		if (Sort.stop) {
			lock.lock();
			condition.await();
			lock.unlock();
		}

		else
			Thread.sleep(Sort.delay);

		return i;
	}

	public void run() {
		// TODO Auto-generated method stub

		try {
			qSort(elements, 0, elements.length - 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers();
		
		

	}

}
