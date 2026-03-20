package mx.com.endtoend.domain.advertising.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.advertising.business.SaleAdvertisingFactory;
import mx.com.endtoend.domain.advertising.business.SaleAdvertisingInterface;
import mx.com.endtoend.domain.advertising.dto.SaleAdvertisingInterfaceService;
import mx.com.endtoend.domain.advertising.ports.AdvertisingPersistencePort;
import mx.com.endtoend.domain.advertising.ports.AdvertisingServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase encargada de obtener la implementación concreta de la interfaz
 * SaleAdvertisingInterface con la lógica de negocio correspondiente al método
 * configurado
 * 
 * @author ddcasas
 *
 */
public class AdvertisingServiceImpl implements AdvertisingServicePort {

	private AdvertisingPersistencePort advertisingPersistencePort;

	public AdvertisingServiceImpl(AdvertisingPersistencePort advertisingPersistencePort) {
		this.advertisingPersistencePort = advertisingPersistencePort;
	}

	private SaleAdvertisingFactory saleAdvertisingFactory = new SaleAdvertisingFactory();
	private final Logger LOG = LoggerFactory.getLogger(AdvertisingServiceImpl.class);

	/**
	 * Método para la creación de los registros de venta anuncio por el método
	 * configurado
	 */
	@Override
	public ResponseModel createAdvertisingByMethod(SaleAdvertisingInterfaceService saleAdvertisingInterfaceService,
			AdvertisingDto advertisingDto, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createAdvertisingByMethod()", idOperation));
		saleAdvertisingInterfaceService.setAdvertisingPersistencePort(advertisingPersistencePort);
		SaleAdvertisingInterface saleAdvertising = saleAdvertisingFactory
				.getImplementationByCode(saleAdvertisingInterfaceService, method);
		if (saleAdvertising == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = saleAdvertising.createAdvertisingByMethod(advertisingDto,
				companyCode, idOperation);
		return responseFromImplementation;
	}

	/**
	 * Método para recuperar la lista de ventas anuncio por el método configurado
	 */
	@Override
	public ResponseModel viewAdvertisingListByMethod(SaleAdvertisingInterfaceService saleAdvertisingInterfaceService,
			String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewAdvertisingListByMethod()", idOperation));
		saleAdvertisingInterfaceService.setAdvertisingPersistencePort(advertisingPersistencePort);
		SaleAdvertisingInterface saleAdvertising = saleAdvertisingFactory
				.getImplementationByCode(saleAdvertisingInterfaceService, method);
		if (saleAdvertising == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = saleAdvertising.viewAdvertisingListByMethod(companyCode,
				idOperation);
		return responseFromImplementation;
	}

	/**
	 * Método para recuperar el detalle de venta anuncio por el método configurado
	 */
	@Override
	public ResponseModel viewAdvertisingDetailByIdAndMethod(
			SaleAdvertisingInterfaceService saleAdvertisingInterfaceService, Long id, String method, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT createAdvertisingByMethod()", idOperation));
		saleAdvertisingInterfaceService.setAdvertisingPersistencePort(advertisingPersistencePort);
		SaleAdvertisingInterface saleAdvertising = saleAdvertisingFactory
				.getImplementationByCode(saleAdvertisingInterfaceService, method);
		if (saleAdvertising == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = saleAdvertising.viewAdvertisingDetailByIdAndMethod(id, companyCode,
				idOperation);
		return responseFromImplementation;
	}

}
