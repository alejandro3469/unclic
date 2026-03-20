package mx.com.endtoend.domain.recharges.business.validations;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.payments.business.validations.GenericPaymentValidation;
import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

/**
 * Clase para la validación de los datos operativos ingresados al sistema para
 * la creación de veta de tiempo aire
 */
public class RechargeSaleValidation {

	private GenericPaymentValidation genericPaymentValidation = new GenericPaymentValidation();

	/**
	 * Método que valida que los datos ingresados sean correctos para la generación
	 * de venta de tiempo aire
	 * 
	 * @param rechargeSaleDto
	 * @return
	 */
	public String validOperativeDataToCreate(RechargeSaleDto rechargeSaleDto) {
		String validations = "";
		if (rechargeSaleDto == null)
			return "-INVALID OPERATIVE DATA";
		validations = validRequiredFields(rechargeSaleDto, validations);
		if (rechargeSaleDto.getPayments() == null)
			return validations += "-INVALID PAYMENT METHOD";
		validations = validPaymentMethods(rechargeSaleDto, validations);
		return validations;
	}

	private String validPaymentMethods(RechargeSaleDto rechargeSaleDto, String validations) {
		int lines = 0;
		if (rechargeSaleDto.getPayments().getPaymentCashList() != null) {
			lines += rechargeSaleDto.getPayments().getPaymentCashList().size();
			validations = genericPaymentValidation
					.validPaymentCashList(rechargeSaleDto.getPayments().getPaymentCashList(), validations);
		}

		if (rechargeSaleDto.getPayments().getCreditCardPaymentList() != null) {
			lines += rechargeSaleDto.getPayments().getCreditCardPaymentList().size();
			validations = genericPaymentValidation
					.validPaymentCreditCard(rechargeSaleDto.getPayments().getCreditCardPaymentList(), validations);
		}

		if (rechargeSaleDto.getPayments().getTransferPaymentList() != null) {
			lines += rechargeSaleDto.getPayments().getTransferPaymentList().size();
			validations = genericPaymentValidation
					.validPaymentTransfer(rechargeSaleDto.getPayments().getTransferPaymentList(), validations);
		}

		if (rechargeSaleDto.getPayments().getCheckPaymentList() != null) {
			lines += rechargeSaleDto.getPayments().getCheckPaymentList().size();
			validations = genericPaymentValidation
					.validPaymentCheck(rechargeSaleDto.getPayments().getCheckPaymentList(), validations);
		}

		if (lines == 0)
			validations += "PAYMENT METHOD IS REQUIRED";
		return validations;
	}

	private String validRequiredFields(RechargeSaleDto rechargeSaleDto, String validations) {
		validations += rechargeSaleDto.getOrderCode() == null ? "-INVALID ORDER CODE" : "";
		validations += rechargeSaleDto.getOrderCode() != null
				? rechargeSaleDto.getOrderCode().isEmpty() ? "-INVALID ORDER CODE" : ""
				: "";

		validations += rechargeSaleDto.getClientNumber() == null ? "-CLIENT IS REQUIRED" : "";

		validations += rechargeSaleDto.getEmployeeEmail() == null ? "-INVALID EMPLOYEE EMAIL" : "";
		validations += rechargeSaleDto.getEmployeeEmail() != null
				? rechargeSaleDto.getEmployeeEmail().isEmpty() ? "-INVALID EMPLOYEE EMAIL" : ""
				: "";

		validations += rechargeSaleDto.getPhoneNumber() == null ? "-PHONE NUMBER IS REQUIRED" : "";
		validations += rechargeSaleDto.getPhoneNumber() != null
				? !validNumber(rechargeSaleDto.getPhoneNumber()) ? "-INVALID PHONE NUMBER" : ""
				: "";

		if (rechargeSaleDto.getCompanyRecharge() == null) {
			validations += "-INVALID COMPANY PHONE";
			return validations;
		}

		validations += rechargeSaleDto.getCompanyRecharge().getCompanyPhoe() == null ? "-INVALID COMPANY PHONE" : "";
		validations += rechargeSaleDto.getCompanyRecharge().getCompanyPhoe() == null ? "-INVALID COMPANY PHONE CODE"
				: "";

		validations += rechargeSaleDto.getCompanyRecharge().getAmount() == null ? "-AMOUNT IS REQUIRED" : "";
		validations += rechargeSaleDto.getCompanyRecharge().getAmount() != null
				? rechargeSaleDto.getCompanyRecharge().getAmount().compareTo(BigDecimal.ZERO) <= 0 ? "-AMOUNT CANNOT BE NEGATIVE " : ""
				: "";

		return validations;
	}

	private boolean validNumber(String number) {
		Pattern pattern = Pattern.compile("^\\d{10}$");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}

	/**
	 * Método que valida que los datos de configración del sistema sean correctos
	 * para la generación de venta de tiempo aire
	 * 
	 * @param orderConfigurationDto
	 * @param employeeDto
	 * @param openingOperation
	 * @param orderNumber
	 * @return
	 */
	public String validSystemOperativeDataToCreate(OrderConfigurationDto orderConfigurationDto, EmployeeDto employeeDto,
			OpeningOperationDto openingOperation, BigDecimal orderNumber) {

		String validations = "";

		validations = validOrderConfiguration(orderConfigurationDto, validations);

		validations = validEmployeeConfiguration(employeeDto, validations);

		validations += orderNumber == null ? "ERROR IN GENERATE ORDER NUMBER BY CURRENT SALE" : "";

		validations = genericPaymentValidation.validOpeningOperation(openingOperation, validations);

		return validations;
	}

	private String validEmployeeConfiguration(EmployeeDto employeeDto, String validations) {
		validations += employeeDto == null ? " -EMPLOYEE CONFIGURATION NOT FOUND" : "";
		validations += employeeDto != null ? (employeeDto.getUserId() == null || employeeDto.getUserNumber() == null)
				? " -EMPLOYEE CONFIGURATION NOT FOUND"
				: "" : "";
		return validations;
	}

	private String validOrderConfiguration(OrderConfigurationDto orderConfigurationDto, String validations) {
		validations += orderConfigurationDto == null ? "INVALID ORDER CONFIGURATION" : "";
		validations += orderConfigurationDto != null
				? orderConfigurationDto.getLineCodeOne() == null || orderConfigurationDto.getLineCodeOne().isEmpty()
						? " -INVALID ORDER CONFIGURAION BY STATE ONE"
						: ""
				: "";
		validations += orderConfigurationDto != null
				? orderConfigurationDto.getLineCodeTwo() == null || orderConfigurationDto.getLineCodeTwo().isEmpty()
						? " -INVALID ORDER CONFIGURAION BY STATE TWO"
						: ""
				: "";
		return validations;
	}

}
