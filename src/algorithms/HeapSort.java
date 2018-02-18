package algorithms;

/*
Visualsorting
Copyright (C) 2014  Maurice Koch

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

This sort algorithm is based on:
http://rosettacode.org/wiki/Sorting_algorithms/Heapsort#Java
(C) Ingy döt Net 

*/

// copied http://rosettacode.org/wiki/Sorting_algorithms/Heapsort#Java


/**
 * Implementation of the respective sort algorithm.
 *
 * @author maurice
 * @version BETA
 * @category Sort
 */

import main.SortVisualisationPanel;
import main.Statics.SortAlgorithm;

public class HeapSort extends Sort {

    public void heapSort() throws InterruptedException {
        int count = elements.length;

        //first place a in max-heap order
        heapify(elements, count);
        int end = count - 1;

        manualInstruction(2);
        while (end > 0) {
            //swap the root(maximum value) of the heap with the
            //last element of the heap
            exchange(0, end);

            //put the heap back in max-heap order
            siftDown(elements, 0, end - 1);
            //decrement the size of the heap so that the previous
            //max value will stay in its proper place
            end--;
            manualInstructionIncrement();
        }
    }

    public void heapify(int[] a, int count) throws InterruptedException {
        //start is assigned the index in a of the last parent node
        int start = (count - 2) / 2; //binary heap
        manualInstructionIncrement();

        while (start >= 0) {
            //sift down the node at index start to the proper place
            //such that all nodes below the start index are in heap
            //order
            siftDown(a, start, count - 1);
            start--;
            manualInstruction(2);
        }
        //after sifting down the root all nodes/elements are in heap order
    }

    public void siftDown(int[] a, int start, int end) throws InterruptedException {
        //end represents the limit of how far down the heap to sift
        int root = start;
        manualInstructionIncrement();

        while ((root * 2 + 1) <= end) {      //While the root has at least one child
            int child = root * 2 + 1;           //root*2+1 points to the left child
            manualInstructionIncrement();

            //if the child has a sibling and the child's value is less than its sibling's...
            if (child + 1 <= end && compare(child, child+1) == -1) {
                child = child + 1;           //... then point to the right child instead
            }

            if (compare(root, child) == -1) {     //out of max-heap order
                exchange(child, root);
                root = child;                //repeat to continue sifting down the child now
            } else {
                return;
            }

        }
    }

    public void run() {
        try {
            heapSort();
            setChanged();
            notifyObservers(panelUI.getID());

            if (flashing) svp.visualTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SortAlgorithm getAlgorithmName() {
        return SortAlgorithm.Heapsort;
    }

}
