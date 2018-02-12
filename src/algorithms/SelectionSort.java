package algorithms;

import main.Statics;

public class SelectionSort extends Sort {

    @Override
    public Statics.SortAlgorithm getAlgorithmName() {
        return Statics.SortAlgorithm.Selectionsort;
    }

    @Override
    public void run() {
        for (int i = 0; i < elements.length - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < elements.length; j++) {
                if (compare(j, indexMin) == -1) {
                    indexMin = j;
                }
            }
            exchange(i, indexMin);
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();
    }
}