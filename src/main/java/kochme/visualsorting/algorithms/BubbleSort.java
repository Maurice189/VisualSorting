package kochme.visualsorting.algorithms;
import kochme.visualsorting.app.OperationExecutor;
import kochme.visualsorting.algorithms.SortAlgorithm;
import kochme.visualsorting.app.Consts;

/**
* Provides sorting using the BubbleSort Algorithm (https://en.wikipedia.org/wiki/Bubble_sort)
*/
public class BubbleSort extends SortAlgorithm {

    public BubbleSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    public void run() {
        try {
            boolean swapped;
            do {
                swapped = false;
                for (int i = 0; i < operationExecutor.getNumberOfElements() - 1; i++) {
                    if (operationExecutor.compare(i, i + 1) == 1) {
                        operationExecutor.exchange(i, i + 1);
                        swapped = true;
                    }
                }
            } while (swapped);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }

    @Override
    public Consts.SortAlgorithm getAlgorithmName() {
        return Consts.SortAlgorithm.Bubblesort;
    }

}
