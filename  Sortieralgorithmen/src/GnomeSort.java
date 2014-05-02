public class GnomeSort extends Sort {

	public GnomeSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public GnomeSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public void run() {

		int pos = 1, last = 0;
		while (pos < elements.length) {
			if (elements[pos] >= elements[pos - 1]) {

				svp.drawElements(pos, pos - 1, false);
				svp.setInfo(("GnomeSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

				if (last != 0) {
					pos = last;
					last = 0;
				}
				pos = pos + 1;
			} else {
				int swap = elements[pos];
				elements[pos] = elements[pos - 1];
				elements[pos - 1] = swap;

				svp.drawElements(pos, pos - 1, true);
				svp.setInfo(("GnomeSort [ " + elements.length
						+ " Elemente ] - Iterationen: " + iterates++));

				if (pos > 1) {
					if (last == 0) {
						last = pos;
					}
					pos = pos - 1;
				} else {
					pos = pos + 1;
				}

			}

			try {
				if (Sort.stop) {
					lock.lock();
					condition.await();
					lock.unlock();
				} else

					Thread.sleep(Sort.delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		setChanged();
		notifyObservers();

	}

}