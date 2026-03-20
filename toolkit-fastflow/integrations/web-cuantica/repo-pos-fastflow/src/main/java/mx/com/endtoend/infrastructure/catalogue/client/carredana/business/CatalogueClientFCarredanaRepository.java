package mx.com.endtoend.infrastructure.catalogue.client.carredana.business;

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
import mx.com.endtoend.infrastructure.catalogue.client.carredana.repositories.CFDIFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.client.carredana.repositories.ClientTypeFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.client.carredana.repositories.ContactMethodFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.client.carredana.repositories.RegimeFiscalFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.client.carredana.repositories.WorkTypeFCarRepository;
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
public class CatalogueClientFCarredanaRepository implements GenericCatalogueClientRepository {

	@Autowired
	private ClientTypeFCarRepository clientTypeRepository;

	@Autowired
	private ClientTypeConverter clientTypeConverter;

	@Autowired
	private WorkTypeFCarRepository workTypeRepository;

	@Autowired
	private WorkTypeConverter workTypeConverter;

	@Autowired
	private RegimeFiscalFCarRepository regimeFiscalRepository;

	@Autowired
	private RegimeFiscalConverter regimeFiscalConverter;

	@Autowired
	private CFDIFCarRepository cfdiRepository;

	@Autowired
	private CFDIConverter cfdiConverter;

	@Autowired
	private ContactMethodFCarRepository contactMethodRepository;

	@Autowired
	private ContactMethodConverter contactMethodConverter;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueClientFCarredanaRepository.class);

	@Transactional
	@Override
	public ResponseModel getCfdiByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCfdiByCompanyCode()", idOperation));
			List<CFDIDto> cfdiDtoList = new ArrayList<CFDIDto>();
			List<CFDIEntity> cfdiEntityList = cfdiRepository.findAll();
			if (cfdiEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				cfdiDtoList = cfdiConverter.cfdiEntityListToCfdiDtoList(cfdiEntityList);
			}
			return new ResponseModel(cfdiDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getCfdiByCompanyCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getRegimeFiscalCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getRegimeFiscalCompanyCode()", idOperation));
			List<RegimeFiscalDto> regimeFiscalDtoList = new ArrayList<RegimeFiscalDto>();
			List<RegimeFiscalEntity> regimeFiscalEntityList = regimeFiscalRepository.findAll();
			if (regimeFiscalEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				regimeFiscalDtoList = regimeFiscalConverter
						.regimeFiscalEntityListToRegimeFiscalDtoList(regimeFiscalEntityList);
			}
			return new ResponseModel(regimeFiscalDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getRegimeFiscalCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getClientTypeCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getClientTypeCompanyCode()", idOperation));
			List<ClientTypeDto> clientTypeDtoList = new ArrayList<ClientTypeDto>();
			List<ClientTypeEntity> clientTypeEntityList = clientTypeRepository.findAll();
			if (clientTypeEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				clientTypeDtoList = clientTypeConverter.clientTypeEntityListToClientTypeDtoList(clientTypeEntityList);
			}
			return new ResponseModel(clientTypeDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getClientTypeCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getContectMethodCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getContectMethodCompanyCode()", idOperation));
			List<ContactMethodDto> contactMethodDtoList = new ArrayList<ContactMethodDto>();
			List<ContactMethodEntity> contactMethodEntityList = contactMethodRepository.findAll();
			if (contactMethodEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				contactMethodDtoList = contactMethodConverter
						.contactMethodEntityListToConctactMethodDtoList(contactMethodEntityList);
			}
			return new ResponseModel(contactMethodDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getContectMethodCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getWorkTypeByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getWorkTypeByCompanyCode()", idOperation));
			List<WorkTypeDto> workTypeDtoList = new ArrayList<WorkTypeDto>();
			List<WorkTypeEntity> workTypeEntityList = workTypeRepository.findAll();
			if (workTypeEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				workTypeDtoList = workTypeConverter.workTypeEntityListToWorkTypeDtoList(workTypeEntityList);
			}
			return new ResponseModel(workTypeDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getWorkTypeByCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueCfdiByCompanyCodeAndList(List<CFDIDto> cfdiDtoList, String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueCfdiByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<CFDIEntity> cfdiEntityList = cfdiConverter.cfdiDtoListToCfdiEntityList(cfdiDtoList);
			for (CFDIEntity cfdiEntity : cfdiEntityList) {
				try {
					cfdiRepository.save(cfdiEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, cfdiEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueCfdiByCompanyCodeAndList(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel updateCatalogueClientTypeByCompanyCodeAndList(List<ClientTypeDto> clientTypeDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueClientTypeByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;

			List<ClientTypeEntity> clientTypeEntityList = clientTypeConverter
					.clientTypeDtoListToClientTypeEntityList(clientTypeDtoList);
			for (ClientTypeEntity clientTypeEntity : clientTypeEntityList) {
				try {
					clientTypeRepository.save(clientTypeEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, clientTypeEntity.toString()));
				}
			}

			return new ResponseModel(totalRecord);

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueClientTypeByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel updateCatalogueRegimeFiscalByCompanyCodeAndList(List<RegimeFiscalDto> regimeFiscalDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueRegimeFiscalByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;

			List<RegimeFiscalEntity> regimeFiscalEntityList = regimeFiscalConverter
					.regimeFiscalDtoListToRegimeFiscalEntityList(regimeFiscalDtoList);
			for (RegimeFiscalEntity regimeFiscalEntity : regimeFiscalEntityList) {
				try {
					regimeFiscalRepository.save(regimeFiscalEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, regimeFiscalEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueRegimeFiscalByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel updateCatalogueContactMethodByCompanyCodeAndList(List<ContactMethodDto> contactMethodDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueContactMethodByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<ContactMethodEntity> contactMethodEntityList = contactMethodConverter
					.contactMethodtoListToContactMethodEntityList(contactMethodDtoList);
			for (ContactMethodEntity contactMethodEntity : contactMethodEntityList) {
				try {
					contactMethodRepository.save(contactMethodEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(
							String.format("%s ERROR IN UPDATE DATA: %s", idOperation, contactMethodEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueContactMethodByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel updateCatalogueWorkTypeByCompanyCodeAndList(List<WorkTypeDto> workTypeDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueWorkTypeByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<WorkTypeEntity> workTypeEntityList = workTypeConverter
					.workTypeDtoListToWorkTypeEntityList(workTypeDtoList);
			for (WorkTypeEntity workTypeEntity : workTypeEntityList) {
				try {
					workTypeRepository.save(workTypeEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(
							String.format("%s ERROR IN UPDATE DATA: %s", idOperation, workTypeEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueWorkTypeByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

}
