package mx.com.endtoend.infrastructure.catalogue.client.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.catalogue.client.common.factory.CatalogueClientRepositoryFactory;
import mx.com.endtoend.infrastructure.catalogue.client.common.repository.GenericCatalogueClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueClientPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueClientJpaAdapter implements CatalogueClientPersistencePort {

    private final Logger LOG = LoggerFactory.getLogger(CatalogueClientJpaAdapter.class);
    @Autowired
    private CatalogueClientRepositoryFactory catalogueClientRepositoryFactory;

    @Override
    public ResponseModel getCfdiByCompanyCode(String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getCfdiByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.getCfdiByCompanyCode(idOperation);
    }

    @Override
    public ResponseModel getRegimeFiscalCompanyCode(String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getRegimeFiscalCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.getRegimeFiscalCompanyCode(idOperation);
    }

    @Override
    public ResponseModel getClientTypeCompanyCode(String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getClientTypeCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.getClientTypeCompanyCode(idOperation);
    }

    @Override
    public ResponseModel getContectMethodCompanyCode(String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getContectMethodCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.getContectMethodCompanyCode(idOperation);
    }

    @Override
    public ResponseModel getWorkTypeByCompanyCode(String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getWorkTypeByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.getWorkTypeByCompanyCode(idOperation);
    }

    @Override
    public ResponseModel updateCatalogueCfdiByCompanyCodeAndList(String companyCode, List<CFDIDto> cfdiDtoList,
                                                                 String idOperation) {
        LOG.info(String.format("%s INIT updateCatalogueCfdiByCompanyCodeAndList()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s , cfdiDtoList-Size: %d]", idOperation, companyCode,
                cfdiDtoList.size()));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.updateCatalogueCfdiByCompanyCodeAndList(cfdiDtoList, idOperation);
    }

    @Override
    public ResponseModel updateCatalogueRegimeFiscalByCompanyCodeAndList(String companyCode,
                                                                         List<RegimeFiscalDto> regimeFiscalDtoList, String idOperation) {
        LOG.info(String.format("%s INIT updateCatalogueRegimeFiscalByCompanyCodeAndList()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s , regimeFiscalDtoList-Size: %d]", idOperation, companyCode,
                regimeFiscalDtoList.size()));

        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);

        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.updateCatalogueRegimeFiscalByCompanyCodeAndList(regimeFiscalDtoList, idOperation);
    }

    @Override
    public ResponseModel updateCatalogueClientTypeByCompanyCodeAndList(String companyCode,
                                                                       List<ClientTypeDto> clientTypeDtoList, String idOperation) {
        LOG.info(String.format("%s INIT updateCatalogueClientTypeByCompanyCodeAndList()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s , clientTypeDtoList-Size: %d]", idOperation, companyCode,
                clientTypeDtoList.size()));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.updateCatalogueClientTypeByCompanyCodeAndList(clientTypeDtoList, idOperation);
    }

    @Override
    public ResponseModel updateCatalogueContactMethodByCompanyCodeAndList(String companyCode,
                                                                          List<ContactMethodDto> contactMethodDtoList, String idOperation) {

        LOG.info(String.format("%s INIT updateCatalogueContactMethodByCompanyCodeAndList()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s , contactMethodDtoList-Size: %d]", idOperation, companyCode,
                contactMethodDtoList.size()));

        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);

        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.updateCatalogueContactMethodByCompanyCodeAndList(contactMethodDtoList, idOperation);
    }

    @Override
    public ResponseModel updateCatalogueWorkTypeByCompanyCodeAndList(String companyCode,
                                                                     List<WorkTypeDto> workTypeDtoList, String idOperation) {
        LOG.info(String.format("%s INIT updateCatalogueWorkTypeByCompanyCodeAndList()", idOperation));
        LOG.info(String.format("%s PARAMS [companyCode: %s , workTypeDtoList-Size: %d]", idOperation, companyCode,
                workTypeDtoList.size()));
        GenericCatalogueClientRepository repository = catalogueClientRepositoryFactory.getRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        return repository.updateCatalogueWorkTypeByCompanyCodeAndList(workTypeDtoList, idOperation);
    }

}
