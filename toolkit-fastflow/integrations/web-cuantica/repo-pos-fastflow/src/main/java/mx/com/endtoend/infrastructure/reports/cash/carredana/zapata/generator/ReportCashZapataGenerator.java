package mx.com.endtoend.infrastructure.reports.cash.carredana.zapata.generator;

import java.util.*;

import mx.com.endtoend.infrastructure.reports.cash.common.generator.BaseReportCashGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.carredana.zapata.repositories.AccountingRecordCZapataRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.carredana.zapata.business.EmailCashConfigurationCZapataRepository;
import mx.com.endtoend.infrastructure.closings.carredana.zapta.repositories.ClosingOperationCZapataRepository;
import mx.com.endtoend.infrastructure.openings.carredana.zapata.repositories.OpeningOperationCZapataRepository;

@Service
public class ReportCashZapataGenerator extends BaseReportCashGenerator {

	@Autowired
	private OpeningOperationCZapataRepository openingOperationCalRepository;

	@Autowired
	private ClosingOperationCZapataRepository closingOperationCalRepository;

	@Autowired
	private AccountingRecordCZapataRepository accountingRecordRepository;

	@Autowired
	private EmailCashConfigurationCZapataRepository emailCashConfigurationCalzadaRepository;




	private static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("CLOSING_TEMPLATE", "/reports/zapata/ClosingOperation.jasper");
		TEMPLATES.put("OPENING_TEMPLATE", "/reports/zapata/OpeningOperation.jasper");
		TEMPLATES.put("LOGO_REPORT", "/reports/zapata/logo_color_2.png");
	}

	private final Logger LOG = LoggerFactory.getLogger(ReportCashZapataGenerator.class);

	public ReportCashZapataGenerator(OpeningOperationCZapataRepository openingOperationCalRepository,
									 ClosingOperationCZapataRepository closingOperationCalRepository,
									 AccountingRecordCZapataRepository accountingRecordRepository,
									 EmailCashConfigurationCZapataRepository emailCashConfigurationCalzadaRepository) {
		super(TEMPLATES,
				ReportCashZapataGenerator.class,
				openingOperationCalRepository,
				closingOperationCalRepository,
				accountingRecordRepository,
				emailCashConfigurationCalzadaRepository
				);
	}
}
