package com.knowy.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyTest {

	@Test
	void testSuma() {
		Assertions.assertEquals(2, suma(1, 1));
	}


	public static int suma(int one, int two) {
		return one + two;
	}
}
