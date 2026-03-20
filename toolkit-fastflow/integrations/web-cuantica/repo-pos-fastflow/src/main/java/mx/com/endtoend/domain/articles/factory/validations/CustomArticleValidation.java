package mx.com.endtoend.domain.articles.factory.validations;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.domain.articles.ports.spi.CustomArticlePersistencePort;
import mx.com.endtoend.domain.commons.constants.CustomArticleSaleTypeEnum;

/**
 * Clase para validación de datos operativos de los artículos personalizados del
 * sistema
 * 
 * @author ddcasas
 *
 */
public class CustomArticleValidation {

	public String validOperativeDataOnCreate(CustomArticlePersistencePort customArticlePersistencePort,
			CustomArticleDto customArticleDto, String companyCode, String branchCode, String idOperation) {

		String validations = "";
		boolean existsByName = (boolean) customArticlePersistencePort
				.existsCustomArticleByName(customArticleDto.getName(), companyCode, branchCode, idOperation).getData();

		validations = existsByName ? validations + "-NAME ALREADY EXISTS-" : validations;

		boolean existByArticleNumber = (boolean) customArticlePersistencePort.existsCustomArticleByArticleNumber(
				customArticleDto.getArticleNumber(), companyCode, branchCode, idOperation).getData();

		validations = existByArticleNumber ? validations + "-ARTICLE NUMBER ALREADY EXISTS-" : validations;

		validations = customArticleDto.getSaleType() == null ? validations + "-INVALID SALE TYPE-" : validations;

		validations = customArticleDto.getSaleType() != null
				? customArticleDto.getSaleType().isEmpty() ? validations + "-INVALID SALE TYPE-" : validations
				: validations;

		validations = customArticleDto.getSaleType() != null
				? !CustomArticleSaleTypeEnum.isValid(customArticleDto.getSaleType())
						? validations + "-INVALID SALE TYPE-"
						: validations
				: validations;

		return validations;
	}

	public String validOperativeDataOnUpdate(CustomArticlePersistencePort customArticlePersistencePort,
			CustomArticleDto customArticleDto, String companyCode, String branchCode, String idOperation) {

		String validations = "";

		boolean existsByNameAndIdNot = (boolean) customArticlePersistencePort.existsCustomArticleByNameAndIdNot(
				customArticleDto.getName(), customArticleDto.getId(), companyCode, branchCode, idOperation).getData();

		validations = existsByNameAndIdNot ? validations + "-NAME ALREADY EXISTS-" : validations;

		boolean existByArticleNumberAndIdNot = (boolean) customArticlePersistencePort
				.existsCustomArticleByArticleNumberAndIdNot(customArticleDto.getArticleNumber(),
						customArticleDto.getId(), companyCode, branchCode, idOperation)
				.getData();

		validations = existByArticleNumberAndIdNot ? validations + "-ARTICLE NUMBER ALREADY EXISTS-" : validations;

		validations = customArticleDto.getSaleType() == null ? validations + "-INVALID SALE TYPE-" : validations;

		validations = customArticleDto.getSaleType() != null
				? customArticleDto.getSaleType().isEmpty() ? validations + "-INVALID SALE TYPE-" : validations
				: validations;

		validations = customArticleDto.getSaleType() != null
				? !CustomArticleSaleTypeEnum.isValid(customArticleDto.getSaleType())
						? validations + "-INVALID SALE TYPE-"
						: validations
				: validations;

		return validations;
	}

}
