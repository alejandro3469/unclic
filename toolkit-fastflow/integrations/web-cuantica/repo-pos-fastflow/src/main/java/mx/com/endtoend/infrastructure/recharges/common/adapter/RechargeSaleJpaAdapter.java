package mx.com.endtoend.infrastructure.recharges.common.adapter;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.infrastructure.recharges.common.repository.GenericRechargeSaleRepository;
import mx.com.endtoend.infrastructure.recharges.common.factory.RechargeSaleRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.recharges.dto.CompanyPhoneDto;
import mx.com.endtoend.domain.recharges.dto.CompanyRechargeConfigurationDto;
import mx.com.endtoend.domain.recharges.dto.RechargeRequestDto;
import mx.com.endtoend.domain.recharges.dto.RechargeTickteDto;
import mx.com.endtoend.domain.recharges.ports.RechargeSalePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class RechargeSaleJpaAdapter implements RechargeSalePersistencePort {

	@Autowired
	private RechargeSaleRepositoryFactory rechargeSaleRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(RechargeSaleJpaAdapter.class);

	@Override
	public ResponseModel getCompanyConfigurationByRecharge(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCompanyPhoneListByCompanyCode()", idOperation));
		GenericRechargeSaleRepository rechargeRepository = rechargeSaleRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (rechargeRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CompanyRechargeConfigurationDto companyRechargeConfigurationDto = rechargeRepository
				.getCompanyConfigurationByRecharge(idOperation);
		return new ResponseModel(companyRechargeConfigurationDto);
	}

	@Override
	public ResponseModel sendRechargeRequestToSELIAByCompanyCode(SeliaRequest seliaRequest, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT sendRechargeRequestToSELIAByCompanyCode()", idOperation));
		GenericRechargeSaleRepository rechargeRepository = rechargeSaleRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (rechargeRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		SeliaResponse seliaResponse = rechargeRepository.sendRechargeRequestToSELIAByCompanyCode(seliaRequest,
				idOperation);
		return new ResponseModel(seliaResponse);
	}

	@Override
	public ResponseModel getCompanyPhoneListByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCompanyPhoneListByCompanyCode()", idOperation));
		GenericRechargeSaleRepository rechargeRepository = rechargeSaleRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (rechargeRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<CompanyPhoneDto> companyPhoneDtoLis = rechargeRepository.getCompanyPhoneListByCompanyCode(idOperation);
		return new ResponseModel(companyPhoneDtoLis);
	}

	@Override
	public ResponseModel generateTicketRechargeSaleByCompanmyCode(RechargeTickteDto rechargeTickteDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateTicketRechargeSaleByCompanmyCode()", idOperation));
		GenericRechargeSaleRepository rechargeRepository = rechargeSaleRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (rechargeRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		
		return rechargeRepository.generateTicketRechargeSaleByCompanmyCode(rechargeTickteDto, idOperation);
	}

	@Override
	public void saveRechargeRequestByCompanyCode(RechargeRequestDto rechargeRequestDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT saveRechargeRequestByCompanyCode()", idOperation));
		GenericRechargeSaleRepository rechargeRepository = rechargeSaleRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (rechargeRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		rechargeRepository.saveRechargeRequest(rechargeRequestDto, idOperation);
	}

	@Override
	public ResponseModel getRechargeRequestByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getRechargeRequestByOrderNumberAndCodeAndCompanyCode()", idOperation));
		GenericRechargeSaleRepository rechargeRepository = rechargeSaleRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (rechargeRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		RechargeRequestDto rechargeRequestDto = rechargeRepository
				.getRechargeRequestByOrderNumberAndCodeAndCompanyCode(orderNumber, orderCode, idOperation);

		return new ResponseModel(rechargeRequestDto);
	}

}
