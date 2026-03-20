package mx.com.endtoend.infrastructure.advertising.common.adapter;

import mx.com.endtoend.domain.advertising.ports.AdvertisingPersistencePort;
import mx.com.endtoend.infrastructure.advertising.common.repository.BaseAdversitingRespositoryFactory;
import mx.com.endtoend.infrastructure.advertising.common.repository.SaleAdvertisingRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SaleAdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SearchAdversitingParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BaseAdversitingJpaAdapter implements AdvertisingPersistencePort {

    private  final Logger LOG;
    private final BaseAdversitingRespositoryFactory adversitingRepositoryFactory;

    public BaseAdversitingJpaAdapter(Class<?> loggerClass, BaseAdversitingRespositoryFactory _adversitingRepositoryFactory) {
        LOG = LoggerFactory.getLogger(loggerClass);
        adversitingRepositoryFactory = _adversitingRepositoryFactory;
    }

    @Override
    public ResponseModel createAdvertisingByCompanyCode(SaleAdvertisingDto saleAdvertisingDto, String companyCode,
                                                        String idOperation) {
        LOG.info(String.format("%s INIT createAdvertisingByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS:[ saleAdvertisingDto: %s , companyCode: %s ] ", idOperation,
                saleAdvertisingDto.toString(), companyCode));

        SaleAdvertisingRepository repository = adversitingRepositoryFactory.getRepositoryByCompanyCode(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        SaleAdvertisingDto saleAdvertisingCreated = repository.createAdvertising(saleAdvertisingDto, idOperation);

        return new ResponseModel(saleAdvertisingCreated);
    }

    @Override
    public ResponseModel viewAdvertisingListByParamsCompanyCode(SearchAdversitingParamsDto searchAdversitingParams,
                                                                String companyCode, String idOperation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseModel viewAdvertisingDetailByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseModel updateSatusByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String statusCode,
                                                                       String companyCode, String idOperation) {
        // TODO Auto-generated method stub
        return null;
    }
}
