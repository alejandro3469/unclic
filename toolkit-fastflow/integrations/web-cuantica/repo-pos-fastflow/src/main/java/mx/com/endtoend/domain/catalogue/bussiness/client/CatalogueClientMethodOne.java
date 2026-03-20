package mx.com.endtoend.domain.catalogue.bussiness.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.dto.CatalogueUpdateResult;
import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueClientPersistencePort;
import mx.com.endtoend.domain.commons.constants.CatalogueClientTypeEnum;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueClientMethodOne implements CatalogueClientInterface {

	private final CatalogueJdeServicePort catalogueOracleServicePort;

	public CatalogueClientMethodOne(CatalogueJdeServicePort catalogueOracleServicePort) {
		this.catalogueOracleServicePort = catalogueOracleServicePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueClientMethodOne.class);

	@Override
	public ResponseModel getCfdiByCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getCfdiByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueClientPersistencePort.getCfdiByCompanyCode(companyCode, idOperation);

		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getRegimeFiscalCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getRegimeFiscalCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueClientPersistencePort.getRegimeFiscalCompanyCode(companyCode,
				idOperation);

		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getClientTypeCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getClientTypeCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueClientPersistencePort.getClientTypeCompanyCode(companyCode, idOperation);

		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getContectMethodCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getContectMethodCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueClientPersistencePort.getContectMethodCompanyCode(companyCode,
				idOperation);

		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getWorkTypeByCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getWorkTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueClientPersistencePort.getWorkTypeByCompanyCode(companyCode, idOperation);

		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueCompanyCodeAndCatalogueType(
			CatalogueClientPersistencePort catalogueClientPersistencePort, String companyCode, String catalogueType,
			String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueCompanyCodeAndCatalogueType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , catalogueType: %s ]", idOperation, companyCode,
				catalogueType));

		boolean validCataloguetype = CatalogueClientTypeEnum.isValid(catalogueType);

		if (!validCataloguetype) {
			LOG.warn(String.format("%s INVALID CLIENT-TYPE", idOperation));
			throw new ValidationError("WRONG CATALOGUE TYPE SELECTED");
		}

		ResponseModel responseModel = new ResponseModel();

		CatalogueClientTypeEnum catalogueTypeValue = CatalogueClientTypeEnum.valueOf(catalogueType);

		switch (catalogueTypeValue) {

		case CLIENT_TYPE:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE CLIENT-TYPE", idOperation));
			responseModel = this.updateCatalogueClientType(catalogueClientPersistencePort, companyCode, idOperation);
			break;

		case REGIME_FISCAL:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE REGIME-FISCAL", idOperation));
			responseModel = this.updateCatalogueRegimeFiscal(catalogueClientPersistencePort, companyCode, idOperation);
			break;

		case CONTACT_METHOD:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE CONTACT-METHOD", idOperation));
			responseModel = this.updateCatalogueContactMethod(catalogueClientPersistencePort, companyCode, idOperation);
			break;

		case CFDI:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE CFDI", idOperation));
			responseModel = this.updateCatalogueCFDI(catalogueClientPersistencePort, companyCode, idOperation);
			break;

		case WORK_TYPE:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE WORK-TYPE", idOperation));
			responseModel = this.updateCatalogueWorkType(catalogueClientPersistencePort, companyCode, idOperation);
			break;

		default:
			LOG.error(String.format("%s ERROR IN UPDATE CATALOGUE-CLIENT", idOperation));
			throw new GlobalError();
		}

		return responseModel;
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueClientType(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<ClientTypeDto> updateClientTypeList = new ArrayList<ClientTypeDto>();
		List<ClientTypeDto> disableClientTypeList = new ArrayList<ClientTypeDto>();
		List<ClientTypeDto> newRecordClientTypeList = new ArrayList<ClientTypeDto>();

		List<ClientTypeDto> localClientTypeList = (List<ClientTypeDto>) catalogueClientPersistencePort
				.getClientTypeCompanyCode(companyCode, idOperation).getData();

		List<ClientTypeDto> remoteClientTypeList = (List<ClientTypeDto>) catalogueOracleServicePort
				.getClientTypeByCompanyCode(companyCode, idOperation).getData();

		if (localClientTypeList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE CLIENT-TYPE EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueClientPersistencePort
					.updateCatalogueClientTypeByCompanyCodeAndList(companyCode, remoteClientTypeList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.CLIENT_TYPE.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ClientTypeDto localClientType : localClientTypeList) {
				for (ClientTypeDto remoteclientType : remoteClientTypeList) {
					if (localClientType.getCode().trim().equals(remoteclientType.getCode().trim())) {
						exists = true;
						localClientType.setValue(remoteclientType.getValue());
						updateClientTypeList.add(localClientType);
					}
				}
				if (!exists) {
					localClientType.setIsEnable(false);
					disableClientTypeList.add(localClientType);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (ClientTypeDto remoteClientTypeDto : remoteClientTypeList) {
				exists = false;
				for (ClientTypeDto localClientType : localClientTypeList) {
					if (remoteClientTypeDto.getCode().trim().equals(localClientType.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordClientTypeList.add(remoteClientTypeDto);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableClientTypeList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueClientTypeByCompanyCodeAndList(companyCode,
							disableClientTypeList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateClientTypeList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueClientTypeByCompanyCodeAndList(companyCode,
							updateClientTypeList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordClientTypeList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueClientTypeByCompanyCodeAndList(companyCode,
							newRecordClientTypeList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.CLIENT_TYPE.toString());

			return new ResponseModel(catalogueUpdateResult);
		}

	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueRegimeFiscal(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<RegimeFiscalDto> updateRegimeFiscalList = new ArrayList<RegimeFiscalDto>();
		List<RegimeFiscalDto> disableRegimeFiscalList = new ArrayList<RegimeFiscalDto>();
		List<RegimeFiscalDto> newRecordRegimeFiscalList = new ArrayList<RegimeFiscalDto>();

		List<RegimeFiscalDto> localRegimeFiscalList = (List<RegimeFiscalDto>) catalogueClientPersistencePort
				.getRegimeFiscalCompanyCode(companyCode, idOperation).getData();

		List<RegimeFiscalDto> remoteRegimeFiscalList = (List<RegimeFiscalDto>) catalogueOracleServicePort
				.getFiscalRegimeCatalogeByCompanyCode(companyCode, idOperation).getData();

		if (localRegimeFiscalList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE REGIME-FISCAL EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueClientPersistencePort
					.updateCatalogueRegimeFiscalByCompanyCodeAndList(companyCode, remoteRegimeFiscalList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.REGIME_FISCAL.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (RegimeFiscalDto localRegimeFiscal : localRegimeFiscalList) {
				for (RegimeFiscalDto remoteRegimeFiscal : remoteRegimeFiscalList) {
					if (localRegimeFiscal.getCode().trim().equals(remoteRegimeFiscal.getCode().trim())) {
						exists = true;
						localRegimeFiscal.setValue(remoteRegimeFiscal.getValue());
						localRegimeFiscal.setSatCode(remoteRegimeFiscal.getSatCode());
						updateRegimeFiscalList.add(localRegimeFiscal);
					}
				}
				if (!exists) {
					localRegimeFiscal.setIsEnable(false);
					disableRegimeFiscalList.add(localRegimeFiscal);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (RegimeFiscalDto remoteRegimeFiscal : remoteRegimeFiscalList) {
				exists = false;
				for (RegimeFiscalDto localRegimeFiscal : localRegimeFiscalList) {
					if (remoteRegimeFiscal.getCode().trim().equals(localRegimeFiscal.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordRegimeFiscalList.add(remoteRegimeFiscal);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableRegimeFiscalList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueRegimeFiscalByCompanyCodeAndList(companyCode,
							disableRegimeFiscalList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateRegimeFiscalList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueRegimeFiscalByCompanyCodeAndList(companyCode,
							updateRegimeFiscalList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordRegimeFiscalList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueRegimeFiscalByCompanyCodeAndList(companyCode,
							newRecordRegimeFiscalList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.REGIME_FISCAL.toString());

			return new ResponseModel(catalogueUpdateResult);
		}

	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueContactMethod(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<ContactMethodDto> updateContactMethodDtoList = new ArrayList<ContactMethodDto>();
		List<ContactMethodDto> disableContactMethodDtoList = new ArrayList<ContactMethodDto>();
		List<ContactMethodDto> newRecordContactMethodDtoList = new ArrayList<ContactMethodDto>();

		List<ContactMethodDto> localContactMethodList = (List<ContactMethodDto>) catalogueClientPersistencePort
				.getContectMethodCompanyCode(companyCode, idOperation).getData();

		List<ContactMethodDto> remoteContactMethodList = (List<ContactMethodDto>) catalogueOracleServicePort
				.getHowToContactByCompanyCode(companyCode, idOperation).getData();

		if (localContactMethodList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE CONTACT-METHOD EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueClientPersistencePort
					.updateCatalogueContactMethodByCompanyCodeAndList(companyCode, remoteContactMethodList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.CONTACT_METHOD.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ContactMethodDto localContactMethod : localContactMethodList) {
				for (ContactMethodDto remoteContactMethod : remoteContactMethodList) {
					if (localContactMethod.getCode().trim().equals(remoteContactMethod.getCode().trim())) {
						exists = true;
						localContactMethod.setValue(remoteContactMethod.getValue());
						updateContactMethodDtoList.add(localContactMethod);
					}
				}
				if (!exists) {
					localContactMethod.setIsEnable(false);
					disableContactMethodDtoList.add(localContactMethod);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (ContactMethodDto remoteContactMethod : remoteContactMethodList) {
				exists = false;
				for (ContactMethodDto localContactMethod : localContactMethodList) {
					if (remoteContactMethod.getCode().trim().equals(localContactMethod.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordContactMethodDtoList.add(remoteContactMethod);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableContactMethodDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueContactMethodByCompanyCodeAndList(companyCode,
							disableContactMethodDtoList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateContactMethodDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueContactMethodByCompanyCodeAndList(companyCode,
							updateContactMethodDtoList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordContactMethodDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueContactMethodByCompanyCodeAndList(companyCode,
							newRecordContactMethodDtoList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.CONTACT_METHOD.toString());

			return new ResponseModel(catalogueUpdateResult);
		}

	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueCFDI(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<CFDIDto> updateCfdiDtoList = new ArrayList<CFDIDto>();
		List<CFDIDto> disableCfdiDtoList = new ArrayList<CFDIDto>();
		List<CFDIDto> newRecordCfdiDtoList = new ArrayList<CFDIDto>();

		List<CFDIDto> localCfdiList = (List<CFDIDto>) catalogueClientPersistencePort
				.getCfdiByCompanyCode(companyCode, idOperation).getData();

		List<CFDIDto> remoteCfdiList = (List<CFDIDto>) catalogueOracleServicePort.getCfdi(companyCode, idOperation)
				.getData();

		if (localCfdiList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE CFDI EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueClientPersistencePort
					.updateCatalogueCfdiByCompanyCodeAndList(companyCode, remoteCfdiList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.CFDI.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (CFDIDto localCfdi : localCfdiList) {
				for (CFDIDto remoteCfdi : remoteCfdiList) {
					if (localCfdi.getCode().trim().equals(remoteCfdi.getCode().trim())) {
						exists = true;
						localCfdi.setValue(remoteCfdi.getValue());
						updateCfdiDtoList.add(localCfdi);
					}
				}
				if (!exists) {
					localCfdi.setIsEnable(false);
					disableCfdiDtoList.add(localCfdi);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (CFDIDto remoteCfdi : remoteCfdiList) {
				exists = false;
				for (CFDIDto localCfdi : localCfdiList) {
					if (remoteCfdi.getCode().trim().equals(localCfdi.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordCfdiDtoList.add(remoteCfdi);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableCfdiDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort
							.updateCatalogueCfdiByCompanyCodeAndList(companyCode, disableCfdiDtoList, idOperation)
							.getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateCfdiDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort
							.updateCatalogueCfdiByCompanyCodeAndList(companyCode, updateCfdiDtoList, idOperation)
							.getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordCfdiDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort
							.updateCatalogueCfdiByCompanyCodeAndList(companyCode, newRecordCfdiDtoList, idOperation)
							.getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.CFDI.toString());

			return new ResponseModel(catalogueUpdateResult);
		}

	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueWorkType(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation) {

		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();

		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<WorkTypeDto> updateWorkTypeDtoList = new ArrayList<WorkTypeDto>();
		List<WorkTypeDto> disableWorkTypeDtoList = new ArrayList<WorkTypeDto>();
		List<WorkTypeDto> newRecordWorkTypeDtoList = new ArrayList<WorkTypeDto>();

		List<WorkTypeDto> localWorkTypeList = (List<WorkTypeDto>) catalogueClientPersistencePort
				.getWorkTypeByCompanyCode(companyCode, idOperation).getData();

		List<WorkTypeDto> remoteWorkTypeList = (List<WorkTypeDto>) catalogueOracleServicePort
				.getWorkTypeByCompanyCode(companyCode, idOperation).getData();

		if (localWorkTypeList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE WORK-TYPE EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueClientPersistencePort
					.updateCatalogueWorkTypeByCompanyCodeAndList(companyCode, remoteWorkTypeList, idOperation)
					.getData();

			catalogueUpdateResult.setUpdateRecord(0);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.WORK_TYPE.toString());

			return new ResponseModel(catalogueUpdateResult);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (WorkTypeDto localWorkType : localWorkTypeList) {
				for (WorkTypeDto remoteWorkType : remoteWorkTypeList) {
					if (localWorkType.getCode().trim().equals(remoteWorkType.getCode().trim())) {
						exists = true;
						localWorkType.setValue(remoteWorkType.getValue());
						updateWorkTypeDtoList.add(localWorkType);
					}
				}
				if (!exists) {
					localWorkType.setIsEnable(false);
					disableWorkTypeDtoList.add(localWorkType);
				}
			}
			LOG.info(String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTENAL SOURCE", idOperation));
			for (WorkTypeDto remoteWorkType : remoteWorkTypeList) {
				exists = false;
				for (WorkTypeDto localWorkType : localWorkTypeList) {
					if (remoteWorkType.getCode().trim().equals(localWorkType.getCode().trim())) {
						exists = true;
					}
				}
				if (!exists) {
					newRecordWorkTypeDtoList.add(remoteWorkType);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableWorkTypeDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueWorkTypeByCompanyCodeAndList(companyCode,
							disableWorkTypeDtoList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateWorkTypeDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueWorkTypeByCompanyCodeAndList(companyCode,
							updateWorkTypeDtoList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordWorkTypeDtoList.size() == 0 ? 0
					: (int) catalogueClientPersistencePort.updateCatalogueWorkTypeByCompanyCodeAndList(companyCode,
							newRecordWorkTypeDtoList, idOperation).getData();

			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueClientTypeEnum.WORK_TYPE.toString());

			return new ResponseModel(catalogueUpdateResult);
		}
	}

}
