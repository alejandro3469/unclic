package mx.com.endtoend.infrastructure.accountingRecord.common.adapter;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.infrastructure.accountingRecord.common.repository.GenericAccountingRecordRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.repository.BaseAccountingRecordRepositoryFactory;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseAccountingRecordJpaAdapter implements AccountingRecordPersistencePort {

    private  final Logger LOG;
    private final BaseAccountingRecordRepositoryFactory accountingRecordRepositoryFactory;

    public BaseAccountingRecordJpaAdapter(Class<?> loggerClass,BaseAccountingRecordRepositoryFactory _accountingRecordRepositoryFactory){
        LOG = LoggerFactory.getLogger(loggerClass);
        accountingRecordRepositoryFactory = _accountingRecordRepositoryFactory;
    }

    @Override
    public ResponseModel createAccountingRecordMovementByCompanyCode(List<AccountingRecordDto> accointingRecordDtoList,
                                                                     String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT createAccountingRecordMovementByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS:[ accointingRecordDtoList size: %d , companyCode: %s ] ", idOperation,
                accointingRecordDtoList.size(), companyCode));

        GenericAccountingRecordRepository repository = accountingRecordRepositoryFactory
                .getRepositoryByCompanyCode(companyCode);

        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        boolean created = repository.createAccountingRecordMovement(accointingRecordDtoList, idOperation);

        return new ResponseModel(created);

    }

    @Override
    public ResponseModel fingAccountingRecordByOpeningIdAndCompanyCode(Long openingId, String companyCode,
                                                                       String idOperation) {

        LOG.info(String.format("%s INIT fingAccountingRecordByOpeningIdAndCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS:[ openingId %s , companyCode: %s ] ", idOperation, openingId.toString(),
                companyCode));

        GenericAccountingRecordRepository repository = accountingRecordRepositoryFactory
                .getRepositoryByCompanyCode(companyCode);

        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        List<AccountingRecordDto> accountingRecordList = repository
                .fingAccountingRecordByOpeningIdAndCompanyCode(openingId, idOperation);

        return new ResponseModel(accountingRecordList);
    }
}
