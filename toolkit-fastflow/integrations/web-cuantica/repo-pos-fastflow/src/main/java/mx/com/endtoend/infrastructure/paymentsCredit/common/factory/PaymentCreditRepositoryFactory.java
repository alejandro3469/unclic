package mx.com.endtoend.infrastructure.paymentsCredit.common.factory;

import mx.com.endtoend.infrastructure.paymentsCredit.common.repository.PaymentCreditRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.business.PaymentCreditCalzadaRepository;

@Component
public class PaymentCreditRepositoryFactory {

	@Autowired
	private PaymentCreditCalzadaRepository paymentCreditCalzadaRepository;

	private final Logger LOG = LoggerFactory.getLogger(PaymentCreditRepositoryFactory.class);

	public PaymentCreditRepositoryInterface getRepositoryByCompanyCode(String companyCode) {
		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);
			switch (value) {
			case FCAL:
				LOG.info("RETURN paymentCreditCalzadaRepository");
				return paymentCreditCalzadaRepository;
			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException exeption) {
			return null;
		}
	}
}
