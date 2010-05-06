package gps2itm;

public class MathUtilies {
	
	/**
	 * Raises base with the exponent
	 * @param base
	 * @param exponent
	 * @return
	 */
	static public double pow ( double base, int exponent)
	{
		double sum=1;
	
		for (int i = 0; i < exponent; i++ )
			sum *= base;
		
		return sum;
	}

}
