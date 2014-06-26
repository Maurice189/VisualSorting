package sorting_algorithms;
import main.SortVisualtionPanel;

public class CombSort extends Sort {

	public CombSort() {
		// TODO Auto-generated constructor stub
		super();

	}

	public CombSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public void run() {
		
		System.out.println("GO");

		float shrink = 1.3f;
		int swap;
		int i, gap = elements.length;
		boolean swapped = false;

		while ((gap > 1) || swapped) {
			if (gap > 1) {
				gap = (int) ((float) gap / shrink);
			}
			swapped = false;
			for (i = 0; gap + i < elements.length; ++i) {
				if (elements[i] - elements[i + gap] > 0) {
					swap = elements[i];
					elements[i] = elements[i + gap];
					elements[i + gap] = swap;
					swapped = true;
					svp.visualCmp(i, i + gap, true);
					svp.setInfo("Combsort",iterates++);

				}
				
				else{
					
					svp.visualCmp(i, i + gap, false);
					svp.setInfo("Combsort",iterates++);
				}
				
				
				checkRunCondition();
			
			}
		}
		
		if(flashing) svp.flashing();
		setChanged();
		notifyObservers();
	}
	
	

}