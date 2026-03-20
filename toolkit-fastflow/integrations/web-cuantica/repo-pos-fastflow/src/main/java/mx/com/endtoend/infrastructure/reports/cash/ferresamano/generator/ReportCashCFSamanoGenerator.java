package mx.com.endtoend.infrastructure.reports.cash.ferresamano.generator;

import java.util.*;

import mx.com.endtoend.infrastructure.reports.cash.common.generator.BaseReportCashGenerator;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.ferresamano.repositories.AccountingRecordFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.ferresamano.business.EmailCashConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.closings.ferresamano.repositories.ClosingOperationFSamanoRepository;
import mx.com.endtoend.infrastructure.openings.ferresamano.repositories.OpeningOperationFSamanoRepository;

@Service
public class ReportCashCFSamanoGenerator extends BaseReportCashGenerator {
	private static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("CLOSING_TEMPLATE", "/reports/ferresamano/ClosingOperation.jasper");
		TEMPLATES.put("OPENING_TEMPLATE", "/reports/ferresamano/OpeningOperation.jasper");
		TEMPLATES.put("LOGO_REPORT", "/reports/ferresamano/logo_color_2.jpeg");
	}


	public ReportCashCFSamanoGenerator(OpeningOperationFSamanoRepository openingOperationCalRepository,
									   ClosingOperationFSamanoRepository closingOperationCalRepository,
									   AccountingRecordFSamanoRepository accountingRecordRepository,
									   EmailCashConfigurationCFSamanoRepository emailCashConfigurationCalzadaRepository) {
		super(TEMPLATES,
				ReportCashCFSamanoGenerator.class,
				openingOperationCalRepository,
				closingOperationCalRepository,
				accountingRecordRepository,
				emailCashConfigurationCalzadaRepository
				);
	}
}
