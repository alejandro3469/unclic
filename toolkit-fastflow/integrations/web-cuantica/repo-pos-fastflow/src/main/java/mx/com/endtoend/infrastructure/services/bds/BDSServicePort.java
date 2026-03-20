package mx.com.endtoend.infrastructure.services.bds;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface BDSServicePort {
	
	ResponseModel validStatusByTransactionId(String transactionId);

}
