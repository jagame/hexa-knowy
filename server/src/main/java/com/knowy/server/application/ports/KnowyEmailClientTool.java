package com.knowy.server.application.ports;

import com.knowy.server.application.exception.KnowyMailDispatchException;

public interface KnowyEmailClientTool {
	void sendEmail(String to, String subject, String body) throws KnowyMailDispatchException;
}
