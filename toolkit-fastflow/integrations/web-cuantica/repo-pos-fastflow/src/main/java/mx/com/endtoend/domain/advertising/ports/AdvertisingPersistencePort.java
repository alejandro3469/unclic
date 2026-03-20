package mx.com.endtoend.domain.advertising.ports;

import java.math.BigDecimal;

import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SaleAdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SearchAdversitingParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface AdvertisingPersistencePort {

	ResponseModel createAdvertisingByCompanyCode(SaleAdvertisingDto saleAdvertisingDto, String companyCode,
			String idOperation);

	ResponseModel viewAdvertisingListByParamsCompanyCode(SearchAdversitingParamsDto searchAdversitingParams,
			String companyCode, String idOperation);

	ResponseModel viewAdvertisingDetailByIdAndCompanyCode(Long id, String companyCode, String idOperation);

	ResponseModel updateSatusByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String statusCode,
			String companyCode, String idOperation);

}
