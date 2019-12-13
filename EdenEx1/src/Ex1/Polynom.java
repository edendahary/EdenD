package Ex1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

import Ex1.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also
 * should support the following: 1. Riemann's Integral:
 * https://en.wikipedia.org/wiki/Riemann_integral 2. Finding a numerical value
 * between two values (currently support root only f(x)=0). 3. Derivative
 * 
 * @author Boaz
 *
 */

public class Polynom implements Polynom_able {
	/**
	 * 
	 */
	public static final Comparator<Monom> C = new Monom_Comperator();
	public static final double EPSILON = 0.0000001;
	private ArrayList<Monom> PolArr = new ArrayList<Monom>();

	/**
	 * Zero (empty polynom)
	 */
	public function initFromString(String s) {
		function m = new Polynom(s);
		return m;
		}

	public Polynom(Polynom copy) {
		for (int i = 0; i < copy.PolArr.size(); i++) {
			this.add(new Monom(copy.PolArr.get(i)));
		}
	}
	
	public Polynom() {
		// empty list or zero monom
	}

	/**
	 * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
	 * "(2x^2-4)(-1.2x-7.1)", "(3-3.4x+1)((3.1x-1.2)-(3X^2-3.1))"};
	 *
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		s = s.replace(" ", "");
		
		int n = 0;
		String f = "";
		if (s.charAt(0) == '-') {
			f = f + s.charAt(0);
			s = s.substring(1, s.length());
		}
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '+' || s.charAt(i) == '-') {
				Monom e = new Monom(s.substring(n, i));
				this.PolArr.add(e);
				n = i;
			} else if (i == s.length() - 1) {
				Monom e = new Monom(s.substring(n, s.length()));
				this.PolArr.add(e);
			}
		}
		if (!f.isEmpty()) {
			Monom a = new Monom(-1, 0);
			this.PolArr.get(0).multipy(a);
		}
		this.PolArr.sort(C);
		this.SortByPower();

	}

	private void SortByPower() {
		PolArr.sort(C);
		for (int i = 0; i < this.PolArr.size(); i++) {
			double x = this.PolArr.get(i).get_power();
			// helpSsortByPower(i, helpSortByPower(i, x));
			for (int j = i + 1; j < this.PolArr.size(); j++) {
				if (this.PolArr.get(j).get_power() == this.PolArr.get(i).get_power()) {
					this.PolArr.get(i).add(this.PolArr.get(j));
					this.PolArr.remove(j--);
				}
			}
		}
	}

	public String toString() {
		String ans = "";
		for (int i = 0; i < PolArr.size(); i++) {
			if (this.PolArr.get(i).get_coefficient() == 0) {
				;
			} else if (this.PolArr.get(i).get_coefficient() > 0 && i > 0) {
				ans += "+" + PolArr.get(i);
			} else {
				ans += PolArr.get(i);
			}

		}

		return ans;

	}

	@Override
	public double f(double x) {
		double sum = 0;
		for (int i = 0; i < PolArr.size(); i++) {
			sum += PolArr.get(i).f(x);
		}
		return sum;
	}

	@Override
	public void add(Polynom_able p1) {
		Polynom temp = new Polynom((Polynom) p1);
		for (int i = 0; i < temp.PolArr.size(); i++) {
			this.add((Monom)temp.PolArr.get(i).copy());
		}
		//
		this.PolArr.sort(C);
		this.SortByPower();
	}

	@Override
	public void add(Monom m1) {
		this.PolArr.add(m1);
		//
		this.PolArr.sort(C);
		this.SortByPower();
	}

	public void substract(Monom m1) {
		Monom minus = new Monom(-1, 0);
		Monom m1Copy = (Monom)m1.copy();
		m1Copy.multipy(minus);
		this.PolArr.add(m1Copy);
		//
		this.PolArr.sort(C);
		this.SortByPower();
	}

	@Override
	public void substract(Polynom_able p1) {
		Polynom temp = new Polynom((Polynom) p1);
		for (int i = 0; i < temp.PolArr.size(); i++) {
			this.substract(temp.PolArr.get(i));
		}
		//
	//	this.PolArr.sort(C);
	// .SortByPower();
	}

	@Override
	public void multiply(Polynom_able p1) {
		Polynom mult = new Polynom((Polynom) p1);
		String save = this.toString();
		int length = this.PolArr.size();
		this.PolArr.clear();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < mult.PolArr.size(); j++) {
				Polynom copy = new Polynom(save);
				copy.PolArr.get(i).multipy(mult.PolArr.get(j));
				this.add(copy.PolArr.get(i));
			}

		}
		//
		this.PolArr.sort(C);
		this.SortByPower();
	}

	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Polynom_able) {
			Polynom_able objectPoly = (Polynom_able)o;
			Polynom_able copyThis = copy();
			copyThis.substract(objectPoly);
			
			return copyThis.isZero();
			
		} else {
			if(o instanceof Monom) {
				Monom oMonom = (Monom)o;
				Polynom polyFromMonom = new Polynom();
				polyFromMonom.add(oMonom);
				return equals(polyFromMonom);
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean isZero() {
		for (int i = 0; i < this.PolArr.size(); i++) {
			if (this.PolArr.get(i).get_coefficient() > EPSILON) {
				return false;
			}
		}
		return true;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		
		if (f(x0) * f(x1) >= 0) 
		{ 
			throw new RuntimeException("Eror");
		} 

		double r = x0; 
		while ((x1-x0) >= eps) 
		{ 
			r = (x0+x1)/2; 

			if (f(r) == 0.0) {
				return r;}

			else if (f(r)*f(x0) < 0) {
				x1 = r; }
			else {
				x0 = r; 
			}
		} 
				
	return r;
	}

	@Override
	public Polynom_able copy() {
		Polynom_able copy = new Polynom();
		for (int i = 0; i < this.PolArr.size(); i++) {
			copy.add((Monom)this.PolArr.get(i).copy());
		}
		return copy;
	}

	@Override
	public Polynom_able derivative() {
		Polynom_able p = new Polynom();
		for (int i = 0; i < this.PolArr.size(); i++) {
			p.add(PolArr.get(i).derivative());		}
		return p;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double res = 0;
		for (; x0 < x1; x0 += eps) {
			res += Math.abs(f(x0) * eps);
		}
		return res;
	}

	@Override
	public void multiply(Monom m1) {
		ArrayList<Monom> newMonomList = new ArrayList<>();
		
		for(int i = 0; i < this.PolArr.size(); i++) {
			newMonomList.add((Monom)this.PolArr.get(i).copy());
			newMonomList.get(i).multipy(m1);
		}
		
		this.PolArr = newMonomList;
	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Monom> iterator() {
		// TODO Auto-generated method stub
		return PolArr.iterator();
	}
}