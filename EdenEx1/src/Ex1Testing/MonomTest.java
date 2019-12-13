package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.function;

class MonomTest {

	@Test
	void derivative() {
		Monom m = new Monom("x");
		Monom m1 = new Monom("-x^3");
		Monom m2 = new Monom("4");
		Monom expect = new Monom("1");
		Monom expect1 = new Monom("-3x^2");
		Monom expect2 = new Monom("0");
		assertTrue(m.derivative().equals(expect));
		assertTrue(m1.derivative().equals(expect1));
		assertTrue(m2.derivative().equals(expect2));
	}

	@Test
	void multipy() {
		Monom m = new Monom("-1");
		Monom m2 = new Monom("4");
		Monom m3 = new Monom("4");
		m.multipy(m);
		assert (m.equals(new Monom("1")));
		m2.multipy(m3);
		assert (m2.equals(new Monom("16")));
		m2 = new Monom("4x");
		m3 = new Monom("5x");
		m2.multipy(m3);
		assert (m2.equals(new Monom("20x^2")));
		m2 = new Monom("4x^3");
		m3 = new Monom("5x^3");
		m2.multipy(m3);
		assert (m2.equals(new Monom("20x^6")));
	}

	@Test
	void testCopy() {
		Monom m = new Monom("2x^2");
		function f = m.copy();
		assertEquals(m, f);
	}
}
