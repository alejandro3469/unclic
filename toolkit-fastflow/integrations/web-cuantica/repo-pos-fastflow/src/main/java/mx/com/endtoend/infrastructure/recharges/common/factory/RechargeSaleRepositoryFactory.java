package mx.com.endtoend.infrastructure.recharges.common.factory;

import mx.com.endtoend.infrastructure.recharges.common.repository.GenericRechargeSaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.recharges.calzada.business.RechargeSaleCalzadaRepository;

@Component
public class RechargeSaleRepositoryFactory {

	@Autowired(required = false)
	private RechargeSaleCalzadaRepository rechargeSaleCalzadaRepository;

	private final Logger LOG = LoggerFactory.getLogger(RechargeSaleRepositoryFactory.class);

	public GenericRechargeSaleRepository getRepositoryByCompanyCode(String companyCode) {
		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);
			switch (value) {
			case FCAL:
				LOG.info("RETURN rechargeSaleCalzadaRepository");
				return rechargeSaleCalzadaRepository;
			case CFRA:
				LOG.info("RETURN null");
				return null;
			case FCAR:
				LOG.info("RETURN null");
				return null;
			case CZAP:
				LOG.info("RETURN null");
				return null;
			case CFSA:
				LOG.info("RETURN null");
				return null;
			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			LOG.error("EXEPTION: " + e.getMessage());
			return null;
		}
	}
}
