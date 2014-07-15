package algorithms;

import main.SortVisualtionPanel;
import main.Statics.SORTALGORITHMS;

public class BogoSort extends Sort{
	

	public BogoSort(SortVisualtionPanel svp) {
		// TODO Auto-generated constructor stub
		super(svp);

	}

	public BogoSort() {
		// TODO Auto-generated constructor stub
		super();


	}
	
	void bogo()
	{
		int shuffle=1;
		for(;!isSorted();shuffle++)
			shuffle();

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
		svp.setInfo("Bogosort",accesses,comparisons);
		accesses+=3;
		
		checkRunCondition();
	}
	boolean isSorted()
	{
 
		for(int i=1;i<elements.length;i++){
			
			svp.setInfo("Bogosort",accesses,comparisons++);
			if(elements[i]<elements[i-1])
				return false;
			
		}
		return true;
		
		
	}

	
	
	@Override
	public void run() {
		
		bogo();
		
		if (flashing)
			svp.flashing();
		setChanged();
		notifyObservers();
	}
	@Override
	public SORTALGORITHMS getAlgorithmName() {
		return SORTALGORITHMS.Bogosort;
	}

}
