package mx.com.endtoend.infrastructure.reports.sales.closingOperation.demo;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseClosingOperationReportRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.demo.repositories.CustomDSLClosingOperationDemoRepository;

@Service
public class ClosingOperationReportDemoRepository extends BaseClosingOperationReportRepository {

    public ClosingOperationReportDemoRepository(CustomDSLClosingOperationDemoRepository customDSLClosingOperationCarredanaRepository){
        super(ClosingOperationReportDemoRepository.class, customDSLClosingOperationCarredanaRepository);
    }
}
