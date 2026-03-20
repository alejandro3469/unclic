package mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.factory;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.GenericClosingOperationReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.calzada.ClosingOperationReportCalzadaRepository;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.calzada.fragua.ClosingOperationReportFraguaRepository;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.carredana.ClosingOperationReportCarredanaRepository;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.ferresamano.ClosingOperationReportFSamanoRepository;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.carredana.zapata.ClosingOperationReportZapataRepository;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.demo.ClosingOperationReportDemoRepository;

@Component
public class ClosingReportRepositoryFactory {

	@Autowired
	private ClosingOperationReportCalzadaRepository closingOperationReportCalzadaRepository;

	@Autowired
	private ClosingOperationReportFraguaRepository closingOperationReportFraguaRepository;

	@Autowired
	private ClosingOperationReportCarredanaRepository closingOperationReportCarredanaRepository;

	@Autowired
	private ClosingOperationReportZapataRepository closingOperationReportZapataRepository;

	@Autowired
	private ClosingOperationReportFSamanoRepository closingOperationReportFSamanoRepository;

	@Autowired
	private ClosingOperationReportDemoRepository closingOperationReportDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClosingReportRepositoryFactory.class);

	public GenericClosingOperationReportRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN closingOperationReportCalzadaRepository");
				return closingOperationReportCalzadaRepository;

			case CFRA:
				LOG.info("RETURN closingOperationReportFraguaRepository");
				return closingOperationReportFraguaRepository;

			case FCAR:
				LOG.info("RETURN closingOperationReportCarredanaRepository");
				return closingOperationReportCarredanaRepository;

			case CZAP:
				LOG.info("RETURN closingOperationReportZapataRepository");
				return closingOperationReportZapataRepository;

			case CFSA:
				LOG.info("RETURN closingOperationReportFSamanoRepository");
				return closingOperationReportFSamanoRepository;

				case DEMO:
					LOG.info("RETURN closingOperationReportDemoRepository");
					return closingOperationReportDemoRepository;

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
