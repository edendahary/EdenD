
package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real
 * number and a is an integer (summed a none negative), see:
 * https://en.wikipedia.org/wiki/Monomial The class implements function and
 * support simple operations as: construction, value at x, derivative, add and
 * multiply.
 * 
 * @author Boaz
 *
 */

public class Monom implements function {
	/**
	 * 
	 */
	
	public static final Monom ZERO = new Monom(0, 0);
	public static final Monom MINUS1 = new Monom(-1, 0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {
		return _Comp;
	}
	public Monom(double a, int b) {
		this.set_coefficient(a);
		this.set_power(b);
	}

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}

	public int get_power() {
		return this._power;
	}
	
	public function initFromString(String s) {
		s = s.replace(" ", "");
		function m = new Monom(s);
		return m;
	}
	
	public function copy() {
		return new Monom(get_coefficient(), get_power());
	}

	/**
	 * this method returns the derivative monom of this.
	 * 
	 * @return
	 */
	public Monom derivative() {
		if (this.get_power() == 0) {
			return getNewZeroMonom();
		}
		return new Monom(this.get_coefficient() * this.get_power(), this.get_power() - 1);
	}
	
	

	public double f(double x) {
		double ans = 0;
		double p = this.get_power();
		ans = this.get_coefficient() * Math.pow(x, p);
		return ans;
	}

	public boolean isZero() {
		return this.get_coefficient() == 0;
	}
	public Monom(String s) {
		if (s.contains("x")) {
			if (s.charAt(0) == 'x') {
				this.set_coefficient(1);
			} else if (s.substring(0, 2).equals("-x")) {
				this.set_coefficient(-1);
			} 
			else if(s.substring(0, 2).equals("+x")) {
				this.set_coefficient(1);
			} else {
				try {
					this.set_coefficient(Double.parseDouble(s.substring(0, s.indexOf("x"))));
				} catch (Exception e) {
					throw new RuntimeException("Eror");

				}
			}
		}
		if (!s.contains("x")) {
			this.set_power(0);
			try {
				this.set_coefficient(Double.parseDouble(s));
			} catch (Exception e) {
				throw new RuntimeException("Eror");
			}
		}
		if (s.contains("x^")) {
			try {
				this.set_power(Integer.parseInt(s.substring(s.indexOf("^") + 1, s.length())));
			} catch (Exception e) {
				throw new RuntimeException("Eror");
			}
		} else if (s.charAt(s.length() - 1) == 'x') {
			this.set_power(1);
		} else if (s.contains("x")) {
			throw new RuntimeException("Eror");
		}
	}

	public void add(Monom m) {
		if (this.get_power() == m.get_power()) {
			this.set_coefficient(this.get_coefficient() + m.get_coefficient());
		}
	}

	public void multipy(Monom d) {
		this.set_coefficient((this.get_coefficient())*(d.get_coefficient()));
		this.set_power(this.get_power()+ d.get_power());	
	}

	public String toString() {
		String ans = "";
		ans += this.get_coefficient();
		if (this.get_power() == 0) {
			return ans;
		} else if (this.get_power() == 1) {
			ans += "x";
		} else {
			ans += "x^" + this.get_power();
		}
		return ans;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Monom) {
			Monom oMonom = (Monom)o;
			return compDoubles(get_coefficient(), oMonom.get_coefficient()) && get_power() == oMonom.get_power();
		} else if (o instanceof Polynom) {
			Polynom poly = (Polynom)o;
			Polynom polyCopy = (Polynom)poly.copy();
			polyCopy.substract(this);
			return polyCopy.isZero();
		} else {
			return false;
		}
	}
	
	// you may (always) add other methods.

	// ****************** Private Methods and Data *****************

	private void set_coefficient(double a) {
		this._coefficient = a;
	}
	
	private static boolean compDoubles(double a, double b) {
		return Math.abs(a - b) <= EPSILON;
	}

	private void set_power(int p) {
		if (p < 0) {
			throw new RuntimeException("ERR the power of Monom should not be negative, got: " + p);
		}
		this._power = p;
	}

	private static Monom getNewZeroMonom() {
		return new Monom(ZERO);
	}

	private double _coefficient;
	private int _power;

}
