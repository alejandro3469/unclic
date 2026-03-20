package mx.com.endtoend.infrastructure.accountingRecord.common.repository;

import mx.com.endtoend.infrastructure.accountingRecord.calzada.business.AccountingRecordCalRepository;
import mx.com.endtoend.infrastructure.accountingRecord.calzada.fragua.business.AccountingRecordFraRepository;
import mx.com.endtoend.infrastructure.accountingRecord.carredana.business.AccountingRecordFCarredanaRepository;
import mx.com.endtoend.infrastructure.accountingRecord.ferresamano.business.AccountingRecordCFSamanoRepository;
import mx.com.endtoend.infrastructure.accountingRecord.carredana.zapata.business.AccountingRecordZapataRepository;
import mx.com.endtoend.infrastructure.accountingRecord.demo.business.AccountingRecordDemoBusinessRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseAccountingRecordRepositoryFactory {

    private final AccountingRecordCalRepository accountingRecordCalRepository;
    private final AccountingRecordFraRepository accountingRecordFraRepository;
    private final AccountingRecordFCarredanaRepository accountingRecordFCarredanaRepository;
    private final AccountingRecordZapataRepository accountingRecordZapataRepository;
    private final AccountingRecordCFSamanoRepository accountingRecordCFSamanoRepository;
    private final AccountingRecordDemoBusinessRepository accountingRecordDemoBusinessRepository;
    private final Logger LOG;


    public BaseAccountingRecordRepositoryFactory(Class<?> loggerClass, AccountingRecordCalRepository _accountingRecordCalRepository,
                                                 AccountingRecordFraRepository _accountingRecordFraRepository,
                                                 AccountingRecordFCarredanaRepository _accountingRecordFCarredanaRepository,
                                                 AccountingRecordZapataRepository _accountingRecordZapataRepository,
                                                 AccountingRecordCFSamanoRepository _accountingRecordCFSamanoRepository,
                                                 AccountingRecordDemoBusinessRepository _accountingRecordDemoBusinessRepository){
        accountingRecordCalRepository = _accountingRecordCalRepository;
        accountingRecordFraRepository = _accountingRecordFraRepository;
        accountingRecordFCarredanaRepository = _accountingRecordFCarredanaRepository;
        accountingRecordZapataRepository = _accountingRecordZapataRepository;
        accountingRecordCFSamanoRepository = _accountingRecordCFSamanoRepository;
        accountingRecordDemoBusinessRepository = _accountingRecordDemoBusinessRepository;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    public GenericAccountingRecordRepository getRepositoryByCompanyCode(String companyCode) {

        try {

            CompanyCodes value = CompanyCodes.valueOf(companyCode);

            switch (value) {

                case FCAL:
                    LOG.info("RETURN accountingRecordCalRepository");
                    return accountingRecordCalRepository;

                case CFRA:
                    LOG.info("RETURN accountingRecordFraRepository");
                    return accountingRecordFraRepository;

                case FCAR:
                    LOG.info("RETURN accountingRecordFCarredanaRepository");
                    return accountingRecordFCarredanaRepository;

                case CZAP:
                    LOG.info("RETURN accountingRecordZapataRepository");
                    return accountingRecordZapataRepository;

                case CFSA:
                    LOG.info("RETURN accountingRecordCFSamanoRepository");
                    return accountingRecordCFSamanoRepository;

                case DEMO:
                    LOG.info("RETURN accountingRecordDemoBusinessRepository");
                    return accountingRecordDemoBusinessRepository;

                default:
                    LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
                    return null;
            }

        } catch (Exception e) {
            LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
            return null;
        }

    }
}
