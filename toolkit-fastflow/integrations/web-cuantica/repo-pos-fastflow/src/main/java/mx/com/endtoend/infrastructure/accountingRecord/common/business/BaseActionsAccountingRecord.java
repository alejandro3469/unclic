package mx.com.endtoend.infrastructure.accountingRecord.common.business;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.infrastructure.accountingRecord.common.repository.GenericAccountingRecordRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;
import mx.com.endtoend.infrastructure.accountingRecord.common.entities.AccountingRecordEntity;
import mx.com.endtoend.infrastructure.accountingRecord.common.repository.BaseAccountingRecordRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BaseActionsAccountingRecord implements GenericAccountingRecordRepository {

    private final Logger LOG;
    private  final BaseAccountingRecordRepository accountingRecordRepository;
    private final AccountingRecordConverter accountingRecordConverter;

    public BaseActionsAccountingRecord(Class<?> loggerClass,
                                       BaseAccountingRecordRepository _accountingRecordRepository,
                                       AccountingRecordConverter _accountingRecordConverter) {
        LOG = LoggerFactory.getLogger(loggerClass);
        accountingRecordRepository = _accountingRecordRepository;
        accountingRecordConverter = _accountingRecordConverter;
    }

    @Override
    public boolean createAccountingRecordMovement(List<AccountingRecordDto> accointingRecordDtoList,
                                                  String idOperation) {

        try {
            LOG.info(String.format("%s INIT createAccountingRecordMovement()", idOperation));

            List<AccountingRecordEntity> accountingRecordEntityList = accountingRecordConverter
                    .accountingRecordDtoListToAccountingRecordEntityList(accointingRecordDtoList);
            for (AccountingRecordEntity accountingRecordEntity : accountingRecordEntityList) {
                accountingRecordRepository.save(accountingRecordEntity);
            }
            LOG.info(String.format("%s SAVE DATA OK", idOperation));
            return true;

        } catch (Exception e) {

            return false;
        }

    }

    @Override
    public List<AccountingRecordDto> fingAccountingRecordByOpeningIdAndCompanyCode(Long openingId, String idOperation) {
        try {

            LOG.info(String.format("%s INIT fingAccountingRecordByOpeningIdAndCompanyCode()", idOperation));
            List<AccountingRecordDto> accountingRecordDtoList = new ArrayList<>();
            List<AccountingRecordEntity> accountingRecordEntityList = accountingRecordRepository
                    .findAllByOpeningId(openingId);
            if (!accountingRecordEntityList.isEmpty()) {
                accountingRecordDtoList = accountingRecordConverter
                        .accountingRecordEntityListToAccountingRecordDtoList(accountingRecordEntityList);
            }
            return accountingRecordDtoList;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN fingAccountingRecordByOpeningIdAndCompanyCode: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }
}
