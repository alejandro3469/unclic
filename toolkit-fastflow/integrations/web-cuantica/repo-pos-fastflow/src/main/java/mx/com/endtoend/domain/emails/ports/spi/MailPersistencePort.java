package mx.com.endtoend.domain.emails.ports.spi;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface MailPersistencePort {

	ResponseModel mailList(String idOperation);
	
	Boolean mailFile(String entry, String subject, List<String> emails, String orderType, String idOperation,String companyCode);
	
	ResponseModel mail(String entry, String subject, List<String> emails, String idOperation,String companyCode);
}
