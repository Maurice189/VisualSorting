package algorithms;

// copied from http://www.cs.waikato.ac.nz/~bernhard/317/source/IntroSort.java

import main.OperationExecutor;
import main.Consts.SortAlgorithm;


//TODO : ADD VISUAL PIVOT

public class IntroSort extends Sort {
    public IntroSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return null;
    }

    @Override
    public void run() {

    }
    /*

    private int size_threshold = 16;
    private int pivotIndex;


    public IntroSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    public void sort() throws InterruptedException {
        introsort_loop(0, elements.length, 2 * floor_lg(elements.length));
    }

    public void sort(int begin, int end) throws InterruptedException {
        if (begin < end) {
            introsort_loop(begin, end, 2 * floor_lg(end - begin));
        }
    }


    private void introsort_loop(int lo, int hi, int depth_limit) throws InterruptedException {

        while (hi - lo > size_threshold) {
            if (depth_limit == 0) {
                heapsort(lo, hi);
                return;
            }
            depth_limit = depth_limit - 1;
            pivotIndex = medianof3(lo, lo + ((hi - lo) / 2) + 1, hi - 1);


            int p = partition(lo, hi, pivotIndex);
            introsort_loop(a, p, hi, depth_limit);
            hi = p;
        }
        insertionsort(lo, hi);

    }

    private int partition(int lo, int hi, int x) throws InterruptedException {
        int i = lo, j = hi;
        while (true) {
            while (operationExecutor.compare(i, x) == -1) {
                i++;
            }
            j = j - 1;
            while (operationExecutor.compare(x, j) == -1) {
                j = j - 1;
            }
            if (!(i < j))
                return i;
            operationExecutor.exchange(i, j);
            i++;
        }
    }

    private int medianof3(int lo, int mid, int hi) throws InterruptedException {
        if (operationExecutor.compare(mid, lo) == -1) {
            if (operationExecutor.compare(hi, mid) == -1)
                return mid;
            else {
                if (operationExecutor.compare(hi, lo) == -1)
                    return hi;
                else
                    return lo;
            }
        } else {
            if (operationExecutor.compare(hi, mid) == -1) {
                if (operationExecutor.compare(hi, lo) == -1)
                    return lo;
                else
                    return hi;
            } else
                return mid;
        }
    }

    private void heapsort(int lo, int hi) throws InterruptedException {
        int n = hi - lo;
        for (int i = n / 2; i >= 1; i = i - 1) {
            downheap(i, n, lo);
        }
        for (int i = n; i > 1; i = i - 1) {
            operationExecutor.exchange(lo, lo + i - 1);
            downheap(1, i - 1, lo);
        }
    }

    private void downheap(int i, int n, int lo) throws InterruptedException {

        int d = a[lo + i - 1];
        int child;

        while (i <= n / 2) {
            child = 2 * i;

            if (child < n && operationExecutor.compare(lo + child - 1, lo + child) == -1) {
                child++;
            }

            if (operationExecutor.compare(lo + i - 1, lo + child - 1) != -1) break;


            operationExecutor.insertByIndex(lo + i - 1,lo + child - 1);
            i = child;
        }

        operationExecutor.insertByIndex(lo + i - 1,lo + i - 1);
        svp.visualInsert(lo + i - 1, d);
        panelUI.setInfo(accesses++, comparisons++);
        checkRunCondition();
        a[lo + i - 1] = d;

    }

    private void insertionsort(int lo, int hi) throws InterruptedException {
        int i, j;
        int t;


        for (i = lo; i < hi; i++) {
            j = i;
            t = a[i];

            while (j != lo && t < a[j - 1]) {
                svp.visualInsert(j, a[j - 1]);
                panelUI.setInfo(accesses++, comparisons++);
                checkRunCondition();
                a[j] = a[j - 1];
                j--;
            }

            svp.visualInsert(j, t);
            panelUI.setInfo(accesses++, comparisons);
            checkRunCondition();
            a[j] = t;
        }
    }


    private void mexchange(int[] a, int i, int j) throws InterruptedException {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;

        svp.visualCmp(i, j, true);
        accesses += 3;
        checkRunCondition();

    }

    private int floor_lg(int a) {
        return (int) (Math.floor(Math.log(a) / Math.log(2)));
    }

    @Override
    public void run() {
        try {
            sort();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        operationExecutor.terminate();

    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Introsort;
    }

    */


}
