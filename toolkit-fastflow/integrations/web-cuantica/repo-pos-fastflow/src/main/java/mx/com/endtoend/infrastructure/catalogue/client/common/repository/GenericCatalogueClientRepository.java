package mx.com.endtoend.infrastructure.catalogue.client.common.repository;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericCatalogueClientRepository {

	ResponseModel getCfdiByCompanyCode(String idOperation);

	ResponseModel getRegimeFiscalCompanyCode(String idOperation);

	ResponseModel getClientTypeCompanyCode(String idOperation);

	ResponseModel getContectMethodCompanyCode(String idOperation);

	ResponseModel getWorkTypeByCompanyCode(String idOperation);

	ResponseModel updateCatalogueCfdiByCompanyCodeAndList(List<CFDIDto> cfdiDtoList, String idOperation);

	ResponseModel updateCatalogueRegimeFiscalByCompanyCodeAndList(List<RegimeFiscalDto> regimeFiscalDtoList,
			String idOperation);

	ResponseModel updateCatalogueClientTypeByCompanyCodeAndList(List<ClientTypeDto> clientTypeDtoList,
			String idOperation);

	ResponseModel updateCatalogueContactMethodByCompanyCodeAndList(List<ContactMethodDto> contactMethodDtoList,
			String idOperation);

	ResponseModel updateCatalogueWorkTypeByCompanyCodeAndList(List<WorkTypeDto> workTypeDtoList, String idOperation);
}
