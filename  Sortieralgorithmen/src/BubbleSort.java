public class BubbleSort extends Sort {

	public BubbleSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public BubbleSort() {
		// TODO Auto-generated constructor stub
		super();


	}

	public void run() {

		int tmp = 0;
		
		for (int j = elements.length; j > 1; j--) {
			for (int i = 0; i < elements.length - 1; i++) {

				if (elements[i] > elements[i + 1]) {

					tmp = elements[i];
					elements[i] = elements[i + 1];
					elements[i + 1] = tmp;
					svp.visualCmp(i, i + 1, true);
					svp.setInfo(("BubbleSort [ " + elements.length
							+ " Elemente ] - Iterationen: " + iterates++));

				} else {
					svp.visualCmp(i, i + 1, false);
					svp.setInfo(("BubbleSort [ " + elements.length
							+ " Elemente ] - Iterationen: " + iterates++));
				}
				try {
					if (Sort.stop) {
						lock.lock();
						condition.await();
						lock.unlock();
					} else

						Thread.sleep(Sort.delayMs,Sort.delayNs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		
		setChanged();
		notifyObservers();

	}

}
