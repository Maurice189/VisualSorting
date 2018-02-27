package algorithms;

import main.OperationExecutor;
import main.Consts.SortAlgorithm;

public class BubbleSort extends Sort {

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
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Bubblesort;
    }

}
