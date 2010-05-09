package gps2itm;

public class MathUtilies {

	/**
	 * Raises base with the exponent
	 * 
	 * @param base
	 * @param exponent
	 * @return base raised in the exponent (base**exponent)
	 */
	static public double pow(double base, int exponent) {
		double product = 1;

		int positiveExponent = Math.abs(exponent);
		for (int i = 0; i < positiveExponent; i++)
			product *= base;

		if (exponent < 0)
			product = 1.0 / product;
		return product;
	}

	/**
	 * No aTam2 in j2me. if you use j2se change this to ues the default one.
	 * 
	 * @param y
	 * @param x
	 * @return
	 */
	static public double aTan2(double y, double x) {
		return mMath.atan2(y, x);
	}
}
