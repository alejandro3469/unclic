package mx.com.endtoend.infrastructure.recharges.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.recharges.dto.CompanyPhoneDto;
import mx.com.endtoend.infrastructure.recharges.common.entities.CompanyPhoneEntity;

@Component
public class CompanyPhoneConverter {

	public CompanyPhoneDto companyPhoneEntityToCompanyPhoneDto(CompanyPhoneEntity companyPhoneEntity) {

		CompanyPhoneDto companyPhoneDto = new CompanyPhoneDto();

		companyPhoneDto.setCompanyPhoneName(companyPhoneEntity.getCompanyPhoneName());
		companyPhoneDto.setCompanyPhoneCode(companyPhoneEntity.getCompanyPhoneCode());
		companyPhoneDto.setCompanyPhoe(companyPhoneEntity.getCompanyPhoe());
		companyPhoneDto.setLineType(companyPhoneEntity.getLineType());
		companyPhoneDto.setArticleCode(companyPhoneEntity.getArticleCode());
		companyPhoneDto.setArticleNumber(companyPhoneEntity.getArticleNumber());
		companyPhoneDto.setStorageType(companyPhoneEntity.getStorageType());
		companyPhoneDto.setDescription(companyPhoneEntity.getDescription());
		companyPhoneDto.setAmount(BigDecimal.valueOf(companyPhoneEntity.getAmount()));

		return companyPhoneDto;
	}

	public List<CompanyPhoneDto> companyPhoneEntityListToCompanyPhoneDtoList(
			List<CompanyPhoneEntity> companyPhoneEntityList) {
		List<CompanyPhoneDto> companyPhoneDtoList = new ArrayList<>();
		for (CompanyPhoneEntity companyPhoneEntity : companyPhoneEntityList) {
			companyPhoneDtoList.add(companyPhoneEntityToCompanyPhoneDto(companyPhoneEntity));
		}
		return companyPhoneDtoList;
	}
}
