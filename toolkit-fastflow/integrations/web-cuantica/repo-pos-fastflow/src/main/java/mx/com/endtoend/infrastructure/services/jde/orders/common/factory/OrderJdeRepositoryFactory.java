package mx.com.endtoend.infrastructure.services.jde.orders.common.factory;

import mx.com.endtoend.infrastructure.services.jde.orders.common.repository.GenericOrderJdeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.services.jde.clients.common.factory.ClientOracleFactory;
import mx.com.endtoend.infrastructure.services.jde.orders.calzada.business.OrderCalzadaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.calzada.fragua.business.OrderSFraguaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.carredana.business.OrderFCarredanaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.ferresamano.business.OrderCFSamanoJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.carredana.zapata.business.OrderCZapataJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.demo.business.OrderDemoJdeRepository;

@Component
public class OrderJdeRepositoryFactory {

	@Autowired(required = false)
	private OrderCalzadaJdeRepository orderCalzadaJdeRepository;

	@Autowired(required = false)
	private OrderSFraguaJdeRepository orderSFraguaJdeRepository;

	@Autowired(required = false)
	private OrderFCarredanaJdeRepository carredanaJdeRepository;

	@Autowired(required = false)
	private OrderCZapataJdeRepository zapataJdeRepository;
	
	@Autowired(required = false)
	private OrderCFSamanoJdeRepository orderCFSamanoJdeRepository;

	@Autowired(required = false)
	private OrderDemoJdeRepository orderDemoJdeRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClientOracleFactory.class);

	public GenericOrderJdeRepository getRepositoryByCompanyCode(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			LOG.info("RETURN orderCalzadaJdeRepository");
			return orderCalzadaJdeRepository;

		case CFRA:
			LOG.info("RETURN orderSFraguaJdeRepository");
			return orderSFraguaJdeRepository;

		case FCAR:
			LOG.info("RETURN carredanaJdeRepository");
			return carredanaJdeRepository;

		case CZAP:
			LOG.info("RETURN zapataJdeRepository");
			return zapataJdeRepository;
			
		case CFSA:
			LOG.info("RETURN zapataJdeRepository");
			return orderCFSamanoJdeRepository;

		case DEMO:
			LOG.info("RETURN orderDemoJdeRepository");
			return orderDemoJdeRepository;

		default:
			LOG.info("RETURN null value");
			return null;
		}

	}

}
