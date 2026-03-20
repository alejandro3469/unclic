package mx.com.endtoend.infrastructure.company.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;

@Component
public class CompanyConverter {

	public CompanyEntity companyDtoEntity(CompanyDto companyDto) {

		CompanyEntity companyEntity = new CompanyEntity();

		companyEntity.setId(companyDto.getId());
		companyEntity.setName(companyDto.getName());
		companyEntity.setCompanyNumber(companyDto.getCompanyNumber());
		companyEntity.setCode(CompanyCodes.valueOf(companyDto.getCode()));
		companyEntity.setApplyInvoiceProcess(companyDto.getApplyInvoiceProcess());
		companyEntity.setRfc(companyDto.getRfc());
		
		return companyEntity;

	}

	public CompanyDto companyEntityToCompanyDto(CompanyEntity companyEntity) {

		CompanyDto companyDto = new CompanyDto();

		companyDto.setId(companyEntity.getId());
		companyDto.setName(companyEntity.getName());
		companyDto.setCompanyNumber(companyEntity.getCompanyNumber());
		companyDto.setCode(companyEntity.getCode().toString());
		companyDto.setApplyInvoiceProcess(companyEntity.isApplyInvoiceProcess());
		companyDto.setRfc(companyEntity.getRfc());

		return companyDto;

	}

	public List<CompanyEntity> companyDtoListToCompanyEntityList(List<CompanyDto> companyDtoList) {

		List<CompanyEntity> companyEntityList = new ArrayList<CompanyEntity>();

		for (CompanyDto companyDto : companyDtoList) {
			companyEntityList.add(companyDtoEntity(companyDto));
		}
		return companyEntityList;
	}

	public List<CompanyDto> companyEntityListToCompanyDtoList(List<CompanyEntity> companyEntityList) {

		List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();

		for (CompanyEntity companyEntity : companyEntityList) {
			companyDtoList.add(companyEntityToCompanyDto(companyEntity));
		}
		return companyDtoList;
	}

}
