package mx.com.endtoend.infrastructure.cash.openingInstruments.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository.GenericOpeningInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.factory.OpeningInstrumentRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.cash.openingInstruments.ports.spi.OpenigInstrumentPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class OpeningInstrumentJpaAdapter implements OpenigInstrumentPersistencePort {

	@Autowired
	private OpeningInstrumentRepositoryFactory openingInstrumentRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(OpeningInstrumentJpaAdapter.class);

	@Override
	public ResponseModel createOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOpenPaymentInstrumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openPaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				openPaymentInstrumentDto.toString(), companyCode));

		GenericOpeningInstrumentRepository repository = openingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OpenPaymentInstrumentDto openPaymentInstrumentCreated = repository
				.createOpenPaymentInstrumentByCompanyCode(openPaymentInstrumentDto, idOperation);

		return new ResponseModel(openPaymentInstrumentCreated);
	}

	@Override
	public ResponseModel updateOpenPaymentInstrumentByCompanyCode(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateOpenPaymentInstrumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openPaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				openPaymentInstrumentDto.toString(), companyCode));
		GenericOpeningInstrumentRepository repository = openingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpenPaymentInstrumentDto openPaymentInstrumentUpdated = repository
				.updateOpenPaymentInstrumentByCompanyCode(openPaymentInstrumentDto, idOperation);
		return new ResponseModel(openPaymentInstrumentUpdated);
	}

	@Override
	public ResponseModel validExisteByCodeOrNameByCompanyCodeToCreate(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT validExisteByCodeOrNameByCompanyCodeToCreate()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openPaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				openPaymentInstrumentDto.toString(), companyCode));
		GenericOpeningInstrumentRepository repository = openingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpenPaymentInstrumentDto openPaymentInstrument = repository
				.validExisteByCodeOrNameByCompanyCodeToCreate(openPaymentInstrumentDto, idOperation);
		return new ResponseModel(openPaymentInstrument);
	}

	@Override
	public ResponseModel validExisteByCodeOrNameByCompanyCodeToUpdate(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT validExisteByCodeOrNameByCompanyCodeToUpdate()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openPaymentInstrumentDto: %s , companyCode: %s ] ", idOperation,
				openPaymentInstrumentDto.toString(), companyCode));
		GenericOpeningInstrumentRepository repository = openingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpenPaymentInstrumentDto openPaymentInstrument = repository
				.validExisteByCodeOrNameByCompanyCodeToUpdate(openPaymentInstrumentDto, idOperation);
		return new ResponseModel(openPaymentInstrument);
	}

	@Override
	public ResponseModel getOpenPaymentInstrumentListByEnableAndCompanyCode(boolean enable, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getOpenPaymentInstrumentListByEnableAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ enable: %b , companyCode: %s ] ", idOperation, enable, companyCode));
		GenericOpeningInstrumentRepository repository = openingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<OpenPaymentInstrumentDto> openPaymentInstrumentList = repository
				.getOpenPaymentInstrumentListByEnableAndCompanyCode(enable, idOperation);
		return new ResponseModel(openPaymentInstrumentList);
	}

	@Override
	public ResponseModel getOpenPaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getOpenPaymentInstrumentByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ id: %s , companyCode: %s ] ", idOperation, id.toString(), companyCode));
		GenericOpeningInstrumentRepository repository = openingInstrumentRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpenPaymentInstrumentDto openPaymentInstrument = repository.getOpenPaymentInstrumentById(id, idOperation);
		return new ResponseModel(openPaymentInstrument);
	}
}
