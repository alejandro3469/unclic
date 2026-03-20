package mx.com.endtoend.infrastructure.reports.sales.branch.ferresamano;


import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.BaseSaleReportRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.payments.ferresamano.repositories.CustomDSLPaymentFSamanoRepository;

@Service
public class SaleReportCFSamanoRepository extends BaseSaleReportRepository {

	public  SaleReportCFSamanoRepository(CustomDSLPaymentFSamanoRepository customDSLPaymentRepository){
		super(SaleReportCFSamanoRepository.class,customDSLPaymentRepository);
	}
}
