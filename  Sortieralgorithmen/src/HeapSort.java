public class HeapSort extends Sort {

	// a comment
	private int n;

	public HeapSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public HeapSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	private void heapsort() throws InterruptedException {
		buildheap();
		while (n > 1) {
			n--;
			exchange(0, n);
			downheap(0);

		}
	}

	private void buildheap() throws InterruptedException {
		for (int v = n / 2 - 1; v >= 0; v--)
			downheap(v);
	}

	private void downheap(int v) throws InterruptedException {
		int w = 2 * v + 1; // erster Nachfolger von v
		while (w < n) {
			if (w + 1 < n) // gibt es einen zweiten Nachfolger?
				if (elements[w + 1] > elements[w])
					w++;
			// w ist der Nachfolger von v mit maximaler Markierung

			if (elements[v] >= elements[w]){
				svp.drawElements(v, w, false);
				svp.setInfo(("HeapSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

				if (Sort.stop) {
					lock.lock();
					condition.await();
					lock.unlock();
				}

				else
					Thread.sleep(Sort.delay);
				return;
			}
			// sonst
			exchange(v, w); // vertausche Markierungen von v und w
			v = w; // fahre mit v=w fort
			w = 2 * v + 1;
		}
	}

	private void exchange(int i, int j) throws InterruptedException {
		int t = elements[i];
		elements[i] = elements[j];
		elements[j] = t;

		svp.drawElements(i, j, true);
		svp.setInfo(("HeapSort [ " + elements.length
				+ " Elemente ] - Iterationen: " + iterates++));

		if (Sort.stop) {
			lock.lock();
			condition.await();
			lock.unlock();
		}

		else
			Thread.sleep(Sort.delay);

	}

	public void run() {

		n = elements.length;
		try {
			heapsort();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers();
	}

}
