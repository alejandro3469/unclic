package mx.com.endtoend.infrastructure.cash.bankReference.common.factory;

import mx.com.endtoend.infrastructure.cash.bankReference.common.persistence.GenericBankConfigurationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.cash.bankReference.calzada.business.BankConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.calzada.fragua.business.BankConfigurationCFraguaRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.carredana.business.BankConfigurationFCarredanaRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.ferresamano.business.BankConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.carredana.zapata.business.BankConfigurationCZapataRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.demo.business.BankConfigurationDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class BankConfigurationRepositoryFactory {

	@Autowired
	private BankConfigurationCalzadaRepository bankConfigurationCalzadaRepository;

	@Autowired
	private BankConfigurationCFraguaRepository bankConfigurationCFraguaRepository;

	@Autowired
	private BankConfigurationFCarredanaRepository bankConfigurationFCarredanaRepository;

	@Autowired
	private BankConfigurationCZapataRepository bankConfigurationCZapataRepository;

	@Autowired
	private BankConfigurationCFSamanoRepository bankConfigurationCFSamanoRepository;

	@Autowired
	private BankConfigurationDemoRepository bankConfigurationDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(BankConfigurationRepositoryFactory.class);

	public GenericBankConfigurationPersistence getRepositoryByCompanyCode(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN bankConfigurationCalzadaRepository");
				return bankConfigurationCalzadaRepository;

			case CFRA:
				LOG.info("RETURN bankConfigurationCFraguaRepository");
				return bankConfigurationCFraguaRepository;

			case FCAR:
				LOG.info("RETURN bankConfigurationFCarredanaRepository");
				return bankConfigurationFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN bankConfigurationCZapataRepository");
				return bankConfigurationCZapataRepository;

			case CFSA:
				LOG.info("RETURN bankConfigurationCFSamanoRepository");
				return bankConfigurationCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN bankConfigurationDemoRepository");
				return bankConfigurationDemoRepository;

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
