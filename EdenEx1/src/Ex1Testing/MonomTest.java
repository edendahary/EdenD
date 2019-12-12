package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;

class MonomTest {
	
	void fTest() {
		Monom m = new Monom(3, 2);
		double res = m.f(2);
		
		assertEquals(res, 12);
	}
	
	void funcTest() {
		
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
