package mx.com.endtoend.infrastructure.orders.common.provider;

import mx.com.endtoend.infrastructure.orders.common.repository.GenericOrderRepositoryInterface;
import mx.com.endtoend.infrastructure.orders.demo.business.GenericOrderActionDemoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.orders.calzada.business.GenericOrderActionCalzadaRepository;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.business.GenericOrderActionSFraguaRepository;
import mx.com.endtoend.infrastructure.orders.carredana.business.GenericOrderActionFCarredanaRepository;
import mx.com.endtoend.infrastructure.orders.ferresamano.business.GenericOrderActionCFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.business.GenericOrderActionCZapataRepository;

@Component
public class GenericOrderActionProvider {

	@Autowired
	private GenericOrderActionCalzadaRepository genericOrderActionCalzadaRepository;
	
	@Autowired
	private GenericOrderActionSFraguaRepository genericOrderActionSFraguaRepository;
	
	@Autowired
	private GenericOrderActionFCarredanaRepository genericOrderActionFCarredanaRepository;
	
	@Autowired
	private GenericOrderActionCZapataRepository genericOrderActionCZapataRepository;
	
	@Autowired
	private GenericOrderActionCFSamanoRepository genericOrderActionCFSamanoRepository;

	@Autowired
	private GenericOrderActionDemoRepository genericOrderActionDemoRepository;


	private final Logger LOG = LoggerFactory.getLogger(GenericOrderActionProvider.class);

	public GenericOrderRepositoryInterface getRepository(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN ORDER-CALZADA-REPOSITOY");
				return genericOrderActionCalzadaRepository;
				
			case CFRA:
				LOG.info("RETURN genericOrderActionSFraguaRepository");
				return genericOrderActionSFraguaRepository;
				
			case FCAR:
				LOG.info("RETURN genericOrderActionFCarredanaRepository");
				return genericOrderActionFCarredanaRepository;
				
			case CZAP:
				LOG.info("RETURN genericOrderActionCZapataRepository");
				return genericOrderActionCZapataRepository;
				
			case CFSA: 
				LOG.info("RETURN genericOrderActionCFSamanoRepository");
				return genericOrderActionCFSamanoRepository;

				case DEMO:
					LOG.info("RETURN genericOrderActionDemoRepository");
					return genericOrderActionDemoRepository;
				
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
