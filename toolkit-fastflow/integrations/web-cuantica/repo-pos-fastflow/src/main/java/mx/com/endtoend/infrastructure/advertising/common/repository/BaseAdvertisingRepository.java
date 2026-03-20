package mx.com.endtoend.infrastructure.advertising.common.repository;

import mx.com.endtoend.infrastructure.advertising.common.converters.AdvertisingConverter;
import mx.com.endtoend.infrastructure.advertising.common.entities.SaleAdvertisingEntity;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SaleAdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SearchAdversitingParamsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class BaseAdvertisingRepository  /*extends SaleAdvertisingRepository*/{

    private final Logger LOG;
    private final BaseSaleAdversitingRepository saleAdvertisingRepository;
    private final AdvertisingConverter advertisingConverter;

    public BaseAdvertisingRepository(Class<?> loggerClass, BaseSaleAdversitingRepository _saleAdvertisingRepository,
                                     AdvertisingConverter _advertisingConverter) {
        LOG = LoggerFactory.getLogger(loggerClass);
        saleAdvertisingRepository = _saleAdvertisingRepository;
        advertisingConverter = _advertisingConverter;
    }

    //@Override
    public SaleAdvertisingDto createAdvertising(SaleAdvertisingDto saleAdvertisingDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT createAdvertising()", idOperation));
            SaleAdvertisingEntity saleAdvertisingEntity = advertisingConverter
                    .saleAdvertisingDtoToSaleAdvertisingEntity(saleAdvertisingDto);
            saleAdvertisingEntity = saleAdvertisingRepository.save(saleAdvertisingEntity);
            return advertisingConverter.saleAdvertisingEntityToSaleAdvertisingDto(saleAdvertisingEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createAdvertising(): ERROR: %s", idOperation, e.getMessage()));
            return null;
        }
    }

    //@Override
    public List<SaleAdvertisingDto> viewAdvertisingListByParams(SearchAdversitingParamsDto searchAdversitingParams,
                                                                String companyCode, String idOperation) {
        // TODO Auto-generated method stub
        return null;
    }

    //@Override
    public SaleAdvertisingDto viewAdvertisingDetailById(Long id, String idOperation) {
        // TODO Auto-generated method stub
        return null;
    }

    //@Override
    public void updateSatusByOrderNumberAndCode(BigDecimal orderNumber, String statusCode, String idOperation) {
        // TODO Auto-generated method stub

    }
}
