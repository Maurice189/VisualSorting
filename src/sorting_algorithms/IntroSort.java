package sorting_algorithms;

// copied from http://www.codecodex.com/wiki/Introsort

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
    private static Sortable[] a;  
    private static int size_threshold = 16;  
  
    /* 
     * Public interface 
     */  
    public void sort(Sortable[] a0)  
    {  
        a=a0;  
        introsort_loop(0, a.length, 2*floor_lg(a.length));  
        insertionsort(0, a.length);  
    }  
  
    public void sort(Sortable[] a0, int begin, int end)  
    {  
        if (begin < end)  
        {  
        a=a0;  
        introsort_loop(begin, end, 2*floor_lg(end-begin));  
        insertionsort(begin, end);  
        }  
    }  
  
    /* 
     * Quicksort algorithm modified for Introsort 
     */  
    private static void introsort_loop (int lo, int hi, int depth_limit)  
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
    }  
  
    private static int partition(int lo, int hi, Sortable x)  
    {  
        int i=lo, j=hi;  
        while (true)  
        {  
            while (a[i].smaller(x)) i++;  
            j=j-1;  
            while (x.smaller(a[j])) j=j-1;  
            if(!(i < j))  
            return i;  
            exchange(i,j);  
            i++;  
        }  
    }  
  
    private static Sortable medianof3(int lo, int mid, int hi)  
    {  
        if (a[mid].smaller(a[lo]))  
        {  
            if (a[hi].smaller(a[mid]))  
                return a[mid];  
            else  
            {  
                if (a[hi].smaller(a[lo]))  
            return a[hi];  
        else  
            return a[lo];  
            }  
        }  
    else  
    {  
            if (a[hi].smaller(a[mid]))  
            {  
                if (a[hi].smaller(a[lo]))  
            return a[lo];  
        else  
            return a[hi];  
            }  
            else  
                    return a[mid];  
    }  
    }  
  
    /* 
     * Heapsort algorithm 
     */  
    private static void heapsort(int lo, int hi)  
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
  
    private static void downheap(int i, int n, int lo)  
    {  
        Sortable d = a[lo+i-1];  
        int child;  
        while (i<=n/2)  
    {  
       child = 2*i;  
       if (child < n && a[lo+child-1].smaller(a[lo+child]))  
       {  
           child++;  
       }  
       if (!d.smaller(a[lo+child-1])) break;  
       a[lo+i-1] = a[lo+child-1];  
       i = child;  
    }  
    a[lo+i-1] = d;  
    }  
  
    /* 
     * Insertion sort algorithm 
     */  
    private static void insertionsort(int lo, int hi)  
    {  
    int i,j;  
    Sortable t;  
    for (i=lo; i < hi; i++)  
    {  
            j=i;  
        t = a[i];  
        while(j!=lo && t.smaller(a[j-1]))  
        {  
            a[j] = a[j-1];  
        j--;  
        }  
        a[j] = t;  
    }  
    }  
  
    /* 
     * Common methods for all algorithms 
     */  
    private static void exchange(int i, int j)  
    {  
        Sortable t=a[i];  
        a[i]=a[j];  
        a[j]=t;  
    }  
  
    private int floor_lg(int a)  
    {  
        return (int)(Math.floor(Math.log(a)/Math.log(2)));  
    }  
 
	public void run() {
		for(int i = 0; i<elements.length;i++){
			a[i] = elements[i];
		}
	}

	@Override
	public SORTALGORITHMS getAlgorithmName() {
		return null;
	}
	
    public interface Sortable  
    {  
        boolean smaller ( Sortable ob );  
    }  
    


}
