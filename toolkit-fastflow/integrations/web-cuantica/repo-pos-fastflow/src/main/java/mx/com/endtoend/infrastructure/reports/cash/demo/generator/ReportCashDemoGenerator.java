package mx.com.endtoend.infrastructure.reports.cash.demo.generator;

import java.util.*;

        import mx.com.endtoend.infrastructure.reports.cash.common.generator.BaseReportCashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.demo.repositories.AccountingRecordDemoRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.demo.business.EmailCashConfigurationDemoRepository;
import mx.com.endtoend.infrastructure.closings.demo.repositories.ClosingOperationDemoRepository;
import mx.com.endtoend.infrastructure.openings.demo.repositories.OpeningOperationDemoRepository;

@Service
public class ReportCashDemoGenerator extends BaseReportCashGenerator {

    private static final Map<String, String> TEMPLATES;
    static {
        TEMPLATES = new HashMap<>();
        TEMPLATES.put("CLOSING_TEMPLATE", "/reports/demo/ClosingOperation.jasper");
        TEMPLATES.put("OPENING_TEMPLATE", "/reports/demo/OpeningOperation.jasper");
        TEMPLATES.put("LOGO_REPORT", "/reports/demo/logo_color_2.jpeg");
    }


    @Autowired
    public ReportCashDemoGenerator(
            AccountingRecordDemoRepository accountingRecordRepository,
            EmailCashConfigurationDemoRepository emailCashConfigurationCalzadaRepository,
            ClosingOperationDemoRepository closingOperationCalRepository,
            OpeningOperationDemoRepository openingOperationCalRepository

    ) {
        super(
                TEMPLATES,
                ReportCashDemoGenerator.class,
                openingOperationCalRepository,
                closingOperationCalRepository,
                accountingRecordRepository,
                emailCashConfigurationCalzadaRepository
        );
    }

}