package mx.com.endtoend.domain.validService.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.validService.business.companies.DemoService;
import mx.com.endtoend.domain.validService.business.companies.EndToEndService;
import mx.com.endtoend.domain.validService.business.companies.FerreteriaCalzadaService;
import mx.com.endtoend.domain.validService.business.companies.FerreteriaCarredanaService;
import mx.com.endtoend.domain.validService.business.companies.FerreteriaFraguaService;
import mx.com.endtoend.domain.validService.business.companies.FerreteriaZapataService;
import mx.com.endtoend.domain.validService.business.companies.FerreteriaFerresamanoService;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CompanyFactory {

	@Autowired
	private EndToEndService endToEnd;

	@Autowired(required = false)
	private FerreteriaCalzadaService ferreteriaCalzada;

	@Autowired(required = false)
	private FerreteriaFraguaService ferreteriaFragua;
	
	@Autowired(required = false)
	private FerreteriaCarredanaService ferreteriaCarredana;
	
	@Autowired(required = false)
	private FerreteriaZapataService ferreteriaZapata;

	@Autowired(required = false)
	private FerreteriaFerresamanoService ferreteriaFerresamanoService;

	@Autowired(required = false)
	private DemoService demoService;

	private final Logger LOG = LoggerFactory.getLogger(CompanyFactory.class);
	
	public StatusConnectionInterface getCompanyByCode(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case ETE:
				LOG.info("RETURN endToEnd");
				return endToEnd;

			case FCAL:
				LOG.info("RETURN ferreteriaCalzada");
				return ferreteriaCalzada;

			case CFRA:
				LOG.info("RETURN ferreteriaFragua");
				return ferreteriaFragua;
				
			case FCAR:
				LOG.info("RETURN ferreteriaCarredana");
				return ferreteriaCarredana;
				
			case CZAP:
				LOG.info("RETURN ferreteriaZapata");
				return ferreteriaZapata;

			case CFSA:
				LOG.info("RETURN ferreteriaFerresamanoService");
				return ferreteriaFerresamanoService;

			case DEMO:
				LOG.info("RETURN demoService");
				return demoService;

			default:
				return null;
			}

		} catch (Exception e) {
			LOG.error("RETURN IN SEARCH COMPANY");
			return null;
		}

	}
}
