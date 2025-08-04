package com.knowy.server.application.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KnowyExceptionTest {

	@Test
	void given_knowyException_when_thrown_then_hasUUID() {
		KnowyException knowyException = new KnowyException("Testing that Exception has UUID");

		Assertions.assertNotNull(knowyException.getExceptionUUID());
		Assertions.assertEquals(
			"Identified exception with uuid " + knowyException.getExceptionUUID() + " - Testing that Exception has " +
				"UUID",
			knowyException.getMessage());
	}

	@Test
	void given_knowyExceptions_when_thrown_then_haveUniqueUUIDs() {
		KnowyException knowyException1 = new KnowyException("Testing that Exception 1 has UUID");
		KnowyException knowyException2 = new KnowyException("Testing that Exception 2 has UUID");

		Assertions.assertNotEquals(knowyException1.getExceptionUUID(), knowyException2.getExceptionUUID());
	}
}
