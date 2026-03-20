package mx.com.endtoend.infrastructure.roles.common.adapters;

import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;
import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;
import mx.com.endtoend.infrastructure.roles.common.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class BaseRoleJpaAdapter implements RolePersistencePort {

    private final Logger LOG;
    private final RoleConverter roleConverter;
    private final RoleRepository roleRepository;
    private final PermissionConverter permissionConverter;

    public BaseRoleJpaAdapter(Class<?> loggerClass,
                              RoleConverter _roleConverter,
                              RoleRepository _roleRepository,
                              PermissionConverter _permissionConverter){
        LOG = LoggerFactory.getLogger(loggerClass);
        roleConverter = _roleConverter;
        roleRepository = _roleRepository;
        permissionConverter = _permissionConverter;
    }
    /**
     * Método para la inserción de roles en la BD principal del sistema.
     *
     * @param roleDto objeto que contiene el conjunto de permisos de acceso a cada
     *                uno de los endpoints del sistema y la descripción del rol
     *
     * @return RoleDto objeto con la incorporación del id generado por el sistema
     */
    @Transactional
    @Override
    public RoleDto create(RoleDto roleDto) {

        LOG.info(String.format("INIT create()"));



        RoleEntity roleEntity = roleConverter.roleDtoToRoleEntity(roleDto);

        List<PermissionEntity> permissionEntityList = permissionConverter
                .permissionDtoListToPermissionEntityList(roleDto.getPermissions());

        roleEntity.setPermissions(permissionEntityList);
        roleEntity = roleRepository.save(roleEntity);

        if (roleEntity != null) {

            return roleConverter.roleEntityToRoleDto(roleEntity);

        } else {

            return null;
        }

    }

    /**
     * Método para la actualización de roles en la BD principal del sistema
     *
     * @param roleDto objeto que contiene el conjunto de permisos de acceso a cada
     *                uno de los endpoints del sistema y la descripción del rol
     *
     * @return RoleDto objeto con la información actualizada
     */
    @Transactional
    @Override
    public RoleDto update(RoleDto roleDto) {

        LOG.info(String.format("INIT update()"));

        LOG.info(String.format("PARAMS: [roleDto: %s ]", roleDto.toString()));

        RoleEntity roleEntity = roleConverter.roleDtoToRoleEntity(roleDto);
        List<PermissionEntity> permissionEntityList = permissionConverter
                .permissionDtoListToPermissionEntityList(roleDto.getPermissions());
        roleEntity.setPermissions(permissionEntityList);
        roleEntity = roleRepository.save(roleEntity);
        if (roleEntity != null) {
            return roleConverter.roleEntityToRoleDto(roleEntity);
        } else {
            return null;
        }
    }

    /**
     * Método de consulta de la existencia de un rol por nombre y código de
     * compañía.
     *
     * @param name       nombre del rol a buscar
     * @param companyKey código de compañía
     *
     * @return valor boolean indicando al existencia o no del rol consultado
     */
    @Transactional
    @Override
    public boolean existsByNameAndCompany(String name, String companyKey) {

        LOG.info(String.format("INIT existsByNameAndCompany()"));

        LOG.info(String.format("PARAMS: [name: %s companyKey: %s ]", name, companyKey));

        Optional<RoleEntity> roleEntity = roleRepository.existsByNameAndCompanyKey(name, companyKey);

        if (roleEntity.isPresent()) {

            return true;
        } else {

            return false;
        }
    }

    /**
     * Método de consulta de la existencia de un rol por nombre, código de compañía
     * y id
     *
     * @param name       nombre del rol a buscar
     * @param companyKey código de compañía
     * @param id         identificador de rol
     *
     * @return valor boolean indicando al existencia o no del rol consultado
     */
    @Transactional
    @Override
    public boolean existsByNameAndCompanyAndIdNot(String name, String companyKey, Long id) {

        LOG.info(String.format("INIT existsByNameAndCompanyAndIdNot()"));

        LOG.info(String.format("PARAMS: [id: %s name: %s companyKey: %s ]", id.toString(), name, companyKey));

        return roleRepository.existsByNameAndCompanyKeyAndIdNot(name, companyKey, id);
    }

    /**
     * Método que busca todos los roles por código de compañía y estado en el
     * sistema
     *
     * @param enabled    indicador de estado
     * @param companyKey código de compañía
     *
     * @return Lista de roles obtenidos de la consulta realizada
     */
    @Transactional
    @Override
    public List<RoleDto> findAllByEnabledAndCompany(boolean enabled, String companyKey) {

        LOG.info(String.format("INIT findAllByEnabledAndCompany()"));

        LOG.info(String.format("PARAMS: [enabled: %s companyKey: %s ]", String.valueOf(enabled), companyKey));

        List<RoleDto> roleDtoList = roleConverter
                .roleEntityListToRoleDtoList(roleRepository.findAllByEnabledAndCompanyKey(enabled, companyKey));
        return roleDtoList;
    }

    /**
     * Método que busca todos los roles asigandos a un usuario por su identificador
     *
     * @param id identificador de usuario
     *
     * @return Lista de roles obtenidos de la consulta realizada
     */
    @Transactional
    @Override
    public List<RoleDto> findAllByUserId(Long id) {

        LOG.info(String.format("INIT findAllByUserId()"));

        LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));

        List<RoleDto> roleDtoList = roleConverter.roleEntityListToRoleDtoList(roleRepository.findAllByUserId(id));
        return roleDtoList;
    }

    /**
     * Método para la búsqueda de un rol por su identificador
     *
     * @param id identificador de rol
     *
     * @return RoleDto objeto con la información recuperada
     */
    @Transactional
    @Override
    public RoleDto findById(Long id) {

        LOG.info(String.format("INIT findById()"));

        LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));

        Optional<RoleEntity> roleEntity = roleRepository.findById(id);

        RoleDto roleDto = new RoleDto();
        if (roleEntity.isPresent()) {
            roleDto = roleConverter.roleEntityToRoleDto(roleEntity.get());
            return roleDto;
        } else {
            return roleDto.roleDtoEmpty();
        }



    }

    /**
     * Método que busca un rol por nombre y código de compañía.
     *
     * @param name       nombre del rol
     * @param companyKey código de compañía
     *
     * @return RoleDto objeto con la información recuperada
     */
    @Transactional
    @Override
    public RoleDto findByNameAndCompany(String name, String companyKey) {

        LOG.info(String.format("INIT findByNameAndCompany()"));

        LOG.info(String.format("PARAMS: [name: %s companyKey: %s ]", name, companyKey));

        Optional<RoleEntity> roleEntity = roleRepository.findByNameAndCompanyKey(name, companyKey);
        if (roleEntity.isPresent()) {
            RoleDto roleDto = roleConverter.roleEntityToRoleDto(roleEntity.get());
            return roleDto;
        } else {
            return null;
        }
    }

    /**
     * Método que actualiza el estado de un rol por su identificador en el sistema.
     *
     * @param id      identificador de rol
     * @param enabled indicador de estado
     *
     * @return oleDto objeto con la información actualizada
     */
    @Transactional
    @Override
    public RoleDto enableById(Long id, boolean enabled) {

        LOG.info(String.format("INIT enableById()"));

        LOG.info(String.format("PARAMS: [id: %s enabled: %s ]", id.toString(), String.valueOf(enabled)));

        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        if (roleEntity.isPresent()) {
            roleRepository.enableById(id, enabled);
            return roleConverter.roleEntityToRoleDto(roleEntity.get());
        } else {
            return null;
        }
    }

}
