package mx.com.endtoend.infrastructure.orders.common.business;


import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.branch.common.repositories.BaseBranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.repository.BaseStatusRepository;
import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepository;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteEntity;
import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCreditNoteRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.repository.BaseOrderConfigurationRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.OrderConfigurationEntity;
import mx.com.endtoend.infrastructure.orders.common.repository.*;
import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import mx.com.endtoend.smart.bussiness.model.orders.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.orders.dto.PdfDto;
import mx.com.endtoend.domain.orders.dto.ProductListDto;
import mx.com.endtoend.domain.orders.dto.tickets.OrderDetail;
import mx.com.endtoend.domain.orders.dto.tickets.TicketDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.genericCommonsFileds.utilities.WrittenCurrency;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.orders.common.entities.AddressEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderDetailEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderHistoryEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderDetailEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.SaleOrderEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.TaxEntity;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class BaseOrderActionRepository implements GenericOrderRepositoryInterface {

    private final BaseOrderRepository orderRepository;
    private final BaseOrderHistoryRepository orderHistoryRepository;
    private final BaseCustomDSLFOrderRepository customDSLFOrderRepository;
    private final BaseUserConfigurationRepository userConfigurationRepository;
    private final BaseOrderConfigurationRepository orderConfigurationRepository;
    private final BaseCreditNoteRepository creditNoteRepository;
    private final BaseClientRepository clientRepository;
    private final BaseStatusRepository statusRepository;
    private final Map<String, String> TEMPLATES;
    private final Logger LOG;
    private final BaseSaleOrderRepository saleOrderRepository;
    private final BaseBranchRepository branchRepository;
    private final UserRepository userRepository;

    private final StatusConverter statusConverter;
    private final AddressConverter addressConverter;
    private final TaxConverter taxConverter;
    private final OrderHistoryConverter orderHistoryConverter;
    private final OrderConverter orderConverter;
    private final OrderDetailConverter orderDetailConverter;
    private final ClientConverter clientConverter;
    private final OrderConfigurationConverter orderConfigurationConverter;
    private final SaleOrderConverter saleOrderConverter;
    private final SaleOrderDetailConverter saleOrderDetailConverter;
    private final BranchConverter branchConverter;
    private final UserConverter userConverter;



    protected String getTemplatePath(String key) {
        String path = TEMPLATES.get(key);
        if (path == null) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en los templates.");
        }
        return path;
    }

    protected String getLogoTicketPath() {
        return getTemplatePath("logo_ticket");
    }

    protected String getTicketTemplatePath() {
        return getTemplatePath("ticket_template");
    }

    protected String getDocumentTemplatePath() {
        return getTemplatePath("document_template");
    }

    protected String getLogoDocumentPath() {
        return getTemplatePath("logo_document");
    }

    protected String getWatermarkDocumentPath() {
        return getTemplatePath("logo_watermark_document");
    }

    protected String getQuoteTemplatePath() {
        return getTemplatePath("quote_template");
    }

    public BaseOrderActionRepository(Map<String, String> _TEMPLATES, Class<?> loggerClass,
                                     /*Clases Necesarias */
                                     BaseStatusRepository _statusRepository,
                                     StatusConverter _statusConverter,
                                     AddressConverter _addressConverter,
                                     TaxConverter _taxConverter,
                                     OrderHistoryConverter _orderHistoryConverter,
                                     OrderConverter _orderConverter,
                                     OrderDetailConverter _orderDetailConverter,
                                     ClientConverter _clientConverter,
                                     OrderConfigurationConverter _orderConfigurationConverter,
                                     SaleOrderConverter _saleOrderConverter,
                                     SaleOrderDetailConverter _saleOrderDetailConverter,
                                     BaseSaleOrderRepository _saleOrderRepository,
                                     BaseBranchRepository _branchRepository,
                                     BranchConverter _branchConverter,
                                     UserRepository _userRepository,
                                     UserConverter _userConverter,
                                     BaseCreditNoteRepository _creditNoteRepository,
                                     BaseClientRepository _clientRepository,
                                     BaseOrderRepository _orderRepository,
                                     BaseOrderHistoryRepository _orderHistoryRepository,
                                     BaseCustomDSLFOrderRepository _customDSLFOrderRepository,
                                     BaseUserConfigurationRepository _userConfigurationRepository,
                                     BaseOrderConfigurationRepository _orderConfigurationRepository
                                     ) {
        TEMPLATES =  _TEMPLATES;
        LOG = LoggerFactory.getLogger(loggerClass);
        statusRepository = _statusRepository;
        statusConverter = _statusConverter;
        addressConverter = _addressConverter;
        taxConverter = _taxConverter;
        orderHistoryConverter = _orderHistoryConverter;
        orderConverter = _orderConverter;
        orderDetailConverter = _orderDetailConverter;
        clientConverter = _clientConverter;
        orderConfigurationConverter = _orderConfigurationConverter;
        saleOrderConverter = _saleOrderConverter;
        saleOrderDetailConverter = _saleOrderDetailConverter;
        saleOrderRepository = _saleOrderRepository;
        branchRepository = _branchRepository;
        branchConverter = _branchConverter;
        userRepository = _userRepository;
        userConverter = _userConverter;
        creditNoteRepository  = _creditNoteRepository;
        clientRepository= _clientRepository;
        orderRepository = _orderRepository;
        orderHistoryRepository = _orderHistoryRepository;
        customDSLFOrderRepository = _customDSLFOrderRepository;
        userConfigurationRepository = _userConfigurationRepository;
        orderConfigurationRepository = _orderConfigurationRepository;
    }

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto, String idOperation) {
        try {
            LOG.info("{} INIT createOrder()", idOperation);
            LOG.info("{} PARAMS: [orderDto: {}]", idOperation, orderDto);

            LOG.info("{} INIT CONVERTION FROM DTO TO ENTITY ---createQuoteOrder()--- ", idOperation);

            LOG.info("{} CONVERT ORDERD", idOperation);
            OrderEntity orderEntity = orderConverter.orderDtoToOrderEntity(orderDto);

            LOG.info("{} CONVERT STATUS", idOperation);
            StatusEntity statusEntity = statusConverter.statusDtoToStatusEntity(orderDto.getStatus());
            orderEntity.setStatus(statusEntity);

            LOG.info("{} CONVERT CLIENT", idOperation);
            orderEntity.setClientId(orderDto.getClient().getId());

            LOG.info("{} ALLOCATION OF ORDER IN TAXES", idOperation);
            List<TaxEntity> taxEntities = taxConverter.taxDtoListToTaxEntityList(orderDto.getTaxes());
            OrderEntity finalOrderEntity = orderEntity;
            taxEntities.forEach(taxEntity -> taxEntity.setOrder(finalOrderEntity));
            orderEntity.setTaxes(taxEntities);

            LOG.info("{} ALLOCATION OF ORDER IN ADDRESSES", idOperation);
            List<AddressEntity> addressEntities = addressConverter.addressDtoListToAddressEntity(orderDto.getAddresses());
            OrderEntity finalOrderEntity1 = orderEntity;
            addressEntities.forEach(addressEntity -> addressEntity.setOrder(finalOrderEntity1));
            orderEntity.setAddresses(addressEntities);

            LOG.info("{} ALLOCATION OF ORDER IN ORDER-DETAIL", idOperation);
            List<OrderDetailEntity> orderDetailEntities = orderDetailConverter
                    .orderDetailDtoListToOrderDetailEntityList(orderDto.getOrderDetail());
            orderDetailEntities.forEach(orderDetailEntity -> orderDetailEntity.setOrder(finalOrderEntity));
            orderEntity.setOrderDetail(orderDetailEntities);

            LOG.info("{} SAVE ORDER", idOperation);
            orderEntity = orderRepository.save(orderEntity);

            if (orderEntity != null) {
                LOG.info("{} SAVE OK", idOperation);
                return orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""), orderDto.getClient());
            } else {
                LOG.error("{} ERROR SAVING ORDER", idOperation);
                return null;
            }
        } catch (Exception e) {
            LOG.error("{} ERROR IN createOrder(). EXCEPTION: {}", idOperation, e.getMessage(), e);
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderDto updateOrder(OrderDto orderDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT updateOrder()", idOperation));
            LOG.info(String.format("%s PARAMS: [orderDto: %s ]", idOperation, orderDto.toString()));

            LOG.info(String.format("%s INIT CONVERTION FROM DTO TO ENTITY ---updateQuoteOrder()--- ", idOperation));

            LOG.info(String.format("%s CONVERT ORDERD", idOperation));
            OrderEntity orderEntity = orderConverter.orderDtoToOrderEntity(orderDto);

            LOG.info(String.format("%s CONVERT STATUS", idOperation));
            StatusEntity statusEntity = statusConverter.statusDtoToStatusEntity(orderDto.getStatus());
            orderEntity.setStatus(statusEntity);

            LOG.info(String.format("%s CONVERT CLIENT", idOperation));
            orderEntity.setClientId(orderDto.getClient().getId());

            LOG.info(String.format("%s ALLOCATION OF ORDER IN TAXES", idOperation));
            List<TaxEntity> taxEntities = taxConverter.taxDtoListToTaxEntityList(orderDto.getTaxes());
            for (TaxEntity taxEntity : taxEntities) {
                taxEntity.setOrder(orderEntity);
            }
            orderEntity.setTaxes(taxEntities);

            LOG.info(String.format("%s ALLOCATION OF ORDER IN ADDRESSES", idOperation));
            List<AddressEntity> addressEntities = addressConverter
                    .addressDtoListToAddressEntity(orderDto.getAddresses());
            for (AddressEntity addressEntity : addressEntities) {
                addressEntity.setOrder(orderEntity);
            }
            orderEntity.setAddresses(addressEntities);

            LOG.info(String.format("%s ALLOCATION OF ORDER IN ORDER-DETAIL", idOperation));
            List<OrderDetailEntity> orderDetailEntities = orderDetailConverter
                    .orderDetailDtoListToOrderDetailEntityList(orderDto.getOrderDetail());

            for (OrderDetailEntity orderDetailEntity : orderDetailEntities) {
                orderDetailEntity.setOrder(orderEntity);
            }

            orderEntity.setOrderDetail(orderDetailEntities);

            LOG.info(String.format("%s UPDATE ORDER", idOperation));
            orderEntity = orderRepository.save(orderEntity);

            if (orderEntity != null) {
                LOG.info(String.format("%s UPDATE OK", idOperation));
                return orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""), null);
            } else {
                LOG.error(String.format("%s ERROR UPDATING ORDER ", idOperation));
                return null;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN updateOrder(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderDto updateStatusOrderByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode,
                                                               StatusDto status, BigDecimal pendingPayment, String idOperation) {

        try {

            LOG.info(String.format("%s INIT updateStatusOrderByOrderNumberAndOrderCode()", idOperation));

            Optional<OrderEntity> order = orderRepository.findByOrderCodeAndOrderNumber(orderNumber, orderCode);

            if (order.isPresent()) {

                LOG.info(String.format("%s ORDER FOUND, SET NEW STATUS", idOperation));
                OrderEntity orderEntity = order.get();
                StatusEntity statusEntity = statusConverter.statusDtoToStatusEntity(status);
                orderEntity.setStatus(statusEntity);
                orderEntity.setPendingPayment(pendingPayment);
                orderEntity = orderRepository.save(orderEntity);
                return orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList("status"), null);

            } else {
                LOG.error(String.format("%s ORDER NOT FOUND", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateStatusOrderByOrderNumberAndOrderCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            return null;
        }
    }

    @Transactional
    @Override
    public OrderDto cancelOrder(OrderDto orderDto, String idOperation) {
        try {
            LOG.info(String.format("%S INIT cancelOrder()", idOperation));
            LOG.info(String.format("%s INIT CONVERT STATUS TO ENTITY ", idOperation));
            StatusEntity statusEntity = statusConverter.statusDtoToStatusEntity(orderDto.getStatus());
            LOG.info(String.format("%s INIT CANCEL ORDER ", idOperation));
            orderRepository.changeStatusOrderByOrderNumberAndCode(orderDto.getOrderNumber(), orderDto.getOrderCode(),
                    statusEntity.getId());
            return orderDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN cancelOrder(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }


    @Transactional
    @Override
    public OrderDto approveOrder(String orderCode, BigDecimal orderNumber, String idOperation) {

        try {

            LOG.info(String.format("%s INIT approveOrder()", idOperation));
            LOG.info(String.format("%s PARAMS: [orderCode: %s , orderNumber: %s ]", idOperation, orderCode,
                    orderNumber.toString()));

            int approveOrder = 0;

            Optional<OrderEntity> orderOptional = orderRepository.findByOrderCodeAndOrderNumber(orderNumber, orderCode);

            if (!orderOptional.isPresent()) {

                LOG.error(String.format("%s ERROR IN SERCH ORDER BY ORDER TYPE: %s AND ORDER-NUMBER: %d ", idOperation,
                        orderCode, orderNumber));
                throw new GlobalError();
            }

            OrderEntity orderEntity = orderOptional.get();

            LOG.info(String.format("%s UPDATE ORDER", idOperation));
            approveOrder = orderRepository.approveOrderByIdAndOrderNumber(orderEntity.getOrderId(), false, orderNumber);

            if (approveOrder > 0) {

                LOG.info(String.format("%s UPDATED ORDER OK", idOperation));
                orderEntity.setRetentionOrder(false);

                return orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""), null);

            } else {

                LOG.info(String.format("%s ERROR UPDATED ORDER", idOperation));
                return null;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN approveOrder(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public SaleOrderDto createOrderDitect(SaleOrderDto saleOrderDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT createOrderDitect() ", idOperation));
            LOG.info(String.format("%s PARAMS: [saleOrderDto: %s ]", idOperation, saleOrderDto.toString()));

            LOG.info(String.format("%s CONVERT ORDERD", idOperation));
            SaleOrderEntity saleOrderEntity = saleOrderConverter.saleOrderDtoToSaleOrderEntity(saleOrderDto);

            LOG.info(String.format("%s CONVERT STATUS", idOperation));
            StatusEntity statusEntity = statusConverter.statusDtoToStatusEntity(saleOrderDto.getStatus());
            saleOrderEntity.setStatus(statusEntity);

            LOG.info(String.format("%s CONVERT CLIENT", idOperation));
            ClientEntity clientEntity = clientConverter.clientDtoToClientEntity(saleOrderDto.getClient());
            saleOrderEntity.setClient(clientEntity);

            LOG.info(String.format("%s ALLOCATION OF ORDER IN ORDER-DETAIL", idOperation));
            List<SaleOrderDetailEntity> orderDetailEntities = saleOrderDetailConverter
                    .saleOrderDetailDtoListToSaleOrderDetailEntityList(saleOrderDto.getSaleOrderDetail());

            for (SaleOrderDetailEntity saleOrderDetailEntity : orderDetailEntities) {
                saleOrderDetailEntity.setSaleOrder(saleOrderEntity);
            }
            saleOrderEntity.setSaleOrderDetail(orderDetailEntities);

            LOG.info(String.format("%s CONVERT ORDER %d", idOperation, saleOrderDto.getOrder().getOrderId()));
            OrderEntity orderEntity = orderConverter.orderDtoToOrderEntity(saleOrderDto.getOrder());
            saleOrderEntity.setOrder(orderEntity);

            LOG.info(String.format("%s SAVE ORDER", idOperation));
            saleOrderEntity = saleOrderRepository.save(saleOrderEntity);

            LOG.info(String.format("%s SAVE ORDER DIRECT OK", idOperation));
            return saleOrderConverter.saleOrderEntityToSaleOrderDto(saleOrderEntity);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN createOrderDitect(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public SaleOrderDto updateOrderDirect(SaleOrderDto saleOrderDto, String idOperation) {

        try {
            LOG.info(String.format("%s INIT updateOrderDirect() ", idOperation));
            LOG.info(String.format("%s PARAMS: [saleOrderDto: %s ]", idOperation, saleOrderDto.toString()));

            LOG.info(String.format("%s CONVERT ORDERD", idOperation));
            SaleOrderEntity saleOrderEntity = saleOrderConverter.saleOrderDtoToSaleOrderEntity(saleOrderDto);

            LOG.info(String.format("%s CONVERT STATUS", idOperation));
            StatusEntity statusEntity = statusConverter.statusDtoToStatusEntity(saleOrderDto.getStatus());
            saleOrderEntity.setStatus(statusEntity);

            LOG.info(String.format("%s CONVERT CLIENT", idOperation));
            ClientEntity clientEntity = clientConverter.clientDtoToClientEntity(saleOrderDto.getClient());
            saleOrderEntity.setClient(clientEntity);

            LOG.info(String.format("%s ALLOCATION OF ORDER IN ORDER-DETAIL", idOperation));
            List<SaleOrderDetailEntity> orderDetailEntities = saleOrderDetailConverter
                    .saleOrderDetailDtoListToSaleOrderDetailEntityList(saleOrderDto.getSaleOrderDetail());

            for (SaleOrderDetailEntity saleOrderDetailEntity : orderDetailEntities) {
                saleOrderDetailEntity.setSaleOrder(saleOrderEntity);
            }

            saleOrderEntity.setSaleOrderDetail(orderDetailEntities);

            LOG.info(String.format("%s CONVERT ORDER %d", idOperation, saleOrderDto.getOrder().getOrderId()));
            OrderEntity orderEntity = orderConverter.orderDtoToOrderEntity(saleOrderDto.getOrder());
            saleOrderEntity.setOrder(orderEntity);

            LOG.info(String.format("%s UPDATE ORDER", idOperation));
            saleOrderEntity = saleOrderRepository.save(saleOrderEntity);

            LOG.info(String.format("%s UPDATE ORDER DIRECT OK", idOperation));

            return saleOrderConverter.saleOrderEntityToSaleOrderDto(saleOrderEntity);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN updateOrderDirect(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public SaleOrderDto cancelOrderDirect(BigDecimal orderNumber, String companyCode, String brancheCode,
                                          String idOperation) {

        try {

            LOG.info(String.format("%s INIT cancelOrderDirect()", idOperation));
            LOG.info(String.format("%s PARAMS: [orderNumber: %s , companyCode: %s , brancheCode: %s ]", idOperation,
                    orderNumber.toString(), companyCode, brancheCode));

            Optional<OrderEntity> orderOptional = orderRepository.findByOrderNumber(orderNumber);

            if (orderOptional.isPresent()) {

                OrderEntity orderEntity = orderOptional.get();

                Optional<SaleOrderEntity> saleOrderOptional = saleOrderRepository
                        .findByOrderId(orderEntity.getOrderId());

                if (saleOrderOptional.isPresent()) {

                    SaleOrderEntity saleOrderEntity = saleOrderOptional.get();

                    saleOrderEntity.setStatus(orderEntity.getStatus());
                    LOG.info(String.format("%s UPDATE SALE-DIRECT-ORDER", idOperation));
                    saleOrderEntity = saleOrderRepository.save(saleOrderEntity);

                    LOG.info(String.format("%s RETURN SALE ORDER UPDATED", idOperation));

                    return saleOrderConverter.saleOrderEntityToSaleOrderDto(saleOrderEntity);

                } else {

                    LOG.warn(String.format("%s ERROR IN FOUND SALE-DIRECT-ORDER ", idOperation));
                    return null;
                }

            } else {

                LOG.warn(String.format("%s ERROR IN FOUND PRINCIPAL ORDER ", idOperation));
                return null;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN cancelOrderDirect(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }


    @Transactional
    @Override
    public SaleOrderDto viewOrderDirectByOrderNumber(BigDecimal orderNumber, String companyCode, String brancheCode,
                                                     String idOperation) {

        try {

            LOG.info(String.format("%s INIT viewOrderDirectByOrderNumber()", idOperation));
            LOG.info(String.format("%s PARAMS: [orderNumber: %s , companyCode: %s , brancheCode: %s ]", idOperation,
                    orderNumber.toString(), companyCode, brancheCode));

            Optional<SaleOrderEntity> saleOrder = saleOrderRepository.findByPrincipalOrderNumber(orderNumber);

            if (saleOrder.isPresent()) {
                LOG.info(String.format("%s ORDER FOUND, START CONVERSIONS", idOperation));

                SaleOrderEntity saleOrderEntity = saleOrder.get();

                LOG.info(String.format("%s CONVERT ORDER ", idOperation));
                SaleOrderDto saleOrderDto = saleOrderConverter.saleOrderEntityToSaleOrderDto(saleOrderEntity);

                LOG.info(String.format("%s CONVERT STATUS ", idOperation));
                StatusDto statusDto = statusConverter.statusEntityToStatusDto(saleOrderEntity.getStatus());
                saleOrderDto.setStatus(statusDto);

                LOG.info(String.format("%s CONVERT CLIENT ", idOperation));
                ClientDto clientDto = clientConverter.clientEntityToClientDto(saleOrderEntity.getClient());
                saleOrderDto.setClient(clientDto);

                LOG.info(String.format("%s ALLOCATION OF ORDER IN ORDER-DETAIL", idOperation));

                List<SaleOrderDetailDto> orderDetailDtoList = saleOrderDetailConverter
                        .saleOrderDetailEntityListToSaleOrderDetailDtoList(saleOrderEntity.getSaleOrderDetail());

                saleOrderDto.setSaleOrderDetail(orderDetailDtoList);

                LOG.info(String.format("%s CONVERT ORDER %d", idOperation, saleOrderEntity.getOrder().getOrderId()));
                OrderDto orderDto = orderConverter.orderEntityToOrderDto(saleOrderEntity.getOrder(), Arrays.asList(""), null);
                saleOrderDto.setOrder(orderDto);

                LOG.info(String.format("%s RETURN ORDER DIRECT", idOperation));
                return saleOrderDto;

            } else {

                LOG.warn(String.format("%s ORDER DIRECT: %s NOT FOUND", idOperation, orderNumber.toString()));
                return null;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN viewOrderDirectByOrderNumber(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public OrderDto viewOrderDetailByOrderNumber(BigDecimal orderNumber, String orderCode, String companyCode,
                                                 String branch, String idOperation) {

        try {

            LOG.info(String.format("%s INIT viewOrderDetailByOrderNumber() ", idOperation));
            LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderCode: %s , companyCode: %s , branch: %s ]",
                    idOperation, orderNumber.toString(), orderCode, companyCode, branch));

            Optional<OrderEntity> order = orderRepository.findByOrderCodeAndOrderNumber(orderNumber, orderCode);

            if (order.isPresent()) {
                LOG.info(String.format("%s ORDER FOUND, START CONVERSIONS", idOperation));

                OrderEntity orderEntity = order.get();

                LOG.info(String.format("%s CONVERT ORDER ", idOperation));
                OrderDto orderDto = orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""), null);

                LOG.info(String.format("%s CONVERT STATUS ", idOperation));
                StatusDto statusDto = statusConverter.statusEntityToStatusDto(orderEntity.getStatus());
                orderDto.setStatus(statusDto);

                LOG.info(String.format("%s CONVERT CLIENT ", idOperation));
                ClientDto clientDto = clientConverter
                        .clientEntityToClientDto(clientRepository.findById(orderEntity.getClientId()));
                orderDto.setClient(clientDto);

                LOG.info(String.format("%s CONVERT ADRESSES ", idOperation));
                List<AddressDto> addressDtoList = addressConverter
                        .addresEntityListToAddresDtoList(orderEntity.getAddresses());
                orderDto.setAddresses(addressDtoList);

                LOG.info(String.format("%s CONVERT TAXES ", idOperation));
                List<TaxDto> taxDtoList = taxConverter.taxEntityListToTaxDtoList(orderEntity.getTaxes());
                orderDto.setTaxes(taxDtoList);

                LOG.info(String.format("%s CONVERT ORDER-DETAIL ", idOperation));
                List<OrderDetailDto> orderDetailDto = orderDetailConverter
                        .orderDetailEntityToOrderDetailDetoList(orderEntity.getOrderDetail());
                orderDto.setOrderDetail(orderDetailDto);

                LOG.info(String.format("%s RETURN ORDER ", idOperation));
                return orderDto;

            } else {
                LOG.warn(String.format("%s ORDER: %s NOT FOUND", idOperation, orderNumber.toString()));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN viewOrderDetailByOrderNumber(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public List<OrderHistoryDto> viewHistoricalDetailOrderByOrderNumber(BigDecimal orderNumber, String companyCode,
                                                                        String branch, String idOperation) {

        try {

            LOG.info(String.format("%s INIT viewHistoricalDetailOrderByOrderNumber( )", idOperation));
            LOG.info(String.format("%s PARAMS: [orderNumber: %s , companyCode: %s , branch: %s ]", idOperation,
                    orderNumber.toString(), companyCode, branch));

            List<OrderHistoryEntity> orderHistoricalEntities = orderHistoryRepository.findByOrderCode(orderNumber);

            if (orderHistoricalEntities != null) {
                LOG.info(String.format("%s ORDER-HISTORICAL FOUND, SATART CONVERSIONS", idOperation));
                List<OrderHistoryDto> orderHistoryDtoList = orderHistoryConverter
                        .orderHistoryEntityListToOrderHistoryDtoList(orderHistoricalEntities);

                LOG.info(String.format("%s RETURN ORDER-HISTORICAL ", idOperation));
                return orderHistoryDtoList;

            } else {
                LOG.warn(String.format("%s ORDER-HISTORICAL: %s NOT FOUND", idOperation, orderNumber.toString()));
                return null;
            }
        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN viewHistoricalDetailOrderByOrderNumber(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public UserDto getPersonalUserConfigurationById(Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT getPersonalUserConfigurationById()", idOperation));
            LOG.info(String.format("%s PARAMS: [ id: %s ]", idOperation, id.toString()));

            Optional<UserEntity> userOptional = userRepository.findById(id);

            if (userOptional.isPresent()) {
                LOG.info(String.format("%s USER FOUND, CONVERT TO DTO", idOperation));
                return userConverter.userEntityToUserDto(userOptional.get(), true);

            } else {

                LOG.info(String.format("%s USER NOT FOUND, RETURN NULL VALUE", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getUserConfiguration(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public StatusDto getStatusByCode(String branchCode, String statusCode, String idOperation) {

        try {
            LOG.info(String.format("%s INIT getStatusByCode() ", idOperation));
            LOG.info(String.format("%s PARAMS: [ branchCode: %s , statusCode: %s ]", idOperation, branchCode,
                    statusCode));

            Optional<StatusEntity> status = statusRepository.findByCode(statusCode);
            if (status.isPresent()) {
                LOG.info(String.format("%s STATUS: %s  FOUND", idOperation, statusCode));
                StatusDto statusDto = statusConverter.statusEntityToStatusDto(status.get());
                return statusDto;
            } else {
                LOG.warn(String.format("%s STATUS %s NOT FOUND", idOperation, statusCode));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getUserConfiguration(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderHistoryDto createRecordToOrderHistory(OrderHistoryDto orderHistoryDto, String idOperation) {

        try {
            LOG.info(String.format("%s INIT createRecordToOrderHistory()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderHistoryDto: %s ]", idOperation, orderHistoryDto.toString()));

            OrderHistoryEntity orderHistoryEntity = orderHistoryConverter
                    .orderHistoryDtoToOrderHistoryEntity(orderHistoryDto);

            orderHistoryEntity = orderHistoryRepository.save(orderHistoryEntity);

            if (orderHistoryEntity != null) {

                LOG.info(String.format("%s SAVE OK", idOperation));
                return orderHistoryConverter.orderHistoryEntityToOrderHistoryDto(orderHistoryEntity);

            } else {
                LOG.error(String.format("%s ERROR SAVING ORDER ", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createRecordToOrderHistory(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public boolean getOrderToUpdate(BigDecimal orderNumber, String orderType, String idOperation) {

        try {

            LOG.info(String.format("%s INIT getOrderToUpdate()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderType: %s ]", idOperation,
                    orderNumber.toString(), orderType));

            int afectedRows = orderRepository.changeStatusOrderToActiveUpdate(orderNumber, orderType);

            boolean activeUpdate = ((afectedRows > 0) ? true : false);

            LOG.info(String.format("%s ORDER UPDATE: %b", idOperation, activeUpdate));

            return activeUpdate;

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN getOrderToUpdate(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public boolean cancelUpdateOrder(BigDecimal orderNumber, String orderType, String idOperation) {

        try {

            LOG.info(String.format("%s INIT cancelUpdateOrder()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderNumber: %s , orderType: %s ]", idOperation,
                    orderNumber.toString(), orderType));

            int afectedRows = orderRepository.changeStatusOrderToInactiveUpdate(orderNumber, orderType);

            boolean inactiveUpdate = ((afectedRows > 0) ? true : false);

            LOG.info(String.format("%s ORDER UPDATE: %b", idOperation, inactiveUpdate));

            return inactiveUpdate;

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN cancelUpdateOrder(). EXCEPTION: %s", idOperation, e.getMessage()));

            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public ResponseModel generateTicket(OrderDto orderDto, UserDto userDto, String branch, String orderType,
                                        String idOperation) {

        try {

            LOG.info(String.format("%s INIT generateTicket()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderDto: %s , branch: %s , orderType: %s ]", idOperation,
                    orderDto.toString(), branch, orderType));

            LOG.info(String.format("%s LOAD IMAGE ", idOperation));
            InputStream logoImage = this.getClass().getResourceAsStream(getLogoTicketPath());

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            String employeeName = userDto.getName() + " " + userDto.getFirstSurname() + " "
                    + userDto.getSecondSurname();

            List<OrderDetail> detail = new ArrayList<OrderDetail>();
            TicketDto ticketDto = new TicketDto();

            ticketDto.setOrder(orderType);
            ticketDto.setClientNumber(orderDto.getClient().getNoClient().toString());
            ticketDto.setClientPhone(orderDto.getClient().getCell());
            ticketDto.setEmployeName(employeeName);
            ticketDto.setBarcodeNumber(
                    orderDto.getOrderCode() + "!" + String.valueOf(orderDto.getOrderNumber().longValue()));
            ticketDto.setDate(strDate);
            ticketDto.setOrderNumber(String.valueOf(orderDto.getOrderNumber().longValue()));
            ticketDto.setImage(logoImage);

            for (OrderDetailDto orderDetailDto : orderDto.getOrderDetail()) {
                OrderDetail oDetail = new OrderDetail();

                oDetail.setArticle(orderDetailDto.getArticleCode());
                oDetail.setDescription(
                        orderDetailDto.getDescriptionOne().trim() + orderDetailDto.getDescriptionTwo().trim());
                oDetail.setUm(orderDetailDto.getUnitMeasurement());
                oDetail.setQuantity(String.valueOf(orderDetailDto.getRequestAmount()));

                detail.add(oDetail);

            }

            ticketDto.setDetail(detail);
            ticketDto.setRequestTotal(orderDto.getOrderDetail().size());

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getTicketTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<TicketDto> collection = Collections.singletonList(ticketDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN GENERATE TICKET", idOperation));
            LOG.error(e.getMessage());
            throw new GlobalError();
        }

    }
    @Transactional
    @Override
    public ResponseModel generateDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto, String idOperation) {

        try {

            LOG.info(String.format("%s INIT generateDocument()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderDto: %s ]", idOperation, orderDto.toString()));

            LOG.info(String.format("%s LOAD LOGO-IMAGE ", idOperation));
            InputStream logoImage = this.getClass().getResourceAsStream(getLogoDocumentPath());
            LOG.info(String.format("%s LOAD WATER-BRAND-IMAGE ", idOperation));
            InputStream watermark = this.getClass().getResourceAsStream(getWatermarkDocumentPath());

            PdfDto pdfDto = new PdfDto();
            AddressDto direction = new AddressDto();
            AddressDto shippingAddress = new AddressDto();
            WrittenCurrency amountLetter = new WrittenCurrency();

            pdfDto.setLogo(logoImage);
            pdfDto.setWatermark(watermark);

            Date date = new Date();
            String fomatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);

            for (OrderDetailDto detail : orderDto.getOrderDetail()) {
                detail.setSubTotal(detail.getSubTotal());
                detail.setUnitPriceTax(detail.getUnitPriceTax());
                detail.setSubTotalTax(detail.getSubTotalTax());
                detail.setArticleTax(detail.getArticleTax());
            }

            for (AddressDto address : orderDto.getAddresses()) {

                if (address.getAddressType().contains("Fis")) {
                    direction = address;
                }

                if (address.getAddressType().contains("Env")) {
                    shippingAddress = address;
                }

            }

            LOG.info(String.format("%s SET TAXES", idOperation));
            for (TaxDto taxDto : orderDto.getTaxes()) {

                if (taxDto.getTaxValue().equals("0")) {
                    pdfDto.setFreeTax(taxDto.getValue());
                }

                if (taxDto.getTaxValue().equals("16")) {
                    pdfDto.setNationalTax(taxDto.getValue());
                }

                if (taxDto.getTaxValue().equals("IEPS")) {
                    pdfDto.setIepsTax(taxDto.getValue());
                }
            }

            pdfDto.setEmployeeName(
                    userDto.getName() + " " + userDto.getFirstSurname() + " " + userDto.getSecondSurname());
            pdfDto.setBranchName(branchDto.getName());
            pdfDto.setOrderType(orderDto.getOrderType());
            pdfDto.setOrderCode(orderDto.getOrderCode());

            String nunber = orderDto.getOrderNumber().toString();
            int indice = nunber.indexOf(".");
            String orderNumber = nunber.substring(0, indice);

            pdfDto.setOrderNumber(orderNumber);
            pdfDto.setDate(fomatDate);
            pdfDto.setUserNumber(orderDto.getUserNumber().toString());

            pdfDto.setMail(orderDto.getEmployeeEmail());
            pdfDto.setNoClient(orderDto.getClient().getNoClient().toString());
            if (!orderDto.getClient().getName().isEmpty()) {
                pdfDto.setName(orderDto.getClient().getName() + " " + orderDto.getClient().getFatherSurname() + " "
                        + orderDto.getClient().getMotherSurname());// apellidos igual
            } else {

                pdfDto.setName(orderDto.getClient().getBusinessName());// apellidos igual

            }
            pdfDto.setState(direction.getState());
            pdfDto.setOutdoorNumber(direction.getOutdoorNumber());
            pdfDto.setInteriorNumber(direction.getInteriorNumber());
            pdfDto.setCp(direction.getCp().toString());
            pdfDto.setColony(direction.getColony());
            pdfDto.setDelegation(direction.getDelegation());
            pdfDto.setStreet(direction.getStreet());

            pdfDto.setPhone(orderDto.getClient().getPhone());
            pdfDto.setCell(orderDto.getClient().getCell());

            pdfDto.setStateShipping(shippingAddress.getState());
            pdfDto.setOutdoorNumberShipping(shippingAddress.getOutdoorNumber());
            pdfDto.setInteriorNumberShipping(shippingAddress.getInteriorNumber());
            pdfDto.setCpShipping(shippingAddress.getCp().toString());
            pdfDto.setColonyShipping(shippingAddress.getColony());
            pdfDto.setDelegationShipping(shippingAddress.getDelegation());
            pdfDto.setStreetShipping(shippingAddress.getStreet());

            pdfDto.setObservations(orderDto.getObservations());
            pdfDto.setIvaTotal(orderDto.getIvaTotal().toString());
            pdfDto.setOrderTotal(orderDto.getOrderTotal().toString());

            List<ProductListDto> list = new ArrayList<>();
            List<OrderDetailDto> orderdetail = orderDto.getOrderDetail();

            LOG.info(String.format("%s LOAD ARTICLES ", idOperation));

            for (OrderDetailDto products : orderdetail) {

                ProductListDto listDto = new ProductListDto();

                listDto.setLineNumber(Integer.toString(products.getLineNumber()));
                listDto.setSku(products.getArticleCode().trim());
                listDto.setDescription(products.getDescriptionOne() + products.getDescriptionTwo());
                listDto.setUnitMeasurement(products.getUnitMeasurement());
                listDto.setRequestAmount(products.getRequestAmount().toString());
                listDto.setUnitPrice(products.getFinalUnitPrice().toString());
                listDto.setDiscountSeller(products.getDiscountSeller().compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.ZERO.toString() : products.getDiscountSeller().toString());
                listDto.setSubTotal(products.getSubTotal().toString());

                list.add(listDto);
            }

            pdfDto.setProducts(list);
            pdfDto.setCurrency(orderDto.getCurrency());
            pdfDto.setSubtotal(orderDto.getSubTotal().toString());
            pdfDto.setAmountLetter(amountLetter.enterNumber(orderDto.getOrderTotal().toString()));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getDocumentTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<PdfDto> collection = Collections.singletonList(pdfDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN generateDocument()", idOperation));
            LOG.error(e.getMessage());
            return new ResponseModel(null);

        }
    }

    @Transactional
    @Override
    public ResponseModel generateQuoteDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto,
                                               String idOperation) {

        try {

            LOG.info(String.format("%s INIT generateQuoteDocument()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderDto: %s ]", idOperation, orderDto.toString()));

            LOG.info(String.format("%s LOAD LOGO-IMAGE ", idOperation));
            InputStream logoImage = this.getClass().getResourceAsStream(getLogoDocumentPath());
            LOG.info(String.format("%s LOAD WATER-BRAND-IMAGE ", idOperation));
            InputStream watermark = this.getClass().getResourceAsStream(getWatermarkDocumentPath());

            PdfDto pdfDto = new PdfDto();
            AddressDto direction = new AddressDto();
            AddressDto shippingAddress = new AddressDto();
            WrittenCurrency amountLetter = new WrittenCurrency();

            pdfDto.setLogo(logoImage);
            pdfDto.setWatermark(watermark);

            Date date = new Date();
            String fomatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);

            for (OrderDetailDto detail : orderDto.getOrderDetail()) {
                detail.setSubTotal(detail.getSubTotal());
                detail.setUnitPriceTax(detail.getUnitPriceTax());
                detail.setSubTotalTax(detail.getSubTotalTax());
                detail.setArticleTax(detail.getArticleTax());
            }

            for (AddressDto address : orderDto.getAddresses()) {

                if (address.getAddressType().contains("Fis")) {
                    direction = address;
                }

                if (address.getAddressType().contains("Env")) {
                    shippingAddress = address;
                }

            }

            LOG.info(String.format("%s SET TAXES", idOperation));
            for (TaxDto taxDto : orderDto.getTaxes()) {

                if (taxDto.getTaxValue().equals("0")) {
                    pdfDto.setFreeTax(taxDto.getValue());
                }

                if (taxDto.getTaxValue().equals("16")) {
                    pdfDto.setNationalTax(taxDto.getValue());
                }

                if (taxDto.getTaxValue().equals("IEPS")) {
                    pdfDto.setIepsTax(taxDto.getValue());
                }
            }

            pdfDto.setEmployeeName(
                    userDto.getName() + " " + userDto.getFirstSurname() + " " + userDto.getSecondSurname());
            pdfDto.setBranchName(branchDto.getName());
            pdfDto.setOrderType(orderDto.getOrderType());
            pdfDto.setOrderCode(orderDto.getOrderCode());

            String nunber = orderDto.getOrderNumber().toString();
            int indice = nunber.indexOf(".");
            String orderNumber = nunber.substring(0, indice);

            pdfDto.setOrderNumber(orderNumber);
            pdfDto.setDate(fomatDate);
            pdfDto.setUserNumber(orderDto.getUserNumber().toString());

            pdfDto.setMail(orderDto.getEmployeeEmail());
            pdfDto.setNoClient(orderDto.getClient().getNoClient().toString());

            if (!orderDto.getClient().getName().isEmpty()) {
                pdfDto.setName(orderDto.getClient().getName() + " " + orderDto.getClient().getFatherSurname() + " "
                        + orderDto.getClient().getMotherSurname());// apellidos igual
            } else {

                pdfDto.setName(orderDto.getClient().getBusinessName());// apellidos igual

            }
            pdfDto.setState(direction.getState());
            pdfDto.setOutdoorNumber(direction.getOutdoorNumber());
            pdfDto.setInteriorNumber(direction.getInteriorNumber());
            pdfDto.setCp(direction.getCp().toString());
            pdfDto.setColony(direction.getColony());
            pdfDto.setDelegation(direction.getDelegation());
            pdfDto.setStreet(direction.getStreet());

            pdfDto.setPhone(orderDto.getClient().getPhone());
            pdfDto.setCell(orderDto.getClient().getCell());

            pdfDto.setStateShipping(shippingAddress.getState());
            pdfDto.setOutdoorNumberShipping(shippingAddress.getOutdoorNumber());
            pdfDto.setInteriorNumberShipping(shippingAddress.getInteriorNumber());
            pdfDto.setCpShipping(shippingAddress.getCp().toString());
            pdfDto.setColonyShipping(shippingAddress.getColony());
            pdfDto.setDelegationShipping(shippingAddress.getDelegation());
            pdfDto.setStreetShipping(shippingAddress.getStreet());

            pdfDto.setObservations(orderDto.getObservations());
            pdfDto.setIvaTotal(orderDto.getIvaTotal().toString());
            pdfDto.setOrderTotal(orderDto.getOrderTotal().toString());

            List<ProductListDto> list = new ArrayList<>();
            List<OrderDetailDto> orderdetail = orderDto.getOrderDetail();

            LOG.info(String.format("%s LOAD ARTICLES ", idOperation));

            for (OrderDetailDto products : orderdetail) {

                ProductListDto listDto = new ProductListDto();

                listDto.setLineNumber(Integer.toString(products.getLineNumber()));
                listDto.setSku(products.getArticleCode().trim());
                listDto.setDescription(products.getDescriptionOne() + products.getDescriptionTwo());
                listDto.setUnitMeasurement(products.getUnitMeasurement());
                listDto.setRequestAmount(products.getRequestAmount().toString());
                listDto.setUnitPrice(products.getFinalUnitPrice().toString());
                listDto.setDiscountSeller(products.getDiscountSeller().compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.ZERO.toString() : products.getDiscountSeller().toString());
                listDto.setSubTotal(products.getSubTotal().toString());

                list.add(listDto);
            }

            pdfDto.setProducts(list);
            pdfDto.setCurrency(orderDto.getCurrency());
            pdfDto.setSubtotal(orderDto.getSubTotal().toString());
            pdfDto.setAmountLetter(amountLetter.enterNumber(orderDto.getOrderTotal().toString()));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getQuoteTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<PdfDto> collection = Collections.singletonList(pdfDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN generateDocument()", idOperation));
            LOG.error(e.getMessage());
            return new ResponseModel(null);

        }

    }
    @Transactional
    @Override
    public BranchDto getBranchByCodeAndCompanyCode(String branchCode, String companyCode, String idOperation) {

        try {
            LOG.info(String.format("%s INIT getBranchByCodeAndCompanyCode() ", idOperation));
            LOG.info(String.format("%s PARAMS: [branchCode: %s , companyCode: %s ]", idOperation, branchCode,
                    companyCode));

            Optional<BranchEntity> branchOptional = branchRepository.findByCodeAndCompanyCode(branchCode,
                    CompanyCodes.valueOf(companyCode));

            if (!branchOptional.isPresent()) {

                LOG.info(String.format("%s ERROR IN SERCH BRANCH-DETAIL ", idOperation));
                return null;

            } else {

                BranchDto branchDto = branchConverter.branchEntityToBranchDto(branchOptional.get());
                LOG.info(String.format("%s RETURN BRANCH-DETAIL: %s ", idOperation, branchDto.toString()));
                return branchDto;
            }

        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR getBranchByCodeAndCompanyCode(). ERROR: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public boolean updateConvertionStatusByOrderNumber(String orderCode, BigDecimal orderNumber, String idOperation) {

        try {

            LOG.info(String.format("%s INIT updateConvertionStatusByOrderNumber() ", idOperation));
            LOG.info(String.format("%s PARAMS: [orderCode: %s , orderNumber: %s ]", idOperation, orderCode,
                    orderNumber.toString()));

            int updateOrder = 0;

            LOG.info(String.format("%s UPDATE ORDER", idOperation));
            updateOrder = orderRepository.updateConvertionStatusByOrderNumberAndOrderCode(orderCode, true, orderNumber);

            if (updateOrder > 0) {

                LOG.info(String.format("%s UPDATED ORDER OK", idOperation));
                return true;

            } else {

                LOG.info(String.format("%s ERROR UPDATED ORDER", idOperation));
                return false;
            }

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN updateConvertionStatusByOrderNumber(). EXCEPTION: %s", idOperation,
                    e.getMessage()));

            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public EmployeeDto getUserConfiguration(String branchCode, String email, String idOperation) {

        try {
            LOG.info("{} INIT getUserConfiguration()", idOperation);
            LOG.info("{} PARAMS: [ email: {} , branchCode: {} ]", idOperation, email, branchCode);

            EmployeeDto employeeDto = userConfigurationRepository
                    .findUserConfigurationByEmailAndBranchCode(email, branchCode, idOperation);

            if (employeeDto != null) {
                LOG.info("{} USER: {} FOUND", idOperation, email);
            } else {
                LOG.warn("{} USER {} NOT FOUND", idOperation, email);
            }
            return employeeDto;

        } catch (Exception e) {
            LOG.error("{} ERROR IN getUserConfiguration(). EXCEPTION: {}", idOperation, e.getMessage());
            throw new GlobalError();
        }
    }



    @Transactional
    @Override
    public List<OrderDto> getOrderList(GenericSerchParamsOrderDto params, String companyCode, String branch,
                                       String idOperation) {

        try {
            LOG.info("{} INIT getOrderList() ", idOperation);
            List<OrderSummary> orderSummaryList = customDSLFOrderRepository.getOrderByParams(params);
            return convertAndEnhanceOrders(orderSummaryList);
        } catch (Exception e) {
            LOG.error("{} ERROR IN getOrderList(). EXCEPTION: {}", idOperation, e.getMessage());
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderConfigurationDto getOrderConfigurationByOrderCode(String orderCode, String idOperation) {

        try {

            LOG.info("{} INIT getOrderConfigurationByOrderCode()", idOperation);
            LOG.info("{} PARAMS: [ orderCode: {}  ]", idOperation, orderCode);

            OrderConfigurationEntity orderConfigurationEntity = orderConfigurationRepository.findByOrerCode(orderCode);

            if (orderConfigurationEntity != null) {
                LOG.info("{} ORDER CONFIGURATION FOUND, CONVERT TO DTO", idOperation);
                OrderConfigurationDto orderConfigurationDto = orderConfigurationConverter
                        .orderConfigurationEntityToOrderConfigurationDto(orderConfigurationEntity);
                return orderConfigurationDto;

            } else {
                LOG.warn("{} ORDER CONFIGURATION NOT FOUND", idOperation);
                return null;
            }
        } catch (Exception e) {
            LOG.error("{} ERROR IN getOrderConfigurationByOrderCode. EXCEPTION: {}", idOperation,e.getMessage());
            throw new GlobalError();
        }

    }


    @Transactional
    @Override
    public List<OrderDto> getAllOrdersListByCompany(GenericSerchParamsOrderDto params, String companyCode, String branch,
                                                    String idOperation) {

        try {
            LOG.info("{} INIT getAllOrdersListByCompany() ", idOperation);
            List<OrderSummary> orderSummaryList = customDSLFOrderRepository.getOrderByCompany(params);
            return convertAndEnhanceOrdersNotCredit(orderSummaryList);

        } catch (Exception e) {
            LOG.error("{} ERROR IN getAllOrdersListByCompany(). EXCEPTION: {}", idOperation, e.getMessage());
            throw new GlobalError();
        }
    }


    protected  OrderDto convertToOrderDto(OrderSummary orderSummary) {
        OrderDto order = new OrderDto();
        order.setOrderId(orderSummary.getOrderId());
        order.setOrderNumber(orderSummary.getOrderNumber());
        order.setOrderCode(orderSummary.getOrderCode());
        order.setBranchCode(orderSummary.getBranchCode());
        order.setCompanyNumber(orderSummary.getCompanyNumber());
        order.setCurrency(orderSummary.getCurrency());
        order.setExchangeRate(orderSummary.getExchangeRate());
        order.setCreationDate(orderSummary.getCreationDate());
        order.setRequestDate(orderSummary.getRequestDate());
        order.setValidityDate(orderSummary.getValidityDate());
        order.setClientTax(orderSummary.getClientTax());
        order.setUserNumber(orderSummary.getUserNumber());
        order.setIdUser(orderSummary.getIdUser());
        order.setEmployeeEmail(orderSummary.getEmployeeEmail());
        order.setSubTotal(orderSummary.getSubTotal());
        order.setIvaTotal(orderSummary.getIvaTotal());
        order.setOrderTotal(orderSummary.getOrderTotal());
        order.setPendingPayment(orderSummary.getPendingPayment());
        order.setDiscountTotal(orderSummary.getDiscountTotal());

        StatusDto status = new StatusDto();
        status.setCode(orderSummary.getCode());
        status.setDescription(orderSummary.getDescription());
        order.setStatus(status);

        order.setRetentionCode(orderSummary.getRetentionCode());
        order.setOrderType(orderSummary.getOrderType());
        order.setTempMigStatus(orderSummary.getTempMigStatus());
        order.setObservations(orderSummary.getObservations());
        order.setCfdiType(orderSummary.getCfdiType());
        order.setInvoiceAmount(BigDecimal.ZERO);
        order.setIsUpdated(orderSummary.getIsUpdated());
        order.setClientId(orderSummary.getClientId());
        order.setIsRetentionOrder(orderSummary.getIsRetentionOrder());

        return order;
    }

    protected List<OrderDto> convertAndEnhanceOrders(List<OrderSummary> orderSummaryList) {
        return orderSummaryList.stream()
                .map(this::convertToOrderDto)
                .peek(this::enhanceWithCreditNote)
                .collect(Collectors.toList());
    }

    protected List<OrderDto> convertAndEnhanceOrdersNotCredit(List<OrderSummary> orderSummaryList) {
        return orderSummaryList.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    private void enhanceWithCreditNote(OrderDto orderDto) {
        Optional<CreditNoteEntity> creditNoteOptional = creditNoteRepository
                .findByOrderNumberAndOrderCode(orderDto.getOrderNumber(), orderDto.getOrderCode());
        orderDto.setIsCreditNoteComplete(creditNoteOptional.map(CreditNoteEntity::isTotal).orElse(false));
    }
}
