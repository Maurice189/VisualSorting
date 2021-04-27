package kochme.visualsorting.instruction;

import kochme.visualsorting.ui.FramedElementsCanvas;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InstructionPlayback implements Runnable {
    private final List<InstructionMediator> instructionMediators;
    private final List<FramedElementsCanvas> elementsCanvases;
    private final AtomicInteger sleepMillis;
    private final AtomicInteger sleepNanos;

    private enum Status {PLAYING, PAUSED, STOPPED};
    private Status status;

    private final int[] accessCounts;
    private final int[] comparisonCounts;

    public InstructionPlayback(List<InstructionMediator> instructionMediators, List<FramedElementsCanvas> elementsCanvases) {
        this.instructionMediators = instructionMediators;
        this.elementsCanvases = elementsCanvases;
        this.sleepMillis = new AtomicInteger();
        this.sleepNanos = new AtomicInteger();
        this.status = Status.PLAYING;

        this.accessCounts = new int[elementsCanvases.size()];
        this.comparisonCounts = new int[elementsCanvases.size()];

        Arrays.fill(accessCounts, 0);
        Arrays.fill(comparisonCounts, 0);
    }

    public void setDelay(int sleepMillis, int sleepNanos) {
        this.sleepMillis.set(sleepMillis);
        this.sleepNanos.set(sleepNanos);
    }

    public void play() {
        this.status = Status.PLAYING;
    }

    public void pause() {
        this.status = Status.PAUSED;
    }

    public void halt() {
        this.status = Status.STOPPED;
    }

    @Override
    public void run() {
        boolean hasLeft = true;
        while(status != Status.STOPPED && hasLeft) {
            try {
                if (status == Status.PLAYING) {
                    hasLeft = instructionStep();
                }
                Thread.sleep(sleepMillis.get(), sleepNanos.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean instructionStep() {
        boolean leftInstructions = false;

        for (int idx = 0; idx < instructionMediators.size(); idx++) {
            FramedElementsCanvas elementsCanvas = elementsCanvases.get(idx);
            InstructionMediator instructionMediator = instructionMediators.get(idx);
            if (instructionMediator.hasNextInstruction()) {
                InstructionMediator.InstructionType type = instructionMediator.getTypeOfNextInstruction();
                switch (type) {
                    case INSERT: {
                        InstructionInsert inst = instructionMediator.getNextInsertInstruction();
                        elementsCanvas.visualInsert(inst.getFirst(), inst.getSecond());
                        accessCounts[idx] += inst.accessCount();
                        comparisonCounts[idx] += inst.compareCount();
                    }
                    break;
                    case COMPARE: {
                        InstructionCompare inst = instructionMediator.getNextCompareInstruction();
                        elementsCanvas.visualCompare(inst.getFirst(), inst.getSecond(), inst.isPivot());
                        accessCounts[idx] += inst.accessCount();
                        comparisonCounts[idx] += inst.compareCount();
                    }
                    break;
                    case SWAP: {
                        InstructionSwap inst = instructionMediator.getNextSwapInstruction();
                        elementsCanvas.visualExchange(inst.getFirstIdx(), inst.getSecondIdx());
                        accessCounts[idx] += inst.accessCount();
                        comparisonCounts[idx] += inst.compareCount();
                    }
                    break;
                }
                elementsCanvas.setInfo(accessCounts[idx], comparisonCounts[idx]);
                leftInstructions = true;
            }
        }
        return leftInstructions;
    }
}
