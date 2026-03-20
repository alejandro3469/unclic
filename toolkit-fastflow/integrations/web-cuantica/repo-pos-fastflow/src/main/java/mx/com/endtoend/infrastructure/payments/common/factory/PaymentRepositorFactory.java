package mx.com.endtoend.infrastructure.payments.common.factory;

import mx.com.endtoend.infrastructure.payments.common.persistence.GenericPaymentPersistenceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.payments.calzada.business.PaymentCalzadaRepository;
import mx.com.endtoend.infrastructure.payments.calzada.fragua.business.PaymentFraguaRepository;
import mx.com.endtoend.infrastructure.payments.carredana.business.PaymentFCarredanaRepository;
import mx.com.endtoend.infrastructure.payments.ferresamano.business.PaymentCFSamanoRepository;
import mx.com.endtoend.infrastructure.payments.carredana.zapata.business.PaymentZapataRepository;
import mx.com.endtoend.infrastructure.payments.demo.business.PaymentDemoBusinessRepository;

@Component
public class PaymentRepositorFactory {

	@Autowired
	private PaymentCalzadaRepository paymentCalzadaRepository;

	@Autowired
	private PaymentFraguaRepository paymentFraguaRepository;

	@Autowired
	private PaymentFCarredanaRepository paymentFCarredanaRepository;

	@Autowired
	private PaymentZapataRepository paymentZapataRepository;

	@Autowired
	private PaymentCFSamanoRepository paymentCFSamanoRepository;

	@Autowired
	private PaymentDemoBusinessRepository paymentDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(PaymentRepositorFactory.class);

	public GenericPaymentPersistenceInterface getRepository(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN paymentCalzadaRepository");
				return paymentCalzadaRepository;

			case CFRA:
				LOG.info("RETURN paymentFraguaRepository");
				return paymentFraguaRepository;

			case FCAR:
				LOG.info("RETURN paymentFCarredanaRepository");
				return paymentFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN paymentZapataRepository");
				return paymentZapataRepository;

			case CFSA:
				LOG.info("RETURN paymentCFSamanoRepository");
				return paymentCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN paymentDemoBusinessRepository");
				return paymentDemoBusinessRepository;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException exeption) {

			return null;
		}
	}
}
