package mx.com.endtoend.infrastructure.paymentsCredit.calzada.business;

import java.util.*;

import mx.com.endtoend.infrastructure.paymentsCredit.common.business.BasePaymentCreditBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.CreditSaleDetailrepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.CreditSaleRequestRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.CreditSaleResponseRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.StatusArticleDetailRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.StatusSaleDetailRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.StatusSaleRequestRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.StatusSaleResponseRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.common.converters.CreditSaleRequestConverter;
import mx.com.endtoend.infrastructure.paymentsCredit.common.converters.CreditSaleResponseConverter;

@Service
public class PaymentCreditCalzadaRepository extends BasePaymentCreditBusinessRepository {

	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/calzada/ticket_credito_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/calzada/logo.jpg"); 
	}

	public PaymentCreditCalzadaRepository(CreditSaleRequestRepository creditSaleRequestRepository,
										  CreditSaleDetailrepository creditSaleDetailrepository,
										  CreditSaleResponseRepository creditSaleResponseRepository,
										  CreditSaleRequestConverter creditSaleRequestConverter,
										  StatusSaleRequestRepository statusSaleRequestRepository,
										  StatusSaleResponseRepository statusSaleResponseRepository,
										  StatusSaleDetailRepository statusSaleDetailRepository,
										  StatusArticleDetailRepository statusArticleDetailRepository,
										  CreditSaleResponseConverter creditSaleResponseConverter) {
		super(PaymentCreditCalzadaRepository.class, TEMPLATES,
				creditSaleRequestRepository,
				creditSaleDetailrepository,
				creditSaleResponseRepository,
				creditSaleRequestConverter,
				statusSaleRequestRepository,
				statusSaleResponseRepository,
				statusSaleDetailRepository,
				statusArticleDetailRepository,
				creditSaleResponseConverter);
	}

}
