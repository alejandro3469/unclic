package mx.com.endtoend.infrastructure.reports.sales.branch.calzada.fragua;


import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.BaseSaleReportRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.payments.calzada.fragua.repositories.CustomDSLPaymentFraguaRepository;

@Service
public class SaleReportFraguaRepository extends BaseSaleReportRepository {

	public SaleReportFraguaRepository(CustomDSLPaymentFraguaRepository _customDSLPaymentRepository) {
		super(SaleReportFraguaRepository.class,_customDSLPaymentRepository);
	}
}
