package algorithms;

import main.SortVisualisationPanel;
import main.Statics.SORTALGORITHMS;

public class BogoSort extends Sort {

    public BogoSort() {
        super();
    }

    void bogo() throws InterruptedException {
        while (!isSorted()) // we need to check the interrupt flag here
            shuffle();
    }

    void shuffle() throws InterruptedException {
        int i = elements.length - 1;
        while (i > 0)
            swap(i--, (int) (Math.random() * i));
    }

    void swap(int i, int j) throws InterruptedException {
        int temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;

        svp.visualCmp(i, j, true);
        //svp.setInfo("Bogosort", iterates++);
        panelUI.setInfo(accesses, comparisons);
        accesses += 3;

        checkRunCondition();
    }

    boolean isSorted() throws InterruptedException {

        for (int i = 1; i < elements.length; i++) {
            panelUI.setInfo(accesses, comparisons++);
            checkRunCondition();
            if (elements[i] < elements[i - 1]) {
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

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();

    }

    @Override
    public SORTALGORITHMS getAlgorithmName() {
        return SORTALGORITHMS.Bogosort;
    }

}
