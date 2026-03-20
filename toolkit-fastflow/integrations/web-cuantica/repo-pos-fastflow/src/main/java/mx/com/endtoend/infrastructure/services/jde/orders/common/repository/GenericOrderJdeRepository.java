package mx.com.endtoend.infrastructure.services.jde.orders.common.repository;

import java.math.BigDecimal;

public interface GenericOrderJdeRepository {

	BigDecimal getOrderNumberByOrderType(String orderType, String idOperation);

	BigDecimal getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(String orderType, String companyNumber,
			String idOperation);

	boolean getIsItemAvailabilityByParams(BigDecimal articleNumber, BigDecimal requestAmount, String unitMeasurement,
			String primaryUnitMeasure, String warehouseCode, String idOperation);

	BigDecimal getGenericClientNumberByWarehouseCode(String companyNumber, String warehouseCode, String idOperation);

}
