package mx.com.endtoend.infrastructure.company.common.adapters;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.converters.MethodConverter;
import mx.com.endtoend.infrastructure.company.common.converters.PermissionCompanyConverter;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.company.common.entities.MethodEntity;
import mx.com.endtoend.infrastructure.company.common.entities.PermissionCompanyEntity;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.company.common.repositories.MethodRepository;
import mx.com.endtoend.infrastructure.company.common.repositories.PermissionCompanyRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import mx.com.endtoend.infrastructure.permissions.common.repository.PermissionRepository;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BaseCompanyJpaAdapter implements CompanyPersistencePort {

    private final Logger LOG;
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;
    private final MethodRepository methodRepository;
    private final MethodConverter methodConverter;
    private final PermissionCompanyConverter permissionCompanyConverter;
    private final PermissionCompanyRepository permissionCompanyRepository;
    private final PermissionRepository permissionRepository;
    private final PermissionConverter permissionConverter;

    public BaseCompanyJpaAdapter(Class<?> logger,
                                 CompanyRepository _companyRepository,
                                CompanyConverter _companyConverter,
                                MethodRepository _methodRepository,
                                MethodConverter _methodConverter,
                                PermissionCompanyConverter _permissionCompanyConverter,
                                PermissionCompanyRepository _permissionCompanyRepository,
                                PermissionRepository _permissionRepository,
                                PermissionConverter _permissionConverter) {
        LOG = LoggerFactory.getLogger(logger);
        companyRepository = _companyRepository;
        companyConverter = _companyConverter;
        methodRepository = _methodRepository;
        methodConverter = _methodConverter;
        permissionCompanyConverter = _permissionCompanyConverter;
        permissionCompanyRepository = _permissionCompanyRepository;
        permissionRepository = _permissionRepository;
        permissionConverter = _permissionConverter;
    }

    @Transactional
    @Override
    public ResponseModel saveToModifyCompany(CompanyDto companyDto, String idOperation) {

        try {

            LOG.info(String.format("%s CONVERT DTO TO ENTITY ", idOperation));
            CompanyEntity companyEntity = companyConverter.companyDtoEntity(companyDto);

            LOG.info(String.format("%s CONVERT AND SET METHODS ", idOperation));
            List<MethodEntity> methodEntityList = methodConverter
                    .methodDtoListToMethodEntityList(companyDto.getMethods());
            companyEntity.setMethods(methodEntityList);

            LOG.info(String.format("%s SAVE DATA", idOperation));
            companyEntity = companyRepository.save(companyEntity);

            LOG.info(String.format("%s CONVERT TO DTO", idOperation));
            CompanyDto companyDtoCreated = companyConverter.companyEntityToCompanyDto(companyEntity);

            return new ResponseModel(companyDtoCreated);

        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public ResponseModel existsCompany(CompanyDto companyDto, String idOperation) {
        try {
            LOG.info(String.format("%s FIND BY CODE", idOperation));
            boolean result = companyRepository.existsByCode(CompanyCodes.valueOf(companyDto.getCode()));
            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, result));
            return new ResponseModel(result);
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel findByCode(String companyCode, String idOperation) {
        try {
            Optional<CompanyEntity> companyEntity = companyRepository
                    .findByCompanyCode(CompanyCodes.valueOf(companyCode));
            if (companyEntity.isPresent()) {
                CompanyDto companyDto = companyConverter.companyEntityToCompanyDto(companyEntity.get());
                LOG.info(String.format("%s COMPANY FOUND: %s", idOperation, companyDto.toString()));
                return new ResponseModel(companyDto);
            } else {
                LOG.error(String.format("%s SERCH ERROR ", idOperation));
                throw new GlobalError();
            }
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel companyList(String idOperation) {
        try {
            List<CompanyDto> companyList = companyConverter
                    .companyEntityListToCompanyDtoList((List<CompanyEntity>) companyRepository.findAll());
            LOG.info(String.format("%s SIZE COMPANY LIST: %d", idOperation, companyList.size()));
            return new ResponseModel(companyList);
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel findAllByModule(String module, String idOperation) {
        try {
            List<MethodDto> methodDtoList = methodConverter
                    .methodEntityListToMethodDtoList(methodRepository.findAllByModule(module));
            LOG.info(String.format("%s SIZE METHOD LIST: %d BY MODULE %s", idOperation, methodDtoList.size(), module));
            return new ResponseModel(methodDtoList);
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel findById(Long id, String idOperation) {
        try {
            CompanyEntity companyEntity = companyRepository.findById(id);
            CompanyDto companyDto = new CompanyDto();
            if (companyEntity == null) {
                return new ResponseModel(companyDto.companyDtoEmpty());
            }
            companyDto = companyConverter.companyEntityToCompanyDto(companyEntity);
            List<MethodDto> methodDtoList = methodConverter.methodEntityListToMethodDtoList(companyEntity.getMethods());
            companyDto.setMethods(methodDtoList);

            List<PermissionDto> permissionList = new ArrayList<>();
            List<PermissionCompanyEntity> permisionCompanySavedLis = permissionCompanyRepository
                    .findAllByCompanyId(companyDto.getId());
            for (PermissionCompanyEntity permissionCompanyEntity : permisionCompanySavedLis) {
                PermissionEntity permissionEntity = permissionRepository
                        .findById(permissionCompanyEntity.getPermissionId());
                if (permissionEntity != null) {
                    permissionList.add(permissionConverter.permissionEntityToPermissionDto(permissionEntity));
                }
            }
            companyDto.setPermissions(permissionList);
            return new ResponseModel(companyDto);

        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel findByCompanyCodeAndModule(String companyCode, String module, String idOperation) {
        try {
            Optional<MethodEntity> method = methodRepository
                    .findByCompanyCodeAndModule(CompanyCodes.valueOf(companyCode), module);
            if (method.isPresent()) {
                MethodDto methodDto = methodConverter.methodEntityToMethodDto(method.get());
                LOG.info(String.format("%s METHOD FOUND: %s", idOperation, methodDto.toString()));
                return new ResponseModel(methodDto);
            } else {
                LOG.error(String.format("%s SERCH ERROR ", idOperation));
                throw new GlobalError();
            }
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel existsCompanyByCodeAndIdNot(CompanyDto companyDto, String idOperation) {
        try {
            Optional<CompanyEntity> companyOptional = companyRepository
                    .findByCompanyCodeAndIdNot(CompanyCodes.valueOf(companyDto.getCode()), companyDto.getId());
            if (companyOptional.isPresent()) {
                LOG.info(String.format("%s COMPANY FOUND", idOperation));
                return new ResponseModel(true);
            } else {
                LOG.info(String.format("%s COMPANY NOT FOUND ", idOperation));
                return new ResponseModel(false);
            }
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public void savePermissionToCompany(CompanyDto companyDto, String idOperation) {
        try {
            LOG.info(String.format("%s SAVE PERMISSION LIST", idOperation));
            for (PermissionDto permission : companyDto.getPermissions()) {
                PermissionCompanyEntity permissionCompanyEntity = permissionCompanyConverter
                        .permissionCompanyDtoToEntity(permission, companyDto.getId());
                permissionCompanyRepository.save(permissionCompanyEntity);
            }
        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public void updatePermissionCompanyList(CompanyDto companyDto, String idOperation) {
        try {

            LOG.info(String.format("%s DELETE PERMISSION SAVED BY COMPANY ID LIST", idOperation));
            List<PermissionCompanyEntity> permisionCompanySavedLis = permissionCompanyRepository
                    .findAllByCompanyId(companyDto.getId());
            for (PermissionCompanyEntity permissionCompanyEntity : permisionCompanySavedLis) {
                permissionCompanyRepository.delete(permissionCompanyEntity);
            }

            LOG.info(String.format("%s SAVE PERMISSION LIST", idOperation));
            for (PermissionDto permission : companyDto.getPermissions()) {
                PermissionCompanyEntity permissionCompanyEntity = permissionCompanyConverter
                        .permissionCompanyDtoToEntity(permission, companyDto.getId());
                permissionCompanyRepository.save(permissionCompanyEntity);
            }

        } catch (Exception e) {
            LOG.error(String.format("%s EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }
}
