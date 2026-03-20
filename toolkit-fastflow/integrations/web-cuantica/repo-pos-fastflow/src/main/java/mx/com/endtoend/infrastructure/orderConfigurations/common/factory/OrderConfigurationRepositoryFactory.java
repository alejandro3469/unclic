package mx.com.endtoend.infrastructure.orderConfigurations.common.factory;

import mx.com.endtoend.infrastructure.orderConfigurations.common.persistence.GenericOrderConfigurationPersistenceInterface;
import mx.com.endtoend.infrastructure.orderConfigurations.demo.repositories.OrderConfigurationDemoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.orderConfigurations.calzada.business.OrderConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua.business.OrderConfigurationSFraguaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.carredana.business.OrderConfigurationCarredanaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.ferresamano.business.OrderConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.carredana.zapata.business.OrderConfigurationZapataRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.demo.business.OrderConfigurationFDemoRepository;

@Component
public class OrderConfigurationRepositoryFactory {

	@Autowired(required = false)
	private OrderConfigurationCalzadaRepository orderConfigurationCalzadaRepository;

	@Autowired(required = false)
	private OrderConfigurationSFraguaRepository orderConfigurationSFraguaRepository;

	@Autowired(required = false)
	private OrderConfigurationCarredanaRepository orderConfigurationCarredanaRepository;

	@Autowired(required = false)
	private OrderConfigurationZapataRepository orderConfigurationZapataRepository;

	@Autowired(required = false)
	private OrderConfigurationCFSamanoRepository orderConfigurationCFSamanoRepository;

	@Autowired(required = false)
	private OrderConfigurationFDemoRepository orderConfigurationFDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(OrderConfigurationRepositoryFactory.class);

	public GenericOrderConfigurationPersistenceInterface getRepository(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN orderConfigurationCalzadaRepository");
				return orderConfigurationCalzadaRepository;

			case CFRA:
				LOG.info("RETURN orderConfigurationSFraguaRepository");
				return orderConfigurationSFraguaRepository;

			case FCAR:
				LOG.info("RETURN orderConfigurationCarredanaRepository");
				return orderConfigurationCarredanaRepository;

			case CZAP:
				LOG.info("RETURN orderConfigurationZapataRepository");
				return orderConfigurationZapataRepository;

			case CFSA:
				LOG.info("RETURN orderConfigurationCFSamanoRepository");
				return orderConfigurationCFSamanoRepository;

				case DEMO:
					LOG.info("RETURN orderConfigurationFDemoRepository");
					return orderConfigurationFDemoRepository;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException exeption) {

			return null;
		}
	}
}
