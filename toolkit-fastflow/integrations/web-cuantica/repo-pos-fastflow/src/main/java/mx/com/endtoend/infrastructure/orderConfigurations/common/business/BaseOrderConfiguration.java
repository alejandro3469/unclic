package mx.com.endtoend.infrastructure.orderConfigurations.common.business;

import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.orderConfigurations.common.persistence.GenericOrderConfigurationPersistenceInterface;
import mx.com.endtoend.infrastructure.orderConfigurations.common.repository.BaseOrderConfigurationRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.DocumentConverter;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.DocumentEntity;
import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.OrderConfigurationEntity;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.SaleTypeEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class BaseOrderConfiguration implements GenericOrderConfigurationPersistenceInterface {


    private final BaseOrderConfigurationRepository orderConfigurationRepository;
    protected Logger LOG;

    @Autowired
    protected SaleTypeConverter saleTypeConverter;

    @Autowired
    protected DocumentConverter documentConverter;

    @Autowired
    private OrderConfigurationConverter orderConfigurationConverter;


    public BaseOrderConfiguration(Class<?> loggerClass,
                                  BaseOrderConfigurationRepository _orderConfigurationRepository) {
        LOG = LoggerFactory.getLogger(loggerClass);
        orderConfigurationRepository = _orderConfigurationRepository;

    }

    @Transactional
    @Override
    public OrderConfigurationDto createOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
                                                                       String idOperation) {

        try {

            LOG.info(String.format("%s INIT createOrderConfigurationByCompanyCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderConfigurationDto: %s  ]", idOperation,
                    orderConfigurationDto.toString()));

            LOG.info(String.format("%s CONVERT OrderConfigurationDto", idOperation));

            OrderConfigurationEntity orderConfigurationEntity = orderConfigurationConverter
                    .orderConfigurationDtoToOrderConfigurationEntity(orderConfigurationDto);

            LOG.info(String.format("%s CONVERT AND SET SaleTypeDto", idOperation));
            SaleTypeEntity saleTypeEntity = saleTypeConverter
                    .saleTypeDtoToSaleTypeEntity(orderConfigurationDto.getSaleType());

            orderConfigurationEntity.setSaleType(saleTypeEntity);

            LOG.info(String.format("%s CONVERT AND SET DocumentDtoList", idOperation));
            if (orderConfigurationDto.getDocuments() != null) {

                List<DocumentEntity> documentEntityList = documentConverter
                        .documentDtoLitsToDocumentEntityList(orderConfigurationDto.getDocuments());

                for (DocumentEntity documentEntity : documentEntityList) {
                    documentEntity.setOrderConfiguration(orderConfigurationEntity);
                }

                orderConfigurationEntity.setDocuments(documentEntityList);
            }

            LOG.info(String.format("%s SAVE DATA", idOperation));

            orderConfigurationEntity = orderConfigurationRepository.save(orderConfigurationEntity);

            if (orderConfigurationEntity != null) {

                LOG.info(String.format("%s SAVE OK", idOperation));
                return orderConfigurationConverter
                        .orderConfigurationEntityToOrderConfigurationDto(orderConfigurationEntity);
            } else {

                LOG.error(String.format("%s ERROR SAVING ORDER ", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createOrderConfigurationByCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }

    }

    @Transactional
    @Override
    public boolean existsOrderConfigurationByOrderTypeAndCompanyCode(String orderCode, String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsOrderConfigurationByOrderTypeAndCompanyCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderCode: %s  ]", idOperation, orderCode));

            Optional<OrderConfigurationEntity> orderConfigurationOptional = orderConfigurationRepository
                    .findByOrderCode(orderCode);

            if (orderConfigurationOptional.isPresent()) {
                LOG.info(String.format("%s ORDER-CONFIGURATION EXISTS", idOperation));
                return true;
            } else {
                LOG.info(String.format("%s ORDER-CONFIGURATION NOT EXISTS", idOperation));
                return false;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN existsOrderConfigurationByOrderTypeAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderConfigurationDto updateOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
                                                                       String idOperation) {

        try {

            LOG.info(String.format("%s INIT updateOrderConfigurationByCompanyCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderConfigurationDto: %s  ]", idOperation,
                    orderConfigurationDto.toString()));

            LOG.info(String.format("%s CONVERT OrderConfigurationDto", idOperation));

            OrderConfigurationEntity orderConfigurationEntity = orderConfigurationConverter
                    .orderConfigurationDtoToOrderConfigurationEntity(orderConfigurationDto);

            LOG.info(String.format("%s CONVERT AND SET SaleTypeDto", idOperation));
            SaleTypeEntity saleTypeEntity = saleTypeConverter
                    .saleTypeDtoToSaleTypeEntity(orderConfigurationDto.getSaleType());

            orderConfigurationEntity.setSaleType(saleTypeEntity);

            LOG.info(String.format("%s CONVERT AND SET DocumentDtoList", idOperation));
            if (orderConfigurationDto.getDocuments() != null) {

                List<DocumentEntity> documentEntityList = documentConverter
                        .documentDtoLitsToDocumentEntityList(orderConfigurationDto.getDocuments());

                for (DocumentEntity documentEntity : documentEntityList) {
                    documentEntity.setOrderConfiguration(orderConfigurationEntity);
                }

                orderConfigurationEntity.setDocuments(documentEntityList);
            }

            LOG.info(String.format("%s SAVE DATA", idOperation));

            orderConfigurationEntity = orderConfigurationRepository.save(orderConfigurationEntity);

            if (orderConfigurationEntity != null) {

                orderConfigurationEntity.setSaleType(saleTypeEntity);

                LOG.info(String.format("%s SAVE OK", idOperation));

                return orderConfigurationConverter
                        .orderConfigurationEntityToOrderConfigurationDto(orderConfigurationEntity);

            } else {

                LOG.error(String.format("%s ERROR SAVING ORDER ", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateOrderConfigurationByCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public boolean existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(String orderCode, Long id,
                                                                             String idOperation) {

        try {

            LOG.info(String.format("%s INIT existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderCode: %s , id: %s]", idOperation, orderCode, id.toString()));

            Optional<OrderConfigurationEntity> orderConfigurationOptional = orderConfigurationRepository
                    .findByOrderCodeAndIdNot(orderCode, id);

            if (orderConfigurationOptional.isPresent()) {
                LOG.info(String.format("%s ORDER-CONFIGURATION EXISTS", idOperation));
                return true;
            } else {
                LOG.info(String.format("%s ORDER-CONFIGURATION NOT EXISTS", idOperation));
                return false;
            }

        } catch (Exception e) {
            LOG.error(String.format(
                    "%s ERROR IN existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderConfigurationDto viewOrderConfigurationDetailByIdAndCompanyCode(Long id, String idOperation) {

        try {

            LOG.info(String.format("%s INIT viewOrderConfigurationDetailByIdAndCompanyCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [ id: %s]", idOperation, id.toString()));

            OrderConfigurationEntity orderConfigurationEntity = orderConfigurationRepository
                    .findByOrerConfigurationId(id);

            if (orderConfigurationEntity != null) {

                LOG.info(String.format("%s ORDER-CONFIGURATION EXISTS", idOperation));

                return orderConfigurationConverter
                        .orderConfigurationEntityToOrderConfigurationDto(orderConfigurationEntity);
            } else {

                LOG.info(String.format("%s ORDER-CONFIGURATION NOT EXISTS", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN viewOrderConfigurationDetailByIdAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public OrderConfigurationDto viewOrderConfigurationDetailByOrderCodeAndCompanyCode(String orderCode,
                                                                                       String idOperation) {
        try {

            LOG.info(String.format("%s INIT viewOrderConfigurationDetailByOrderCodeAndCompanyCode()", idOperation));
            LOG.info(String.format("%s PARAMS: [ orderCode: %s]", idOperation, orderCode));

            OrderConfigurationEntity orderConfigurationEntity = orderConfigurationRepository.findByOrerCode(orderCode);

            if (orderConfigurationEntity != null) {

                LOG.info(String.format("%s ORDER-CONFIGURATION EXISTS", idOperation));

                return orderConfigurationConverter
                        .orderConfigurationEntityToOrderConfigurationDto(orderConfigurationEntity);
            } else {

                LOG.info(String.format("%s ORDER-CONFIGURATION NOT EXISTS", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR IN viewOrderConfigurationDetailByOrderCodeAndCompanyCode(). EXCEPTION: %s",
                            idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public List<OrderConfigurationDto> getOrderConfigurationListByCompanyCode(String idOperation) {

        try {

            LOG.info(String.format("%s INIT getOrderConfigurationListByCompanyCode()", idOperation));

            List<OrderConfigurationEntity> orderConfigurationEntityList = orderConfigurationRepository
                    .findOrderConfigurationList();

            if (orderConfigurationEntityList != null) {

                LOG.info(String.format("%s CONVERT ORDER CONFIGURATION LIST AND RETURN ", idOperation));
                return orderConfigurationConverter
                        .orderConfigurationEntityListToOrderConfigurationDtoList(orderConfigurationEntityList);
            } else {

                LOG.info(String.format("%s ORDER-CONFIGURATION LIST IS NULL", idOperation));
                return null;
            }

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getOrderConfigurationListByCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }
}
