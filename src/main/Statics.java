package main;

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
*/

/**
 * This class is used as an interface between the View and the Controller.
 * Different static parameters are determined as well as action commands, that 
 * are important for the event handeling.
 * 
 * @author Maurice Koch
 * @version BETA
 * @category MVC
 * 

 */

public class Statics {
	

	public static enum SORTALGORITHMS {
		Heapsort, Bubblesort, Quicksort, BST, Combsort,Gnomesort, Shakersort, Mergesort, Bitonicsort,
		Radixsort, Shellsort, Insertionsort,Bogosort,Introsort;
		
		public static int length(){
			return SORTALGORITHMS.values().length;
		}
	};

	// actionlistener use actioncommands 
	public static final String ADD_SORT = "action_add";
	public static final String REMOVE_SORT = "action_remove";
	public static final String START = "action_start";
	public static final String NEW_ELEMENTS = "action_setElements";
	public static final String NEXT_ITERATION = "action_nextIter";
	public static final String RESET = "action_reset";
	
	public static final String DELAY = "action_delay";
	public static final String ABOUT = "action_about";
	public static final String INFO = "action_info";
	public static final String AUTO_PAUSE = "action_auto";

	public static final String LANG_DE = "action_de", LANG_EN = "action_en", LANG_FR = "action_fr";
	public static final String DIALOG_EXIT = "action_exitDialog";


	

}
