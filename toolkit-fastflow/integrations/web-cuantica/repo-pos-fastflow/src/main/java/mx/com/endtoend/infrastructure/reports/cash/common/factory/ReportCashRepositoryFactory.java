package mx.com.endtoend.infrastructure.reports.cash.common.factory;

import mx.com.endtoend.infrastructure.reports.cash.common.repository.GenericReportCashRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.reports.cash.calzada.generator.ReportCashCalzadaGenerator;
import mx.com.endtoend.infrastructure.reports.cash.calzada.fragua.generator.ReportCashFraguaGenerator;
import mx.com.endtoend.infrastructure.reports.cash.carredana.generator.ReportCashCarredanaGenerator;
import mx.com.endtoend.infrastructure.reports.cash.ferresamano.generator.ReportCashCFSamanoGenerator;
import mx.com.endtoend.infrastructure.reports.cash.carredana.zapata.generator.ReportCashZapataGenerator;
import mx.com.endtoend.infrastructure.reports.cash.demo.generator.ReportCashDemoGenerator;

@Component
public class ReportCashRepositoryFactory {

	@Autowired
	private ReportCashCalzadaGenerator reportCashCalzadaGenerator;

	@Autowired
	private ReportCashFraguaGenerator reportCashFraguaGenerator;

	@Autowired
	private ReportCashCarredanaGenerator reportCashCarredanaGenerator;

	@Autowired
	private ReportCashZapataGenerator reportCashZapataGenerator;
	
	@Autowired
	private ReportCashCFSamanoGenerator reportCashCFSamanoGenerator;

	@Autowired
	private ReportCashDemoGenerator reportCashDemoGenerator;

	private final Logger LOG = LoggerFactory.getLogger(ReportCashRepositoryFactory.class);

	public GenericReportCashRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN reportCashCalzadaGenerator");
				return reportCashCalzadaGenerator;

			case CFRA:
				LOG.info("RETURN reportCashFraguaGenerator");
				return reportCashFraguaGenerator;

			case FCAR:
				LOG.info("RETURN reportCashCarredanaGenerator");
				return reportCashCarredanaGenerator;

			case CZAP:
				LOG.info("RETURN reportCashZapataGenerator");
				return reportCashZapataGenerator;
				
			case CFSA:
				LOG.info("RETURN reportCashCFSamanoGenerator");
				return reportCashCFSamanoGenerator;

				case DEMO:
					LOG.info("RETURN reportCashCFDemoGenerator");
					return reportCashDemoGenerator;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}

		} catch (Exception e) {
			LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
			return null;
		}
	}

}
