package main;

public class MathFunc {

	public MathFunc() {
		// TODO Auto-generated constructor stub
	}
	
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
