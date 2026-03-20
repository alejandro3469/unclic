package mx.com.endtoend.infrastructure.permissions.common.adapters;

import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.domain.roles.ports.spi.PermissionPersistencePort;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import mx.com.endtoend.infrastructure.permissions.common.repository.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BasePermissionJpaAdapter implements PermissionPersistencePort {

    private final Logger LOG;
    private final PermissionRepository permissionRepository;
    private final PermissionConverter permissionConverter;
    private final CompanyRepository companyRepository;

    public BasePermissionJpaAdapter(Class<?> loggerClass,
                                    PermissionRepository _permissionRepository,
                                    PermissionConverter _permissionConverter,
                                    CompanyRepository _companyRepository) {
        LOG = LoggerFactory.getLogger(loggerClass);
        permissionRepository = _permissionRepository;
        permissionConverter = _permissionConverter;
        companyRepository = _companyRepository;
    }

    @Override
    public PermissionDto create(PermissionDto permissionDto) {
        PermissionEntity permissionEntity = permissionConverter.permissionDtoToPermissionEntity(permissionDto);
        permissionEntity = permissionRepository.save(permissionEntity);
        if (permissionDto != null) {
            return permissionConverter.permissionEntityToPermissionDto(permissionEntity);
        } else {
            return null;
        }
    }

    @Override
    public List<PermissionDto> findAll() {
        LOG.info(String.format("INIT findAll()"));
        List<PermissionDto> permissionDtoList = permissionConverter
                .permissionEntityListToPermissionDtoList(permissionRepository.findAll());
        return permissionDtoList;
    }

    @Override
    public List<PermissionDto> findAllByRoleId(Long id) {
        LOG.info(String.format("INIT findAllByRoleId()"));
        LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));
        List<PermissionDto> permissionDtoList = permissionConverter
                .permissionEntityListToPermissionDtoList(permissionRepository.findAllByRoleId(id));
        return permissionDtoList;
    }

    @Override
    public List<PermissionDto> findAllByUserId(Long id) {
        LOG.info(String.format("INIT findAllByUserId()"));
        LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));
        List<PermissionDto> permissionDtoList = permissionConverter
                .permissionEntityListToPermissionDtoList(permissionRepository.findAllByUserId(id));
        return permissionDtoList;
    }

    @Override
    public PermissionDto findById(Long id) {
        LOG.info(String.format("INIT findById()"));
        LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));
        PermissionDto permissionDto = permissionConverter
                .permissionEntityToPermissionDto(permissionRepository.findById(id));
        return permissionDto;
    }

    @Override
    public PermissionDto findByName(String name) {
        LOG.info(String.format("INIT findByName()"));
        LOG.info(String.format("PARAMS: [name: %s ]", name));
        PermissionDto permissionDto = permissionConverter
                .permissionEntityToPermissionDto(permissionRepository.findByName(name));
        return permissionDto;
    }

    @Override
    public List<PermissionDto> findAllByModule(String module) {
        LOG.info(String.format("INIT findAllByModule()"));
        LOG.info(String.format("PARAMS: [module: %s ]", module));
        List<PermissionDto> permissionDtoList = permissionConverter
                .permissionEntityListToPermissionDtoList(permissionRepository.findAllByModule(module));
        return permissionDtoList;
    }

    @Override
    public List<PermissionDto> findAllExceptModule(String module) {
        LOG.info(String.format("INIT findAllExceptModule()"));
        LOG.info(String.format("PARAMS: [module: %s ]", module));
        List<PermissionDto> permissionDtoList = permissionConverter
                .permissionEntityListToPermissionDtoList(permissionRepository.findAllExcepModule(module));
        return permissionDtoList;
    }

    @Override
    public List<PermissionDto> findAllByCompanyCode(String companyCode) {
        try {
            Optional<CompanyEntity> companyOptional = companyRepository
                    .findByCompanyCode(CompanyCodes.valueOf(companyCode));
            if (companyOptional.isPresent()) {
                List<PermissionEntity> permissionList = permissionRepository
                        .findAllByCompanyId(companyOptional.get().getId());
                List<PermissionDto> permissionDtoList = permissionConverter
                        .permissionEntityListToPermissionDtoList(permissionList);
                return permissionDtoList;
            }
            return null;
        } catch (Exception e) {
            LOG.error(String.format("EXCEPTION: %s", e.getMessage()));
            throw new GlobalError();
        }

    }

}
