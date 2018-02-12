package algorithms;

import main.SortVisualisationPanel;
import main.Statics.SortAlgorithm;

public class BogoSort extends Sort {

    public BogoSort() {
        super();
    }

    void bogo() {
        while (!isSorted()) // we need to check the interrupt flag here
            shuffle();
    }

    void shuffle() {
        int i = elements.length - 1;
        while (i > 0)
            swap(i--, (int) (Math.random() * i));
    }

    void swap(int i, int j) {
        exchange(i, j);
    }

    boolean isSorted() {

        for (int i = 1; i < elements.length; i++) {
            if (compare(i, i - 1) == -1) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void run() {

        bogo();

        setChanged();
        notifyObservers(panelUI.getID());

        if (flashing)
            svp.visualTermination();

    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Bogosort;
    }

}
