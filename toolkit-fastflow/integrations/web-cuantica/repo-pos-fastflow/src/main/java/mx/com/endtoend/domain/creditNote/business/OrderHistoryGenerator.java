package mx.com.endtoend.domain.creditNote.business;

import java.math.BigDecimal;
import java.util.Date;

import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;

/**
 * Clase para la generación de los datos operativos del historico de cambios en
 * las ordenes del sistema
 * 
 * @author ddcasas
 *
 */
public class OrderHistoryGenerator {

	public OrderHistoryDto generateOrderHistory(OrderDto orderDto, String acction, EmployeeDto employeeDto) {
		String branchCode = orderDto.getBranchCode();
		String orderType = orderDto.getOrderCode();
		BigDecimal orderNumber = orderDto.getOrderNumber();
		Date modificationDate = new Date();
		String action = acction;
		String username = employeeDto.getEmployeeEmail();
		Long userNumber = employeeDto.getUserNumber();
		String params = "";
		boolean isRetentionOrder = orderDto.getIsRetentionOrder();

		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(branchCode, orderType, orderNumber, modificationDate,
				action, username, userNumber, params, isRetentionOrder);

		return orderHistoryDto;
	}

}
