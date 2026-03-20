package mx.com.endtoend.domain.advertising.business;

import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface SaleAdvertisingInterface {

	ResponseModel createAdvertisingByMethod(AdvertisingDto advertisingDto, String companyCode, String idOperation);

	ResponseModel viewAdvertisingListByMethod(String companyCode, String idOperation);

	ResponseModel viewAdvertisingDetailByIdAndMethod(Long id, String companyCode, String idOperation);

}
