package sorting_algorithms;
import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;

public class HeapSort extends Sort {

	// a comment
	private int n;

	public HeapSort() {
		super();

	}

	public HeapSort(SortVisualtionPanel svp) {
		super(svp);

	}

	private void heapsort() {
		buildheap();
		while (n > 1) {
			n--;
			exchange(0, n);
			downheap(0);

		}
	}

	private void buildheap() {
		for (int v = n / 2 - 1; v >= 0; v--)
			downheap(v);
	}

	private void downheap(int v) {
		int w = 2 * v + 1; // erster Nachfolger von v
		while (w < n) {
			if (w + 1 < n) // gibt es einen zweiten Nachfolger?
				if (elements[w + 1] > elements[w])
					w++;
			// w ist der Nachfolger von v mit maximaler Markierung

			if (elements[v] >= elements[w]){
				svp.visualCmp(v, w, false);
				svp.setInfo("Heapsort",iterates++);

				checkRunCondition();
				return;
			}
			// sonst
			exchange(v, w); // vertausche Markierungen von v und w
			v = w; // fahre mit v=w fort
			w = 2 * v + 1;
		}
	}

	private void exchange(int i, int j) {
		int t = elements[i];
		elements[i] = elements[j];
		elements[j] = t;

		svp.visualCmp(i, j, true);
		svp.setInfo("Heapsort",iterates++);
		checkRunCondition();

	}

	public void run() {

		n = elements.length;

		heapsort();
		if(flashing) svp.flashing();
		
		setChanged();
		notifyObservers();
	}
	
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		// TODO Auto-generated method stub
		return SORTALGORITHMS.Heapsort;
	}

}
