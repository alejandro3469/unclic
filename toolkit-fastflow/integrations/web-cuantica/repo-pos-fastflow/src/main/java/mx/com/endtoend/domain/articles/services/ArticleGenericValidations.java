package mx.com.endtoend.domain.articles.services;

import java.util.HashMap;
import java.util.Map;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;

/**
 * 
 * @author ddcasas
 *
 */
public class ArticleGenericValidations {

	/**
	 * Método que realiza la llamada a los servicios de validación pertenecientes al
	 * método uno del módulo de artículos.
	 * 
	 * @param serchParamsArticles objeto que contiene los filtros de búsqueda de
	 *                            artículos
	 * 
	 * @return valor boolean indicadondo el resultado de las validaciones
	 * 
	 */

	public boolean validateParamsFilterToMethodOne(GenericSerchParamsArticleDto serchParamsArticles) {

		HashMap<String, Object> validations = new HashMap<>();

		boolean valid = true;

		validations.put("warehouse", existsWarehouse(serchParamsArticles.getWarehouseCode()));
		validations.put("priceType", existsPrice(serchParamsArticles.getPriceType()));
		validations.put("params", existsParams(serchParamsArticles));

		for (Map.Entry<String, Object> entry : validations.entrySet()) {
			if (!(boolean) entry.getValue()) {
				valid = false;
			}
		}

		return valid;
	}

	/**
	 * Método que valida que el código de almacen sea válido
	 * 
	 * @param warehouseCode código de almacén
	 * 
	 * @return valor boolean indicadno el resultado de la operación
	 */
	public boolean existsWarehouse(String warehouseCode) {

		boolean result = true;

		if (warehouseCode == null) {
			result = false;
		} else if (warehouseCode.length() == 0) {
			result = false;
		}

		return result;
	}

	/**
	 * Método que valida que el tipo de precio no sea nulo o vacio.
	 * 
	 * @param priceType tipo de precio seleccionado
	 * @return valor boolean indicadno el resultado de la operación
	 */
	public boolean existsPrice(String priceType) {

		boolean result = true;

		if (priceType == null) {
			result = false;
		} else if (priceType.isEmpty()) {
			result = false;
		}
		return result;
	}

	/**
	 * Metodo que valida que los campos introducidos como filtros no sea cadenas
	 * vacias en caso de ser distintos de null
	 * 
	 * @param serchParamsArticleDto objeto que contiene los filtros de búsqueda de
	 *                              artículos
	 * @return valor boolean indicadno el resultado de la operación
	 */
	public boolean existsParams(GenericSerchParamsArticleDto serchParamsArticleDto) {
		boolean result = true;

		boolean validArticleCode = true;
		boolean validDivision = true;
		boolean validCategory = true;
		boolean validFamily = true;
		boolean validCategoryCode = true;
		boolean validMark = true;
		boolean validArticleDescription = true;
		boolean validCatalogNumber = true;
		boolean validAlternativeDescription = true;

		if (serchParamsArticleDto.getArticleCode() == null) {
			validArticleCode = false;

		} else if (serchParamsArticleDto.getArticleCode().isEmpty()) {
			validArticleCode = false;
		}

		if (serchParamsArticleDto.getDivision() == null) {
			validDivision = false;

		} else if (serchParamsArticleDto.getDivision().isEmpty()) {
			validDivision = false;
		}

		if (serchParamsArticleDto.getCategory() == null) {
			validCategory = false;

		} else if (serchParamsArticleDto.getCategory().isEmpty()) {
			validCategory = false;
		}

		if (serchParamsArticleDto.getFamily() == null) {
			validFamily = false;

		} else if (serchParamsArticleDto.getFamily().isEmpty()) {
			validFamily = false;
		}

		if (serchParamsArticleDto.getCategoryCode() == null) {
			validCategoryCode = false;

		} else if (serchParamsArticleDto.getCategoryCode().isEmpty()) {
			validCategoryCode = false;
		}

		if (serchParamsArticleDto.getBrand() == null) {
			validMark = false;

		} else if (serchParamsArticleDto.getBrand().isEmpty()) {
			validMark = false;
		}

		if (serchParamsArticleDto.getArticleDescription() == null) {
			validArticleDescription = false;

		} else if (serchParamsArticleDto.getArticleDescription().isEmpty()) {
			validArticleDescription = false;
		}

		if (serchParamsArticleDto.getCatalogNumber() == null) {
			validCatalogNumber = false;

		} else if (serchParamsArticleDto.getCatalogNumber().isEmpty()) {
			validCatalogNumber = false;
		}

		if (serchParamsArticleDto.getAlternativeDescription() == null) {
			validAlternativeDescription = false;

		} else if (serchParamsArticleDto.getAlternativeDescription().isEmpty()) {
			validAlternativeDescription = false;
		}

		if (!validArticleCode && !validDivision && !validCategory && !validFamily && !validCategoryCode && !validMark
				&& !validArticleDescription && !validCatalogNumber && !validAlternativeDescription) {

			result = false;
		}

		return result;
	}

	public String validParamsToSerchArticleByBarcode(GenericSerchParamsArticleDto genericSerchParamsArticleDto) {

		String validation = "";
		String wareCodeValidation = "";
		String barcodeValidation = "";
		String priceTypeValidation = "";

		if (genericSerchParamsArticleDto.getWarehouseCode() == null) {

			wareCodeValidation = "WAREHOUSE-CODE REQUIRED ";

		} else if (genericSerchParamsArticleDto.getWarehouseCode().isEmpty()) {

			wareCodeValidation = "WAREHOUSE-CODE REQUIRED ";
		}

		if (genericSerchParamsArticleDto.getBarcode() == null) {

			barcodeValidation = "BARCODE REQUIERED ";

		} else if (genericSerchParamsArticleDto.getBarcode().isEmpty()) {

			barcodeValidation = "BARCODE REQUIERED ";
		}

		if (genericSerchParamsArticleDto.getPriceType() == null) {

			priceTypeValidation = "PRICE REQUIRED ";

		} else if (genericSerchParamsArticleDto.getPriceType().isEmpty()) {

			priceTypeValidation = "PRICE REQUIRED ";
		}

		validation = wareCodeValidation + barcodeValidation + priceTypeValidation;

		return validation;
	}
}
