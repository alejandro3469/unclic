package mx.com.endtoend.infrastructure.reports.cash.calzada.generator;

import java.util.*;

import mx.com.endtoend.infrastructure.reports.cash.common.generator.BaseReportCashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.calzada.repositories.AccountingRecordRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.calzada.business.EmailCashConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.closings.calzada.repositories.ClosingOperationCalRepository;
import mx.com.endtoend.infrastructure.openings.calzada.repositories.OpeningOperationCalRepository;

@Service
public class ReportCashCalzadaGenerator extends BaseReportCashGenerator {
    private static final Map<String, String> TEMPLATES;
    static {
        TEMPLATES = new HashMap<>();
        TEMPLATES.put("CLOSING_TEMPLATE", "/reports/calzada/ClosingOperation.jasper");
        TEMPLATES.put("OPENING_TEMPLATE", "/reports/calzada/OpeningOperation.jasper");
        TEMPLATES.put("LOGO_REPORT", "/reports/calzada/logo.jpg");
    }

    @Autowired
    public ReportCashCalzadaGenerator(
            AccountingRecordRepository accountingRecordRepository,
            EmailCashConfigurationCalzadaRepository emailCashConfigurationCalzadaRepository,
            ClosingOperationCalRepository closingOperationCalRepository,
            OpeningOperationCalRepository openingOperationCalRepository
    ) {
        super(
                TEMPLATES,
                ReportCashCalzadaGenerator.class,
                openingOperationCalRepository,
                closingOperationCalRepository,
                accountingRecordRepository,
                emailCashConfigurationCalzadaRepository
        );
    }

}