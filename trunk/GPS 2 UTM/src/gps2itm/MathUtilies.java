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

	/**
	 * No aTam2 in j2me. if you use j2se change this to ues the default one.
	 * @param y
	 * @param x
	 * @return
	 */
	 static public double aTan2(double y, double x) {
		return mMath.atan2(y, x);
	}
}
