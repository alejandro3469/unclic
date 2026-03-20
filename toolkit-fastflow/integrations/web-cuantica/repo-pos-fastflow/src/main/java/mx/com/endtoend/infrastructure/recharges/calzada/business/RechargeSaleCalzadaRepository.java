package mx.com.endtoend.infrastructure.recharges.calzada.business;

import mx.com.endtoend.infrastructure.recharges.common.business.BaseRechargeSaleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.recharges.calzada.repositories.CompanyPhoneRepository;
import mx.com.endtoend.infrastructure.recharges.calzada.repositories.OperationTraceSeliaRepository;
import mx.com.endtoend.infrastructure.recharges.calzada.repositories.RechargeRequestRepository;
import mx.com.endtoend.infrastructure.recharges.calzada.repositories.RechargeSeliaConfigRepository;
import mx.com.endtoend.infrastructure.recharges.common.converters.CompanyPhoneConverter;
import mx.com.endtoend.infrastructure.recharges.common.converters.RechargerequestConverter;
import mx.com.endtoend.infrastructure.services.selia.SeliaServicePort;

import java.util.HashMap;
import java.util.Map;

@Service
public class RechargeSaleCalzadaRepository extends BaseRechargeSaleBusinessRepository {
 
	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/calzada/RechargeTicket.jasper");
	}

	public RechargeSaleCalzadaRepository(CompanyPhoneRepository companyPhoneRepository,
										 CompanyPhoneConverter companyPhoneConverter,
										 RechargeSeliaConfigRepository rechargeSeliaConfigRepository,
										 SeliaServicePort seliaServicePort,
										 OperationTraceSeliaRepository operationTraceSeliaRepository,
										 RechargeRequestRepository rechargeRequestRepository,
										 RechargerequestConverter rechargerequestConverter){
		super(RechargeSaleCalzadaRepository.class, TEMPLATES,
				companyPhoneRepository,companyPhoneConverter,
				rechargeSeliaConfigRepository, seliaServicePort,
				operationTraceSeliaRepository, rechargeRequestRepository,
				rechargerequestConverter);
	}
}
