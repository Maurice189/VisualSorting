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
import gui.FramedSortPanel;
import gui.Window;


public class Controller implements ComponentListener, ActionListener, WindowListener {

    private boolean pausedByUser = false;

    private ArrayList<OperationExecutor> operationExecutors;

    private Window window;
    private BlockingQueue<Runnable> tasks;
    private ExecutorService executor;
    private volatile int threadsAlive;
    private Timer timer;

    private int[] elements;

    public Controller(int[] initElements) {
        elements = initElements;
        tasks = new LinkedBlockingQueue<>();
        operationExecutors = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
        timer = new Timer();
    }

    public void setView(Window window) {
        this.window = window;
        window.addComponentListener(this);
    }

    public void reset() {
        operationExecutors.forEach(OperationExecutor::reset);

        try {
            executor.shutdownNow();
            executor.awaitTermination(10000, TimeUnit.MILLISECONDS);

            if (!executor.isTerminated()) {
                System.err.println("Attempt to interrupt active threads failed (after 2000 ms timeout). Try again, please.");
                return;
            }

            timer.reset();
            operationExecutors.forEach(OperationExecutor::initElements);
            pausedByUser = false;
            window.setState(Window.State.STOPPED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == Consts.ADD_SORT) {
            SortAlgorithm selectedSort = window.getSelectedSort();
            FramedSortPanel temp = new FramedSortPanel(selectedSort, window.getWidth(), window.getHeight());

            final OperationExecutor operationExecutor = new OperationExecutor(this, temp);
            operationExecutor.initElements(elements);
            operationExecutor.setDelay(InternalConfig.getExecutionSpeedDelayMs(), InternalConfig.getExecutionSpeedDelayNs());

            final Sort sort;

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
            //else if (selectedSort.equals(SortAlgorithm.Introsort))
            //    sort = new IntroSort(operationExecutor);
            else
                sort = new HeapSort(operationExecutor);

            temp.setActionListenerForRemoveAction(
                    e1 -> {
                        if (operationExecutors.size() > 0) {
                            window.removeSortVisualizationPanel(((FramedSortPanel) e1.getSource()));
                            operationExecutors.remove(operationExecutor);
                            tasks.remove(sort);
                        }
                        if (operationExecutors.isEmpty()) {
                            window.setState(Window.State.EMPTY);
                        }
                    });

            operationExecutors.add(operationExecutor);
            window.addSortVisualizationPanel(temp);
            tasks.add(sort);
            window.updateSize();
            window.setState(Window.State.STOPPED);
        } else if (e.getActionCommand() == Consts.START) {
            if (window.getWindowState() == Window.State.RUNNING) {
                operationExecutors.forEach(OperationExecutor::pause);
                timer.stop();
                pausedByUser = true;
                window.setState(Window.State.PAUSED);
            } else if (window.getWindowState() == Window.State.PAUSED) {
                operationExecutors.forEach(OperationExecutor::resume);
                window.setState(Window.State.RUNNING);
                timer.start();
                pausedByUser = false;
            } else if (window.getWindowState() == Window.State.STOPPED) {
                if (executor.isTerminated()) {
                    executor = Executors.newCachedThreadPool();
                }
                operationExecutors.forEach(OperationExecutor::initElements);
                tasks.forEach(executor::execute);

                threadsAlive = tasks.size();

                timer.reset();
                timer.start();
                window.setState(Window.State.RUNNING);
            }
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
                window.setState(Window.State.STOPPED);
            });
            timer.stop();
        }
        operationExecutor.getSortVisualizationPanel().setDuration(timer.getLeftSec(), timer.getLeftMs());
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
        if (InternalConfig.isAutoPauseEnabled() && !pausedByUser && window.getWindowState() == Window.State.PAUSED) {
            if (threadsAlive != 0) {
                timer.start();
                operationExecutors.forEach(OperationExecutor::resume);
            }
            window.setState(Window.State.RUNNING);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        if (InternalConfig.isAutoPauseEnabled() && window.getWindowState() == Window.State.RUNNING) {
            if (threadsAlive != 0) {
                operationExecutors.forEach(OperationExecutor::pause);
                timer.stop();
                window.setState(Window.State.PAUSED);
            }
        }
    }

    public void executionSpeedChanged(int delayMs, int delayNs) {
        InternalConfig.setExecutionSpeedParameters(delayMs, delayNs);
        operationExecutors.forEach(v -> v.setDelay(delayMs, delayNs));
    }

    public void elementsChanged(int[] elements) {
        this.elements = elements;
        InternalConfig.setNumberOfElements(elements.length);
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

    @Override
    public void componentResized(ComponentEvent e) {
        window.updateSize();
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

        private final static int TIMER_DELAY_MS = 100;

        private int leftMs, leftSec;
        private javax.swing.Timer appTimer;

        public Timer() {
            leftMs = 0;
            leftSec = 0;

            assert (TIMER_DELAY_MS <= 1000);

            appTimer = new javax.swing.Timer(TIMER_DELAY_MS, e -> {
                leftMs += TIMER_DELAY_MS;
                if (leftMs >= 1000) {
                    leftMs -= 1000;
                    leftSec++;
                }
                window.setExecutionTime(leftSec, leftMs);
            });
            if (window != null) window.setExecutionTime(0, 0);
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
