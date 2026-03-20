package mx.com.endtoend.infrastructure.reports.sales.articles.common.factory;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.GenericSaleArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.reports.sales.articles.calzada.SaleArticleReportCalzadaRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.calzada.fragua.SaleArticleReportFraguaRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.carredana.SaleArticleReportCarredanaRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.ferresamano.SaleArticleReportCFSamanoRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.carredana.zapata.SaleArticleReportCZapataRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.demo.SaleArticleReportDemoRepository;

@Component
public class SaleArticleRepositoryFactory {

	@Autowired
	private SaleArticleReportCalzadaRepository saleArticleReportCalzadaRepository;

	@Autowired
	private SaleArticleReportFraguaRepository saleArticleReportFraguaRepository;

	@Autowired
	private SaleArticleReportCarredanaRepository saleArticleReportCarredanaRepository;

	@Autowired
	private SaleArticleReportCZapataRepository saleArticleReportCZapataRepository;

	@Autowired
	private SaleArticleReportCFSamanoRepository saleArticleReportCFSamanoRepository;

	@Autowired
	private SaleArticleReportDemoRepository saleArticleReportDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(SaleArticleRepositoryFactory.class);

	public GenericSaleArticleRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN saleArticleReportCalzadaRepository");
				return saleArticleReportCalzadaRepository;

			case CFRA:
				LOG.info("RETURN saleArticleReportFraguaRepository");
				return saleArticleReportFraguaRepository;

			case FCAR:
				LOG.info("RETURN saleArticleReportCarredanaRepository");
				return saleArticleReportCarredanaRepository;

			case CZAP:
				LOG.info("RETURN saleArticleReportCZapataRepository");
				return saleArticleReportCZapataRepository;

			case CFSA:
				LOG.info("RETURN saleArticleReportCFSamanoRepository");
				return saleArticleReportCFSamanoRepository;
				case DEMO:
					LOG.info("RETURN saleArticleReportDemoRepository");
					return saleArticleReportDemoRepository;
				
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
