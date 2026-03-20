package mx.com.endtoend.domain.warehouses.ports.api;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface WarehouseServicePort {

	ResponseModel findAllByCompanyCode(String companyCode);
}
