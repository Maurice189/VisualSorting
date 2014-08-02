package algorithms;

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;

public class BogoSort extends Sort{
	

	public BogoSort(SortVisualtionPanel svp) {
		super(svp);

	}

	public BogoSort() {
		super();


	}
	
	void bogo()
	{
		int shuffle=1;
		for(;!isSorted();shuffle++){
			shuffle();
		}
			

	}
	void shuffle()
	{
		int i=elements.length-1;
		while(i>0)
			swap(i--,(int)(Math.random()*i));
	}
	void swap(int i,int j)
	{
		int temp=elements[i];
		elements[i]=elements[j];
		elements[j]=temp;
		
		svp.visualCmp(i,j, true);
		//svp.setInfo("Bogosort", iterates++);
		panelUI.setInfo("Bogosort",accesses,comparisons);
		accesses+=3;
		
		checkRunCondition();
	}
	boolean isSorted()
	{
 
		for(int i=1;i<elements.length;i++){
			
			panelUI.setInfo("Bogosort",accesses,comparisons++);
			if(elements[i]<elements[i-1])
				return false;
			
		}
		return true;
		
		
	}

	
	
	@Override
	public void run() {
		
		bogo();
		
		setChanged();
		notifyObservers(panelUI.getID());
		
		if (flashing)
			svp.flashing();
		
	}
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		return SORTALGORITHMS.Bogosort;
	}

}
