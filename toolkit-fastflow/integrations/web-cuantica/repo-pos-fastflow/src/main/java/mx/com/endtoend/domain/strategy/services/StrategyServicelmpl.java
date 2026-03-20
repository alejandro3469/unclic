package mx.com.endtoend.domain.strategy.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.strategy.dto.StrategyDto;
import mx.com.endtoend.domain.strategy.ports.api.StrategyServicePort;
import mx.com.endtoend.domain.strategy.ports.spi.StrategyPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class StrategyServicelmpl implements StrategyServicePort{
	
	private StrategyPersistencePort strategyPersistencePort;
	
	public StrategyServicelmpl(StrategyPersistencePort strategyPersistencePort) {
		this.strategyPersistencePort = strategyPersistencePort;
	}
	
	private final static Logger LOG = LoggerFactory.getLogger(StrategyServicelmpl.class);

	@Override
	public ResponseModel strategyList() {
		ResponseModel responseModel = new ResponseModel();
		List<StrategyDto> strategyList = strategyPersistencePort.strategyList();
		if(strategyList!=null) {
			LOG.info("LISTA DE STRATEGY");
			responseModel.setData(strategyList);
			responseModel.setResponseCode(120);
			responseModel.setField("");
		}else {
			LOG.info("ERROR AL RECUPERAR LISTA DE STRATEGY");
			responseModel.setData(null);
			responseModel.setResponseCode(125);
			responseModel.setField("");
		}
		
		return responseModel;
	}


}
