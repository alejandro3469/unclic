package mx.com.endtoend.domain.orderConfigurations.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.DocumentEnum;
import mx.com.endtoend.domain.commons.constants.PanelEnum;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.orders.dto.DocumentDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

/**
 * Clase contenedora de todos los métodos de validaciones para el módulo de
 * ORDER-CONFIGURATION separados por métodos que contienen todas las
 * validaciones por cada método del módulo.
 * 
 * @author ddcasas
 *
 */
public class GenericOrderConfigurationValidation {

	private final static Logger LOG = LoggerFactory.getLogger(GenericOrderConfigurationValidation.class);

	/**
	 * Método que realiza las llamadas a todas las validaciones asociadas al método
	 * ORDER_CONFIGURATION_ONE para el metodo de creación.
	 * 
	 * @param repository
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param idOperation
	 * @return String
	 */

	public String getValidationsToMethodOneOnCreate(OrderConfigurationPersistencePort repository,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT VALIDATIONS TO METHOD ONE getValidationsToMethodOne()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderConfigurationDto: %s , companyCode: %s ]", idOperation,
				orderConfigurationDto.toString(), companyCode));

		String validationResult = "";

		LOG.info(String.format("%s INIT VALIDATION requiredFields()", idOperation));
		String requieredFields = requiredFields(orderConfigurationDto, idOperation);

		LOG.info(String.format("%s INIT VALIDATION existConfigurationByOrderCodeAndCompanyCode()", idOperation));
		String existsOrderConfiguration = existConfigurationByOrderCodeAndCompanyCodeOnCreate(repository,
				orderConfigurationDto, companyCode, idOperation);

		validationResult = requieredFields + existsOrderConfiguration;

		LOG.info(String.format("%s VALIDATION RESULT: %s", idOperation, validationResult));

		return validationResult;
	}

	/**
	 * Método que realiza las llamadas a todas las validaciones asociadas al método
	 * ORDER_CONFIGURATION_ONE para el metodo de actualización.
	 * 
	 * @param repository
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param idOperation
	 * @return String
	 */

	public String getValidationsToMethodOneOnUpdate(OrderConfigurationPersistencePort repository,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT VALIDATIONS TO METHOD ONE getValidationsToMethodOne()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderConfigurationDto: %s , companyCode: %s ]", idOperation,
				orderConfigurationDto.toString(), companyCode));

		String validationResult = "";

		LOG.info(String.format("%s INIT VALIDATION requiredFields()", idOperation));
		String requieredFields = requiredFields(orderConfigurationDto, idOperation);

		LOG.info(String.format("%s INIT VALIDATION existConfigurationByOrderCodeAndCompanyCode()", idOperation));
		String existsOrderConfiguration = existConfigurationByOrderCodeAndCompanyCodeOnUpdate(repository,
				orderConfigurationDto, companyCode, idOperation);

		validationResult = requieredFields + existsOrderConfiguration;

		LOG.info(String.format("%s VALIDATION RESULT: %s", idOperation, validationResult));

		return validationResult;
	}

	/**
	 * Método que valida los campos requeridos en el objeto OrderConfigurationDto,
	 * retorna una cadena vacia si las validaciones son correctas, en caso
	 * contrario, retorna una caedna con los campos incorrectos.
	 * 
	 * @param orderConfigurationDto
	 * @param idOperation
	 * @return String
	 */
	public String requiredFields(OrderConfigurationDto orderConfigurationDto, String idOperation) {

		String validations = "";

		LOG.info(String.format("%s VALID LINE CODE ONE", idOperation));

		if (orderConfigurationDto.getLineCodeOne() == null) {
			LOG.warn(String.format("%s FIELD lineCodeOne IS NULL", idOperation));
			validations = validations + "lineCodeOne:  IS NULL - ";
		} else if (orderConfigurationDto.getLineCodeOne().isEmpty()) {
			LOG.warn(String.format("%s FIELD lineCodeOne IS NULL", idOperation));
			validations = validations + "lineCodeOne: IS EMPTY - ";

		}

		LOG.info(String.format("%s VALID LINE CODE TWO", idOperation));

		if (orderConfigurationDto.getLineCodeTwo() == null) {
			LOG.warn(String.format("%s FIELD lineCodeTwo IS NULL", idOperation));
			validations = validations + "lineCodeTwo: IS NULL - ";
		} else if (orderConfigurationDto.getLineCodeTwo().isEmpty()) {
			LOG.warn(String.format("%s FIELD lineCodeTwo IS NULL", idOperation));
			validations = validations + "lineCodeTwo: IS EMPTY - ";

		}

		LOG.info(String.format("%s VALID SALE TYPE", idOperation));

		if (orderConfigurationDto.getSaleType() == null) {
			validations = validations + "orderType: IS NULL - ";
		}

		LOG.info(String.format("%s VALID RETENTION CODE", idOperation));

		if (orderConfigurationDto.getRetentionCode() == null) {
			validations = validations + "retentionCode: IS NULL - ";
		} else if (orderConfigurationDto.getRetentionCode().isEmpty()) {
			validations = validations + "retentionCode: IS EMPTY - ";
		}

		if (orderConfigurationDto.getIsApplyCreditNote()) {
			validations = orderConfigurationDto.getCreditNoteCode() == null ? validations + "CREDIT NOTE CODE REQUIRED"
					: orderConfigurationDto.getCreditNoteCode().isEmpty() ? validations + "CREDIT NOTE CODE REQUIRED"
							: validations;
		}

		if (orderConfigurationDto.getDocuments() != null) {

			for (DocumentDto documentDto : orderConfigurationDto.getDocuments()) {

				if (!DocumentEnum.isValid(documentDto.getDocumentType()))
					validations = validations + "INVALID DOCUMENT TYPE:" + documentDto.getDocumentType() + " - ";

				if (!PanelEnum.isValid(documentDto.getPanelView()))
					validations = validations + "INVALID PANEL TYPE: " + documentDto.getPanelView() + " - ";
			}
		}

		if (orderConfigurationDto.getIsApplyCreditNote()) {

			if (orderConfigurationDto.getCreditNoteCode() == null) {
				validations = validations + "CREDIT NOTE CODE REQUIRED";
			}
			if (orderConfigurationDto.getCreditNoteCode() != null) {
				if (orderConfigurationDto.getCreditNoteCode().isEmpty())
					validations = validations + "CREDIT NOTE CODE REQUIRED";
			}

			if (orderConfigurationDto.getStateOneCreditNote() == null
					|| orderConfigurationDto.getStateTwoCreditNote() == null) {
				validations = validations + "STATE CREDIT NOTE REQUIRED";
			}
			if (orderConfigurationDto.getStateOneCreditNote() != null
					&& orderConfigurationDto.getStateTwoCreditNote() != null) {
				if (orderConfigurationDto.getStateOneCreditNote().isEmpty()
						|| orderConfigurationDto.getStateTwoCreditNote().isEmpty())
					validations = validations + "STATE CREDIT NOTE REQUIRED";
			}
			
			if (orderConfigurationDto.getStateOneValidCreditNote() == null
					|| orderConfigurationDto.getStateTwoValidCreditNote() == null) {
				validations = validations + "STATE VALIDATION CREDIT NOTE REQUIRED";
			}
			if (orderConfigurationDto.getStateOneValidCreditNote() != null
					&& orderConfigurationDto.getStateTwoValidCreditNote() != null) {
				if (orderConfigurationDto.getStateOneValidCreditNote().isEmpty()
						|| orderConfigurationDto.getStateTwoValidCreditNote().isEmpty())
					validations = validations + "STATE VALIDATION  CREDIT NOTE REQUIRED";
			}

		}
		return validations;
	}

	/**
	 * Método que valida si ya existe una configuración por código de orden y
	 * compañia. Retorna una cadena vacia si no existen registros, en caso contrario
	 * retorna el mensaje de CONFIGURATION-EXISTS.
	 * 
	 * @param repository
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param idOperation
	 * @return String
	 */
	public String existConfigurationByOrderCodeAndCompanyCodeOnCreate(OrderConfigurationPersistencePort repository,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {

		String validation = "";

		LOG.info(String.format("%s VALID EXIST ORDER-CONFIGURATION", idOperation));

		boolean validExists = (boolean) repository.existsOrderConfigurationByOrderTypeAndCompanyCode(
				orderConfigurationDto.getSaleType().getCode(), companyCode, idOperation).getData();

		validation = validExists ? "ORDER-CONFIGURATION EXISTS - " : "";

		return validation;
	}

	/**
	 * Método que valida si ya existe una configuración por código de orden y
	 * compañia al momento de actualizar un registro. Retorna una cadena vacia si no
	 * existen registros, en caso contrario retorna el mensaje de
	 * CONFIGURATION-EXISTS.
	 * 
	 * @param repository
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param idOperation
	 * @return String
	 */
	public String existConfigurationByOrderCodeAndCompanyCodeOnUpdate(OrderConfigurationPersistencePort repository,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {

		String validation = "";

		LOG.info(String.format("%s VALID EXIST ORDER-CONFIGURATION", idOperation));

		boolean validExists = (boolean) repository.existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(
				orderConfigurationDto.getSaleType().getCode(), orderConfigurationDto.getId(), companyCode, idOperation)
				.getData();

		validation = validExists ? "ORDER-CONFIGURATION EXISTS - " : "";

		return validation;
	}

}
