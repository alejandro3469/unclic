package mx.com.endtoend.infrastructure.catalogue.client.demo.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.infrastructure.catalogue.client.common.repository.GenericCatalogueClientRepository;
import mx.com.endtoend.infrastructure.catalogue.client.demo.repositories.CFDIDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.client.demo.repositories.ClientTypeDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.client.demo.repositories.ContactMethodDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.client.demo.repositories.RegimeFiscalDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.client.demo.repositories.WorkTypeDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.client.common.converters.CFDIConverter;
import mx.com.endtoend.infrastructure.catalogue.client.common.converters.ClientTypeConverter;
import mx.com.endtoend.infrastructure.catalogue.client.common.converters.ContactMethodConverter;
import mx.com.endtoend.infrastructure.catalogue.client.common.converters.RegimeFiscalConverter;
import mx.com.endtoend.infrastructure.catalogue.client.common.converters.WorkTypeConverter;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.CFDIEntity;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ClientTypeEntity;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ContactMethodEntity;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.RegimeFiscalEntity;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.WorkTypeEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueClientDemoRepository implements GenericCatalogueClientRepository {

	@Autowired
	private ClientTypeDemoRepository clientTypeRepository;

	@Autowired
	private ClientTypeConverter clientTypeConverter;

	@Autowired
	private WorkTypeDemoRepository workTypeRepository;

	@Autowired
	private WorkTypeConverter workTypeConverter;

	@Autowired
	private ContactMethodDemoRepository contactMethodRepository;

	@Autowired
	private ContactMethodConverter contactMethodConverter;

	@Autowired
	private RegimeFiscalDemoRepository regimeFiscalRepository;

	@Autowired
	private RegimeFiscalConverter regimeFiscalConverter;

	@Autowired
	private CFDIDemoRepository cfdiRepository;

	@Autowired
	private CFDIConverter cfdiConverter;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueClientDemoRepository.class);

	@Override
	public ResponseModel getCfdiByCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getCfdiByCompanyCode() for DEMO", idOperation);
			
			List<CFDIEntity> cfdiEntities = cfdiRepository.findAll();
			List<CFDIDto> cfdiDtos = cfdiConverter.cfdiEntityListToCfdiDtoList(cfdiEntities);
			
			LOG.info("{} DEMO - Retrieved {} CFDI items", idOperation, cfdiDtos.size());
			return new ResponseModel(cfdiDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getCfdiByCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getRegimeFiscalCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getRegimeFiscalCompanyCode() for DEMO", idOperation);
			
			List<RegimeFiscalEntity> regimeFiscalEntities = regimeFiscalRepository.findAll();
			List<RegimeFiscalDto> regimeFiscalDtos = regimeFiscalConverter.regimeFiscalEntityListToRegimeFiscalDtoList(regimeFiscalEntities);
			
			LOG.info("{} DEMO - Retrieved {} Regime Fiscal items", idOperation, regimeFiscalDtos.size());
			return new ResponseModel(regimeFiscalDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getRegimeFiscalCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getClientTypeCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getClientTypeCompanyCode() for DEMO", idOperation);
			
			List<ClientTypeEntity> clientTypeEntities = clientTypeRepository.findAll();
			List<ClientTypeDto> clientTypeDtos = clientTypeConverter.clientTypeEntityListToClientTypeDtoList(clientTypeEntities);
			
			LOG.info("{} DEMO - Retrieved {} Client Type items", idOperation, clientTypeDtos.size());
			return new ResponseModel(clientTypeDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getClientTypeCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getContectMethodCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getContectMethodCompanyCode() for DEMO", idOperation);
			
			List<ContactMethodEntity> contactMethodEntities = contactMethodRepository.findAll();
			List<ContactMethodDto> contactMethodDtos = contactMethodConverter.contactMethodEntityListToConctactMethodDtoList(contactMethodEntities);
			
			LOG.info("{} DEMO - Retrieved {} Contact Method items", idOperation, contactMethodDtos.size());
			return new ResponseModel(contactMethodDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getContectMethodCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getWorkTypeByCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getWorkTypeByCompanyCode() for DEMO", idOperation);
			
			List<WorkTypeEntity> workTypeEntities = workTypeRepository.findAll();
			List<WorkTypeDto> workTypeDtos = workTypeConverter.workTypeEntityListToWorkTypeDtoList(workTypeEntities);
			
			LOG.info("{} DEMO - Retrieved {} Work Type items", idOperation, workTypeDtos.size());
			return new ResponseModel(workTypeDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getWorkTypeByCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	// Update methods - Para DEMO implementamos con return success simple
	@Override
	public ResponseModel updateCatalogueCfdiByCompanyCodeAndList(List<CFDIDto> cfdiDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueCfdiByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update CFDI: {} items", idOperation, cfdiDtoList.size());
		return new ResponseModel("Update CFDI successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueRegimeFiscalByCompanyCodeAndList(List<RegimeFiscalDto> regimeFiscalDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueRegimeFiscalByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update Regime Fiscal: {} items", idOperation, regimeFiscalDtoList.size());
		return new ResponseModel("Update Regime Fiscal successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueClientTypeByCompanyCodeAndList(List<ClientTypeDto> clientTypeDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueClientTypeByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update Client Type: {} items", idOperation, clientTypeDtoList.size());
		return new ResponseModel("Update Client Type successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueContactMethodByCompanyCodeAndList(List<ContactMethodDto> contactMethodDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueContactMethodByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update Contact Method: {} items", idOperation, contactMethodDtoList.size());
		return new ResponseModel("Update Contact Method successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueWorkTypeByCompanyCodeAndList(List<WorkTypeDto> workTypeDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueWorkTypeByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update Work Type: {} items", idOperation, workTypeDtoList.size());
		return new ResponseModel("Update Work Type successful for DEMO");
	}

}
