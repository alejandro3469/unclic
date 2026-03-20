package mx.com.endtoend.infrastructure.advertising.common.repository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseAdversitingRespositoryFactory {

    private final Logger LOG;
    private final BaseAdvertisingRepository advertisingRepository;

    public BaseAdversitingRespositoryFactory(Class<?> logerClass, BaseAdvertisingRepository _advertisingRepository) {
        LOG = LoggerFactory.getLogger(logerClass);
        advertisingRepository = _advertisingRepository;
    }

    public SaleAdvertisingRepository getRepositoryByCompanyCode(String companyCode) {
        try {
            CompanyCodes value = CompanyCodes.valueOf(companyCode);

            switch (value) {

                case FCAL:
                    LOG.info("RETURN adversitingCalzadaRepository");
                    return (SaleAdvertisingRepository) advertisingRepository;

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
