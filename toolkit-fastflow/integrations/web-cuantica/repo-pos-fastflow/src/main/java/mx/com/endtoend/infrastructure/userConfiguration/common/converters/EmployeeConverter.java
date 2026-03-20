package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.EmployeeEntity;

@Component
public class EmployeeConverter {

	public EmployeeDto employeeEntityToEmployeeDto(EmployeeEntity employeeEntity) {

		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setId(employeeEntity.getId());
		employeeDto.setUserId(employeeEntity.getUserId());
        employeeDto.setEmployeeEmail(employeeEntity.getEmployeeEmail());
		employeeDto.setUserNumber(employeeEntity.getUserNumber());
		employeeDto.setBranchCode(employeeEntity.getBranchCode());

		return employeeDto;
	}

	public EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDto employeeDto) {

		EmployeeEntity employeeEntity = new EmployeeEntity();

		employeeEntity.setId(employeeDto.getId());
		employeeEntity.setUserId(employeeDto.getUserId());
        employeeEntity.setEmployeeEmail(employeeDto.getEmployeeEmail());
		employeeEntity.setUserNumber(employeeDto.getUserNumber());
		employeeEntity.setBranchCode(employeeDto.getBranchCode());

		return employeeEntity;
	}

	public List<EmployeeEntity> employeeDtoListToEmployeeEntityList(List<EmployeeDto> employeeDtoList) {
		List<EmployeeEntity> employeeEntityList = new ArrayList<EmployeeEntity>();

		for (EmployeeDto employeeDto : employeeDtoList) {
			employeeEntityList.add(employeeDtoToEmployeeEntity(employeeDto));
		}

		return employeeEntityList;
	}

	public List<EmployeeDto> employeeEntityListToEmployeeDtoList(List<EmployeeEntity> employeeEntityList) {
		List<EmployeeDto> employeeDtoList = new ArrayList<EmployeeDto>();
		for (EmployeeEntity employeeEntity : employeeEntityList) {
			employeeDtoList.add(employeeEntityToEmployeeDto(employeeEntity));
		}
		return employeeDtoList;
	}

}
