package mx.com.endtoend.infrastructure.reports.cash.carredana.generator;

import java.util.*;

import mx.com.endtoend.infrastructure.reports.cash.common.generator.BaseReportCashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.carredana.repositories.AccountingRecordFCarRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.carredana.business.EmailCashConfigurationFCarredanaRepository;
import mx.com.endtoend.infrastructure.closings.carredana.repositories.ClosingOperationFCarRepository;
import mx.com.endtoend.infrastructure.openings.carredana.repositories.OpeningOperationFCarRepository;

@Service
public class ReportCashCarredanaGenerator extends BaseReportCashGenerator {

    private static final Map<String, String> TEMPLATES;
    static {
        TEMPLATES = new HashMap<>();
        TEMPLATES.put("CLOSING_TEMPLATE", "/reports/carredana/ClosingOperation.jasper");
        TEMPLATES.put("OPENING_TEMPLATE", "/reports/carredana/OpeningOperation.jasper");
        TEMPLATES.put("LOGO_REPORT", "/reports/carredana/logo_color_2.jpeg");
    }


    @Autowired
    public ReportCashCarredanaGenerator(
            AccountingRecordFCarRepository accountingRecordRepository,
            EmailCashConfigurationFCarredanaRepository emailCashConfigurationCalzadaRepository,
            ClosingOperationFCarRepository closingOperationCalRepository,
            OpeningOperationFCarRepository openingOperationCalRepository

    ) {
        super(
                TEMPLATES,
                ReportCashCarredanaGenerator.class,
                openingOperationCalRepository,
                closingOperationCalRepository,
                accountingRecordRepository,
                emailCashConfigurationCalzadaRepository
        );
    }

}