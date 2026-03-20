package mx.com.endtoend.domain.catalogue.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueClientPersistencePort {

	ResponseModel getCfdiByCompanyCode(String companyCode, String idOperation);

	ResponseModel updateCatalogueCfdiByCompanyCodeAndList(String companyCode, List<CFDIDto> cfdiDtoList,
			String idOperation);

	ResponseModel getRegimeFiscalCompanyCode(String companyCode, String idOperation);

	ResponseModel updateCatalogueRegimeFiscalByCompanyCodeAndList(String companyCode,
			List<RegimeFiscalDto> regimeFiscalDtoList, String idOperation);

	ResponseModel getClientTypeCompanyCode(String companyCode, String idOperation);

	ResponseModel updateCatalogueClientTypeByCompanyCodeAndList(String companyCode,
			List<ClientTypeDto> clientTypeDtoList, String idOperation);

	ResponseModel getContectMethodCompanyCode(String companyCode, String idOperation);

	ResponseModel updateCatalogueContactMethodByCompanyCodeAndList(String companyCode,
			List<ContactMethodDto> contactMethodDtoList, String idOperation);

	ResponseModel getWorkTypeByCompanyCode(String companyCode, String idOperation);

	ResponseModel updateCatalogueWorkTypeByCompanyCodeAndList(String companyCode, List<WorkTypeDto> workTypeDtoList,
			String idOperation);

}
