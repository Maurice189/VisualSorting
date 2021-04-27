package kochme.visualsorting.instruction;

public class InstructionInsert implements Instruction {
    private final int first, second;

    public InstructionInsert(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getSecond() {
        return second;
    }

    public int getFirst() {
        return first;
    }

    @Override
    public int accessCount() {
        return 1;
    }

    @Override
    public int compareCount() {
        return 0;
    }
}
