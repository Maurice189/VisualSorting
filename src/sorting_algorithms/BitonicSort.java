package sorting_algorithms;

import main.Statics.SORTALGORITHMS;


public class BitonicSort extends Sort {

	private final static boolean ASCENDING = true, DESCENDING = false;

	private void bitonicSort(int lo, int n, boolean dir) {
		if (n > 1) {
			int k = n / 2;
			bitonicSort(lo, k, ASCENDING);
			bitonicSort(lo + k, k, DESCENDING);
			bitonicMerge(lo, n, dir);
		}
	}

	private void bitonicMerge(int lo, int n, boolean dir) {
		if (n > 1) {
			int k = n / 2;
			for (int i = lo; i < lo + k; i++)
				compare(i, i + k, dir);
			bitonicMerge(lo, k, dir);
			bitonicMerge(lo + k, k, dir);
		}
	}

	private void compare(int i, int j, boolean dir) {
		if (dir == (elements[i] > elements[j])) {
			int h = elements[i];
			elements[i] = elements[j];
			elements[j] = h;
			svp.visualCmp(i, j, true);
			svp.setInfo("Bitonicsort",iterates++);

		}
		
		else{
			svp.visualCmp(i, j, false);
			svp.setInfo("Bitonicsort",iterates++);
		}
		
		checkRunCondition();
	}

	public void run() {
		// TODO Auto-generated method stub

		
		bitonicSort(0, elements.length, ASCENDING);
		if(flashing) svp.flashing();
		
		setChanged();
		notifyObservers();

	}

	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Bitonicsort;
	}

} // end class BitonicSorter