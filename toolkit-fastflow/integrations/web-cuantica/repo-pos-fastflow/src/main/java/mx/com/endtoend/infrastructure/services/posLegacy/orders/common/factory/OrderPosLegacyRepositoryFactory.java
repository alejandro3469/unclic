package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.factory;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.GenericOrderPosLegacyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.business.OrderPosLegacyCalzadaRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.fragua.business.OrderPosLegacyCFraguaRepository;

@Component
public class OrderPosLegacyRepositoryFactory {

	@Autowired(required = false)
	private OrderPosLegacyCalzadaRepository orderPosLegacyCalzadaRepository;

	@Autowired(required = false)
	private OrderPosLegacyCFraguaRepository orderPosLegacyCFraguaRepository;

	private final Logger LOG = LoggerFactory.getLogger(OrderPosLegacyRepositoryFactory.class);

	public GenericOrderPosLegacyRepository getRepositoryByCompanyCode(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			LOG.info("RETURN orderPosLegacyCalzadaRepository");
			return orderPosLegacyCalzadaRepository;

		case CFRA:
			LOG.info("RETURN orderPosLegacyCFraguaRepository");
			return orderPosLegacyCFraguaRepository;

		default:
			LOG.info("RETURN null value");
			return null;
		}
	}
}
