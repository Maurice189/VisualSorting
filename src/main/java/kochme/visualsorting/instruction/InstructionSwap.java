package kochme.visualsorting.instruction;

public class InstructionSwap implements Instruction {
    private final int firstIdx, secondIdx;

    public InstructionSwap(int firstIdx, int secondIdx) {
        this.firstIdx = firstIdx;
        this.secondIdx = secondIdx;
    }

    public int getSecondIdx() {
        return secondIdx;
    }

    public int getFirstIdx() {
        return firstIdx;
    }

    @Override
    public int accessCount() {
        return 3;
    }

    @Override
    public int compareCount() {
        return 0;
    }
}
