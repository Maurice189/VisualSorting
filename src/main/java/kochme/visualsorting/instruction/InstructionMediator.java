package kochme.visualsorting.instruction;

import java.util.LinkedList;
import java.util.Queue;

public class InstructionMediator {
    public static enum InstructionType {INSERT, COMPARE, SWAP};

    private int[] elements;
    private Queue<InstructionInsert> insertQueue;
    private Queue<InstructionCompare> compareQueue;
    private Queue<InstructionSwap> swapQueue;
    private Queue<InstructionType> typeQueue;

    public void initElements(int[] elements) {
        this.typeQueue = new LinkedList<>();
        this.swapQueue = new LinkedList<>();
        this.insertQueue = new LinkedList<>();
        this.compareQueue = new LinkedList<>();
        this.elements = new int[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    public boolean hasNextInstruction() {
        return !typeQueue.isEmpty();
    }

    public InstructionType getTypeOfNextInstruction() {
        return typeQueue.poll();
    }

    public InstructionInsert getNextInsertInstruction() {
        return insertQueue.poll();
    }

    public InstructionCompare getNextCompareInstruction() {
        return compareQueue.poll();
    }

    public InstructionSwap getNextSwapInstruction() {
        return swapQueue.poll();
    }

    public void swap(int i, int j) {
        int tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;

        typeQueue.add(InstructionType.SWAP);
        swapQueue.add(new InstructionSwap(i, j));

    }

    public void insertByIndex(int i, int j) {
        typeQueue.add(InstructionType.INSERT);
        insertQueue.add(new InstructionInsert(i, elements[j]));
        elements[i] = elements[j];
    }

    public void insertByValue(int i, int value) {
        typeQueue.add(InstructionType.INSERT);
        insertQueue.add(new InstructionInsert(i, value));
        elements[i] = value;
    }

    public int compare(int[] x, int i, int j, boolean isPivot) {
        int result = 0;

        if (x[i] > x[j])
            result = 1;
        if (x[j] > x[i])
            result = -1;

        typeQueue.add(InstructionType.COMPARE);
        compareQueue.add(new InstructionCompare(i, j, isPivot));
        return result;
    }

    public int compare(int i, int j) {
        return compare(elements, i, j, false);
    }

    public int compareByPivot(int i, int j) {
        return compare(elements, i, j, true);
    }

    public int compareByValue(int i, int value) {
        int result = 0;

        if (elements[i] > value)
            result = 1;
        if (value > elements[i])
            result = -1;

        // TODO : How to visualize svp.visualCompare(i, value, false) ?
        return result;
    }

    public int getElementAtIndex(int index) {
        int result = -1;

        if (index < elements.length) {
            result = elements[index];
        }
        return result;
    }

    public int getNumberOfElements() {
        return elements.length;
    }
}
