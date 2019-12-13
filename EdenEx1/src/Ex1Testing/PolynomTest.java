package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;

class PolynomTest {

	@Test
	void derivative() {
		Polynom m = new Polynom("x+2");
		Polynom m1 = new Polynom("-x^3+4x^2+3x-1");
		Polynom m2 = new Polynom("4");
		Polynom expect = new Polynom("1");
		Polynom expect1 = new Polynom("-3x^2+8x+3");
		Polynom expect2 = new Polynom("0");
		assertTrue(m.derivative().equals(expect));
		assertTrue(m1.derivative().equals(expect1));
		assertTrue(m2.derivative().equals(expect2));
	}
	@Test
	  void constructorTest(){
	        String str="2x^2+3x^3-4x^4";
	        Polynom testUnite= new Polynom(str);
	        Polynom expectedPoly= new Polynom();
	        expectedPoly.add(new Monom(2,2));
	        expectedPoly.add(new Monom(3,3));
	        expectedPoly.add(new Monom(-4,4));
	        assertEquals(testUnite, expectedPoly);
	    }
	@Test
	void testAddPolynom_able() {
		Polynom p =new Polynom("3x^2+5x+2");
		Polynom p1 =new Polynom("6x^3+7x^2+2");
		 p.add(p1);
		assertEquals(new Polynom("6x^3+10x^2+5x+4"),p);
	}
}
