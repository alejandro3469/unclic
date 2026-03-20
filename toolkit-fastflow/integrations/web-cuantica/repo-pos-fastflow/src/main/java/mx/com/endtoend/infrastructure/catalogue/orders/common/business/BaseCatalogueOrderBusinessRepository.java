package mx.com.endtoend.infrastructure.catalogue.orders.common.business;

import mx.com.endtoend.domain.catalogue.dto.CatalogueDto;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogueEntity;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;
import mx.com.endtoend.infrastructure.catalogue.orders.common.repository.BaseCatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.repository.BaseStatusRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.service.GenericCatalogueService;
import mx.com.endtoend.infrastructure.commons.constants.CategoryCodes;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class BaseCatalogueOrderBusinessRepository implements GenericCatalogueService {

    private final CatalogueConverter catalogueConverter;
    private final BaseCatalogueRepository catalogueRepository;
    private final BaseStatusRepository statusRepository;
    private final  StatusConverter statusConverter;
    private final Logger LOG;

    public BaseCatalogueOrderBusinessRepository(Class<?> loggerClass,
                                                CatalogueConverter _cataCatalogueConverter,
                                                BaseCatalogueRepository _catalogueRepository,
                                                BaseStatusRepository _statusRepository,
                                                StatusConverter _statusConverter){
        LOG = LoggerFactory.getLogger(loggerClass);
        catalogueConverter = _cataCatalogueConverter;
        catalogueRepository = _catalogueRepository;
        statusRepository = _statusRepository;
        statusConverter = _statusConverter;
    }


    @Override
    public ResponseModel getCatalogueByDrsyAndDrrt(String type, String idOperation) {

        LOG.info(String.format("%s INIT getCatalogueByDrsyAndDrrt() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ type: %s ]", idOperation, type));

        try {

            List<CatalogueDto> catalogList = catalogueConverter
                    .catalogClientTypeEntityListToCatalogClientTypeDtoList(catalogueRepository.findByType(type));

            if (catalogList != null) {
                LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
                return new ResponseModel(catalogList);

            }

            LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
            List<CatalogueDto> catalog = new ArrayList<CatalogueDto>();

            return new ResponseModel(catalog);

        } catch (Exception e) {

            LOG.error(idOperation + "-" + e.getMessage());

            throw new GlobalError();

        }
    }

    @Override
    public ResponseModel getStatus(Long id, String idOperation) {

        LOG.info(String.format("%s INIT getStatus() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s ]", idOperation, id));

        try {

            StatusDto statusDto = statusConverter.statusEntityToStatusDto(statusRepository.findById(id).get());

            if (statusDto != null) {
                LOG.error(String.format("%s Status search by id: %s", idOperation, id));
                return new ResponseModel(statusDto);
            }
            LOG.error(String.format("%s Status search by id: %s", idOperation, id));
            return new ResponseModel("");

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getStatus(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel createStatus(StatusDto statusDto, String idOperation) {
        LOG.info(String.format("%s INIT createStatus() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ statusDto: %s ]", idOperation, statusDto.toString()));

        try {

            statusRepository.save(statusConverter.statusDtoToStatusEntity(statusDto));

            if (statusDto != null)
                LOG.error(String.format("%s Status search by id: %s", idOperation, statusDto));
            return new ResponseModel(statusDto);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getStatus(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }
    }

    @Override
    public boolean existsStatus(StatusDto statusDto, String idOperation) {

        LOG.info(String.format("%s INIT existsStatus() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ statusDto: %s ]", idOperation, statusDto.toString()));

        try {

            boolean exists = statusRepository.existsByCode(statusDto.getCode());

            return exists;

        } catch (Exception e) {
            LOG.error(idOperation + "-" + e.getMessage());

            throw new GlobalError();
        }

    }

    @Override
    public ResponseModel getCfdi(String type, String idOperation) {

        LOG.info(String.format("%s INIT getCfdi() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ type: %s ]", idOperation, type));

        try {

            List<CatalogueDto> catalogList = catalogueConverter.catalogClientTypeEntityListToCatalogClientTypeDtoList(
                    (List<CatalogueEntity>) catalogueRepository.findByType(type));

            if (catalogList != null) {
                LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS CFDI");
                return new ResponseModel(catalogList);
            }

            LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS CFDI");
            List<CatalogueDto> res = new ArrayList<CatalogueDto>();

            return new ResponseModel(res);

        } catch (Exception e) {

            LOG.error(idOperation + "-" + e.getMessage());

            throw new GlobalError();

        }
    }

    @Transactional
    @Override
    public ResponseModel getFiscalRegimeCatalogue(String type, String idOperation) {

        LOG.info(String.format("%s INIT getFiscalRegimeCatalogue() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ type: %s ]", idOperation, type));

        try {

            List<CatalogueDto> catalogList = catalogueConverter.catalogClientTypeEntityListToCatalogClientTypeDtoList(
                    (List<CatalogueEntity>) catalogueRepository.findByType(type));

            if (catalogList != null) {
                LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS CFDI");
                return new ResponseModel(catalogList);
            }

            LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS CFDI");
            List<CatalogueDto> res = new ArrayList<CatalogueDto>();

            return new ResponseModel(res);

        } catch (Exception e) {

            LOG.error(idOperation + "-" + e.getMessage());

            throw new GlobalError();

        }
    }

    @Override
    public ResponseModel getCategoryListByCode(String categoryCode, String idOperation) {
        LOG.info(String.format("%s INIT getCategoryListByCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ categoryCode: %s ]", idOperation, categoryCode));
        try {

            List<CatalogueDto> res = new ArrayList<CatalogueDto>();

            CategoryCodes code = CategoryCodes.valueOf(categoryCode);

            switch (code) {

                case BRAND:

                    List<CatalogueDto> BRAND = catalogueConverter.catalogClientTypeEntityListToCatalogClientTypeDtoList(
                            (List<CatalogueEntity>) catalogueRepository.findByType(categoryCode));

                    if (BRAND != null) {
                        LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS BRAND");
                        return new ResponseModel(BRAND);
                    }

                    return new ResponseModel(res);

                case CATEGORY:

                    List<CatalogueDto> CATEGORY = catalogueConverter.catalogClientTypeEntityListToCatalogClientTypeDtoList(
                            (List<CatalogueEntity>) catalogueRepository.findByType(categoryCode));

                    if (CATEGORY != null) {
                        LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS CATEGORY");
                        return new ResponseModel(CATEGORY);
                    }

                    return new ResponseModel(res);

                case DIVISION:

                    List<CatalogueDto> DIVISION = catalogueConverter.catalogClientTypeEntityListToCatalogClientTypeDtoList(
                            (List<CatalogueEntity>) catalogueRepository.findByType(categoryCode));

                    if (DIVISION != null) {
                        LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS DIVISION");
                        return new ResponseModel(DIVISION);
                    }

                    return new ResponseModel(res);

                case FAMILY:

                    List<CatalogueDto> FAMILY = catalogueConverter.catalogClientTypeEntityListToCatalogClientTypeDtoList(
                            (List<CatalogueEntity>) catalogueRepository.findByType(categoryCode));

                    if (FAMILY != null) {
                        LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS FAMILY");
                        return new ResponseModel(FAMILY);
                    }

                    return new ResponseModel(res);

                default:
                    return null;

            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN getCategoryListByCode(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }
    }

    @Override
    public List<StatusDto> getStatusList(String idOperation) {
        LOG.info(String.format("%s INIT getStatusList() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ ]", idOperation));

        try {

            List<StatusEntity> statusEntityList = statusRepository.findAll();

            LOG.info(String.format("%s CONVERT STATUS LIST, SIZE: %d ", idOperation, statusEntityList.size()));

            List<StatusDto> statusDtoList = statusConverter.statusEntityListToStatusDtoList(statusEntityList);

            LOG.info(String.format("%s RETURN STATUS LIST", idOperation));

            return statusDtoList;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getStatusList(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }
    }

}
