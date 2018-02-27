package algorithms;


import main.OperationExecutor;
import main.Consts.SortAlgorithm;

public class CombSort extends Sort {

    public CombSort(OperationExecutor operationExecutor) {
        super(operationExecutor);
    }

    public void run() {
        try {
            float shrink = 1.3f;
            int i;
            int gap = operationExecutor.getNumberOfElements();
            boolean swapped = false;

            while (gap > 1 || swapped) {
                if (gap > 1) {
                    gap = (int) ((float) gap / shrink);
                }
                swapped = false;
                for (i = 0; gap + i < operationExecutor.getNumberOfElements(); ++i) {
                    if (operationExecutor.compare(i, i + gap) == 1) {
                        operationExecutor.exchange(i, i + gap);
                        swapped = true;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        operationExecutor.terminate();
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Combsort;
    }


}