package mx.com.endtoend.infrastructure.branch.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;

@Component
public class BranchConverter {

	@Autowired
	private CompanyConverter companyConverter;

	public BranchDto branchEntityToBranchDto(BranchEntity branchEntity) {

		BranchDto branchDto = new BranchDto();

		branchDto.setId(branchEntity.getId());
		branchDto.setName(branchEntity.getName());
		branchDto.setCode(branchEntity.getCode());
		branchDto.setStreet(branchEntity.getStreet() == null ? "" : branchEntity.getStreet());
		branchDto.setInsideNumber(branchEntity.getInsideNumber() == null ? "" : branchEntity.getInsideNumber());
		branchDto.setOutsideNumber(branchEntity.getOutsideNumber() == null ? "" : branchEntity.getOutsideNumber());
		branchDto.setCp(branchEntity.getCp() == null ? "" : branchEntity.getCp());
		branchDto.setColony(branchEntity.getColony() == null ? "" : branchEntity.getColony());
		branchDto.setPhoneNumber(branchEntity.getPhoneNumber() == null ? "" : branchEntity.getPhoneNumber());
		if (branchEntity.getCompany() != null) {
			branchDto.setCompany(companyConverter.companyEntityToCompanyDto(branchEntity.getCompany()));
		}

		return branchDto;
	}

	public BranchEntity branchDtoToBranchEntity(BranchDto branchDto) {

		BranchEntity branchEntity = new BranchEntity();

		branchEntity.setId(branchDto.getId());
		branchEntity.setName(branchDto.getName());
		branchEntity.setCode(branchDto.getCode());
		branchEntity.setStreet(branchDto.getStreet());
		branchEntity.setInsideNumber(branchDto.getInsideNumber());
		branchEntity.setOutsideNumber(branchDto.getOutsideNumber());
		branchEntity.setCp(branchDto.getCp());
		branchEntity.setColony(branchDto.getColony());
		branchEntity.setPhoneNumber(branchDto.getPhoneNumber());
		if (branchDto.getCompany() != null) {
			branchEntity.setCompany(companyConverter.companyDtoEntity(branchDto.getCompany()));
		}

		return branchEntity;
	}

	public List<BranchDto> branchEntityListToBranchDtoList(List<BranchEntity> branchEntityList) {
		List<BranchDto> branchDtoList = new ArrayList<BranchDto>();
		for (BranchEntity branchEntity : branchEntityList) {
			branchDtoList.add(branchEntityToBranchDto(branchEntity));
		}
		return branchDtoList;
	}

	public List<BranchEntity> branchDtoListToBranchEntityList(List<BranchDto> branchDtoList) {
		List<BranchEntity> branchEntityList = new ArrayList<BranchEntity>();
		for (BranchDto branchDto : branchDtoList) {
			branchEntityList.add(branchDtoToBranchEntity(branchDto));
		}
		return branchEntityList;
	}
}
