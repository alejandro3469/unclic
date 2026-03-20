package mx.com.endtoend.infrastructure.reports.sales.branch.carredana.zapata;

import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.BaseSaleReportRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.payments.carredana.zapata.repositories.CustomDSLPaymentCZapataRepository;

@Service
public class SaleReportCZapataRepository extends BaseSaleReportRepository {

	public SaleReportCZapataRepository(CustomDSLPaymentCZapataRepository customDSLPaymentRepository){
		super(SaleReportCZapataRepository.class, customDSLPaymentRepository);

	}
}
