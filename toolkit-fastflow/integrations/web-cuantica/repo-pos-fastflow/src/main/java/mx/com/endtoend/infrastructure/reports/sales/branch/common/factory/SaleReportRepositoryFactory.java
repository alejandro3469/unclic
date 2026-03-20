package mx.com.endtoend.infrastructure.reports.sales.branch.common.factory;

import mx.com.endtoend.infrastructure.reports.sales.branch.common.repository.GenericSaleReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.reports.sales.branch.calzada.SaleReportCalzadaRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.calzada.fragua.SaleReportFraguaRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.carredana.SaleReportCarredanaRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.ferresamano.SaleReportCFSamanoRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.carredana.zapata.SaleReportCZapataRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.demo.SaleReportDemoRepository;

@Component
public class SaleReportRepositoryFactory {

	@Autowired
	private SaleReportCalzadaRepository saleReportCalzadaRepository;

	@Autowired
	private SaleReportFraguaRepository saleReportFraguaRepository;

	@Autowired
	private SaleReportCarredanaRepository saleReportCarredanaRepository;

	@Autowired
	private SaleReportCZapataRepository saleReportCZapataRepository;

	@Autowired
	private SaleReportCFSamanoRepository saleReportCFSamanoRepository;

	@Autowired
	private SaleReportDemoRepository saleReportDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(SaleReportRepositoryFactory.class);

	public GenericSaleReportRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN saleReportCalzadaRepository");
				return saleReportCalzadaRepository;

			case CFRA:
				LOG.info("RETURN saleReportFraguaRepository");
				return saleReportFraguaRepository;

			case FCAR:
				LOG.info("RETURN saleReportCarredanaRepository");
				return saleReportCarredanaRepository;

			case CZAP:
				LOG.info("RETURN saleReportCZapataRepository");
				return saleReportCZapataRepository;

			case CFSA:
				LOG.info("RETURN saleReportCFSamanoRepository");
				return saleReportCFSamanoRepository;

				case DEMO:
					LOG.info("RETURN saleReportDemoRepository");
					return saleReportDemoRepository;

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
