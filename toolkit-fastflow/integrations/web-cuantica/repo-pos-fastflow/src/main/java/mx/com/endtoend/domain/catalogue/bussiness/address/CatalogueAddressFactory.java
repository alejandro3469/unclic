package mx.com.endtoend.domain.catalogue.bussiness.address;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatalogueAddressFactory {

    private final static Logger LOG = LoggerFactory.getLogger(CatalogueAddressFactory.class);

    public CatalogueAddressInterface getImplementationByCode(String method,
                                                             CatalogueJdeServicePort catalogueOracleServicePort) {

        try {
            LOG.info("INIT getImplementationByCode()");
            LOG.info(String.format("PARAMS: [ method: %s ]", method));

            GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);
            switch (value) {

                case CAT_ADDRESS_ONE:
                    LOG.info("RETURN CatalogueArticleMethodOne()");
                    return new CatalogueAddressMethodOne(catalogueOracleServicePort);

                default:
                    return null;
            }

        } catch (IllegalArgumentException | NullPointerException ex) {
            LOG.error("ERROR GETTING METHOD TYPE FOR CATALOGUE-ADDRESS MODULE");
            return null;
        }
    }
}
