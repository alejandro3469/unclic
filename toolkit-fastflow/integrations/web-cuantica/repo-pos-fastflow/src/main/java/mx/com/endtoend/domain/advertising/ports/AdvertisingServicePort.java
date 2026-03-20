package mx.com.endtoend.domain.advertising.ports;

import mx.com.endtoend.domain.advertising.dto.SaleAdvertisingInterfaceService;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface AdvertisingServicePort {

	ResponseModel createAdvertisingByMethod(SaleAdvertisingInterfaceService saleAdvertisingInterfaceService,
			AdvertisingDto advertisingDto, String method, String companyCode, String idOperation);

	ResponseModel viewAdvertisingListByMethod(SaleAdvertisingInterfaceService saleAdvertisingInterfaceService,
			String method, String companyCode, String idOperation);

	ResponseModel viewAdvertisingDetailByIdAndMethod(SaleAdvertisingInterfaceService saleAdvertisingInterfaceService,
			Long id, String method, String companyCode, String idOperation);

}
