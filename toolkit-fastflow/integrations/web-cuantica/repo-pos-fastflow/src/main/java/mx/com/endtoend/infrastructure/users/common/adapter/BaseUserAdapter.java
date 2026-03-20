package mx.com.endtoend.infrastructure.users.common.adapter;

import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.dto.UserPaginationResponse;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;
import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class BaseUserAdapter implements UserPersistencePort {

    private final Logger LOG;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public BaseUserAdapter(Class<?> loggerClass, UserRepository _userRepository,
                           UserConverter _userConverter,RoleConverter _roleConverter,
                           BCryptPasswordEncoder _bCryptPasswordEncoder){
        LOG = LoggerFactory.getLogger(loggerClass);
        userRepository = _userRepository;
        userConverter = _userConverter;
        roleConverter = _roleConverter;
        bCryptPasswordEncoder = _bCryptPasswordEncoder;
    }
    /**
     * Método de búsqueda de usuarios por email, en caso de encontrar el usuario
     * retorna en el objeto ResponseModel los datos del usuario, de lo contrario
     * retorna un nulo.
     *
     * @param email       email para búsqueda
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel findByEmailToLogin(String email, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findByEmailToLogin()", idOperation));
            LOG.info(String.format("%s PARAMS: [email: %s ]", idOperation, email));

            LOG.info(String.format("%s SERCH USER TO LOGIN", idOperation));
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);

            if (userEntity.isPresent()) {

                UserDto userDto = userConverter.userEntityToUserDto(userEntity.get(), false);
                LOG.info(String.format("%s USER FOUND: %s", idOperation, userDto.toString()));

                return new ResponseModel(userDto);

            } else {

                LOG.error(String.format("%s SERCH ERROR: findByUsernameToLogin ", idOperation));
                return new ResponseModel(null);
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN SERCH USER TO LOGIN. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    /**
     * Método que actualiza el estado de la sesion de un usuario por nombre de
     * usuario y código de compañia, en caso de que el usuario no exista, lanzará
     * una excepción de error.
     *
     * @param email      nombre de usuario
     * @param branchCode código de compañía
     * @param active     indicador de estado para habilitar/deshabilitar el estado
     *                   de la sesión
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel updateStatusActiveByUserNameAndBranchCode(String email, String branchCode, boolean active) {

        try {

            LOG.info(String.format("INIT updateStatusActiveByUserNameAndBranchCode()"));
            LOG.info(String.format("PARAMS: [email: %s , branchCode: %s , active: %b ]", email, branchCode, active));

            LOG.info(String.format("CHANGE STATUS SESSION TO USER: %s AND BRANCH: %s TO NEW STATUS: %b", email,
                    branchCode, active));

            int result = 0;

            Optional<UserEntity> user = userRepository.findByEmailAndBranchCode(email, branchCode);

            if (!user.isPresent()) {
                LOG.info("ERROR IN CHANGE STATUS BY USER " + email);
                throw new GlobalError();
            }

            UserEntity userEntity = user.get();

            result = userRepository.enableSessionById(userEntity.getId(), active);

            if (result > 0) {

                LOG.info("UPDATE STATUS OK");
                return new ResponseModel(true);

            } else {

                LOG.info("ERROR IN UPDATE STATUS");
                throw new GlobalError();
            }

        } catch (Exception e) {

            LOG.error(String.format("ERROR IN UPDATE SESION STATUS. EXCEPTION: %s", e.getMessage()));
            throw new GlobalError();
        }

    }

    /**
     * Método para la persistencia de usuarios en el sistema, en caso de error
     * lanzará una excepción de error.
     *
     * @param userDto     módelo de datos con la información de los usuarios
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel create(UserDto userDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT create()", idOperation));
            LOG.info(String.format("%s PARAMS: [userDto: %s ]", idOperation, userDto.toString()));

            LOG.info(String.format("%s START SAVE NEW USER, DATA: %s ", idOperation, userDto.toString()));
            UserEntity userCreate = userConverter.userDtoToUserEntity(userDto, false);

            List<RoleEntity> roleEntityList = roleConverter.roleDtoListToRoleEntityList(userDto.getRoles());

            userCreate.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userCreate.setRoles(roleEntityList);
            userCreate = userRepository.save(userCreate);

            if (userCreate != null) {

                LOG.info(String.format("%s USER CREATED OK: %s", idOperation, userCreate.toString()));
                return new ResponseModel(userConverter.userEntityToUserDto(userCreate, true));

            } else {

                LOG.error(String.format("%s ERROR CREATING USER", idOperation));
                throw new GlobalError();
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR SAVING USER. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();

        }

    }

    /**
     * Método para la actualización de datos de un usuario, en caso de error lanzará
     * una excepción de error.
     *
     * @param userDto     módelo de datos con la información de los usuarios
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel update(UserDto userDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT update()", idOperation));
            LOG.info(String.format("%s PARAMS: [userDto: %s ]", idOperation, userDto.toString()));

            LOG.info(String.format("%s START UPDATE USER, DATA: %s ", idOperation, userDto.toString()));
            UserEntity userCreate = userConverter.userDtoToUserEntity(userDto, false);

            List<RoleEntity> roleEntityList = roleConverter.roleDtoListToRoleEntityList(userDto.getRoles());
            userCreate.setRoles(roleEntityList);

            if (userDto.getPassword() != "") {

                LOG.info(String.format("%s NEW PASSWORD TO USER", idOperation));
                userCreate.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

            } else {

                LOG.info(String.format("%s SAME PASSWORD TO USER", idOperation));
                userCreate.setPassword(userRepository.findPasswordById(userDto.getId()));
            }

            userCreate = userRepository.save(userCreate);

            if (userCreate != null) {

                LOG.info(String.format("%s USER UPDATED OK", idOperation));
                return new ResponseModel(userConverter.userEntityToUserDto(userCreate, true));

            } else {

                LOG.error(String.format("%s ERROR UPDATED USER", idOperation));
                throw new GlobalError();
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR UPDATED USER. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    /**
     * Método para habilitar/deshabilitar usuarios por id
     *
     * @param id          identificador de usuario
     * @param enable      indicador para habilitar/deshabilitar usuarios
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel enableById(Long id, boolean enable, String idOperation) {

        try {

            LOG.info(String.format("%s INIT enableById()", idOperation));
            LOG.info(String.format("%s PARAMS: [id: %s , enable: %b ]", idOperation, id.toString(), enable));

            Optional<UserEntity> userEntity = userRepository.findById(id);

            if (userEntity.isPresent()) {

                userRepository.enableById(id, enable);

                LOG.info(String.format("%s USER ENABLE UPDATE OK", idOperation));

                return new ResponseModel(userConverter.userEntityToUserDto(userEntity.get(), true));

            } else {

                LOG.error(String.format("%s ERROR UPDATED ENBALE STATUS TO USER", idOperation));

                return new ResponseModel(null);
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR UPDATED ENABLE STATUS TO USER. EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }
    }

    /**
     * Metodo para la búsqueda de usuarios por id, retorna true o false en el cuerpo
     * del ResponseModel. En caso de error lanzá una excepción de error.
     *
     * @param id          identificador de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel existsById(Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsById()", idOperation));
            LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id.toString()));

            LOG.info(String.format("%s VALIDATION existsById", idOperation));
            boolean valid = false;

            Optional<UserEntity> userEntity = userRepository.findById(id);
            valid = (userEntity.isPresent() ? true : false);

            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, valid));

            return new ResponseModel(valid);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN VALIDATION: existsById. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    /**
     * Método de búsqueda de usuarios por estado y código de sucursal, en caso de
     * error lanza una excepción de error.
     *
     * @param enable      indicador de estado de usuarios
     * @param branchCode  código de sucursal
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     *
     */
    @Transactional
    @Override
    public ResponseModel findAllByEnableAndBranchCode(boolean enable, String branchCode, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findAllByEnableAndBranchCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [enable: %b , branchCode: %s ]", idOperation, enable, branchCode));

            LOG.info(String.format("%s FIND USER LIST BY ENABLE %b AND BRANCH %s", idOperation, enable, branchCode));
            List<UserEntity> userEntityList = userRepository.findAllByEnableAndBranchCode(enable, branchCode);

            LOG.info(String.format("%s SIZE USER LIST: %d", idOperation, userEntityList.size()));

            return new ResponseModel(userConverter.userEntityListToUserDtoList(userEntityList, true));

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN SERCH USERS BY ENABLE AND BRANCH. EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }
    }

    /**
     * Método de búsqueda de usuarios por filtros y seccionados en páginas por
     * configuración de entrada
     *
     * @param pageNumber                número de página solicitada
     * @param rows                      número de registos solicitados
     * @param genericSerchParamsUserDto objeto con datos para filtro de búsqueda
     * @param idOperation               identificador de traza
     *
     * @returnResponseModel ResponseModel objeto con el código de estado de la
     *                      operación y datos generados por el sistema
     */
    @Override
    public ResponseModel findAllByParamsAndPage(int pageNumber, int rows,
                                                GenericSerchParamsUserDto genericSerchParamsUserDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findUserLitsByParamsAndPage()", idOperation));
            LOG.info(String.format("%s PARAMS: [pageNumber: %d , rows: %d , genericSerchParamsUserDto: %s ]",
                    idOperation, pageNumber, rows, genericSerchParamsUserDto.toString()));

            LOG.info(String.format("%s INIT SERCH USERS BY PARAMS", idOperation));

            List<UserEntity> userEntityList = userRepository.findUserLitsByParams(genericSerchParamsUserDto);

            LOG.info(String.format("%s GENERATE PAGES", idOperation));
            PagedListHolder<UserEntity> pages = new PagedListHolder<>(userEntityList);
            pages.setPageSize(rows);

            LOG.info(String.format("%s RETURN PAGE %d - 1 ", idOperation, pageNumber));
            pages.setPage(pageNumber - 1);

            List<UserEntity> userEntityPageList = pages.getPageList();

            LOG.info(String.format("%s CONVERT USER LIST TO DTO", idOperation));
            List<UserDto> userLits = userConverter.userEntityListToUserDtoList(userEntityPageList, true);

            UserPaginationResponse userPaginationResponse = new UserPaginationResponse();

            userPaginationResponse.setUserList(userLits);
            userPaginationResponse.setTotalPage(pages.getPageCount());

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(userPaginationResponse);

        } catch (Exception e) {

            LOG.error(
                    String.format("%s ERROR IN findAllByParamsAndPage(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    /**
     * Método para la búsqueda de usuarios por id, en caso de error lanza una
     * excepción de error.
     *
     * @param id          identificador de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel findById(Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findById()", idOperation));
            LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id.toString()));

            LOG.info(String.format("%s SERCH USER BY ID: %d ", idOperation, id));
            Optional<UserEntity> userEntity = userRepository.findById(id);

            if (userEntity.isPresent()) {

                LOG.info(String.format("%s USER FOUND: %s ", idOperation, userEntity.get().getEmail()));
                return new ResponseModel(userConverter.userEntityToUserDto(userEntity.get(), true));

            } else {

                LOG.error(String.format("%s ERROR IN SERCH USER BY ID", idOperation));
                throw new GlobalError();
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN findById. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    /**
     * Método de búsqueda de password de usuarios por id. En caso de error lanza una
     * excepción de error.
     *
     * @param id          identificador de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel findPasswordById(Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findPasswordById()", idOperation));
            LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id.toString()));

            LOG.info(String.format("%s FIND PASSWORD BY USER", idOperation));
            String password = userRepository.findPasswordById(id);

            return new ResponseModel(password);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN SERCH PASSWORD. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    /**
     * Método para la búsqueda de usuarios por número de usuario configurado. En
     * caso de que no exista o error en la consulta, lanza una excepción de error.
     *
     * @param userNomber  número de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel findByUserNumber(Long userNomber, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findByUserNumber()", idOperation));
            LOG.info(String.format("%s PARAMS: [userNomber: %s ]", idOperation, userNomber.toString()));

            LOG.info(String.format("%s SERCH USER BY USERNUMBER", idOperation));
            Optional<UserEntity> userEntity = userRepository.findByUserNumber(userNomber);

            if (userEntity.isPresent()) {

                UserDto userDto = userConverter.userEntityToUserDto(userEntity.get(), false);
                LOG.info(String.format("%s USER FOUND: %s", idOperation, userDto.toString()));
                return new ResponseModel(userDto);

            } else {

                LOG.error(String.format("%s SERCH ERROR: findByUserNumber ", idOperation));
                throw new GlobalError();
            }

        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR IN SERCH USER BY USERNUMBER. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel findByUserNumberAndBranchCode(Long userNomber, String branchCode, String idOperation) {
        try {

            LOG.info(String.format("%s INIT findByUserNumberAndBranchCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [userNomber: %s , branchCode: %s]", idOperation, userNomber.toString(),
                    branchCode));

            LOG.info(String.format("%s SERCH USER BY USERNUMBER", idOperation));
            Optional<UserEntity> userEntity = userRepository.findByUserNumberAndBranchCode(userNomber, branchCode);

            if (userEntity.isPresent()) {

                UserDto userDto = userConverter.userEntityToUserDto(userEntity.get(), false);
                LOG.info(String.format("%s USER FOUND: %s", idOperation, userDto.toString()));
                return new ResponseModel(userDto);

            } else {
                LOG.warn(String.format("%s SERCH ERROR: findByUserNumberAndBranchCode ", idOperation));
                return new ResponseModel(null);
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH USER BY USERNUMBER AND BRANCHCODE. EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    /**
     * Método para la búsqueda de usuarios por numero configurado y código de
     * compañia, retorna true o false según sea el resultado de la búsqueda . En
     * caso de error lanza una excepción de error.
     *
     * @param userNumber  número de usuario
     * @param branchCode  código de sucursal
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel existsByUserNumberAndBranchCode(Long userNumber, String branchCode, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsByUserNumberAndBranchCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [userNumber: %s , branchCode: %s ]", idOperation, userNumber.toString(),
                    branchCode));

            boolean valid = false;
            Optional<UserEntity> userEntity = userRepository.findByUserNumberAndBranchCode(userNumber, branchCode);

            valid = (userEntity.isPresent() ? true : false);
            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, valid));

            return new ResponseModel(valid);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN SERCH USER BY USERNUMBER AND BRANCH. EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }
    }

    /**
     * Método para la búsqueda de usuarios por numero configurado, código de
     * sucursal y id distinto al configurado al usuario, retorna true o false según
     * el resultado de la operación. En caso de error lanza una excepción de error.
     *
     * @param userNumber  número de usuario
     * @param branchCode  código de sucursal
     * @param id          identificador de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Transactional
    @Override
    public ResponseModel existsByUserNumberAndBranchCodeAndIdNot(Long userNumber, String branchCode, Long id,
                                                                 String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsByUserNumberAndBranchCodeAndIdNot()", idOperation));
            LOG.info(String.format("%s PARAMS: [userNumber: %s , branchCode: %s , id: %s ]", idOperation,
                    userNumber.toString(), branchCode, id.toString()));

            LOG.info(String.format("%s VALIDATION existsByUserNumberAndBranchCodeAndIdNot", idOperation));
            boolean exists = false;

            Optional<UserEntity> userEntity = userRepository.findByUserNumberAndBranchCodeAndIdNot(userNumber,
                    branchCode, id);

            exists = (userEntity.isPresent() ? true : false);

            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, exists));

            return new ResponseModel(exists);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existsByUserNumberAndBranchCodeAndIdNot(). EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }

    }

    /**
     * Método para la búsqueda de usuarios por email, retorna true o false según el
     * resultado de la operación. En caso de error lanza una excepción de error.
     *
     * @param email       correo electrónico
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Override
    public ResponseModel existEmail(String email, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existEmail()", idOperation));
            LOG.info(String.format("%s PARAMS: [email: %s ]", idOperation, email));

            boolean exists = false;

            List<UserEntity> userEntity = userRepository.findByEmailInAllCompanies(email);

            exists = (userEntity.size() > 0 ? true : false);

            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, exists));

            return new ResponseModel(exists);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existEmail(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    /**
     * Método para la búsqueda de usuarios por email y id distinto al ingresado,
     * retorna true o false según el resultado de la oepración. En caso de error
     * lanza una excepción de error.
     *
     * @param email       correo electrónico
     * @param id          identificador de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Override
    public ResponseModel existEmailAndIdNot(String email, Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existEmailAndIdNot()", idOperation));
            LOG.info(String.format("%s PARAMS: [email: %s , id: %s ]", idOperation, email, id.toString()));

            boolean exists = false;

            List<UserEntity> userEntity = userRepository.findByEmailAndIdNot(email, id);

            exists = (userEntity.size() > 0 ? true : false);

            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, exists));

            return new ResponseModel(exists);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existEmailAndIdNot(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    /**
     * Método para la búsqueda de usuarios por número de usuario en todas las compañías,
     * retorna true o false según el resultado de la operación. En caso de error
     * lanza una excepción de error.
     *
     * @param userNumber  número de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Override
    public ResponseModel existsByUserNumber(Long userNumber, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsByUserNumber()", idOperation));
            LOG.info(String.format("%s PARAMS: [userNumber: %s ]", idOperation, userNumber.toString()));

            boolean exists = false;

            List<UserEntity> userEntity = userRepository.findByUserNumberInAllCompanies(userNumber);

            exists = (userEntity.size() > 0 ? true : false);

            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, exists));

            return new ResponseModel(exists);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existsByUserNumber(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    /**
     * Método para la búsqueda de usuarios por número de usuario y id distinto al ingresado
     * en todas las compañías, retorna true o false según el resultado de la operación.
     * En caso de error lanza una excepción de error.
     *
     * @param userNumber  número de usuario
     * @param id          identificador de usuario
     * @param idOperation identificador de traza
     *
     * @return ResponseModel ResponseModel objeto con el código de estado de la
     *         operación y datos generados por el sistema
     */
    @Override
    public ResponseModel existsByUserNumberAndIdNot(Long userNumber, Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsByUserNumberAndIdNot()", idOperation));
            LOG.info(String.format("%s PARAMS: [userNumber: %s , id: %s ]", idOperation, userNumber.toString(), id.toString()));

            boolean exists = false;

            List<UserEntity> userEntity = userRepository.findByUserNumberAndIdNot(userNumber, id);

            exists = (userEntity.size() > 0 ? true : false);

            LOG.info(String.format("%s VALIDATION RESULT: %b", idOperation, exists));

            return new ResponseModel(exists);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existsByUserNumberAndIdNot(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

}
