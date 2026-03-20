package mx.com.endtoend.infrastructure.logs.security.converters;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.logs.security.dto.EmployeeSummaryLogDto;
import mx.com.endtoend.domain.userConfigurations.dto.CreditNoteTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.PriceTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.UserConfigurationDto;
import mx.com.endtoend.domain.userConfigurations.dto.WarehouseOptionsDto;
import mx.com.endtoend.infrastructure.logs.security.entities.EmployeeFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.EmployeeLogEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.EmployeePreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserConfigurationFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserConfigurationPrevStateEntity;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

@Component
public class EmployeeLogConverter {

	public EmployeeLogEntity employeeSummaryToEntity(EmployeeSummaryLogDto employeeSummaryLogDto) {

		EmployeeLogEntity employeeLogEntity = new EmployeeLogEntity();

		employeeLogEntity.setUser(employeeSummaryLogDto.getUser());
		employeeLogEntity.setUpdatedDate(employeeSummaryLogDto.getUpdatedDate());
		employeeLogEntity.setUserId(employeeSummaryLogDto.getUserId());
		employeeLogEntity.setUsername(employeeSummaryLogDto.getUsername());

		return employeeLogEntity;

	}

	public EmployeePreviousStateEntity employeeSavedSummaryToEntity(EmployeeDto employeeSaved, Long employeeSummaryId) {

		EmployeePreviousStateEntity employeePreviousStateEntity = new EmployeePreviousStateEntity();

		employeePreviousStateEntity.setEmployeeSummaryId(employeeSummaryId);
		employeePreviousStateEntity.setUserId(employeeSaved.getUserId());
		employeePreviousStateEntity.setUserNumber(employeeSaved.getUserNumber());
		employeePreviousStateEntity.setEmployeeEmail(employeeSaved.getEmployeeEmail());
		employeePreviousStateEntity.setBranchCode(employeeSaved.getBranchCode());

		String roleJob = employeeSaved.getRoleJob().getCode() + employeeSaved.getRoleJob().getName();
		employeePreviousStateEntity.setRoleJob(roleJob);

		employeePreviousStateEntity.setDirectBoss(
				employeeSaved.getDirectBoss() != null ? employeeSaved.getDirectBoss().getEmployeeEmail() : "EMPTY");

		StringBuilder employees = new StringBuilder();
		if (employeeSaved.getEmployees() != null) {
			for (EmployeeDto employee : employeeSaved.getEmployees()) {
				if (employees.length() > 0) {
					employees.append(", ");
				}
				employees.append(employee.getEmployeeEmail());
			}
		}
		String employeeEmails = employees.toString();
		employeePreviousStateEntity.setEmployeeList(employeeEmails);

		return employeePreviousStateEntity;
	}

	public UserConfigurationPrevStateEntity userConfigurationSavedToEntity(UserConfigurationDto userConfiguration,
			Long employeePrevId) {

		UserConfigurationPrevStateEntity userConfigurationPrevStateEntity = new UserConfigurationPrevStateEntity();

		userConfigurationPrevStateEntity.setEmployeePrevId(employeePrevId);
		userConfigurationPrevStateEntity.setPercentageAuthorized(userConfiguration.getPercentageAuthorized());
		userConfigurationPrevStateEntity.setAuthorizationCode(userConfiguration.getAuthorizationCode());
		userConfigurationPrevStateEntity.setCreationDate(userConfiguration.getCreationDate());
		userConfigurationPrevStateEntity.setUpdatedDate(userConfiguration.getUpdatedDate());
		userConfigurationPrevStateEntity.setModifyBy(userConfiguration.getModifyBy());

		StringBuilder salesTypes = new StringBuilder();
		if (userConfiguration.getSaleTypes() != null) {
			for (SaleTypeDto saleType : userConfiguration.getSaleTypes()) {
				if (salesTypes.length() > 0)
					salesTypes.append(", ");
				salesTypes.append(saleType.getCode() + saleType.getType());
			}
		}
		String salesTypeList = salesTypes.toString();
		userConfigurationPrevStateEntity.setSaleTypes(salesTypeList);

		StringBuilder creditNotes = new StringBuilder();
		if (userConfiguration.getCreditNoteTypes() != null) {
			for (CreditNoteTypeDto creditNoteType : userConfiguration.getCreditNoteTypes()) {
				if (creditNotes.length() > 0)
					creditNotes.append(", ");
				creditNotes.append(creditNoteType.getCode() + creditNoteType.getType());
			}
		}
		String creditNoteList = creditNotes.toString();
		userConfigurationPrevStateEntity.setCreditNoteTypes(creditNoteList);

		StringBuilder priceTypes = new StringBuilder();
		if (userConfiguration.getPriceTypes() != null) {
			for (PriceTypeDto priceType : userConfiguration.getPriceTypes()) {
				if (priceTypes.length() > 0)
					priceTypes.append(", ");
				creditNotes.append(priceType.getCode() + priceType.getDescription());
			}
		}
		String priceTypeList = creditNotes.toString();
		userConfigurationPrevStateEntity.setPriceTypes(priceTypeList);

		StringBuilder warehouseOptions = new StringBuilder();
		if (userConfiguration.getWarehouseOptions() != null) {
			for (WarehouseOptionsDto warehouseOption : userConfiguration.getWarehouseOptions()) {
				if (warehouseOptions.length() > 0)
					warehouseOptions.append(", ");
				warehouseOptions.append(warehouseOption.getWarehouseCode());
			}
		}
		String warehouseOptionList = warehouseOptions.toString();
		userConfigurationPrevStateEntity.setWarehouseOptions(warehouseOptionList);

		return userConfigurationPrevStateEntity;
	}

	public EmployeeFinalStateEntity employeeUpdatedSummaryToEntity(EmployeeDto employeeUpdated,
			Long employeeSummaryId) {

		EmployeeFinalStateEntity employeeFinalStateEntity = new EmployeeFinalStateEntity();

		employeeFinalStateEntity.setEmployeeSummaryId(employeeSummaryId);
		employeeFinalStateEntity.setUserId(employeeUpdated.getUserId());
		employeeFinalStateEntity.setUserNumber(employeeUpdated.getUserNumber());
		employeeFinalStateEntity.setEmployeeEmail(employeeUpdated.getEmployeeEmail());
		employeeFinalStateEntity.setBranchCode(employeeUpdated.getBranchCode());

		String roleJob = employeeUpdated.getRoleJob().getCode() + employeeUpdated.getRoleJob().getName();
		employeeFinalStateEntity.setRoleJob(roleJob);

		employeeFinalStateEntity.setDirectBoss(
				employeeUpdated.getDirectBoss() != null ? employeeUpdated.getDirectBoss().getEmployeeEmail() : "EMPTY");

		StringBuilder employees = new StringBuilder();
		if (employeeUpdated.getEmployees() != null) {
			for (EmployeeDto employee : employeeUpdated.getEmployees()) {
				if (employees.length() > 0) {
					employees.append(", ");
				}
				employees.append(employee.getEmployeeEmail());
			}
		}
		String employeeEmails = employees.toString();
		employeeFinalStateEntity.setEmployeeList(employeeEmails);

		return employeeFinalStateEntity;
	}

	public UserConfigurationFinalStateEntity userConfigurationFinalToEntity(UserConfigurationDto userConfiguration,
			Long employeeFinalId) {

		UserConfigurationFinalStateEntity userConfigurationFinalStateEntity = new UserConfigurationFinalStateEntity();

		userConfigurationFinalStateEntity.setEmployeeFinalId(employeeFinalId);
		userConfigurationFinalStateEntity.setPercentageAuthorized(userConfiguration.getPercentageAuthorized());
		userConfigurationFinalStateEntity.setAuthorizationCode(userConfiguration.getAuthorizationCode());
		userConfigurationFinalStateEntity.setCreationDate(userConfiguration.getCreationDate());
		userConfigurationFinalStateEntity.setUpdatedDate(userConfiguration.getUpdatedDate());
		userConfigurationFinalStateEntity.setModifyBy(userConfiguration.getModifyBy());

		StringBuilder salesTypes = new StringBuilder();
		if (userConfiguration.getSaleTypes() != null) {
			for (SaleTypeDto saleType : userConfiguration.getSaleTypes()) {
				if (salesTypes.length() > 0)
					salesTypes.append(", ");
				salesTypes.append(saleType.getCode() + saleType.getType());
			}
		}
		String salesTypeList = salesTypes.toString();
		userConfigurationFinalStateEntity.setSaleTypes(salesTypeList);

		StringBuilder creditNotes = new StringBuilder();
		if (userConfiguration.getCreditNoteTypes() != null) {
			for (CreditNoteTypeDto creditNoteType : userConfiguration.getCreditNoteTypes()) {
				if (creditNotes.length() > 0)
					creditNotes.append(", ");
				creditNotes.append(creditNoteType.getCode() + creditNoteType.getType());
			}
		}
		String creditNoteList = creditNotes.toString();
		userConfigurationFinalStateEntity.setCreditNoteTypes(creditNoteList);

		StringBuilder priceTypes = new StringBuilder();
		if (userConfiguration.getPriceTypes() != null) {
			for (PriceTypeDto priceType : userConfiguration.getPriceTypes()) {
				if (priceTypes.length() > 0)
					priceTypes.append(", ");
				creditNotes.append(priceType.getCode() + priceType.getDescription());
			}
		}
		String priceTypeList = creditNotes.toString();
		userConfigurationFinalStateEntity.setPriceTypes(priceTypeList);

		StringBuilder warehouseOptions = new StringBuilder();
		if (userConfiguration.getWarehouseOptions() != null) {
			for (WarehouseOptionsDto warehouseOption : userConfiguration.getWarehouseOptions()) {
				if (warehouseOptions.length() > 0)
					warehouseOptions.append(", ");
				warehouseOptions.append(warehouseOption.getWarehouseCode());
			}
		}
		String warehouseOptionList = warehouseOptions.toString();
		userConfigurationFinalStateEntity.setWarehouseOptions(warehouseOptionList);

		return userConfigurationFinalStateEntity;
	}

}
