package mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport;

import java.math.BigDecimal;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OrderJdeServicePort {

	ResponseModel getConsecutiveOrderNumberByCompanyCode(String companyCode, String branchCode, String companyNumber,
			String idOperation, String orderType);

	ResponseModel getIsItemAvailabilityByParams(BigDecimal articleNumber, String companyCode, String branchCode,
			String warehouseCode, double requestAmount, String unitMeasurement, String primaryUnitMeasure,
			String idOperation);

	ResponseModel getIsItemAvailabilityByParams(BigDecimal articleNumber, String companyCode, String branchCode,
												String warehouseCode, BigDecimal requestAmount, String unitMeasurement, String primaryUnitMeasure,
												String idOperation);

	ResponseModel getGenericClientNumberByCompanyCodeAndWarehouseCode(String companyCode, String companyNumber,
																	  String warehouseCode, String idOperation);

}
