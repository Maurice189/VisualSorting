package kochme.visualsorting.algorithms;
import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.app.Constants;

/**
* Provides sorting using the BubbleSort Algorithm (https://en.wikipedia.org/wiki/Bubble_sort)
*/
public class BubbleSort extends SortAlgorithm {

    public BubbleSort(InstructionMediator instructionMediator) {
        super(instructionMediator);
    }

    public void run() {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < instructionMediator.getNumberOfElements() - 1; i++) {
                if (instructionMediator.compare(i, i + 1) == 1) {
                    instructionMediator.swap(i, i + 1);
                    swapped = true;
                }
            }
        } while (swapped);
    }

    @Override
    public Constants.SortAlgorithm getAlgorithmName() {
        return Constants.SortAlgorithm.Bubblesort;
    }

}
