package mx.com.endtoend.infrastructure.payments.common.business;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaidOrderSummaryDto;
import mx.com.endtoend.domain.payments.dto.ticket.PaymentTicketDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.RoleJobTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.UserConfigurationDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.branch.common.repositories.BaseBranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;
import mx.com.endtoend.infrastructure.catalogue.orders.common.repository.BaseStatusRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationEntity;
import mx.com.endtoend.infrastructure.openings.common.repositories.BaseOpeningOperationRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.*;
import mx.com.endtoend.infrastructure.orders.common.repository.BaseOrderHistoryRepository;
import mx.com.endtoend.infrastructure.orders.common.repository.BaseOrderRepository;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderHistoryEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.CreditPaymentEntity;
import mx.com.endtoend.infrastructure.payments.common.converters.*;
import mx.com.endtoend.infrastructure.payments.common.entities.*;
import mx.com.endtoend.infrastructure.payments.common.persistence.GenericPaymentPersistenceInterface;
import mx.com.endtoend.infrastructure.payments.common.repository.*;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.*;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.EmployeeEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.PriceTypeEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.SaleTypeEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.WarehouseOptionEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.repository.BaseEmployeeRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.*;
import mx.com.endtoend.smart.bussiness.model.payments.*;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class BasePaymentBusinessRepository implements GenericPaymentPersistenceInterface {

    private final Map<String, String> TEMPLATES;
    private final Logger LOG;
    private final StatusConverter statusConverter;
    private final TaxConverter taxConverter;
    private final AddressConverter addressConverter;
    private final OrderDetailConverter orderDetailConverter;
    private final OrderConverter orderConverter;
    private final ClientConverter clientConverter;
    private final SaleTypeConverter saleTypeConverter;
    private final PriceTypeConverter priceTypeConverter;
    private final RoleJobTypeConverter roleJobTypeConverter;
    private final UserConfigurationConverter userConfigurationConverter;
    private final WarehouseOptionConverter warehouseOptionConverter;
    private final EmployeeConverter employeeConverter;
    private final PaymentConverter paymentConverter;
    private final PaymentCashConverter paymentCashConverter;
    private final OrderHistoryConverter orderHistoryConverter;
    private final OpeningOperationConverter openingOperationConverter;
    private final CreditCardPaymentConverter creditCardPaymentConverter;
    private final TransferPaymentConverter transferPaymentConverter;
    private final UserConverter userConverter;
    private final BranchConverter branchConverter;
    private final CreditNotePaymentConverter creditNotePaymentConverter;
    private final CheckPaymentConverter checkPaymentConverter;
    private final CreditPaymentConverter creditPaymentConverter;

    private final BaseCheckPaymentRepository checkPaymentRepository;
    private final BaseBranchRepository branchRepository;
    private final BaseOrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BaseEmployeeRepository employeeRepository;
    private final BasePaymentRepository paymentRepository;
    private final BasePaymentCashRepository paymentCashRepository;
    private final BaseOrderHistoryRepository orderHistoryRepository;
    private final BaseStatusRepository statusRepository;
    private final BaseOpeningOperationRepository openingOperationCalRepository;
    private final BaseCreditCardPaymentRepository creditCardPaymentRepository;
    private final BaseTransferPaymentRepository transferPaymentRepository;
    private final BaseCreditNotePaymentRepository creditNotePaymentRepository;
    private final BaseCustomDSLPaymentRepository customDSLPaymentRepository;
    private final BaseCreditPaymentRepository creditPaymentRepository;
    private final BaseClientRepository clientRepository;

    public BasePaymentBusinessRepository(Class<?> loggerClass, Map<String, String> _TEMPLATES,
                                     StatusConverter _statusConverter,
                                     TaxConverter _taxConverter,
                                     AddressConverter _addressConverter,
                                     OrderDetailConverter _orderDetailConverter,
                                     OrderConverter _orderConverter,
                                     ClientConverter _clientConverter,
                                     SaleTypeConverter _saleTypeConverter,
                                     PriceTypeConverter _priceTypeConverter,
                                     RoleJobTypeConverter _roleJobTypeConverter,
                                     UserConfigurationConverter _userConfigurationConverter,
                                     WarehouseOptionConverter _warehouseOptionConverter,
                                     EmployeeConverter _employeeConverter,
                                     PaymentConverter _paymentConverter,
                                     PaymentCashConverter _paymentCashConverter,
                                     OrderHistoryConverter _orderHistoryConverter,
                                     OpeningOperationConverter _openingOperationConverter,
                                     CreditCardPaymentConverter _creditCardPaymentConverter,
                                     TransferPaymentConverter _transferPaymentConverter,
                                     UserConverter _userConverter,
                                     BranchConverter _branchConverter,
                                     CreditNotePaymentConverter _creditNotePaymentConverter,
                                     CheckPaymentConverter _checkPaymentConverter,
                                     CreditPaymentConverter _creditPaymentConverter,
                                     BaseCheckPaymentRepository _checkPaymentRepository,
                                     BaseBranchRepository _branchRepository,
                                     BaseOrderRepository _orderRepository,
                                     UserRepository _userRepository,
                                     BaseEmployeeRepository _employeeRepository,
                                     BasePaymentRepository _paymentRepository,
                                     BasePaymentCashRepository _paymentCashRepository,
                                     BaseOrderHistoryRepository _orderHistoryRepository,
                                     BaseStatusRepository _statusRepository,
                                     BaseOpeningOperationRepository _openingOperationCalRepository,
                                     BaseCreditCardPaymentRepository _creditCardPaymentRepository,
                                     BaseTransferPaymentRepository _transferPaymentRepository,
                                     BaseCreditNotePaymentRepository _creditNotePaymentRepository,
                                     BaseCustomDSLPaymentRepository _customDSLPaymentRepository,
                                     BaseCreditPaymentRepository _creditPaymentRepository,
                                     BaseClientRepository _clientRepository) {
        LOG= LoggerFactory.getLogger(loggerClass);
        TEMPLATES =_TEMPLATES;
        statusConverter = _statusConverter;
        taxConverter = _taxConverter;
        addressConverter = _addressConverter;
        orderDetailConverter = _orderDetailConverter;
        orderConverter = _orderConverter;
        clientConverter = _clientConverter;
        saleTypeConverter = _saleTypeConverter;
        priceTypeConverter = _priceTypeConverter;
        roleJobTypeConverter = _roleJobTypeConverter;
        userConverter = _userConverter;
        userConfigurationConverter = _userConfigurationConverter;
        warehouseOptionConverter = _warehouseOptionConverter;
        employeeConverter = _employeeConverter;
        paymentConverter = _paymentConverter;
        paymentCashConverter = _paymentCashConverter;
        orderHistoryConverter = _orderHistoryConverter;
        openingOperationConverter = _openingOperationConverter;
        creditCardPaymentConverter = _creditCardPaymentConverter;
        transferPaymentConverter = _transferPaymentConverter;
        userRepository = _userRepository;
        branchConverter = _branchConverter;
        creditNotePaymentConverter = _creditNotePaymentConverter;
        checkPaymentConverter = _checkPaymentConverter;
        creditPaymentConverter = _creditPaymentConverter;
        checkPaymentRepository = _checkPaymentRepository;
        branchRepository = _branchRepository;
        orderRepository = _orderRepository;
        employeeRepository = _employeeRepository;
        paymentRepository = _paymentRepository;
        paymentCashRepository = _paymentCashRepository;
        orderHistoryRepository = _orderHistoryRepository;
        statusRepository = _statusRepository;
        openingOperationCalRepository = _openingOperationCalRepository;
        creditCardPaymentRepository = _creditCardPaymentRepository;
        transferPaymentRepository = _transferPaymentRepository;
        creditNotePaymentRepository = _creditNotePaymentRepository;
        customDSLPaymentRepository = _customDSLPaymentRepository;
        creditPaymentRepository = _creditPaymentRepository;
        clientRepository = _clientRepository;

    }


    protected String getTemplatePath(String key) {
        String path = TEMPLATES.get(key);
        if (path == null) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en los templates.");
        }
        return path;
    }


    protected String getTicketTemplatePath() {
        return getTemplatePath("ticket_template");
    }

    protected String getLogoReport() {
        return getTemplatePath("logo_report");
    }

    protected String getCompanyName() {
        return getTemplatePath("company_name");
    }

    protected String getBusinessName() {
        return getTemplatePath("business_name");
    }

    @Transactional
    @Override
    public List<OrderDto> getOrdersToSendToQueue() {

        /*try {*/
            LOG.info("INIT getOrdersToSendToQueue()");

            // Retrieve a list of orders
            //List<OrderEntity> orders = orderRepository.getOrdersToSendToQueue();
			List<BigDecimal> orderNumbers = Arrays.asList(new BigDecimal("2862820"),
					new BigDecimal("2862821"),
					new BigDecimal("2862822"),
					new BigDecimal("2864932"),
					new BigDecimal("2864935"),
					new BigDecimal("11438786"),
					new BigDecimal("11438881"),
					new BigDecimal("11438979"),
					new BigDecimal("11439126"),
					new BigDecimal("11439202"),
					new BigDecimal("11439374"),
					new BigDecimal("11439557"),
					new BigDecimal("11439618"),
					new BigDecimal("11439774"),
					new BigDecimal("11473109"),
					new BigDecimal("11473135"),
					new BigDecimal("11473157"),
					new BigDecimal("11473159"),
					new BigDecimal("11473160"), new BigDecimal("11473161"), new BigDecimal("11473162"), new BigDecimal("11473164"), new BigDecimal("11473165"), new BigDecimal("11481268"), new BigDecimal("11481271"), new BigDecimal("7564"));



			List<OrderEntity> orders = orderRepository.getOrdersToSendToQueue(orderNumbers);


            if (orders != null && !orders.isEmpty()) { // BREAKPOINT
                // Convert each OrderEntity to OrderDto
                List<OrderDto> orderDtoList = new ArrayList<>();

                for (OrderEntity orderEntity : orders) {
                    OrderDto orderDto = orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""));

                    // Convert and set StatusDto
                    StatusDto statusDto = statusConverter.statusEntityToStatusDto(orderEntity.getStatus());
                    orderDto.setStatus(statusDto);

                    // Convert and set ClientDto
                    ClientDto clientDto = clientConverter.clientEntityToClientDto(clientRepository.findById(orderEntity.getClientId()));
                    orderDto.setClient(clientDto);

                    // Convert and set AddressDto list
                    List<AddressDto> addressDtoList = addressConverter.addresEntityListToAddresDtoList(orderEntity.getAddresses());
                    orderDto.setAddresses(addressDtoList);

                    // Convert and set TaxDto list
                    List<TaxDto> taxDtoList = taxConverter.taxEntityListToTaxDtoList(orderEntity.getTaxes());
                    orderDto.setTaxes(taxDtoList);

                    // Convert and set OrderDetailDto list
                    List<OrderDetailDto> orderDetailDtoList = orderDetailConverter.orderDetailEntityToOrderDetailDetoList(orderEntity.getOrderDetail());
                    orderDto.setOrderDetail(orderDetailDtoList);

                    // Add to the final list
                    orderDtoList.add(orderDto);
                }

                return orderDtoList;
            } else {
                LOG.warn("No orders found to send to the queue.");
                return new ArrayList<>();
            }

        /*} catch (Exception e) {
            LOG.error("ERROR IN getOrdersToSendToQueue(). EXCEPTION: {}", e.getMessage());
            throw new GlobalError();
        }*/
    }

	@Transactional
	@Override
	public PaymentDto findByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode, String companyCode, String idOperation) {

        try {
            LOG.info("INIT getOrdersToSendToQueue()");

            // Retrieve a list of orders
            PaymentEntity paymentEntity = paymentRepository
        .findByOrderNumberAndOrderCode(orderNumber, orderCode)
        .orElseThrow(() -> new EntityNotFoundException("PaymentEntity not found for orderNumber: "
                + orderNumber + " and orderCode: " + orderCode));


			PaymentDto paymentDto = paymentConverter.paymentEntityToPaymentDto(paymentEntity);

            if (orderCode != null && !orderCode.isEmpty()) {
                return paymentDto;
            } else {
                LOG.warn("No orders found to send to the queue.");
                return null;
            }

        } catch (Exception e) {
            LOG.error("ERROR IN getOrdersToSendToQueue(). EXCEPTION: {}", e.getMessage());
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderDto getOrderByParams(BigDecimal orderNumber, String orderCode, String companyCode, String idOperation) {

        try {

            LOG.info(String.format("%s INIT getOrderByParams() ", idOperation));
            Optional<OrderEntity> order = orderRepository.findByOrderCodeAndOrderNumber(orderNumber, orderCode);

            if (order.isPresent()) {
                OrderEntity orderEntity = order.get();
                OrderDto orderDto = orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""));

                StatusDto statusDto = statusConverter.statusEntityToStatusDto(orderEntity.getStatus());
                orderDto.setStatus(statusDto);

                ClientDto clientDto = clientConverter
                        .clientEntityToClientDto(clientRepository.findById(order.get().getClientId()));
                orderDto.setClient(clientDto);

                List<AddressDto> addressDtoList = addressConverter
                        .addresEntityListToAddresDtoList(orderEntity.getAddresses());
                orderDto.setAddresses(addressDtoList);

                List<TaxDto> taxDtoList = taxConverter.taxEntityListToTaxDtoList(orderEntity.getTaxes());
                orderDto.setTaxes(taxDtoList);

                List<OrderDetailDto> orderDetailDto = orderDetailConverter
                        .orderDetailEntityToOrderDetailDetoList(orderEntity.getOrderDetail());
                orderDto.setOrderDetail(orderDetailDto);

                return orderDto;

            } else {
                LOG.warn(String.format("%s ORDER: %s NOT FOUND", idOperation, orderNumber.toString()));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getOrderByParams(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public EmployeeDto getEmployeConfigurationByEmailAndCompanyCode(String email, String companyCode,
                                                                    String idOperation) {

        try {
            LOG.info(String.format("%s INIT getEmployeConfigurationByEmailAndCompanyCode() ", idOperation));

            Optional<UserEntity> userOptional = userRepository.findByEmail(email);

            if (userOptional.isPresent()) {
                Optional<EmployeeEntity> employeeOptional = employeeRepository.findByUserId(userOptional.get().getId());

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

                    List<WarehouseOptionEntity> listWarehouses = employeeEntity.getUserConfiguration().getWarehouses();

                    LOG.info(String.format("%s CONVERT AND SET PRICE-LIST", idOperation));
                    userConfigurationDto
                            .setPriceTypes(priceTypeConverter.priceTypeEntityListToPriceTypeDtoList(listPrices));

                    LOG.info(String.format("%s CONVERT AND SET SALE-LIST", idOperation));
                    userConfigurationDto.setSaleTypes(saleTypeConverter.saleTypeEntityListToSaleTypeDtoList(listSales));

                    LOG.info(String.format("%s CONVERT AND SET WAREHOUSE-LIST", idOperation));
                    userConfigurationDto.setWarehouseOptions(warehouseOptionConverter
                            .warehouseOptionEntityListToWarehouseOptionsDtoList(listWarehouses));

                    LOG.info(String.format("%s SET USER-CONFIGURATION", idOperation));

                    employeeDto.setUserConfiguration(userConfigurationDto);

                    return employeeDto;
                } else {
                    LOG.info(String.format("%s USER CONFIGURATION NOT FOUND", idOperation));
                    return null;
                }
            } else {
                LOG.info(String.format("%s USER CONFIGURATION NOT FOUND", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getEmployeConfigurationByEmailAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public PaymentDto createPayment(PaymentDto paymentDto, String idOperation) {

        try {
            LOG.info(String.format("%s INIT createPayment() ", idOperation));

            PaymentEntity paymentEntity = paymentConverter.paymentDtoToPaymentEntity(paymentDto);
            paymentEntity = paymentRepository.save(paymentEntity);

            List<PaymentCashEntity> paymentCashCreated = new ArrayList<>();
            List<CreditCardPaymentEntity> creditCardPaymentCreated = new ArrayList<>();
            List<TransferPaymentEntity> transferPaymentCreated = new ArrayList<>();
            List<CreditNotePaymentEntity> creditNotePaymentCreated = new ArrayList<>();
            List<CheckPaymentEntity> checkPaymentCreated = new ArrayList<>();
            List<CreditPaymentEntity> creditPaymentCreated = new ArrayList<>();

            if (paymentDto.getCreditPaymentList() != null)
                saveCreditPayment(paymentDto, idOperation, paymentEntity.getPaymentId(), creditPaymentCreated);

            if (paymentDto.getPaymentCashList() != null)
                saveCashPayment(paymentDto, idOperation, paymentEntity.getPaymentId(), paymentCashCreated);

            if (paymentDto.getCreditCardPaymentList() != null)
                saveCreditCardPayment(paymentDto, idOperation, paymentEntity.getPaymentId(), creditCardPaymentCreated);

            if (paymentDto.getTransferPaymentList() != null)
                saveTransferPayment(paymentDto, idOperation, paymentEntity.getPaymentId(), transferPaymentCreated);

            if (paymentDto.getCreditNotePaymentList() != null)
                saveCreditNotePayment(paymentDto, idOperation, paymentEntity.getPaymentId(), creditNotePaymentCreated);

            if (paymentDto.getCheckPaymentList() != null)
                saveCheckPayment(paymentDto, idOperation, paymentEntity.getPaymentId(), checkPaymentCreated);

            PaymentDto paymentCreated = paymentConverter.paymentEntityToPaymentDto(paymentEntity);

            if (!creditPaymentCreated.isEmpty()) {
                List<CreditPaymentDto> creditPaymentList = creditPaymentConverter
                        .creditPaymentEntityToCreditPaymentDto(creditPaymentCreated);
                paymentCreated.setCreditPaymentList(creditPaymentList);
            }

            if (!paymentCashCreated.isEmpty()) {
                List<PaymentCashDto> paymentCashList = paymentCashConverter
                        .paymentCashEntityListToPaymentCashDtoList(paymentCashCreated);
                paymentCreated.setPaymentCashList(paymentCashList);
            }

            if (!creditCardPaymentCreated.isEmpty()) {
                List<CreditCardPaymentDto> creditCardPaymentList = creditCardPaymentConverter
                        .creditCardPaymentEntityListToCreditCardPaymentDtoList(creditCardPaymentCreated);
                paymentCreated.setCreditCardPaymentList(creditCardPaymentList);
            }

            if (!transferPaymentCreated.isEmpty()) {
                List<TransferPaymentDto> transferPaymentList = transferPaymentConverter
                        .transferPaymentEntityListToTransferPaymentDtoList(transferPaymentCreated);
                paymentCreated.setTransferPaymentList(transferPaymentList);
            }

            if (!creditNotePaymentCreated.isEmpty()) {
                List<CreditNotePaymentDto> creditNotePaymentList = creditNotePaymentConverter
                        .creditNotePaymentEntityListToCreditNotePaymentDtoList(creditNotePaymentCreated);
                paymentCreated.setCreditNotePaymentList(creditNotePaymentList);

            }

            if (!checkPaymentCreated.isEmpty()) {
                List<CheckPaymentDto> checkPaymenttList = checkPaymentConverter
                        .checkPaymentEntityToCheckPaymentDto(checkPaymentCreated);
                paymentCreated.setCheckPaymentList(checkPaymenttList);
            }

            return paymentCreated;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createPayment(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    private void saveCreditPayment(PaymentDto paymentDto, String idOperation, Long paymentId,
                                   List<CreditPaymentEntity> creditPaymentCreated) {
        List<CreditPaymentEntity> creditPaymentEntityList = creditPaymentConverter
                .creditPaymentDtoListToCreditPaymentEntityList(paymentDto.getCreditPaymentList(), paymentId);
        for (CreditPaymentEntity creditPaymentEntity : creditPaymentEntityList) {
            creditPaymentCreated.add(creditPaymentRepository.save(creditPaymentEntity));
        }
    }

    private void saveCheckPayment(PaymentDto paymentDto, String idOperation, Long paymentId,
                                  List<CheckPaymentEntity> checkPaymentCreated) {
        List<CheckPaymentEntity> checkPaymentEntityList = checkPaymentConverter
                .checkPaymentDtoToCheckPaymentEntity(paymentDto.getCheckPaymentList(), paymentId);
        for (CheckPaymentEntity checkPaymentEntity : checkPaymentEntityList) {
            checkPaymentCreated.add(checkPaymentRepository.save(checkPaymentEntity));
        }
    }

    private void saveCreditNotePayment(PaymentDto paymentDto, String idOperation, Long paymentId,
                                       List<CreditNotePaymentEntity> creditNotePaymentCreated) {
        List<CreditNotePaymentEntity> creditNotePaymentEntityList = creditNotePaymentConverter
                .creditNotePaymentDtoListToCreditNotePaymentEntityList(paymentDto.getCreditNotePaymentList(),
                        paymentId);
        for (CreditNotePaymentEntity creditNotePaymentEntity : creditNotePaymentEntityList) {
            creditNotePaymentCreated.add(creditNotePaymentRepository.save(creditNotePaymentEntity));
        }
    }

    private void saveTransferPayment(PaymentDto paymentDto, String idOperation, Long paymentId,
                                     List<TransferPaymentEntity> transferPaymentCreated) {
        List<TransferPaymentEntity> transferPaymentEntityList = transferPaymentConverter
                .transferPaymentDtoListToTransferPaymentEntityList(paymentDto.getTransferPaymentList(), paymentId);
        for (TransferPaymentEntity transferPaymentEntity : transferPaymentEntityList) {
            transferPaymentCreated.add(transferPaymentRepository.save(transferPaymentEntity));
        }

    }

    private void saveCreditCardPayment(PaymentDto paymentDto, String idOperation, Long paymentId,
                                       List<CreditCardPaymentEntity> creditCardPaymentCreated) {
        List<CreditCardPaymentEntity> creditCardPaymentEntityList = creditCardPaymentConverter
                .creditCardPaymentDtoListToCreditCardPaymentEntityList(paymentDto.getCreditCardPaymentList(),
                        paymentId);
        for (CreditCardPaymentEntity creditCardPaymentEntity : creditCardPaymentEntityList) {
            creditCardPaymentCreated.add(creditCardPaymentRepository.save(creditCardPaymentEntity));
        }
    }

    private void saveCashPayment(PaymentDto paymentDto, String idOperation, Long paymentId,
                                 List<PaymentCashEntity> paymentCashCreated) {
        List<PaymentCashEntity> paymentCashEntityList = paymentCashConverter
                .paymentCashDtoListToPaymentCashEntityList(paymentDto.getPaymentCashList(), paymentId);
        for (PaymentCashEntity paymentCashEntity : paymentCashEntityList) {
            paymentCashCreated.add(paymentCashRepository.save(paymentCashEntity));
        }
    }

    @Transactional
    @Override
    public void updateOrderStatusByOrderNumberAndCompanyCode(BigDecimal orderNumber, String orderCode,
                                                             String statusCode, BigDecimal pendingPayment, Long batchFolio, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateOrderStatusByOrderNumberAndCompanyCode()", idOperation));
            Optional<StatusEntity> status = statusRepository.findByCode(statusCode);
            StatusDto statusDto = statusConverter.statusEntityToStatusDto(status.get());
            LOG.info(String.format("%s SAVE ORDER", idOperation));
            orderRepository.updateStatusPaymentByOrderNumberAndOrderCode(orderNumber, orderCode, statusDto.getId(),
                    pendingPayment, batchFolio, false);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateOrderStatusByOrderNumberAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderHistoryDto saveRecordInOrderHistory(OrderHistoryDto orderHistoryDto, String idOperation) {

        try {
            LOG.info(String.format("%s INIT saveRecordInOrderHistory()", idOperation));
            OrderHistoryEntity orderHistoryEntity = orderHistoryConverter
                    .orderHistoryDtoToOrderHistoryEntity(orderHistoryDto);

            orderHistoryEntity = orderHistoryRepository.save(orderHistoryEntity);

            return orderHistoryConverter.orderHistoryEntityToOrderHistoryDto(orderHistoryEntity);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN saveRecordInOrderHistory(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OpeningOperationDto getOpeningOperationByEmail(String email, String idOperation) {

        try {
            LOG.info(String.format("%s INIT getOpeningOperationByEmail() ", idOperation));

            Optional<OpeningOperationEntity> openingOperationOptional = openingOperationCalRepository
                    .findByEmployeeEmailAndIsActive(email, true);
            OpeningOperationDto openingOperationDto = null;
            if (openingOperationOptional.isPresent()) {
                openingOperationDto = openingOperationConverter
                        .openingOperationEntityToOpeningOperationDto(openingOperationOptional.get());
            }
            return openingOperationDto;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getOpeningOperationByEmail(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public boolean updateFlagByParams(BigDecimal orderNumber, String orderCode, boolean enable, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateFlagByParams() ", idOperation));
            orderRepository.changeStatusActiveByOrderNumberAndOrderCode(orderNumber, orderCode, enable);
            return true;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateFlagByParams(). EXCEPTION: %s", idOperation, e.getMessage()));
            return false;
        }
    }

    public boolean orderHasPayment(BigDecimal orderNumber, String orderCode) {
        Optional<PaymentEntity> paymentEntityOptional = paymentRepository.findByOrderNumberAndOrderCode(orderNumber,
                orderCode);
        if (!paymentEntityOptional.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    @Override
    public BranchDto getBranchDetailByBranchCodeAndCompanyCode(String branchCode, String companyCode,
                                                               String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBranchDetailByBranchCodeAndCompanyCode() ", idOperation));

            Optional<BranchEntity> branchEntityOptional = branchRepository.findByCodeAndCompanyCode(branchCode,
                    CompanyCodes.valueOf(companyCode));
            if (!branchEntityOptional.isPresent()) {
                return null;
            }
            BranchDto branchDto = branchConverter.branchEntityToBranchDto(branchEntityOptional.get());
            return branchDto;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getBranchDetailByBranchCodeAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public UserDto getUserInformationByEmailAndCompanyCode(String email, String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getUserInformationByEmailAndCompanyCode() ", idOperation));

            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
            if (!userEntityOptional.isPresent()) {
                return null;
            }
            UserDto userDto = userConverter.userEntityToUserDto(userEntityOptional.get(), true);
            return userDto;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getUserInformationByEmailAndCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public UserDto getSellerInformationByEmailAndCompanyCode(Long userNumber, String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getUserInformationByEmailAndCompanyCode() ", idOperation));

            Optional<UserEntity> userEntityOptional = userRepository.findByUserNumber(userNumber);
            if (!userEntityOptional.isPresent()) {
                return null;
            }
            UserDto userDto = userConverter.userEntityToUserDto(userEntityOptional.get(), true);
            return userDto;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getUserInformationByEmailAndCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public PaymentDto getPaymentDetailByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode,
                                                                String idOperation) {
        try {

            LOG.info(String.format("%s INIT getPaymentDetailByOrderNumberAndOrderCode() ", idOperation));

            Optional<PaymentEntity> paymentEntityOptional = paymentRepository.findByOrderNumberAndOrderCode(orderNumber,
                    orderCode);
            if (!paymentEntityOptional.isPresent()) {
                return null;
            }

            PaymentEntity paymentEntity = paymentEntityOptional.get();
            PaymentDto payment = paymentConverter.paymentEntityToPaymentDto(paymentEntity);

            List<PaymentCashEntity> paymentCashEntityList = paymentCashRepository
                    .finByPaymentId(paymentEntity.getPaymentId());
            if (!paymentCashEntityList.isEmpty()) {
                List<PaymentCashDto> paymentCashDtoList = paymentCashConverter
                        .paymentCashEntityListToPaymentCashDtoList(paymentCashEntityList);
                payment.setPaymentCashList(paymentCashDtoList);
            }

            List<CreditCardPaymentEntity> creditCardPaymentEntityList = creditCardPaymentRepository
                    .finByPaymentId(paymentEntity.getPaymentId());
            if (!creditCardPaymentEntityList.isEmpty()) {
                List<CreditCardPaymentDto> creditCardPaymentDtoList = creditCardPaymentConverter
                        .creditCardPaymentEntityListToCreditCardPaymentDtoList(creditCardPaymentEntityList);
                payment.setCreditCardPaymentList(creditCardPaymentDtoList);
            }

            List<TransferPaymentEntity> transferPaymentEntityList = transferPaymentRepository
                    .finByPaymentId(paymentEntity.getPaymentId());
            if (!transferPaymentEntityList.isEmpty()) {
                List<TransferPaymentDto> transferPaymentDtoList = transferPaymentConverter
                        .transferPaymentEntityListToTransferPaymentDtoList(transferPaymentEntityList);
                payment.setTransferPaymentList(transferPaymentDtoList);
            }

            List<CreditNotePaymentEntity> creditNotePaymentEntityList = creditNotePaymentRepository
                    .finByPaymentId(paymentEntity.getPaymentId());
            if (!creditNotePaymentEntityList.isEmpty()) {
                List<CreditNotePaymentDto> creditNotePaymentDtoList = creditNotePaymentConverter
                        .creditNotePaymentEntityListToCreditNotePaymentDtoList(creditNotePaymentEntityList);
                payment.setCreditNotePaymentList(creditNotePaymentDtoList);
            }

            List<CheckPaymentEntity> checkPaymentEntityList = checkPaymentRepository
                    .finByPaymentId(paymentEntity.getPaymentId());
            if (!checkPaymentEntityList.isEmpty()) {
                List<CheckPaymentDto> checkPaymentDtoList = checkPaymentConverter
                        .checkPaymentEntityToCheckPaymentDto(checkPaymentEntityList);
                payment.setCheckPaymentList(checkPaymentDtoList);
            }

            List<CreditPaymentEntity> creditPaymentEntityList = creditPaymentRepository
                    .finByPaymentId(paymentEntity.getPaymentId());
            if (!creditPaymentEntityList.isEmpty()) {
                List<CreditPaymentDto> creditPaymentDtoList = creditPaymentConverter
                        .creditPaymentEntityToCreditPaymentDto(creditPaymentEntityList);
                payment.setCreditPaymentList(creditPaymentDtoList);
            }

            return payment;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getPaymentDetailByOrderNumberAndOrderCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel getPaymentTicket(PaymentTicketDto paymentTicketDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getPaymentTicket() ", idOperation));

            LOG.info(String.format("%s LOAD IMAGE ", idOperation));
            InputStream logoImage = this.getClass().getResourceAsStream(getLogoReport());

            paymentTicketDto.setImage(logoImage);
            paymentTicketDto.setBusinessName(getBusinessName());
            paymentTicketDto.setCompanyName(getCompanyName());

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getTicketTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<PaymentTicketDto> collection = Collections.singletonList(paymentTicketDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getPaymentTicket(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public List<PaidOrderSummaryDto> searchPaidOrderSummaryByCompanyCode(
            GenericSearchPaymentDto genericSearchPaymentDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchPaidOrderSummaryByCompanyCode() ", idOperation));
            List<PaidOrderSummaryDto> paidOrderSummaryDtos = customDSLPaymentRepository
                    .searhPaidSummaryByParams(genericSearchPaymentDto);
            LOG.info(String.format("%s SIZE LIST: %d", idOperation, paidOrderSummaryDtos.size()));
            return paidOrderSummaryDtos;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN searchPaidOrderSummaryByCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            return null;
        }
    }

    @Override
    public void updatePrintStateByPaymentId(Long paymentId, boolean printStatus, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updatePrintStateByPaymentId() ", idOperation));
            paymentRepository.updatePrintSatateByPaymentId(printStatus, paymentId);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updatePrintStateByPaymentId(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
        }
    }

    /**
     * COMPAÑIA SIN IMPLEMENTACIÓN RE REGISTROS DE FACTIRACIÓN
     */
    @Override
    public boolean saveInvoiceReference(InvoiceReferenceDto invoiceReference, PaymentDto paymentDto, String branchCode,
                                        String idOperation) {
        return true;
    }

    /**
     * COMPAÑIA SIN IMPLEMENTACIÓN RE REGISTROS DE FACTIRACIÓN
     */
    @Override
    public InvoiceReferenceDto getInvoiceReferenceByPaymentIdAndCompanyCode(Long paymentId, String idOperation) {
        return null;
    }

}
