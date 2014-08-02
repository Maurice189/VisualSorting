package algorithms;

// copied from http://www.cs.waikato.ac.nz/~bernhard/317/source/IntroSort.java

/**
 * 
 * Implementation of the respective sort algorithm.
 * 
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.Statics.SORTALGORITHMS;

public class IntroSort extends Sort {

	
	 /*
	   * Class Variables
	   */
	  private  int size_threshold = 16;
	  private int pivotIndex;

	  /*
	   * Public interface
	   */
	  public void sort()
	    {
	      introsort_loop(elements, 0, elements.length, 2*floor_lg(elements.length));
	    }

	  public void sort(int[] a, int begin, int end)
	    {
	      if (begin < end)
		  {
		    introsort_loop(a, begin, end, 2*floor_lg(end-begin));
		  }
	    }

	  /*
	   * Quicksort algorithm modified for Introsort
	   */
	  private  void introsort_loop (int[] a, int lo, int hi, int depth_limit)
	    {
	      while (hi-lo > size_threshold)
		  {
		    if (depth_limit == 0)
			{
			  heapsort(a, lo, hi);
			  return;
			}
		    depth_limit=depth_limit-1;
			pivotIndex = medianof3(a, lo, lo+((hi-lo)/2)+1, hi-1);
		    

		    int p=partition(a, lo, hi, a[pivotIndex]);
		    introsort_loop(a, p, hi, depth_limit);
		    hi=p;
		  }
	      insertionsort(a, lo, hi);
	    }

	  private  int partition(int[] a, int lo, int hi, int x)
	    {
	      int i=lo, j=hi;
	      while (true)
		  {
		    while (a[i] < x) i++;
		    j=j-1;
		    while (x < a[j]) j=j-1;
		    if(!(i < j))
		      return i;
		    exchange(a,i,j);
		    svp.visualPivot(pivotIndex);
		    i++;
		  }
	    }

	  private  int medianof3(int[] a, int lo, int mid, int hi)
	    {
	      if (a[mid] < a[lo])
		  {
	            if (a[hi] < a[mid])
		      return mid;
	            else
			{
			  if (a[hi] < a[lo])
			    return hi;
			  else
			    return lo;
			}
		  }
	      else
		  {
	            if (a[hi] < a[mid])
			{
			  if (a[hi] < a[lo])
			    return lo;
			  else
			    return hi;
			}
	            else
		      return mid;
		  }
	    }

	  /*
	   * Heapsort algorithm
	   */
	  private  void heapsort(int[] a, int lo, int hi)
	    {
	      int n = hi-lo;
	      for (int i=n/2; i>=1; i=i-1)
		  {
		    downheap(a,i,n,lo);
		  }
	      for (int i=n; i>1; i=i-1)
		  {
		    exchange(a,lo,lo+i-1);
		    downheap(a,1,i-1,lo);
		  }
	    }

	  private  void downheap(int[] a, int i, int n, int lo)
	    {
	      int d = a[lo+i-1];
	      int child;
	      while (i<=n/2)
		  {
		    child = 2*i;
		    if (child < n && a[lo+child-1] < a[lo+child])
			{
			  child++;
			}
		    if (d >= a[lo+child-1]) break;
		    
		    
		    svp.visualInsert(lo+i-1,a[lo+child-1]);
		    panelUI.setInfo("Introsort",accesses++,comparisons++);
			checkRunCondition();
			
		    a[lo+i-1] = a[lo+child-1];
		    i = child;
	
		  }
	      
	      svp.visualInsert(lo+i-1,d);
	      panelUI.setInfo("Introsort",accesses++,comparisons++);
		  checkRunCondition();
	      a[lo+i-1] = d;
	    }

	  /*
	   * Insertion sort algorithm
	   */
	  private  void insertionsort(int[] a, int lo, int hi)
	    {
	      int i,j;
	      int t;
	      for (i=lo; i < hi; i++)
		  {
	            j=i;
		    t = a[i];
		    while(j!=lo && t < a[j-1])
			{
		    	
		      svp.visualInsert(j,a[j-1]);
		      panelUI.setInfo("Introsort",accesses++,comparisons++);
			  checkRunCondition();
			  a[j] = a[j-1];
			  j--;
			}
		    
		    svp.visualInsert(j,t);
		    panelUI.setInfo("Introsort",accesses++,comparisons);
			checkRunCondition();
		    a[j] = t;
		  }
	    }

	  /*
	   * Common methods for all algorithms
	   */
	  private void exchange(int[] a, int i, int j)
	    {
	      int t=a[i];
	      a[i]=a[j];
	      a[j]=t;
	      
	      svp.visualCmp(i,j,true);
	      panelUI.setInfo("Introsort",accesses,comparisons++);
		  accesses+=3;
		  checkRunCondition();
	      
	    }

	  private  int floor_lg(int a)
	    {
	      return (int)(Math.floor(Math.log(a)/Math.log(2)));
	    }

	@Override
	public void run() {
		
		sort();
		setChanged();
		notifyObservers(panelUI.getID());

		if (flashing)
			svp.flashing();
		
	}

	@Override
	public SORTALGORITHMS getAlgorithmName() {
		return SORTALGORITHMS.Introsort;
	}
    


}
