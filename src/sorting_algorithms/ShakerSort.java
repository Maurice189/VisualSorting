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

	public int[] sort() {

		int i = 0, l = elements.length;
		while (i < l) {
			shaker1(i, l);
			l--;
			shaker2(i, l);
			i++;
		}
		return elements;
	}

	private void shaker1(int i, int l) {
		for (int j = i; j < l - 1; j++) {
			if (elements[j] > elements[j + 1]) {
				k = elements[j];
				elements[j] = elements[j + 1];
				elements[j + 1] = k;
				svp.visualCmp(j, j + 1, true);
				svp.setInfo("Shakersort",iterates++);

			}

			else {
				svp.visualCmp(j, j + 1, false);
				svp.setInfo("Shakersort",iterates++);
			}

			checkRunCondition();
		}
	}

	private void shaker2(int i, int l) {
		for (int j = l - 1; j >= i; j--) {
			if (elements[j] > elements[j + 1]) {
				k = elements[j];
				elements[j] = elements[j + 1];
				elements[j + 1] = k;

				svp.visualCmp(j, j + 1, true);
				svp.setInfo("Shakersort",iterates++);

			}

			else {
				svp.visualCmp(j, j + 1, false);
				svp.setInfo("Shakersort",iterates++);
			}

			checkRunCondition();
		}
	}

	public void run() {
		int i = 0, l = elements.length;

		while (i < l) {
				shaker1(i, l);
				l--;
				shaker2(i, l);
				i++;

		}

		if(flashing) svp.flashing();
		setChanged();
		notifyObservers();
	}

}