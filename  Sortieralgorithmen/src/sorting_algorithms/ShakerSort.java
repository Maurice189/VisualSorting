package sorting_algorithms;
import main.SortVisualtionPanel;

public class ShakerSort extends Sort {

	private int k;

	public ShakerSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public ShakerSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public int[] sort() throws InterruptedException {

		int i = 0, l = elements.length;
		while (i < l) {
			shaker1(i, l);
			l--;
			shaker2(i, l);
			i++;
		}
		return elements;
	}

	private void shaker1(int i, int l) throws InterruptedException {
		for (int j = i; j < l - 1; j++) {
			if (elements[j] > elements[j + 1]) {
				k = elements[j];
				elements[j] = elements[j + 1];
				elements[j + 1] = k;
				svp.visualCmp(j, j + 1, true);
				svp.setInfo(("ShakerSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

			}

			else {
				svp.visualCmp(j, j + 1, false);
				svp.setInfo(("ShakerSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));
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

	private void shaker2(int i, int l) throws InterruptedException {
		for (int j = l - 1; j >= i; j--) {
			if (elements[j] > elements[j + 1]) {
				k = elements[j];
				elements[j] = elements[j + 1];
				elements[j + 1] = k;

				svp.visualCmp(j, j + 1, true);
				svp.setInfo(("ShakerSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

			}

			else {
				svp.visualCmp(j, j + 1, false);
				svp.setInfo(("ShakerSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));
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

	public void run() {
		int i = 0, l = elements.length;

		while (i < l) {
			try {
				shaker1(i, l);

				l--;
				shaker2(i, l);
				i++;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		setChanged();
		notifyObservers();
	}

}