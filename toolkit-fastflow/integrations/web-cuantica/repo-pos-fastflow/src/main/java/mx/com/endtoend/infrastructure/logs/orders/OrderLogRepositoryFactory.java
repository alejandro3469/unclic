package mx.com.endtoend.infrastructure.logs.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.logs.orders.calzada.business.OrderLogCalzadaRepository;
import mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.business.OrderLogFraguaRepository;
import mx.com.endtoend.infrastructure.logs.orders.carredana.business.OrderLogCarredanaRepository;
import mx.com.endtoend.infrastructure.logs.orders.ferresamano.business.OrderLogFSamanoRepository;
import mx.com.endtoend.infrastructure.logs.orders.carredana.zapata.business.OrderLogZapataRepository;
import mx.com.endtoend.infrastructure.logs.orders.demo.business.OrderLogDemoBusinessRepository;

@Component
public class OrderLogRepositoryFactory {

	@Autowired
	private OrderLogCalzadaRepository orderLogCalzadaRepository;

	@Autowired
	private OrderLogFraguaRepository orderLogFraguaRepository;

	@Autowired
	private OrderLogCarredanaRepository orderLogCarredanaRepository;

	@Autowired
	private OrderLogZapataRepository orderLogZapataRepository;

	@Autowired
	private OrderLogFSamanoRepository orderLogFSamanoRepository;

	@Autowired
	private OrderLogDemoBusinessRepository orderLogDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(OrderLogRepositoryFactory.class);

	public GenericOrderLogPersistenceInterface getRepositoryByCompanyCode(String companyCode) {
		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);
			switch (value) {
			case FCAL:
				LOG.info("RETURN orderLogCalzadaRepository");
				return orderLogCalzadaRepository;
			case CFRA:
				LOG.info("RETURN orderLogFraguaRepository");
				return orderLogFraguaRepository;
			case FCAR:
				LOG.info("RETURN orderLogCarredanaRepository");
				return orderLogCarredanaRepository;
			case CZAP:
				LOG.info("RETURN orderLogZapataRepository");
				return orderLogZapataRepository;
			case CFSA:
				LOG.info("RETURN orderLogFSamanoRepository");
				return orderLogFSamanoRepository;
			case DEMO:
				LOG.info("RETURN orderLogDemoBusinessRepository");
				return orderLogDemoBusinessRepository;
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
