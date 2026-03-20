package mx.com.endtoend.infrastructure.reports.cash.calzada.fragua.generator;

import java.util.*;

import mx.com.endtoend.infrastructure.reports.cash.common.generator.BaseReportCashGenerator;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.accountingRecord.calzada.fragua.repositories.AccountingRecordCFraRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.calzada.fragua.business.EmailCashConfigurationFraguaRepository;
import mx.com.endtoend.infrastructure.closings.calzada.fragua.repositories.ClosingOperationFraRepository;
import mx.com.endtoend.infrastructure.openings.calzada.fragua.repositories.OpeningOperationFraRepository;

@Service
public class ReportCashFraguaGenerator extends BaseReportCashGenerator {

	private static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("CLOSING_TEMPLATE","/reports/fragua/ClosingOperation.jasper");
		TEMPLATES.put("OPENING_TEMPLATE", "/reports/fragua/OpeningOperation.jasper");
		TEMPLATES.put("LOGO_REPORT",  "/reports/fragua/logo.jpg");
	}

	public ReportCashFraguaGenerator(OpeningOperationFraRepository openingOperationCalRepository,
									 ClosingOperationFraRepository closingOperationCalRepository,
									 AccountingRecordCFraRepository accountingRecordRepository,
									 EmailCashConfigurationFraguaRepository emailCashConfigurationCalzadaRepository){
		super(TEMPLATES,ReportCashFraguaGenerator.class,
				openingOperationCalRepository, closingOperationCalRepository,
				accountingRecordRepository,emailCashConfigurationCalzadaRepository);
	}
}
