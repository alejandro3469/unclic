package mx.com.endtoend.infrastructure.userConfiguration.common.business;

import mx.com.endtoend.domain.userConfigurations.dto.*;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.userConfiguration.common.repository.GenericUserConfigurationRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.*;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.*;
import mx.com.endtoend.infrastructure.userConfiguration.common.repository.*;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BaseUserConfigurationRepository implements GenericUserConfigurationRepository {


    private final Logger LOG;
    private final BaseSaleTypeRepository saleTypeRepository;
    private final BasePriceTypeRepository priceTypeRepository;
    private final BaseRoleJobTypeRepository roleJobTypeRepository;
    private final BaseEmployeeRepository employeeRepository;
    private final BaseCreditNoteTypeRepositoty creditNoteTypeRepositoty;
    private final CreditNoteTypeConverter creditNoteTypeConverter;
    private final EmployeeConverter employeeConverter;
    private final WarehouseOptionConverter warehouseOptionConverter;
    private final UserConfigurationConverter userConfigurationConverter;
    private final UserRepository userRepository;
    private final RoleJobTypeConverter roleJobTypeConverter;
    private final SaleTypeConverter saleTypeConverter;
    private final PriceTypeConverter priceTypeConverter;



    public BaseUserConfigurationRepository(Class<?> loggerClass,
                                           BaseCreditNoteTypeRepositoty _creditNoteTypeRepositoty,
                                           BaseEmployeeRepository _employeeRepository,
                                           BaseRoleJobTypeRepository  _roleJobTypeRepository,
                                           BasePriceTypeRepository _priceTypeRepository,
                                           BaseSaleTypeRepository _saleTypeRepository,
                                           CreditNoteTypeConverter _creditNoteTypeConverter,
                                            EmployeeConverter _employeeConverter,
                                            WarehouseOptionConverter _warehouseOptionConverter,
                                            UserConfigurationConverter _userConfigurationConverter,
                                            UserRepository _userRepository,
                                            RoleJobTypeConverter _roleJobTypeConverter,
                                            SaleTypeConverter _saleTypeConverter,
                                            PriceTypeConverter _priceTypeConverter
                                           ){
        LOG = LoggerFactory.getLogger(loggerClass);
        creditNoteTypeRepositoty = _creditNoteTypeRepositoty;
        employeeRepository = _employeeRepository;
        roleJobTypeRepository = _roleJobTypeRepository;
        priceTypeRepository = _priceTypeRepository;
        saleTypeRepository = _saleTypeRepository;
        creditNoteTypeConverter = _creditNoteTypeConverter;
        employeeConverter = _employeeConverter;
        warehouseOptionConverter = _warehouseOptionConverter;
        userConfigurationConverter = _userConfigurationConverter;
        userRepository = _userRepository;
        roleJobTypeConverter = _roleJobTypeConverter;
        saleTypeConverter = _saleTypeConverter;
        priceTypeConverter = _priceTypeConverter;
    }

    @Transactional
    @Override
    public EmployeeDto createUserConfiguration(EmployeeDto employeeDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT CREATE IN REPOSITORY: createUserConfiguration() ", idOperation));
            LOG.info(String.format("%s PARAMS: [employeeDto: %s ]", idOperation, employeeDto.toString()));

            LOG.info(String.format("%s START CONVERSIONS", idOperation));

            LOG.info(String.format("%s CONVERT EMPLOYEE", idOperation));
            EmployeeEntity employeeEntity = employeeConverter.employeeDtoToEmployeeEntity(employeeDto);

            LOG.info(String.format("%s CONVERT AND SET ROLE JOB", idOperation));
            RoleJobTypeEntity roleJobTypeEntity = roleJobTypeConverter
                    .roleJobTypeDtoToRoleJobTypeEntity(employeeDto.getRoleJob());
            employeeEntity.setRoleJob(roleJobTypeEntity);

            if (employeeDto.getDirectBoss() != null) {
                LOG.info(String.format("%s CONVERT AND SET DIRECT-BOSS", idOperation));
                employeeEntity
                        .setDirectBoss(employeeConverter.employeeDtoToEmployeeEntity(employeeDto.getDirectBoss()));
            } else {
                LOG.info(String.format("%s EMPTY DIRECT-BOSS", idOperation));
                employeeEntity.setDirectBoss(null);
            }

            LOG.info(String.format("%s CONVERT AND SET USERCONFIGURATION", idOperation));

            UserConfigurationEntity userConfigurationEntity = userConfigurationConverter
                    .userConfigurationDtoToUserConfigurationEntity(employeeDto.getUserConfiguration());

            Date newDate = new Date();

            userConfigurationEntity.setCreationDate(newDate);
            userConfigurationEntity.setUpdatedDate(newDate);

            LOG.info(String.format("%s CONVERT AND SET PRICE-LIST", idOperation));
            userConfigurationEntity.setPriceTypes(priceTypeConverter
                    .priceTypeDtoListToPriceTypeEntityList(employeeDto.getUserConfiguration().getPriceTypes()));

            LOG.info(String.format("%s CONVERT AND SET SALE-LIST", idOperation));
            userConfigurationEntity.setSaleTypes(saleTypeConverter
                    .saleTypeDtoListToSaleTypeEntityList(employeeDto.getUserConfiguration().getSaleTypes()));

            LOG.info(String.format("%s CONVERT AND SET CREDIT-NOTE-LIST", idOperation));
            userConfigurationEntity
                    .setCreditNoteTypes(creditNoteTypeConverter.creditNoteTypeDtoListToCreditNoteTypeEntityList(
                            employeeDto.getUserConfiguration().getCreditNoteTypes()));

            LOG.info(String.format("%s CONVERT AND SET WAREHOUSE-LIST", idOperation));
            List<WarehouseOptionEntity> warehouseOptionEntities = warehouseOptionConverter
                    .warehouseOptionDtoListToWarehouseOptionEntityList(
                            employeeDto.getUserConfiguration().getWarehouseOptions());

            for (WarehouseOptionEntity warehouseOptionEntity : warehouseOptionEntities) {
                warehouseOptionEntity.setUserConfiguration(userConfigurationEntity);
            }

            userConfigurationEntity.setWarehouses(warehouseOptionEntities);
            userConfigurationEntity.setEmployee(employeeEntity);

            employeeEntity.setUserConfiguration(userConfigurationEntity);

            LOG.info(String.format("%s SAVE EMPLOYEE", idOperation));
            employeeEntity = employeeRepository.save(employeeEntity);

            if (employeeDto.getEmployees() != null) {

                LOG.info(String.format("%s CONVERT AND SET EMPLOYEES", idOperation));
                List<EmployeeEntity> employees = employeeConverter
                        .employeeDtoListToEmployeeEntityList(employeeDto.getEmployees());

                for (EmployeeEntity employee : employees) {
                    employee = employeeRepository.findByEmployeeId(employee.getId());
                    employee.setDirectBoss(employeeEntity);
                    employeeRepository.save(employee);
                }

            } else {

                LOG.info(String.format("%s EMPTY EMPLOYEES", idOperation));
                employeeEntity.setEmployees(null);
            }

            LOG.info(String.format("%s RETURN EMPLOYEE", idOperation));

            return employeeConverter.employeeEntityToEmployeeDto(employeeEntity);

        } catch (Exception e) {

            LOG.error(
                    String.format("%s ERROR IN createUserConfiguration(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public EmployeeDto updateUserConfiguration(EmployeeDto employeeDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT UPDATE: updateUserConfiguration() ", idOperation));
            LOG.info(String.format("%s PARAMS: [employeeDto: %s ]", idOperation, employeeDto.toString()));

            LOG.info(String.format("%s START CONVERSIONS", idOperation));

            LOG.info(String.format("%s CONVERT EMPLOYEE", idOperation));
            EmployeeEntity employeeEntity = employeeConverter.employeeDtoToEmployeeEntity(employeeDto);

            LOG.info(String.format("%s CONVERT AND SET ROLE JOB", idOperation));
            RoleJobTypeEntity roleJobTypeEntity = roleJobTypeConverter
                    .roleJobTypeDtoToRoleJobTypeEntity(employeeDto.getRoleJob());
            employeeEntity.setRoleJob(roleJobTypeEntity);

            if (employeeDto.getDirectBoss() != null) {
                LOG.info(String.format("%s CONVERT AND SET DIRECT-BOSS", idOperation));
                employeeEntity
                        .setDirectBoss(employeeConverter.employeeDtoToEmployeeEntity(employeeDto.getDirectBoss()));
            } else {
                LOG.info(String.format("%s EMPTY DIRECT-BOSS", idOperation));
                employeeEntity.setDirectBoss(null);
            }

            LOG.info(String.format("%s CONVERT USERCONFIGURATION", idOperation));
            UserConfigurationEntity userConfigurationEntity = userConfigurationConverter
                    .userConfigurationDtoToUserConfigurationEntity(employeeDto.getUserConfiguration());

            Date newDate = new Date();

            userConfigurationEntity.setCreationDate(newDate);
            userConfigurationEntity.setUpdatedDate(newDate);

            LOG.info(String.format("%s CONVERT AND SET PRICE-LIST", idOperation));
            userConfigurationEntity.setPriceTypes(priceTypeConverter
                    .priceTypeDtoListToPriceTypeEntityList(employeeDto.getUserConfiguration().getPriceTypes()));

            LOG.info(String.format("%s CONVERT AND SET SALE-LIST", idOperation));
            userConfigurationEntity.setSaleTypes(saleTypeConverter
                    .saleTypeDtoListToSaleTypeEntityList(employeeDto.getUserConfiguration().getSaleTypes()));

            LOG.info(String.format("%s CONVERT AND SET CREDIT-NOTE-LIST", idOperation));
            userConfigurationEntity
                    .setCreditNoteTypes(creditNoteTypeConverter.creditNoteTypeDtoListToCreditNoteTypeEntityList(
                            employeeDto.getUserConfiguration().getCreditNoteTypes()));

            LOG.info(String.format("%s CONVERT AND SET WAREHOUSE-LIST", idOperation));
            List<WarehouseOptionEntity> warehouseOptionEntities = warehouseOptionConverter
                    .warehouseOptionDtoListToWarehouseOptionEntityList(
                            employeeDto.getUserConfiguration().getWarehouseOptions());

            for (WarehouseOptionEntity warehouseOptionEntity : warehouseOptionEntities) {
                warehouseOptionEntity.setUserConfiguration(userConfigurationEntity);
            }

            userConfigurationEntity.setWarehouses(warehouseOptionEntities);
            userConfigurationEntity.setEmployee(employeeEntity);

            employeeEntity.setUserConfiguration(userConfigurationEntity);

            LOG.info(String.format("%s SAVE EMPLOYEE", idOperation));
            employeeEntity = employeeRepository.save(employeeEntity);

            if (employeeDto.getEmployees() != null) {

                LOG.info(String.format("%s DELETE PREVIOUS RECORDS", idOperation));

                EmployeeEntity employeeToUpdate = employeeRepository.findByEmployeeId(employeeEntity.getId());
                for (EmployeeEntity employee : employeeToUpdate.getEmployees()) {
                    employee = employeeRepository.findByEmployeeId(employee.getId());
                    employee.setDirectBoss(null);
                    employeeRepository.save(employee);
                }

                LOG.info(String.format("%s CONVERT AND SET EMPLOYEES", idOperation));

                List<EmployeeEntity> employees = employeeConverter
                        .employeeDtoListToEmployeeEntityList(employeeDto.getEmployees());

                for (EmployeeEntity employee : employees) {
                    employee = employeeRepository.findByEmployeeId(employee.getId());
                    employee.setDirectBoss(employeeEntity);
                    employeeRepository.save(employee);
                }

            } else {

                LOG.info(String.format("%s EMPTY EMPLOYEES, DELETE RECORDS", idOperation));

                EmployeeEntity employeeToUpdate = employeeRepository.findByEmployeeId(employeeEntity.getId());
                for (EmployeeEntity employee : employeeToUpdate.getEmployees()) {
                    employee = employeeRepository.findByEmployeeId(employee.getId());
                    employee.setDirectBoss(null);
                    employeeRepository.save(employee);
                }

            }

            LOG.info(String.format("%s RETURN EMPLOYEE", idOperation));

            return employeeConverter.employeeEntityToEmployeeDto(employeeEntity);

        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR IN updateUserConfiguration(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public EmployeeDto findUserConfigurationByUserId(Long userId, String idOperation) {

        try {

            LOG.info(String.format("%s INIT SERCH IN REPOSITORY: findUserConfigurationByUserId() ", idOperation));
            LOG.info(String.format("%s PARAMS: [userId: %s ]", idOperation, userId.toString()));

            Optional<EmployeeEntity> employeeOptional = employeeRepository.findByUserId(userId);

            if (employeeOptional.isPresent()) {

                LOG.info(String.format("%s USER FOUND, START CONVERTIONS ", idOperation));

                EmployeeEntity employeeEntity = employeeOptional.get();

                LOG.info(String.format("%s CONVERT EMPLOYEE", idOperation));
                EmployeeDto employeeDto = employeeConverter.employeeEntityToEmployeeDto(employeeEntity);

                LOG.info(String.format("%s CONVERT AND SET ROLE-JOB", idOperation));
                RoleJobTypeDto roleJobTypeDto = roleJobTypeConverter
                        .roleJobTypeEntityToRoleJobTypeDto(employeeEntity.getRoleJob());
                employeeDto.setRoleJob(roleJobTypeDto);

                if (employeeEntity.getDirectBoss() == null) {

                    LOG.info(String.format("%s EMPTY/NULL DIRECT-BOSS", idOperation));
                    employeeDto.setDirectBoss(null);

                } else {

                    LOG.info(String.format("%s CONVERT AND SET DIRECT-BOSS", idOperation));
                    employeeDto.setDirectBoss(
                            employeeConverter.employeeEntityToEmployeeDto(employeeEntity.getDirectBoss()));
                }

                if (employeeEntity.getEmployees() == null) {

                    LOG.info(String.format("%s EMPTY/NULL EMPLOYEES", idOperation));
                    employeeDto.setEmployees(null);

                } else {

                    LOG.info(String.format("%s CONVERT AND SET EMPLOYEES", idOperation));
                    List<EmployeeDto> employees = employeeConverter
                            .employeeEntityListToEmployeeDtoList(employeeEntity.getEmployees());

                    employeeDto.setEmployees(employees);
                }

                LOG.info(String.format("%s CONVERT USER-CONFIGURATION", idOperation));
                UserConfigurationDto userConfigurationDto = userConfigurationConverter
                        .userConfigurationEntityToUserConfigurationDto(employeeEntity.getUserConfiguration());

                LOG.info(String.format("%s GET USER-CONFIGURATION LISTS", idOperation));
                List<PriceTypeEntity> listPrices = employeeEntity.getUserConfiguration().getPriceTypes();

                List<SaleTypeEntity> listSales = employeeEntity.getUserConfiguration().getSaleTypes();

                List<CreditNoteTypeEntity> listCreditNote = employeeEntity.getUserConfiguration().getCreditNoteTypes();

                List<WarehouseOptionEntity> listWarehouses = employeeEntity.getUserConfiguration().getWarehouses();

                LOG.info(String.format("%s CONVERT AND SET PRICE-LIST", idOperation));
                userConfigurationDto
                        .setPriceTypes(priceTypeConverter.priceTypeEntityListToPriceTypeDtoList(listPrices));

                LOG.info(String.format("%s CONVERT AND SET SALE-LIST", idOperation));
                userConfigurationDto.setSaleTypes(saleTypeConverter.saleTypeEntityListToSaleTypeDtoList(listSales));

                LOG.info(String.format("%s CONVERT AND SET CREDIT-NOTE-LIST", idOperation));
                userConfigurationDto.setCreditNoteTypes(
                        creditNoteTypeConverter.creditNoteTypeEntityListToCreditNoteTypeDtoList(listCreditNote));

                LOG.info(String.format("%s CONVERT AND SET WAREHOUSE-LIST", idOperation));
                userConfigurationDto.setWarehouseOptions(
                        warehouseOptionConverter.warehouseOptionEntityListToWarehouseOptionsDtoList(listWarehouses));

                LOG.info(String.format("%s SET USER-CONFIGURATION", idOperation));

                employeeDto.setUserConfiguration(userConfigurationDto);

                return employeeDto;

            } else {
                EmployeeDto employeeDtoEmpty = new EmployeeDto();
                employeeDtoEmpty.setId(null);
                employeeDtoEmpty.setUserId(null);
                employeeDtoEmpty.setUserNumber(null);
                employeeDtoEmpty.setBranchCode("");
                employeeDtoEmpty.setRoleJob(null);
                employeeDtoEmpty.setDirectBoss(null);
                employeeDtoEmpty.setEmployees(null);
                employeeDtoEmpty.setUserConfiguration(null);

                LOG.info(String.format("%s USER NOT FOUND BY ID: %s ", idOperation, userId.toString()));

                return employeeDtoEmpty;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN findUserConfigurationByUserId(). EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public List<PriceTypeDto> findAllPriceTypes(String idOperation) {

        try {

            LOG.info(String.format("%s INIT SERCH IN REPOSITORY: findAllPriceTypes() ", idOperation));
            List<PriceTypeEntity> priceTypeEntityList = priceTypeRepository.findAllSaleTypes();
            if (priceTypeEntityList != null) {
                return priceTypeConverter.priceTypeEntityListToPriceTypeDtoList(priceTypeEntityList);
            } else {
                return null;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN findAllSaleTypes(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public List<SaleTypeDto> findAllSaleTypes(String idOperation) {
        try {
            LOG.info(String.format("%s INIT SERCH IN REPOSITORY: findAllSaleTypes() ", idOperation));
            List<SaleTypeEntity> saleTypeEntityList = saleTypeRepository.findAllSaleTypes();
            if (saleTypeEntityList != null) {
                return saleTypeConverter.saleTypeEntityListToSaleTypeDtoList(saleTypeEntityList);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findAllSaleTypes(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public List<CreditNoteTypeDto> findAllCreditNotes(String idOperation) {
        try {
            LOG.info(String.format("%s INIT findAllCreditNotes() ", idOperation));
            List<CreditNoteTypeEntity> creditNoteTypeEntityList = creditNoteTypeRepositoty.findAll();
            if (creditNoteTypeEntityList != null) {
                return creditNoteTypeConverter
                        .creditNoteTypeEntityListToCreditNoteTypeDtoList(creditNoteTypeEntityList);
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN CreditNoteTypeDto(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public List<RoleJobTypeDto> findAllRoleJobTypes(String idOperation) {

        try {

            LOG.info(String.format("%s INIT SERCH IN REPOSITORY: findAllRoleJobTypes() ", idOperation));

            List<RoleJobTypeEntity> roleJobTypeList = roleJobTypeRepository.findAllRoleJobTypes();

            if (roleJobTypeList != null) {
                return roleJobTypeConverter.roleJobTypeEntityListToRoleJobTypeDtoList(roleJobTypeList);

            } else {

                return null;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN findAllRoleJobTypes(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public boolean existsUserByIdAndUserNumber(Long id, Long userNumber, String idOperation) {
        try {
            LOG.info(String.format("%s INIT VALIDATION IN REPOSITORY: existsUserByIdAndUserNumber() ", idOperation));
            Optional<UserEntity> userEntity = userRepository.findByIdAndUserNumber(id, userNumber);
            if (userEntity.isPresent()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN existsUserByIdAndUserNumber(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public boolean existUseConfigurationByUserIdAndUserNumber(Long userId, Long userNumber, String idOperation) {
//		try {
//			LOG.info(String.format("%s INIT VALIDATION IN REPOSITORY: existUseConfigurationByUserIdAndUserNumber() ",
//					idOperation));
//			Optional<UserConfigurationEntity> userConfigurationEntity = userConfigurationRepository
//					.findByUserIdAndUserNumber(userId, userNumber);
//			if (userConfigurationEntity.isPresent()) {
//				return true;
//			} else {
//				return false;
//			}
//
//		} catch (Exception e) {
//			LOG.error(String.format("%s ERROR IN existUseConfigurationByUserIdAndUserNumber(). EXCEPTION: %s",
//					idOperation, e.getMessage()));
//			throw new GlobalError();
//		}

        return false;
    }

    @Transactional
    @Override
    public EmployeeDto findUserConfigurationByEmailAndBranchCode(String email, String branchCode, String idOperation) {

        try {

            LOG.info(String.format("%s INIT findUserConfigurationByEmailAndBranchCode() ", idOperation));
            LOG.info(String.format("%s PARAMS: [ email: %s , branchCode: %s ]", idOperation, email, branchCode));

            Optional<UserEntity> user = userRepository.findByEmailAndBranchCode(email, branchCode);

            if (user.isPresent()) {

                LOG.info(String.format("%s USER EXISTS IN PRINCIPAL BD, FIND CONFIGURATION", idOperation));

                UserEntity userEntity = user.get();
                Optional<EmployeeEntity> employeeOptional = employeeRepository.findByUserId(userEntity.getId());

                if (userEntity.isConfigurationComplete() && employeeOptional.isPresent()) {

                    EmployeeEntity employeeEntity = employeeOptional.get();

                    LOG.info(String.format("%s CONVERT EMPLOYEE", idOperation));
                    EmployeeDto employeeDto = employeeConverter.employeeEntityToEmployeeDto(employeeEntity);

                    LOG.info(String.format("%s CONVERT AND SET ROLE-JOB", idOperation));
                    RoleJobTypeDto roleJobTypeDto = roleJobTypeConverter
                            .roleJobTypeEntityToRoleJobTypeDto(employeeEntity.getRoleJob());
                    employeeDto.setRoleJob(roleJobTypeDto);

                    if (employeeEntity.getDirectBoss() == null) {

                        LOG.info(String.format("%s EMPTY/NULL DIRECT-BOSS", idOperation));
                        employeeDto.setDirectBoss(null);

                    } else {

                        LOG.info(String.format("%s CONVERT AND SET DIRECT-BOSS", idOperation));
                        employeeDto.setDirectBoss(
                                employeeConverter.employeeEntityToEmployeeDto(employeeEntity.getDirectBoss()));
                    }

                    if (employeeEntity.getEmployees() == null) {

                        LOG.info(String.format("%s EMPTY/NULL EMPLOYEES", idOperation));
                        employeeDto.setEmployees(null);

                    } else {

                        LOG.info(String.format("%s CONVERT AND SET EMPLOYEES", idOperation));
                        List<EmployeeDto> employees = employeeConverter
                                .employeeEntityListToEmployeeDtoList(employeeEntity.getEmployees());

                        employeeDto.setEmployees(employees);
                    }

                    LOG.info(String.format("%s CONVERT USER-CONFIGURATION", idOperation));
                    UserConfigurationDto userConfigurationDto = userConfigurationConverter
                            .userConfigurationEntityToUserConfigurationDto(employeeEntity.getUserConfiguration());

                    LOG.info(String.format("%s GET USER-CONFIGURATION LISTS", idOperation));
                    List<PriceTypeEntity> listPrices = employeeEntity.getUserConfiguration().getPriceTypes();

                    List<SaleTypeEntity> listSales = employeeEntity.getUserConfiguration().getSaleTypes();

                    List<CreditNoteTypeEntity> listCreditNote = employeeEntity.getUserConfiguration()
                            .getCreditNoteTypes();

                    List<WarehouseOptionEntity> listWarehouses = employeeEntity.getUserConfiguration().getWarehouses();

                    LOG.info(String.format("%s CONVERT AND SET PRICE-LIST", idOperation));
                    userConfigurationDto
                            .setPriceTypes(priceTypeConverter.priceTypeEntityListToPriceTypeDtoList(listPrices));

                    LOG.info(String.format("%s CONVERT AND SET SALE-LIST", idOperation));
                    userConfigurationDto.setSaleTypes(saleTypeConverter.saleTypeEntityListToSaleTypeDtoList(listSales));

                    LOG.info(String.format("%s CONVERT AND SET CREDIT-NOTE-LIST", idOperation));
                    userConfigurationDto.setCreditNoteTypes(
                            creditNoteTypeConverter.creditNoteTypeEntityListToCreditNoteTypeDtoList(listCreditNote));

                    LOG.info(String.format("%s CONVERT AND SET WAREHOUSE-LIST", idOperation));
                    userConfigurationDto.setWarehouseOptions(warehouseOptionConverter
                            .warehouseOptionEntityListToWarehouseOptionsDtoList(listWarehouses));

                    LOG.info(String.format("%s SET USER-CONFIGURATION", idOperation));

                    employeeDto.setUserConfiguration(userConfigurationDto);

                    return employeeDto;

                } else {

                    LOG.info(String.format("%s USER EXISTS IN PRINCIPAL BD, BUT CONFIGURATION IS INCOMPLETE O NULL",
                            idOperation));

                    throw new ValidationError("CONFIGURATION-INCOMPLETE");
                }

            } else {
                LOG.warn(String.format("%s USER %s NOT FOUNT", idOperation, email));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findUserConfigurationByEmailAndBranchCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public boolean updateUserConfigurationStatusByUserId(Long userId, String idOperation) {

        try {

            int updated = userRepository.updateUserConfigurationStatusById(userId, true);

            if (updated > 0) {

                LOG.info(String.format("%s USER-CONFIGURATION-STATUS UPDATE", idOperation));
                return true;
            } else {

                LOG.warn(String.format("%s ERROR UPDATING USER-CONFIGURATION-STATUS ", idOperation));
                return false;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateUserConfigurationStatusByUserId(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public List<EmployeeDto> findEmployeeByOperativeRoleAndBranchCode(List<String> operativeRole, String branchCode,
                                                                      String idOperation) {

        try {

            LOG.info(String.format("%s INIT SERCH IN REPOSITORY: findUserConfigurationByUserId() ", idOperation));
            LOG.info(String.format("%s PARAMS: [operativeRole: %s , branchCode: %s ]", idOperation,
                    operativeRole.toString(), branchCode));

            List<EmployeeEntity> employeeEntityList = employeeRepository
                    .findEmployeesByOperationalRoleAndBranchCode(operativeRole, branchCode);

            LOG.info(String.format("%s RESULT SIZE LITS:  %d", idOperation, employeeEntityList.size()));

            return employeeConverter.employeeEntityListToEmployeeDtoList(employeeEntityList);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN findEmployeeByOperativeRoleAndBranchCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));

            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public List<EmployeeDto> findEmployeeByOperativeRoleList(List<String> operativeRole, String idOperation) {

        try {

            LOG.info(String.format("%s INIT  findEmployeeByOperativeRoleList() ", idOperation));
            LOG.info(String.format("%s PARAMS: [operativeRole: %s ]", idOperation, operativeRole.toString()));

            List<EmployeeEntity> employeeEntityList = employeeRepository.findEmployeesByOperationalRole(operativeRole);

            LOG.info(String.format("%s RESULT SIZE LITS:  %d", idOperation, employeeEntityList.size()));

            return employeeConverter.employeeEntityListToEmployeeDtoList(employeeEntityList);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN findEmployeeByOperativeRoleList(). EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }

    }

}
