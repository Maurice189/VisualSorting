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
    private final Boolean[] terminated;

    public static enum Status {PLAYING, PAUSED, STOPPED};
    private Status status = Status.STOPPED;

    private final int[] accessCosts;
    private final int[] compareCosts;

    public InstructionPlayback(List<InstructionMediator> instructionMediators, List<FramedElementsCanvas> elementsCanvases) {
        this.instructionMediators = instructionMediators;
        this.elementsCanvases = elementsCanvases;
        this.sleepMillis = new AtomicInteger();
        this.sleepNanos = new AtomicInteger();
        this.accessCosts = new int[elementsCanvases.size()];
        this.compareCosts = new int[elementsCanvases.size()];
        this.terminated = new Boolean[instructionMediators.size()];

        Arrays.fill(accessCosts, 0);
        Arrays.fill(compareCosts, 0);
        Arrays.fill(terminated, false);
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
    public void halt() { this.status = Status.STOPPED; }

    @Override
    public void run() {
        this.status = Status.PAUSED;
        boolean hasLeftInstructions = true;
        while(status != Status.STOPPED && hasLeftInstructions) {
            try {
                if (status == Status.PLAYING) {
                    hasLeftInstructions = instructionStep();
                }
                Thread.sleep(sleepMillis.get(), sleepNanos.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isEligibleForStep(int j) {
        boolean eligible = true;
        for (int i = 0; i < accessCosts.length; i++) {
            if (i != j && !terminated[i]) {
                eligible &= accessCosts[j] <= accessCosts[i];
            }
        }
        return eligible;
    }

    public boolean instructionStep() {
        for (int idx = 0; idx < instructionMediators.size(); idx++) {
            FramedElementsCanvas elementsCanvas = elementsCanvases.get(idx);
            InstructionMediator instructionMediator = instructionMediators.get(idx);

            if (instructionMediator.hasNextInstruction() && isEligibleForStep(idx)) {
                InstructionMediator.InstructionType type = instructionMediator.getTypeOfNextInstruction();
                Instruction inst = instructionMediator.getNextInstruction();

                // These are the supported instructions for visualization
                switch (type) {
                    case INSERT: {
                        elementsCanvas.visualInsert(inst.getFirst(), inst.getSecond());
                    }
                    break;
                    case COMPARE: {
                        elementsCanvas.visualCompare(inst.getFirst(), inst.getSecond(), inst.getFlag());
                    }
                    break;
                    case SWAP: {
                        elementsCanvas.visualSwap(inst.getFirst(), inst.getSecond());
                    }
                }
                accessCosts[idx] += inst.accessCosts();
                compareCosts[idx] += inst.compareCosts();
                elementsCanvas.setInfo(accessCosts[idx], compareCosts[idx]);
            }
            else if (!terminated[idx] && !instructionMediator.hasNextInstruction()) {
                elementsCanvas.visualTermination();
                terminated[idx] = true;
            }
        }
        return Arrays.stream(terminated).anyMatch(b -> !b);
    }
}
