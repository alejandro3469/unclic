package mx.com.endtoend.infrastructure.reports.sales.branch.calzada;

import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.BaseSaleReportRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.CustomDSLPaymentRepository;

@Service
public class SaleReportCalzadaRepository extends BaseSaleReportRepository {


	public SaleReportCalzadaRepository(CustomDSLPaymentRepository customDSLPaymentRepository){
		super(SaleReportCalzadaRepository.class, customDSLPaymentRepository);

	}
}
