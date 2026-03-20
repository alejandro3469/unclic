package mx.com.endtoend.domain.orders.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.commons.constants.ActionOrder;
import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;

/**
 * Clase para la configuración de datos en el cuerpo de las ordenes del sistema
 * 
 * @author ddcasas
 *
 */

public class OrderUtilityService {

	private final Logger LOG = LoggerFactory.getLogger(OrderUtilityService.class);

	/**
	 * Método para la asignación de los datos operacionales de las ordenes del
	 * sistema
	 * 
	 * @param orderDto
	 * @param employeeDto
	 * @param orderConfigurationDto
	 * @param idOperation
	 * @return
	 */
	public OrderDto setOrderConfigurationOnCreate(OrderDto orderDto, EmployeeDto employeeDto,
			OrderConfigurationDto orderConfigurationDto, String idOperation) {

		LOG.info("{} INIT setOrderConfigurationOnCreate() ", idOperation);
		LOG.info("{} PARAMS: [ orderDto: {} , employeeDto: {} , orderConfigurationDto: {} ] ", idOperation,
				orderDto.toString(), employeeDto.toString(), orderConfigurationDto.toString());

		String lineCodeOne = orderConfigurationDto.getLineCodeOne();
		String lineCodeTwo = orderConfigurationDto.getLineCodeTwo();
		String retentionCode = orderConfigurationDto.getRetentionCode();

		LOG.info("{}START EVALUATION BY LINE", idOperation);
		for (OrderDetailDto orderDetailDto : orderDto.getOrderDetail()) {

			// ASIGNA CODIGOS DE LINEA Y SE ASOCIAN CON NUMERO DE VENDEDOR
			orderDetailDto.setUerNumberSeller(employeeDto.getUserNumber());
			orderDetailDto.setLineCodeOne(lineCodeOne);
			orderDetailDto.setLineCodeTwo(lineCodeTwo);

			// VALIDA SI SE HIZO UN DESCUENTO AL PRECIO UNITARIO DEL ARTICULO
			if (orderDetailDto.getDiscountSeller().compareTo(BigDecimal.ZERO) > 0 && (employeeDto.getUserConfiguration()
					.getPercentageAuthorized().compareTo(orderDetailDto.getDiscountSeller()) < 0)) {

				LOG.warn("{} ORDER RETENTION DUE TO DISCOUNT NOT ALLOWED", idOperation);
				orderDto.setIsRetentionOrder(true);
				orderDto.setRetentionCode(retentionCode);
				orderDetailDto.setIsRetentionArticle(true);
				orderDetailDto.setRetentionCode(retentionCode);
			}
		}

		LOG.info("{} SET ORDER TYPE", idOperation);
		orderDto.setOrderType(orderConfigurationDto.getSaleType().getType());

		LOG.info("{} RETUEN ORDER", idOperation);
		return orderDto;
	}

	public OrderDto setOperativeDataOnCreate(OrderDto orderDto, CompanyDto companyDto, EmployeeDto employeeDto,
			StatusDto statusDto, BigDecimal orderNumber) {

		orderDto.setOrderNumber(orderNumber);
		orderDto.setCompanyNumber(companyDto.getCompanyNumber());
		orderDto.setIdUser(employeeDto.getUserId());
		orderDto.setStatus(statusDto);
		orderDto.setIsUpdated(false);
		orderDto.setUserNumber(employeeDto.getUserNumber());
		orderDto.setCreationDate(new Date());
		orderDto.setTempMigStatus("1");
		orderDto.setValidityDate(getValidityDate());

		return orderDto;
	}

	public Date getValidityDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 30);
		return calendar.getTime();
	}

	/**
	 * Método para el envío de las ordenes del sistema por correo electrónico
	 * 
	 * @param orderPersistencePort
	 * @param emailServicePort
	 * @param orderDto
	 * @param orderNumber
	 * @param companyCode
	 * @param mailUsuario
	 * @param idOperation
	 * @return
	 */
	public String sendOrderDocument(OrderPersistencePort orderPersistencePort, EmailServicePort emailServicePort,
			OrderDto orderDto, BigDecimal orderNumber, String companyCode, boolean mailUsuario, String idOperation) {

		try {

			LOG.info(String.format("%s INIT sendOrderDocument() ", idOperation));
			LOG.info(String.format("%s PARAMS: [ orderDto: %s , companyCode: %s ] ", idOperation, orderDto.toString(),
					companyCode));

			File reportToSend = null;
			boolean getOrderCreated = true;
			boolean generatePdf = true;
			boolean sendDocument = true;
			String incompleteFunctions = "";

			LOG.info(String.format("%s GET ORDER CREATED", idOperation));
			OrderDto orderDtoToSend = (OrderDto) orderPersistencePort
					.vieworderDetailByOrderNumber(orderNumber, orderDto.getOrderCode(), companyCode, idOperation)
					.getData();

			if (orderDtoToSend == null) {

				LOG.warn(String.format("%s ERROR IN SERCH OREDR CERATED %s ", idOperation, orderNumber.toString()));
				getOrderCreated = false;
				generatePdf = false;
			}

			BranchDto branchDto = (BranchDto) orderPersistencePort.getBranchConfigurationByBranchCodeAndCompanyCode(
					orderDto.getBranchCode(), companyCode, idOperation).getData();

			if (branchDto == null) {
				LOG.warn(
						String.format("%s ERROR IN SERCH BRANCH INFORMATION %s ", idOperation, orderNumber.toString()));
				getOrderCreated = false;
				generatePdf = false;
			}

			UserDto userDto = (UserDto) orderPersistencePort
					.getPersonalUserConfigurationByUserIdAndCompanyCode(orderDto.getIdUser(), companyCode, idOperation)
					.getData();

			if (userDto == null) {
				LOG.warn(String.format("%s ERROR IN SERCH PERSONAL USER CONFIGURATION %s ", idOperation,
						orderNumber.toString()));
				getOrderCreated = false;
				generatePdf = false;
			}

			LOG.info(String.format("%s GENERATE REPORT", idOperation));
			if (getOrderCreated) {

				ResponseModel documentByteArray = orderPersistencePort.generateDocument(orderDtoToSend, branchDto,
						userDto, companyCode, orderDtoToSend.getOrderCode(), idOperation);

				if (documentByteArray.getData() != null) {

					reportToSend = new File(
							orderDto.getOrderType() + "-" + orderDto.getOrderNumber().longValue() + ".pdf");

					FileUtils.writeByteArrayToFile(reportToSend, (byte[]) documentByteArray.getData());

				} else {

					generatePdf = false;
				}
			}

			LOG.info(String.format("%s SEND DOCUMENT", idOperation));

			if (generatePdf) {

				List<String> emailList = new ArrayList<String>();

				if (orderDto.getEmails() != null) {
					emailList.addAll(orderDto.getEmails());
				}

				if (mailUsuario) {
					emailList.add(orderDtoToSend.getEmployeeEmail());
				}

				sendDocument = (boolean) emailServicePort
						.sendDocumentByCompanyCode(emailList, reportToSend, companyCode, idOperation).getData();
			}

			if (!generatePdf || !sendDocument) {

				LOG.warn(String.format("%s FINAL PROCESS SEND DOCUMENT EMAIL OR DOCUMENT ORDER WAS NOT GENERATED",
						idOperation));

				incompleteFunctions = (sendDocument ? "" : " - EMAIL");
				incompleteFunctions = incompleteFunctions + (generatePdf ? "" : " - DOCUMENT ");

			}

			reportToSend.delete();

			return incompleteFunctions;

		} catch (IOException e) {
			LOG.error(String.format("%s ERROR GENERATED DOCUMENT. IOException: %s ", idOperation, e.getMessage()));
			return " DOCUMENT ";
		}
	}

	/**
	 * Método que actualiza el estado de la orden del sistema local con la
	 * información del estado de la orden del sistema POS-Legacy
	 * 
	 * 
	 * @return
	 */
	public OrderDto updateStatusFromExternalSource(OrderPersistencePort orderPersistencePort, OrderDto localOrder,
			OrderDto externalOrder, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT EVALUATION STATUS", idOperation));
		OrderDto orderUpdated = localOrder;

		if (!externalOrder.getStatus().getCode().equals(localOrder.getStatus().getCode())) {

			LOG.info(String.format("%s UPDATE IN PRINCIPAL DB", idOperation));
			orderUpdated = (OrderDto) orderPersistencePort.updateStatusOrderByOrderNumberAndOrderCodeAndCompanyCode(
					localOrder.getOrderNumber(), localOrder.getOrderCode(), externalOrder.getStatus(),
					externalOrder.getPendingPayment(), companyCode, idOperation).getData();

			if (orderUpdated != null) {

				OrderHistoryDto orderHistoryDto = new OrderHistoryDto(branchCode, orderUpdated.getOrderCode(),
						orderUpdated.getOrderNumber(), new Date(), ActionOrder.UPDATE_PYMENT.toString(),
						orderUpdated.getEmployeeEmail(), orderUpdated.getUserNumber(), " ",
						orderUpdated.getIsRetentionOrder());
				orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

			} else {
				orderUpdated = localOrder;
			}
		}
		return orderUpdated;
	}
}
