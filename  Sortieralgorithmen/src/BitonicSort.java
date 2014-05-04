

//FIXME sortiert nicht richtig
public class BitonicSort extends Sort {

	private final static boolean ASCENDING = true, DESCENDING = false;

	private void bitonicSort(int lo, int n, boolean dir) throws Exception {
		if (n > 1) {
			int k = n / 2;
			bitonicSort(lo, k, ASCENDING);
			bitonicSort(lo + k, k, DESCENDING);
			bitonicMerge(lo, n, dir);
		}
	}

	private void bitonicMerge(int lo, int n, boolean dir) throws Exception {
		if (n > 1) {
			int k = n / 2;
			for (int i = lo; i < lo + k; i++)
				compare(i, i + k, dir);
			bitonicMerge(lo, k, dir);
			bitonicMerge(lo + k, k, dir);
		}
	}

	private void compare(int i, int j, boolean dir) throws Exception {
		if (dir == (elements[i] > elements[j])) {
			int h = elements[i];
			elements[i] = elements[j];
			elements[j] = h;
			svp.visualCmp(i, j, true);
			svp.setInfo(("Bitonicsort [ " + elements.length
					+ " Elemente ] - Iterationen: " + iterates++));

		}
		
		else{
			svp.visualCmp(i, j, false);
			svp.setInfo(("Bitonicsort [ " + elements.length
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

	public void run() {
		// TODO Auto-generated method stub

		try {
			bitonicSort(0, elements.length, ASCENDING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers();

	}

} // end class BitonicSorter