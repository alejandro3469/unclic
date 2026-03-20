package mx.com.endtoend.infrastructure.services.jde.payments.common.factory;

import mx.com.endtoend.infrastructure.services.jde.payments.common.repository.GenericPaymentJdeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.business.PaymentCalzadaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.fragua.business.PaymentFraguaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.business.PaymentFCarredanaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.business.PaymentCFSamanoJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.zapata.business.PaymentCZapataJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.business.PaymentDemoJdeRepository;

@Component
public class PaymentJdeRepositoryFactory {

	@Autowired(required = false)
	private PaymentCalzadaJdeRepository paymentCalzadaJdeRepository;

	@Autowired(required = false)
	private PaymentFraguaJdeRepository paymentFraguaJdeRepository;

	@Autowired(required = false)
	private PaymentFCarredanaJdeRepository paymentFCarredanaJdeRepository;

	@Autowired(required = false)
	private PaymentCZapataJdeRepository paymentCZapataJdeRepository;

	@Autowired(required = false)
	private PaymentCFSamanoJdeRepository paymentCFSamanoJdeRepository;

	@Autowired(required = false)
	private PaymentDemoJdeRepository paymentDemoJdeRepository;

	private final Logger LOG = LoggerFactory.getLogger(PaymentJdeRepositoryFactory.class);

	public GenericPaymentJdeRepository getRepositoryByCompanyCode(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			LOG.info("RETURN paymentCalzadaJdeRepository");
			return paymentCalzadaJdeRepository;

		case CFRA:
			LOG.info("RETURN paymentFraguaJdeRepository");
			return paymentFraguaJdeRepository;

		case FCAR:
			LOG.info("RETURN paymentFCarredanaJdeRepository");
			return paymentFCarredanaJdeRepository;

		case CZAP:
			LOG.info("RETURN paymentCZapataJdeRepository");
			return paymentCZapataJdeRepository;

		case CFSA:
			LOG.info("RETURN paymentCFSamanoJdeRepository");
			return paymentCFSamanoJdeRepository;

		case DEMO:
			LOG.info("RETURN paymentDemoJdeRepository");
			return paymentDemoJdeRepository;

		default:
			LOG.info("RETURN null value");
			return null;
		}
	}

}
