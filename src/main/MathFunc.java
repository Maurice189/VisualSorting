package main;
/*
VisualSorting
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
public class MathFunc {
	
	public static int getRandomNumber(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
	
	public static int getMax(int values[]){
		
		int max = 0;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max)
				max = values[i];
		}
		
		return max;
	}

}
