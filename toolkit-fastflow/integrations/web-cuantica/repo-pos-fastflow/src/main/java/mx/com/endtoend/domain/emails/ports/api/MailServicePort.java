package mx.com.endtoend.domain.emails.ports.api;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface MailServicePort {
	
	ResponseModel mailList(String idOperation);
	
}
