package main;
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
import java.util.concurrent.*;

import algorithms.*;
import dialogs.AboutDialog;
import dialogs.DelayDialog;
import dialogs.EnterDialog;
import main.Consts.SortAlgorithm;


public class Controller implements ComponentListener, ActionListener, WindowListener {

    private boolean pausedByUser = false;

    private enum State {RUNNING, PAUSED, RESET}

    private State state = State.RESET;
    private ArrayList<OperationExecutor> operationExecutors;

    private Window window;
    private BlockingQueue<Runnable> tasks;
    private ExecutorService executor;
    private volatile int threadsAlive;
    private Timer timer;

    public Controller() {
        tasks = new LinkedBlockingQueue<>();
        operationExecutors = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
        timer = new Timer();
    }

    public void setView(Window window) {
        this.window = window;
        SortVisualisationPanel.setBackgroundColor(window.getBackground());
        window.addComponentListener(this);
        window.updateNumberOfElements(InternalConfig.getElements().length);
    }

    public void reset() {
        operationExecutors.forEach(OperationExecutor::reset);

        try {
            executor.shutdownNow();
            executor.awaitTermination(10000, TimeUnit.MILLISECONDS);

            if (!executor.isTerminated()) {
                System.err.println("Attempt to interrupt active threads failed (after 2000 ms timeout). Try again, please.");
            }

            timer.reset();
            operationExecutors.forEach(OperationExecutor::initElements);
            operationExecutors.forEach(v -> v.getSortVisualizationPanel().enableRemoveButton(true));

            window.unlockAddSort(true);
            threadsAlive = 0;
            pausedByUser = false;
            state = State.RESET;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == Consts.ADD_SORT) {
            Sort sort;
            SortAlgorithm selectedSort;

            operationExecutors.forEach(OperationExecutor::initElements);
            threadsAlive = 0;
            selectedSort = window.getSelectedSort();

            SortVisualisationPanel temp = new SortVisualisationPanel(selectedSort, window.getWidth(), window.getHeight(),
                    e1 -> {
                        if (operationExecutors.size() > 0) {
                            window.removeSortVisualizationPanel(((SortVisualisationPanel) e1.getSource()));
                            resize();
                        }
                    });

            OperationExecutor operationExecutor = new OperationExecutor(this, temp);
            operationExecutor.initElements(InternalConfig.getElements());
            operationExecutor.setDelay(InternalConfig.getExecutionSpeedDelayMs(), InternalConfig.getExecutionSpeedDelayNs());

            if (selectedSort.equals(SortAlgorithm.Heapsort))
                sort = new HeapSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Bubblesort))
                sort = new BubbleSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Quicksort_FIXED))
                sort = new QuickSort(QuickSort.PivotStrategy.FIXED, operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Quicksort_RANDOM))
                sort = new QuickSort(QuickSort.PivotStrategy.RANDOM, operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Quicksort_MO3))
                sort = new QuickSort(QuickSort.PivotStrategy.MO3, operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Combsort))
                sort = new CombSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Selectionsort))
                sort = new SelectionSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Shakersort))
                sort = new ShakerSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Mergesort))
                sort = new MergeSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Shellsort))
                sort = new ShellSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Insertionsort))
                sort = new InsertionSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Bogosort))
                sort = new BogoSort(operationExecutor);
            else if (selectedSort.equals(SortAlgorithm.Introsort))
                sort = new IntroSort(operationExecutor);
            else
                sort = new HeapSort(operationExecutor);

            operationExecutors.add(operationExecutor);
            tasks.add(sort);
            window.addSortVisualizationPanel(temp);
            resize();

        } else if (e.getActionCommand() == Consts.START) {
            if (state == State.RUNNING) {
                operationExecutors.forEach(OperationExecutor::pause);
                window.unlockManualIteration(true);
                timer.stop();
                pausedByUser = true;
                state = State.PAUSED;
            } else if (state == State.PAUSED) {
                window.unlockManualIteration(false);
                operationExecutors.forEach(OperationExecutor::resume);
                state = State.RUNNING;
                timer.start();
                pausedByUser = false;
            } else if (state == State.RESET) {
                if (executor.isTerminated()) {
                    executor = Executors.newCachedThreadPool();
                }
                operationExecutors.forEach(OperationExecutor::initElements);
                tasks.forEach(executor::execute);

                threadsAlive = tasks.size();

                operationExecutors.forEach(v -> v.getSortVisualizationPanel().enableRemoveButton(false));
                window.unlockManualIteration(false);
                window.unlockAddSort(false);

                timer.reset();
                timer.start();
                state = State.RUNNING;
            }
            window.toggleStartStop();
        } else if (e.getActionCommand() == Consts.RESET) {
            reset();
        } else if (e.getActionCommand() == Consts.AUTO_PAUSE) {
            InternalConfig.toggleAutoPause();
        } else if (e.getActionCommand() == Consts.NEW_ELEMENTS) {
            EnterDialog.getInstance(this, 500, 300);
        } else if (e.getActionCommand() == Consts.ABOUT) {
            AboutDialog.getInstance(400, 470);
        } else if (e.getActionCommand() == Consts.NEXT_ITERATION) {
            operationExecutors.forEach(OperationExecutor::executeNextStep);
        } else if (e.getActionCommand() == Consts.DELAY) {
            DelayDialog.getInstance(this, 320, 150);
        }
    }


    public void terminationSignal(OperationExecutor operationExecutor) {

        if (--threadsAlive == 0) {
            EventQueue.invokeLater(() -> {
                window.toggleStartStop();
                window.unlockAddSort(true);
                window.unlockManualIteration(true);
            });
            timer.stop();
            operationExecutors.forEach(v -> v.getSortVisualizationPanel().enableRemoveButton(true));
            state = State.RESET;
        }
        operationExecutor.getSortVisualizationPanel().setDuration(timer.getLeftSec(), timer.getLeftMs());
        System.out.println("Termination signal : " + threadsAlive);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        timer.stop();
        InternalConfig.saveChanges();
    }

    @Override
    public void windowActivated(WindowEvent e) {

        if (InternalConfig.isAutoPauseEnabled() && !pausedByUser && state == State.PAUSED) {
            if (threadsAlive != 0) {
                timer.start();
                operationExecutors.forEach(OperationExecutor::resume);
            }
            state = State.RUNNING;
            window.appReleased();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

        if (InternalConfig.isAutoPauseEnabled() && state == State.RUNNING) {
            if (threadsAlive != 0) {
                operationExecutors.forEach(OperationExecutor::pause);
                window.appStopped();
                timer.stop();
                state = State.PAUSED;
            }
        }
    }

    public void executionSpeedChanged(int delayMs, int delayNs) {
        InternalConfig.setExecutionSpeedParameters(delayMs, delayNs);
        operationExecutors.forEach(v -> v.setDelay(delayMs, delayNs));
    }

    public void elementsChanged(int[] elements) {
        InternalConfig.setElements(elements);
        window.updateNumberOfElements(elements.length);
        operationExecutors.forEach(v -> v.initElements(elements));
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

    private void resize() {
        window.updateSize();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resize();
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

        private int leftMs, leftSec;
        private javax.swing.Timer appTimer;

        public Timer() {
            leftMs = 0;
            leftSec = 0;

            appTimer = new javax.swing.Timer(10, e -> {
                leftMs += 10;
                if (leftMs == 1000) {
                    leftMs = 0;
                    leftSec++;
                }
                window.setClockParam(leftSec, leftMs);
            });
            if (window != null) window.setClockParam(0, 0);
        }

        public int getLeftMs() {
            return leftMs;
        }

        public int getLeftSec() {
            return leftSec;
        }

        public void reset() {
            leftMs = 0;
            leftSec = 0;
        }

        public void start() {
            appTimer.start();
        }

        public void stop() {
            appTimer.stop();
        }
    }
}
