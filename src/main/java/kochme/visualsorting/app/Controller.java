package kochme.visualsorting.app;

/*
 * This software is licensed under the MIT License.
 * Copyright 2018, Maurice Koch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.sun.security.auth.login.ConfigFile;
import kochme.visualsorting.algorithms.*;
import kochme.visualsorting.ui.dialogs.*;
import kochme.visualsorting.instruction.InstructionMediator;
import kochme.visualsorting.instruction.InstructionPlayback;
import kochme.visualsorting.ui.FramedElementsCanvas;
import kochme.visualsorting.ui.MainWindow;

public class Controller implements ComponentListener, ActionListener, WindowListener {
    private final List<InstructionMediator> instructionMediators;
    private final List<FramedElementsCanvas> elementsCanvases;
    private final BlockingQueue<Runnable> tasks;
    private final Timer timer;

    private InstructionPlayback instructionPlayback;
    private MainWindow mainWindow;
    private ExecutorService executor;
    private int[] elements;
    private MainWindow.State state;

    public Controller(int[] initElements) {
        state = MainWindow.State.EMPTY;
        elements = initElements;
        tasks = new LinkedBlockingQueue<>();
        instructionMediators = new ArrayList<>();
        elementsCanvases = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
        timer = new Timer();
        instructionPlayback = new InstructionPlayback(instructionMediators, elementsCanvases);
    }

    public void setView(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.addComponentListener(this);
    }

    public void reset() {
        timer.reset();
        instructionPlayback.halt();
        instructionMediators.forEach(oe -> oe.initElements(elements));
        elementsCanvases.forEach(ec -> ec.setElements(elements));
        state = MainWindow.State.STOPPED;
        mainWindow.setState(state);
    }

    private boolean executeTasks() {
        boolean terminated = false;
        try {
            instructionPlayback = new InstructionPlayback(instructionMediators, elementsCanvases);
            instructionPlayback.setDelay(Configuration.getExecutionSpeedDelayMs(), Configuration.getExecutionSpeedDelayNs());

            executor = Executors.newCachedThreadPool();
            tasks.forEach(executor::execute);
            executor.shutdown();
            terminated = executor.awaitTermination(1, TimeUnit.SECONDS);

            if (terminated) {
                timer.reset();
                timer.start();

                CompletableFuture.runAsync(instructionPlayback).thenAccept((s) -> {
                    EventQueue.invokeLater(() -> {
                        timer.stop();
                        state = MainWindow.State.STOPPED;
                        mainWindow.setState(state);
                    });
                });
            } else {
                System.err.println("Error - Timeout !");
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return terminated;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case Constants.ADD_SORT:
                    Constants.SortAlgorithm selectedSort = mainWindow.getSelectedSort();
                FramedElementsCanvas sortPanel = new FramedElementsCanvas(selectedSort, mainWindow.getWidth(), mainWindow.getHeight());

                final InstructionMediator instructionMediator = new InstructionMediator();
                final SortAlgorithm sort;

                sortPanel.setElements(elements);
                instructionMediator.initElements(elements);

                if (selectedSort.equals(Constants.SortAlgorithm.Heapsort))
                    sort = new HeapSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Bubblesort))
                    sort = new BubbleSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Quicksort_FIXED))
                    sort = new QuickSort(QuickSort.PivotStrategy.FIXED, instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Quicksort_RANDOM))
                    sort = new QuickSort(QuickSort.PivotStrategy.RANDOM, instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Quicksort_MO3))
                    sort = new QuickSort(QuickSort.PivotStrategy.MO3, instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Combsort))
                    sort = new CombSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Selectionsort))
                    sort = new SelectionSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Shakersort))
                    sort = new ShakerSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Mergesort))
                    sort = new MergeSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Shellsort))
                    sort = new ShellSort(instructionMediator);
                else if (selectedSort.equals(Constants.SortAlgorithm.Insertionsort))
                    sort = new InsertionSort(instructionMediator);
                else
                    sort = new HeapSort(instructionMediator);

                tasks.add(sort);
                elementsCanvases.add(sortPanel);
                instructionMediators.add(instructionMediator);
                mainWindow.addSortVisualizationPanel(sortPanel);

                sortPanel.setActionListenerForRemoveAction(
                        e1 -> {
                            if (instructionMediators.size() > 0) {
                                mainWindow.removeSortVisualizationPanel(((FramedElementsCanvas) e1.getSource()));
                                elementsCanvases.remove(((FramedElementsCanvas) e1.getSource()));
                                instructionMediators.remove(instructionMediator);
                                tasks.remove(sort);
                            }
                            if (instructionMediators.isEmpty()) {
                                state = MainWindow.State.EMPTY;
                                mainWindow.setState(state);
                            }
                        });

                state = MainWindow.State.STOPPED;
                mainWindow.setState(state);
                mainWindow.updateSize();
                break;
            case Constants.START:
                if (state == MainWindow.State.RUNNING) {
                    instructionPlayback.pause();
                    timer.stop();
                    state = MainWindow.State.PAUSED;
                    mainWindow.setState(state);
                } else if (state == MainWindow.State.PAUSED) {
                    instructionPlayback.play();
                    state = MainWindow.State.RUNNING;
                    mainWindow.setState(state);
                    timer.start();
                } else if (state == MainWindow.State.STOPPED) {
                    if (executeTasks()) {
                        timer.reset();
                        timer.start();

                        state = MainWindow.State.RUNNING;
                        mainWindow.setState(state);

                        instructionPlayback.play();
                    }
                    else {
                        System.err.println("Error - Timeout !");
                    }
                }
                break;
            case Constants.RESET:
                reset();
                break;
            case Constants.AUTO_PAUSE:
                Configuration.toggleAutoPause();
                break;
            case Constants.NEW_ELEMENTS:
                ElementEditorDialog.getInstance(this, 500, 300);
                break;
            case Constants.ABOUT:
                AboutDialog.getInstance(420, 500);
                break;
            case Constants.NEXT_ITERATION:
                if (state == MainWindow.State.STOPPED) {
                    if (executeTasks()) {
                        state = MainWindow.State.PAUSED;
                        mainWindow.setState(state);
                    }
                    else {
                        System.err.println("Error - Timeout !");
                    }
                }
                instructionPlayback.instructionStep();
                break;
            case Constants.DELAY:
                ExecutionSpeedDialog.getInstance(this, 320, 150);
                break;
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        timer.stop();
        instructionPlayback.halt();
        Configuration.saveChanges();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        /*
        if (InternalConfig.isAutoPauseEnabled() && !pausedByUser && state == MainWindow.State.PAUSED) {
            if (threadsAlive != 0) {
                timer.start();
                operationExecutors.forEach(OperationExecutor::resume);
            }
            state = MainWindow.State.RUNNING;
            mainWindow.setState(state);
        }
         */
    }

    @Override
    public void windowDeactivated(WindowEvent e) {}

    public void executionSpeedChanged(int delayMs, int delayNs) {
        Configuration.setExecutionSpeedParameters(delayMs, delayNs);
        instructionPlayback.setDelay(delayMs, delayNs);
    }

    public void elementsChanged(int[] elements) {
        this.elements = elements;

        instructionMediators.forEach(oe -> oe.initElements(elements));
        elementsCanvases.forEach(ec -> ec.setElements(elements));

        Configuration.setNumberOfElements(elements.length);
        mainWindow.updateNumberOfElements(elements.length);
        reset();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        mainWindow.updateSize();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    private class Timer {
        private final static int TIMER_DELTA_MS = 100;
        private final javax.swing.Timer appTimer;
        private int elapsedMs;

        public Timer() {
            elapsedMs = 0;
            appTimer = new javax.swing.Timer(TIMER_DELTA_MS, e -> {
                elapsedMs += TIMER_DELTA_MS;
                mainWindow.setExecutionTime(elapsedMs);
            });
            if (mainWindow != null) mainWindow.setExecutionTime(0);
        }

        public void reset() {
            elapsedMs = 0;
        }

        public void start() {
            appTimer.start();
        }

        public void stop() {
            appTimer.stop();
        }
    }
}
