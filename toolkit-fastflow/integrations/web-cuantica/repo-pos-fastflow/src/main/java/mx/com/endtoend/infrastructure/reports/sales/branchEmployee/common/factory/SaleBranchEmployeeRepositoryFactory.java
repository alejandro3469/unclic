package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.factory;

import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository.GenericSaleBranchEmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.calzada.SaleBranchEmployeeReportCalzadaRepository;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.calzada.fragua.SaleBranchEmployeeReportFraguaRepository;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.carredana.SaleBranchEmployeeReportCarredanaRepository;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.ferresamano.SaleBranchEmployeeReportCFSamanoRepository;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.carredana.zapata.SaleBranchEmployeeReportCZapataRepository;
import mx.com.endtoend.infrastructure.reports.sales.branchEmployee.demo.SaleBranchEmployeeReportDemoRepository;

@Component
public class SaleBranchEmployeeRepositoryFactory {

	@Autowired
	private SaleBranchEmployeeReportCalzadaRepository saleBranchEmployeeReportCalzadaRepository;

	@Autowired
	private SaleBranchEmployeeReportFraguaRepository saleBranchEmployeeReportFraguaRepository;

	@Autowired
	private SaleBranchEmployeeReportCarredanaRepository saleBranchEmployeeReportCarredanaRepository;

	@Autowired
	private SaleBranchEmployeeReportCZapataRepository saleBranchEmployeeReportCZapataRepository;

	@Autowired
	private SaleBranchEmployeeReportCFSamanoRepository saleBranchEmployeeReportCFSamanoRepository;

	@Autowired
	private SaleBranchEmployeeReportDemoRepository saleBranchEmployeeReportDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(SaleBranchEmployeeRepositoryFactory.class);

	public GenericSaleBranchEmployeeRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN saleBranchEmployeeReportCalzadaRepository");
				return saleBranchEmployeeReportCalzadaRepository;

			case CFRA:
				LOG.info("RETURN saleBranchEmployeeReportFraguaRepository");
				return saleBranchEmployeeReportFraguaRepository;

			case FCAR:
				LOG.info("RETURN saleBranchEmployeeReportCarredanaRepository");
				return saleBranchEmployeeReportCarredanaRepository;

			case CZAP:
				LOG.info("RETURN saleBranchEmployeeReportCZapataRepository");
				return saleBranchEmployeeReportCZapataRepository;

			case CFSA:
				LOG.info("RETURN saleBranchEmployeeReportCFSamanoRepository");
				return saleBranchEmployeeReportCFSamanoRepository;
				case DEMO:
					LOG.info("RETURN saleBranchEmployeeReportDemoRepository");
					return saleBranchEmployeeReportDemoRepository;

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
