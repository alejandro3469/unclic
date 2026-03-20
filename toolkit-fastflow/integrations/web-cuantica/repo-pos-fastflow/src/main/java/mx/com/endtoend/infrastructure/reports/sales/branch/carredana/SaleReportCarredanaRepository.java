package mx.com.endtoend.infrastructure.reports.sales.branch.carredana;

import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.BaseSaleReportRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.payments.carredana.repositories.CustomDSLPaymentFCarredanaRepository;

@Service
public class SaleReportCarredanaRepository extends BaseSaleReportRepository {

	public SaleReportCarredanaRepository(CustomDSLPaymentFCarredanaRepository customDSLPaymentRepository){
		super(SaleReportCarredanaRepository.class, customDSLPaymentRepository);
	}
}
