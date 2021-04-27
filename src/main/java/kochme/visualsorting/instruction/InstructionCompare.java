package kochme.visualsorting.instruction;

public class InstructionCompare implements Instruction {
    private final int first, second;
    private final boolean isPivot;

    public InstructionCompare(int first, int second, boolean isPivot) {
        this.first = first;
        this.second = second;
        this.isPivot = isPivot;
    }

    public int getSecond() {
        return second;
    }

    public int getFirst() {
        return first;
    }

    public boolean isPivot() {
        return isPivot;
    }

    @Override
    public int accessCount() {
        return 2;
    }

    @Override
    public int compareCount() {
        return 1;
    }
}
