package mx.com.endtoend.domain.advertising.business.validations;

import mx.com.endtoend.smart.bussiness.model.adversiting.dto.AdvertisingDto;

import java.math.BigDecimal;

/**
 * Clase para la validación de datos operativos en la generación de ordenes de
 * venta anuncio
 * 
 * @author ddcasas
 *
 */
public class SaleAdvertisingValidation {

	/**
	 * Método para validar que los datos operativos sean validos para el proceso de
	 * generacion de la orden
	 * 
	 * @param advertising
	 * @return
	 */
	public String validOperativeDataToGenerateOrder(AdvertisingDto advertising) {

		String validations = "";

		if (advertising == null)
			return "-INVALID OPERATIVE DATA";

		validations = validBranchCode(advertising, validations);

		validations = validEmployeeEmail(advertising, validations);

		validations = validAdvertisingMesage(advertising, validations);

		validations = validPublishDay(advertising, validations);

		validations = validDescription(advertising, validations);

		validations = validPricePerWord(advertising, validations);

		validations = validArticleNumber(advertising, validations);

//		validations = validArticleCode(advertising, validations);

		return validations;
	}

//	private String validArticleCode(AdvertisingDto advertising, String validations) {
//		validations = advertising.getArticleCode() == null ? validations += "-ARTICLE CODE IS REQUIRED" : validations;
//		validations = advertising.getArticleCode() == null
//				? advertising.getArticleCode().isEmpty() ? validations += "-ARTICLE CODE IS REQUIRED" : validations
//				: validations;
//		return validations;
//	}

	private String validArticleNumber(AdvertisingDto advertising, String validations) {
		validations = advertising.getArticleNumber() == null ? validations += "-ARTICLE NUMBER IS REQUIRED"
				: validations;
		return validations;
	}

	private String validPricePerWord(AdvertisingDto advertising, String validations) {
		validations = advertising.getPricePerWord() == null ? validations += "-PRICE IS REQUIRED" : validations;
		validations = advertising.getPricePerWord() == null
				? advertising.getPricePerWord().compareTo(BigDecimal.ZERO) <= 0 ? validations += "-PRICE CAN NOT BE ZERO OR NEGATIVE" : validations
				: validations;
		return validations;
	}

	private String validDescription(AdvertisingDto advertising, String validations) {
		validations = advertising.getDescription() == null ? validations += "-DESCRIPTION IS REQUIRED" : validations;
		validations = advertising.getDescription() != null
				? advertising.getDescription().isEmpty() ? validations += "-DESCRIPTION IS REQUIRED" : validations
				: validations;
		return validations;
	}

	private String validPublishDay(AdvertisingDto advertising, String validations) {
		validations = advertising.getPublishedDay() == null ? validations += "-PUBLISHED DAY IS REQUIRED" : validations;
		validations = advertising.getPublishedDay() != null
				? advertising.getPublishedDay() <= 0 ? validations += "-PUBLISHED DAY CANT NOT BE ZERO OR NEGATIVE"
						: validations
				: validations;
		return validations;
	}

	private String validAdvertisingMesage(AdvertisingDto advertising, String validations) {
		validations = advertising.getMessage() == null ? validations += "-ADVERTISING MESSAGE IS REQUIRED"
				: validations;

		validations = advertising.getMessage() != null
				? advertising.getMessage().isEmpty() ? validations += "-ADVERTISING MESSAGE IS REQUIRED" : validations
				: validations;
		return validations;
	}

	private String validEmployeeEmail(AdvertisingDto advertising, String validations) {
		validations = advertising.getEmployeeEmail() == null ? validations += "-EMPLOYEE EMAIL IS REQUIRED"
				: validations;
		validations = advertising.getEmployeeEmail() != null
				? advertising.getEmployeeEmail().isEmpty() ? validations += "-EMPLOYEE EMAIL IS REQUIRED" : validations
				: validations;
		return validations;
	}

	private String validBranchCode(AdvertisingDto advertising, String validations) {
		validations = advertising.getBranchCode() == null ? validations += "-BRANCH REQUIRED " : validations;
		validations = advertising.getBranchCode() != null
				? advertising.getBranchCode().isEmpty() ? validations += "-BRANCH REQUIRED " : validations
				: validations;
		return validations;
	}

}
