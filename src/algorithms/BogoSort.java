package algorithms;

import main.OperationExecutor;
import main.Consts.SortAlgorithm;

public class BogoSort extends Sort {

    public BogoSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    void bogo() throws InterruptedException {
        while (!isSorted()) // we need to check the interrupt flag here
            shuffle();
    }

    void shuffle() throws InterruptedException {
        int i = operationExecutor.getNumberOfElements() - 1;
        while (i > 0)
            swap(i--, (int) (Math.random() * i));
    }

    void swap(int i, int j) throws InterruptedException {
        operationExecutor.exchange(i, j);
    }

    boolean isSorted() throws InterruptedException {

        for (int i = 1; i < operationExecutor.getNumberOfElements(); i++) {
            if (operationExecutor.compare(i, i - 1) == -1) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void run() {

        try {
            bogo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Bogosort;
    }

}
