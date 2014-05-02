
// TODO implementiere Grafikausgabe etc.
public class IntroSort extends Sort 
{
  /*
   * Class Variables
   */
  private static int size_threshold = 16;

  /*
   * Public interface
   */
  public void sort()
    {
      introsort_loop(0, elements.length, 2*floor_lg(elements.length));
    }

  public void sort(int begin, int end)
    {
      if (begin < end)
	  {
	    introsort_loop(begin, end, 2*floor_lg(end-begin));
	  }
    }

  /*
   * Quicksort algorithm modified for Introsort
   */
  private void introsort_loop (int lo, int hi, int depth_limit)
    {
      while (hi-lo > size_threshold)
	  {
	    if (depth_limit == 0)
		{
		  heapsort(lo, hi);
		  return;
		}
	    depth_limit=depth_limit-1;
	    int p=partition(lo, hi, medianof3(lo, lo+((hi-lo)/2)+1, hi-1));
	    introsort_loop(p, hi, depth_limit);
	    hi=p;
	  }
      insertionsort(lo, hi);
    }

  private  int partition(int lo, int hi, int x)
    {
      int i=lo, j=hi;
      while (true)
	  {
	    while (elements[i] < x) i++;
	    j=j-1;
	    while (x < elements[j]) j=j-1;
	    if(!(i < j))
	      return i;
	    exchange(i,j);
	    i++;
	  }
    }

  private int medianof3(int lo, int mid, int hi)
    {
      if (elements[mid] < elements[lo])
	  {
            if (elements[hi] < elements[mid])
	      return elements[mid];
            else
		{
		  if (elements[hi] < elements[lo])
		    return elements[hi];
		  else
		    return elements[lo];
		}
	  }
      else
	  {
            if (elements[hi] < elements[mid])
		{
		  if (elements[hi] < elements[lo])
		    return elements[lo];
		  else
		    return elements[hi];
		}
            else
	      return elements[mid];
	  }
    }

  /*
   * Heapsort algorithm
   */
  private void heapsort(int lo, int hi)
    {
      int n = hi-lo;
      for (int i=n/2; i>=1; i=i-1)
	  {
	    downheap(i,n,lo);
	  }
      for (int i=n; i>1; i=i-1)
	  {
	    exchange(lo,lo+i-1);
	    downheap(1,i-1,lo);
	  }
    }

  private void downheap(int i, int n, int lo)
    {
      int d = elements[lo+i-1];
      int child;
      while (i<=n/2)
	  {
	    child = 2*i;
	    if (child < n && elements[lo+child-1] < elements[lo+child])
		{
		  child++;
		}
	    if (d >= elements[lo+child-1]) break;
	    elements[lo+i-1] = elements[lo+child-1];
	    i = child;
	  }
      elements[lo+i-1] = d;
    }

  /*
   * Insertion sort algorithm
   */
  private void insertionsort(int lo, int hi)
    {
      int i,j;
      int t;
      for (i=lo; i < hi; i++)
	  {
            j=i;
	    t = elements[i];
	    while(j!=lo && t < elements[j-1])
		{
		  elements[j] = elements[j-1];
		  j--;
		}
	    elements[j] = t;
	  }
    }

  /*
   * Common methods for all algorithms
   */
  private void exchange(int i, int j)
    {
      int t=elements[i];
      elements[i]=elements[j];
      elements[j]=t;
      svp.drawElements(i, j, true);
      
    }

  private static int floor_lg(int a)
    {
      return (int)(Math.floor(Math.log(a)/Math.log(2)));
    }

public void run() {
	// TODO Auto-generated method stub
	
}


}
