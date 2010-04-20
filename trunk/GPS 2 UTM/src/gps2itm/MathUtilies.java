package gps2itm;

public class MathUtilies {
	
	static public double pow ( double base, int power)
	{
		double sum=1;
	
		for (int i = 0; i < power; i++ )
			sum *= base;
		
		return sum;
	}

}
