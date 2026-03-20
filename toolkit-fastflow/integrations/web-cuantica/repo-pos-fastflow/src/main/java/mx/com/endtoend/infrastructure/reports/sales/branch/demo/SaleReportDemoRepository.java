package mx.com.endtoend.infrastructure.reports.sales.branch.demo;

import mx.com.endtoend.infrastructure.payments.demo.repositories.CustomDSLPaymentDemoRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.BaseSaleReportRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleReportDemoRepository extends BaseSaleReportRepository {

    public SaleReportDemoRepository(CustomDSLPaymentDemoRepository customDSLPaymentRepository){
        super(SaleReportDemoRepository.class, customDSLPaymentRepository);
    }
}
