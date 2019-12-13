package Ex1Testing;
/**
 * 

 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Polynom;
import Ex1.function;

class ComplexFunctionTest {
	public static final double EPS = 0.00001;
	@Test
	void test() {
		Monom m1 = new Monom(2,2);
		Monom m2 = new Monom(3,3);
		ComplexFunction cf = new ComplexFunction("plus", m1,m2);
	//	System.out.println(cf);
		cf.mul(m2);
		System.out.println(cf);
		Polynom p = new Polynom();
		p.add(m1);
		p.add(m2);
		p.multiply(m2);
		double v = 4.0;
		double dp = p.f(v);
		double dcf = cf.f(v);
		double dd = Math.abs(dp-dcf);
		if(dd>EPS) {
			System.out.println(p+" at "+v+" = "+dp);
			System.out.println(cf+" at "+v+" = "+dcf);
			fail("ERR: should got the same value from: "+p+"  and "+cf);
		
		}
	}

		@Test
		void testOfString() {
			Polynom p1 = new Polynom();
			p1.add( new Monom(2,2));
			Polynom p2 = new Polynom();
			p2.add(new Monom(3,3));
			Monom m1 = new Monom(2,2);
			Monom m2 = new Monom(3,3);
			ComplexFunction cf = new ComplexFunction("plus", m1,m2);
			ComplexFunction cf3 = new ComplexFunction("plus", p1,p2);
			//System.out.println(cf);
			cf.mul(m2);
			cf3.mul(m2);
			String s = cf.toString();
			function cf2 = cf.initFromString(s);
			if(!cf.equals(cf2)) {
				fail("ERR: "+cf+" should be equals to "+cf2);
			}
			if(!cf.equals(cf3)) {
				fail("ERR: "+cf+" should be equals to "+cf3);
			}
	}
	@Test
	void testComplexFunction() {
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x -1","x -2", "x -3", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		for(int i=1;i<s3.length;i++) {
			p3.multiply(new Polynom(s3[i]));
		}
		ComplexFunction cf = new ComplexFunction("plus", p1,p2); 
		ComplexFunction cf4 = new ComplexFunction("div", new Monom("x"),p3);
		cf.div(p1);
		String s = cf.toString();
		function cf5 = cf4.initFromString(s);
		if(!cf.equals(cf5)) {
			fail("ERR: "+cf+" should be equals to "+cf5);
		}
		int size=10;
		for(int i=0;i<size;i++) {
			double x = Math.random();
			double d = cf.f(x);
			double d5 = cf5.f(x);
			assertEquals(d,d5,EPS);
		}
		System.out.println(cf4);
		System.out.println(cf5);
	}
	@Test
	void testCopy() {
		Polynom p = new Polynom("15x^2-7x+5");
		Monom m = new Monom(2,4);
		function f1 = new ComplexFunction("mul", p, m);
		function f2 = f1.copy();

		assertEquals(f1, f2);

	}
	@Test
	void testComplexFunctionStringFunctionFunction() {
		Polynom p1 =new Polynom("2x^3+3x^6");
		Polynom p2 =new Polynom("3x^6");
		ComplexFunction cf1= new ComplexFunction("plus",p1,p2);
		function cf2=cf1.initFromString(cf1.toString());
		assertEquals(cf1,cf2);

	}
}
