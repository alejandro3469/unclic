package mx.com.endtoend.domain.catalogue.bussiness.branch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchAddressDto;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueBranchPersistencePort;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueBranchMethodOne implements CatalogueBranchInterface {

	private CatalogueJdeServicePort catalogueOracleServicePort;

	public CatalogueBranchMethodOne(CatalogueJdeServicePort catalogueOracleServicePort) {
		this.catalogueOracleServicePort = catalogueOracleServicePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueBranchMethodOne.class);

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel updateBranchAddressByCompanyCode(CatalogueBranchPersistencePort catalogueBranchPersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateBranchAddressByCompanyCode() ", idOperation));
		ResponseModel responseGetBranch = catalogueBranchPersistencePort.getBranchListByCompanyCode(companyCode,
				idOperation);
		List<BranchDto> branchList = (List<BranchDto>) responseGetBranch.getData();
		List<BranchDto> branchToUpdate = new ArrayList<>();

		for (BranchDto branchDto : branchList) {
			ResponseModel responseBranchDirection = catalogueOracleServicePort
					.getBrancgAddressByCode(branchDto.getCode(), companyCode, idOperation);
			BranchAddressDto branchAddressDto = (BranchAddressDto) responseBranchDirection.getData();
			if (branchAddressDto != null) {
				branchToUpdate.add(new BranchDto(branchDto, branchAddressDto.getStreet(),
						branchAddressDto.getInsideNumber(), branchAddressDto.getOutsideNumber(),
						branchAddressDto.getCp(), branchAddressDto.getColony(), branchAddressDto.getPhoneNumber()));
			}
		}

		int updateRecords = 0;
		LOG.info(String.format("%s LIST SIZE: %d ", idOperation,branchToUpdate.size()));
		for (BranchDto branchDto : branchToUpdate) {
			LOG.info(String.format("%s BRANCH: %s", idOperation, branchDto.toString()));
			ResponseModel responseUpdateBranch = catalogueBranchPersistencePort.updateBranchByCompanyCode(branchDto,
					companyCode, idOperation);
			boolean isUpdate = (boolean) responseUpdateBranch.getData();
			if (isUpdate)
				updateRecords++;
		}
		return new ResponseModel(updateRecords);
	}
}