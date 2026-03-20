package mx.com.endtoend.infrastructure.services.email;

import java.io.File;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface EmailServicePort {

	ResponseModel sendDocumentByCompanyCode(List<String> emailList, File document, String companyCode,
			String idOperation);
	
	void sendDocumentUnconfirmedByCompanyCode(List<String> emailList, File document, String companyCode,
			String idOperation);

}
