package algorithms;

import main.Statics;

public class SelectionSort extends Sort {

    public SelectionSort() {
        super();
    }

    @Override
    public Statics.SORTALGORITHMS getAlgorithmName() {
        return Statics.SORTALGORITHMS.Selectionsort;
    }

    @Override
    public void run() {
        for (int i = 0; i < elements.length - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < elements.length; j++) {
                svp.visualCmp(j, indexMin, false);
                panelUI.setInfo(accesses, comparisons++);
                accesses += 2;

                if (elements[j] < elements[indexMin]) {
                    indexMin = j;
                }
                checkRunCondition();
            }

            int tmp = elements[i];
            elements[i] = elements[indexMin];
            elements[indexMin] = tmp;

            svp.visualCmp(i, indexMin, true);
            accesses += 2;
            panelUI.setInfo(accesses, comparisons);
        }

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();
    }
}