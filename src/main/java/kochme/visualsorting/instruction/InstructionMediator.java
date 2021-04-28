package kochme.visualsorting.instruction;

import java.util.LinkedList;
import java.util.Queue;

public class InstructionMediator {
    public static enum InstructionType {INSERT, COMPARE, SWAP, INVISIBLE};

    private int[] elements;
    private Queue<Instruction> instructionQueue;
    private Queue<InstructionType> typeQueue;

    public void initElements(int[] elements) {
        this.instructionQueue = new LinkedList<>();
        this.typeQueue = new LinkedList<>();
        this.elements = new int[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    public boolean hasNextInstruction() {
        return !typeQueue.isEmpty();
    }

    public InstructionType getTypeOfNextInstruction() {
        return typeQueue.poll();
    }

    public Instruction getNextInstruction() {
        return instructionQueue.poll();
    }

    public void swap(int i, int j) {
        int tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;

        typeQueue.add(InstructionType.SWAP);
        instructionQueue.add(Instruction.swapInstance(i, j));
    }

    public void insertByIndex(int i, int j) {
        typeQueue.add(InstructionType.INSERT);
        instructionQueue.add(Instruction.insertInstance(i, elements[j]));
        elements[i] = elements[j];
    }

    public void insertByValue(int i, int value) {
        typeQueue.add(InstructionType.INSERT);
        instructionQueue.add(Instruction.insertInstance(i, value));
        elements[i] = value;
    }

    public int compare(int[] x, int i, int j, boolean isPivot) {
        int result = 0;

        if (x[i] > x[j])
            result = 1;
        if (x[j] > x[i])
            result = -1;

        typeQueue.add(InstructionType.COMPARE);
        instructionQueue.add(Instruction.compareInstance(i, j, isPivot));
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

        typeQueue.add(InstructionType.COMPARE);
        // We cannot compare with values but only with indices.
        instructionQueue.add(Instruction.compareInstance(i, i, false));
        return result;
    }

    public void insertOutOfPlace(int[] other, int i, int j) {
        other[i] = elements[j];
        typeQueue.add(InstructionType.INVISIBLE);
        instructionQueue.add(new Instruction(-1, -1, false, 2, 0));
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
    public int[] getElements() {
        return elements;
    }
}
