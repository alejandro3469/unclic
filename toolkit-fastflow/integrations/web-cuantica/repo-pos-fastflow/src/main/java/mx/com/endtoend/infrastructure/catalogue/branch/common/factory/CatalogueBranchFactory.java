package mx.com.endtoend.infrastructure.catalogue.branch.common.factory;

import mx.com.endtoend.infrastructure.catalogue.branch.common.repository.GenericCatalogueBranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.branch.calzada.business.CatalogueBranchCalzadaRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.calzada.fragua.business.CatalogueBranchFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.carredana.business.CatalogueBranchFCarredanaRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.ferresamano.business.CatalogueBranchCFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.carredana.zapata.business.CatalogueBranchCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.demo.business.CatalogueBranchDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CatalogueBranchFactory {

	@Autowired(required = false)
	private CatalogueBranchCalzadaRepository calzadaRepository;

	@Autowired(required = false)
	private CatalogueBranchFraguaRepository fraguaRepository;

	@Autowired(required = false)
	private CatalogueBranchFCarredanaRepository carredanaRepository;

	@Autowired(required = false)
	private CatalogueBranchCZapataRepository catalogueBranchCZapataRepository;

	@Autowired(required = false)
	private CatalogueBranchCFSamanoRepository catalogueBranchCFSamanoRepository;

	@Autowired(required = false)
	private CatalogueBranchDemoRepository catalogueBranchDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueBranchFactory.class);

	public GenericCatalogueBranchRepository getRepositoryByCompanyCode(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN calzadaRepository()");
				return calzadaRepository;

			case CFRA:
				LOG.info("RETURN fraguaRepository()");
				return fraguaRepository;

			case FCAR:
				LOG.info("RETURN carredanaRepository()");
				return carredanaRepository;

			case CZAP:
				LOG.info("RETURN catalogueBranchCZapataRepository()");
				return catalogueBranchCZapataRepository;

			case CFSA:
				LOG.info("RETURN catalogueBranchCFSamanoRepository()");
				return catalogueBranchCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN catalogueBranchDemoRepository()");
				return catalogueBranchDemoRepository;

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
