package mx.com.endtoend.domain.recharges.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.recharges.business.RechargeSaleFactory;
import mx.com.endtoend.domain.recharges.business.RechargeSaleInterface;
import mx.com.endtoend.domain.recharges.dto.CustomRechargeSaleParams;
import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.domain.recharges.ports.RechargeSalePersistencePort;
import mx.com.endtoend.domain.recharges.ports.RechargeSaleServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase encargada de la obtención del objeto concreto de la interfaz
 * {@link RechargeSaleInterface} con base en el método configurado a las
 * compañías del sistema.
 */
public class RechargeSaleServiceImpl implements RechargeSaleServicePort {

	private RechargeSalePersistencePort rechargeSalePersistencePort;

	public RechargeSaleServiceImpl(RechargeSalePersistencePort rechargeSalePersistencePort) {
		this.rechargeSalePersistencePort = rechargeSalePersistencePort;
	}

	private RechargeSaleFactory rechargeSaleFactory = new RechargeSaleFactory();
	private final Logger LOG = LoggerFactory.getLogger(RechargeSaleServiceImpl.class);

	@Override
	public ResponseModel createRechargeSaleByCompanyCode(RechargeSaleDto rechargeSaleDto,
			CustomRechargeSaleParams customRechargeSaleParams) {
		LOG.info(String.format("INIT createRechargeSaleByCompanyCode"));
		customRechargeSaleParams.setRechargeSalePersistencePort(rechargeSalePersistencePort);
		RechargeSaleInterface rechargeSale = rechargeSaleFactory.getImplementationByMethod(customRechargeSaleParams);
		if (rechargeSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					customRechargeSaleParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = rechargeSale.createRechargeSale(rechargeSaleDto);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel generateTicketRechargeByCompanyCode(CustomRechargeSaleParams customRechargeSaleParams) {
		LOG.info(String.format("INIT generateTicketRechargeByCompanyCode"));
		customRechargeSaleParams.setRechargeSalePersistencePort(rechargeSalePersistencePort);
		RechargeSaleInterface rechargeSale = rechargeSaleFactory.getImplementationByMethod(customRechargeSaleParams);
		if (rechargeSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					customRechargeSaleParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = rechargeSale.generateTicketRecharge(
				customRechargeSaleParams.getOrderCode(), customRechargeSaleParams.getOrderNumber());
		return responseFromImplementation;
	}

	@Override
	public ResponseModel getCompanyPhoneList(CustomRechargeSaleParams customRechargeSaleParams) {
		LOG.info(String.format("INIT getCompanyPhoneList"));
		customRechargeSaleParams.setRechargeSalePersistencePort(rechargeSalePersistencePort);
		RechargeSaleInterface rechargeSale = rechargeSaleFactory.getImplementationByMethod(customRechargeSaleParams);
		if (rechargeSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					customRechargeSaleParams.getIdOperation()));
			throw new GlobalError();
		}

		ResponseModel responseFromImplementation = rechargeSale.getCompanyPhoneList();
		return responseFromImplementation;
	}

}
