package mx.com.endtoend.infrastructure.cash.closingInstruments.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.factory.ClosingInstrumentRepositoryFactory;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories.GenericClosingInstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.cash.closingInstruments.ports.spi.ClosingInstrumentPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class ClosingInstrumentJpaAdapter implements ClosingInstrumentPersistencePort {

	@Autowired
	private ClosingInstrumentRepositoryFactory closingInstrumentRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(ClosingInstrumentJpaAdapter.class);

	@Override
	public ResponseModel createClosePaymentInstrumentByCompanyCode(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createClosePaymentInstrumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closePaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				closePaymentInstrumentDto.toString(), companyCode));
		GenericClosingInstrumentRepository repository = closingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosePaymentInstrumentDto closePaymentInstrumentCreated = repository
				.createClosePaymentInstrument(closePaymentInstrumentDto, idOperation);
		return new ResponseModel(closePaymentInstrumentCreated);
	}

	@Override
	public ResponseModel updateClosePaymentInstrumentByCompanyCode(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateClosePaymentInstrumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closePaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				closePaymentInstrumentDto.toString(), companyCode));
		GenericClosingInstrumentRepository repository = closingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosePaymentInstrumentDto closePaymentInstrumentUpdated = repository
				.updateClosePaymentInstrument(closePaymentInstrumentDto, idOperation);
		return new ResponseModel(closePaymentInstrumentUpdated);
	}

	@Override
	public ResponseModel validExisteByCodeOrNameByCompanyCodeToCreate(
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT validExisteByCodeOrNameByCompanyCodeToCreate()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closePaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				closePaymentInstrumentDto.toString(), companyCode));
		System.out.println("ANTES DE LLAMADA");
		GenericClosingInstrumentRepository repository = closingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosePaymentInstrumentDto closePaymentInstrument = repository
				.validExisteByCodeOrNameToCreate(closePaymentInstrumentDto, idOperation);
		return new ResponseModel(closePaymentInstrument);
	}

	@Override
	public ResponseModel validExisteByCodeOrNameByCompanyCodeToUpdate(
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT validExisteByCodeOrNameByCompanyCodeToUpdate()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closePaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				closePaymentInstrumentDto.toString(), companyCode));
		GenericClosingInstrumentRepository repository = closingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosePaymentInstrumentDto closePaymentInstrument = repository
				.validExisteByCodeOrNameToUpdate(closePaymentInstrumentDto, idOperation);
		return new ResponseModel(closePaymentInstrument);
	}

	@Override
	public ResponseModel getClosePaymentInstrumentListByEnableAndCompanyCode(boolean enable, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getClosePaymentInstrumentListByEnableAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ enable: %b , companyCode: %s ] ", idOperation, enable, companyCode));
		GenericClosingInstrumentRepository repository = closingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<ClosePaymentInstrumentDto> closePaymentInstrumentList = repository
				.getClosePaymentInstrumentListByEnable(enable, idOperation);
		return new ResponseModel(closePaymentInstrumentList);
	}

	@Override
	public ResponseModel getClosePaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getClosePaymentInstrumentByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ id: %s , companyCode: %s ] ", idOperation, id.toString(), companyCode));
		GenericClosingInstrumentRepository repository = closingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosePaymentInstrumentDto closePaymentInstrument = repository.getClosePaymentInstrumentById(id, idOperation);
		return new ResponseModel(closePaymentInstrument);
	}

}
