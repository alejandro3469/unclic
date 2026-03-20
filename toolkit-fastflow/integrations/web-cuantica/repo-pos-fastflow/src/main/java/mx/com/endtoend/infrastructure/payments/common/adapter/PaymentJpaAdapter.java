package mx.com.endtoend.infrastructure.payments.common.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.payments.common.factory.PaymentRepositorFactory;
import mx.com.endtoend.infrastructure.payments.common.persistence.GenericPaymentPersistenceInterface;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaidOrderSummaryDto;
import mx.com.endtoend.domain.payments.dto.ticket.PaymentTicketDto;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

/**
 * Clase de servicio para la recuperación de los repositorios de las compañias
 * registradas en el sistema
 *
 * @author ddcasas
 *
 */
public class PaymentJpaAdapter implements PaymentPersistencePort {

	@Autowired
	private PaymentRepositorFactory paymentRepositorFactory;

	private final Logger LOG = LoggerFactory.getLogger(PaymentJpaAdapter.class);

	/**
	 * Metodo que se encarga de obtener la implementación concreta del repositorio a
	 * emplear para la recuperación de las órdenes del sistema con base ene l código
	 * de compañia
	 *
	 * @param orderNumber número de orden de venta
	 * @param orderCode   código de orden de venta
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 *
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 * operación y los datos solicitados
	 */

	@Override
    public ResponseModel getOrdersToSendToQueue(BigDecimal orderNumber, String orderCode, String companyCode,
												String idOperation) {
        LOG.info(String.format("INIT getOrdersToSendToQueue()"));

        // Retrieve the repository for the specific company
        GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
        if (paymentRepository == null) {
            LOG.error("AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION FOR companyCode: {}", companyCode);
            throw new GlobalError();
        }

        try {

			List<PaymentOrderJDE> paymentOrderJDEList = new ArrayList<PaymentOrderJDE>();
            // Fetch the list of orders
            List<OrderDto> orderDtos = paymentRepository.getOrdersToSendToQueue();

            // Iterate over the list of OrderDto objects
            PaymentOrderJDE paymentOrderJDE = null;
            for (OrderDto orderDto : orderDtos) {
                // Perform your desired operations on each orderDto
                // For example, you can log the order details
                LOG.info(String.format("Processing order - OrderNumber: %s, OrderCode: %s",
                        orderDto.getOrderNumber(), orderDto.getOrderCode()));

                PaymentDto paymentDto = paymentRepository.findByOrderNumberAndOrderCode(orderDto.getOrderNumber(), orderDto.getOrderCode(), companyCode, idOperation);

                paymentOrderJDE = new PaymentOrderJDE(orderDto, paymentDto);

				paymentOrderJDEList.add(paymentOrderJDE);

            }

            // Return the list wrapped in a ResponseModel
            return new ResponseModel(paymentOrderJDEList);

        } catch (Exception e) {
            LOG.error("ERROR IN getOrdersToSendToQueue(). EXCEPTION: {}", e.getMessage());
            throw new GlobalError();
		}
    }

	@Override
	public List<PaymentOrderJDE> getOrdersToSendToQueueV2(BigDecimal orderNumber, String orderCode, String companyCode,
														  String idOperation) {

        LOG.info(String.format("INIT getOrdersToSendToQueue()"));

        // Retrieve the repository for the specific company
        GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
        if (paymentRepository == null) {
            LOG.error("AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION FOR companyCode: {}", companyCode);
            throw new GlobalError();
        }


        try {
            List<PaymentOrderJDE> paymentOrderJDEList = new ArrayList<>();

            // Fetch the list of orders
            List<OrderDto> orderDtos = paymentRepository.getOrdersToSendToQueue();

            // Iterate over the list of OrderDto objects
            PaymentOrderJDE paymentOrderJDE = null;
            for (OrderDto orderDto : orderDtos) {
                // Perform your desired operations on each orderDto
                // For example, you can log the order details
                LOG.info(String.format("Processing order - OrderNumber: %s, OrderCode: %s",
                        orderDto.getOrderNumber(), orderDto.getOrderCode()));

                PaymentDto paymentDto = paymentRepository.findByOrderNumberAndOrderCode(orderDto.getOrderNumber(), orderDto.getOrderCode(), companyCode, idOperation);

                paymentOrderJDE = new PaymentOrderJDE(orderDto, paymentDto);

				paymentOrderJDEList.add(paymentOrderJDE);


            }

            // Return the list wrapped in a ResponseModel
            return paymentOrderJDEList;

        } catch (Exception e) {
            LOG.error("ERROR IN getOrdersToSendToQueue(). EXCEPTION: {}", e.getMessage());
            throw new GlobalError();
		}
    }

	@Override
	public ResponseModel getOrderByCompanyCodeAndParams(BigDecimal orderNumber, String orderCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getOrderByCompanyCodeAndParams()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderDto = paymentRepository.getOrderByParams(orderNumber, orderCode, companyCode, idOperation);
		return new ResponseModel(orderDto);

	}

	/**
	 * Método que se encarga de obtener la implemetación concreta del repositorio a
	 * emplear para la recuperación de los datos operativos de los empleados
	 * 
	 * @param email       correo electrónico del empleado logeado
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel getEmployeConfigurationByEmailAndCompanyCode(String email, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getEmployeConfigurationByEmailAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmployeeDto employeeDto = paymentRepository.getEmployeConfigurationByEmailAndCompanyCode(email, companyCode,
				idOperation);

		return new ResponseModel(employeeDto);
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para el alta de
	 * cobros del sistema
	 * 
	 * @param paymentDto  datos operativos del cobro
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel createPayment(PaymentDto paymentDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createPayment()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		PaymentDto payment = paymentRepository.createPayment(paymentDto, idOperation);
		return new ResponseModel(payment);
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la
	 * actualización del estado de la órdenes del sistema despues del cobro
	 * 
	 * @param orderNumber    número de ordene del sistema
	 * @param orderCode      código de orden
	 * @param statusCode     código de estado de orden
	 * @param pendingPayment saldo pendiente por pagar
	 * @param batchFolio     folio generado por JDE
	 * @param companyCode    código de compañía
	 * @param idOperation    identificador de traza de operación
	 */
	@Override
	public void updateOrderStatusByOrderNumberAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String statusCode, BigDecimal pendingPayment, Long batchFolio, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateOrderStatusByOrderNumberAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		paymentRepository.updateOrderStatusByOrderNumberAndCompanyCode(orderNumber, orderCode, statusCode, pendingPayment, batchFolio,
				idOperation);

	}

	/**
	 * Método que se encarga de obtener la implementacion concreta para el registro
	 * del historial de acciones de las órdenes del sistema
	 * 
	 * @param orderHistoryDto datos operativos de las acciones realizadas en la
	 *                        orden
	 * @param companyCode     código de compañia
	 * @param idOperation     identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@Override
	public ResponseModel saveRecordInOrderHistory(OrderHistoryDto orderHistoryDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT saveRecordInOrderHistory()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OrderHistoryDto orderHistory = paymentRepository.saveRecordInOrderHistory(orderHistoryDto, idOperation);
		return new ResponseModel(orderHistory);

	}

	@Override
	public ResponseModel getOpeningOperationByEmailAndCompanyCode(String email, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getOpeningOperationByEmailAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpeningOperationDto openingOperation = paymentRepository.getOpeningOperationByEmail(email, idOperation);
		return new ResponseModel(openingOperation);
	}

	@Override
	public ResponseModel updateFlagByCompanyCodeAndParams(BigDecimal orderNumber, String orderCode, boolean enable,
			String idOperation, String companyCode) {
		LOG.info(String.format("%s INIT updateFlagByCompanyCodeAndParams()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		boolean updateStatus = paymentRepository.updateFlagByParams(orderNumber, orderCode, enable, idOperation);
		return new ResponseModel(updateStatus);
	}

	@Override
	public ResponseModel orderHasPayment(BigDecimal orderNumber, String orderCode, String idOperation, String companyCode) {
		LOG.info(String.format("%s INIT updateFlagByCompanyCodeAndParams()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean updateStatus;
		if(companyCode.equals("FCAL")) {
			updateStatus = paymentRepository.orderHasPayment(orderNumber, orderCode);
		} else {
			updateStatus = false;
		}

		return new ResponseModel(updateStatus);
	}

	@Override
	public ResponseModel getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(BigDecimal orderNumber,
			String orderCode, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		PaymentDto payment = paymentRepository.getPaymentDetailByOrderNumberAndOrderCode(orderNumber, orderCode,
				idOperation);
		return new ResponseModel(payment);
	}

	@Override
	public ResponseModel getBranchDetailByBranchCodeAndCompanyCode(String branchCode, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getBranchDetailByBranchCodeAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BranchDto branch = paymentRepository.getBranchDetailByBranchCodeAndCompanyCode(branchCode, companyCode,
				idOperation);
		return new ResponseModel(branch);
	}

	@Override
	public ResponseModel getUserInformationByEmailAndCompanyCode(String email, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getUserInformationByEmailAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		UserDto user = paymentRepository.getUserInformationByEmailAndCompanyCode(email, companyCode, idOperation);
		return new ResponseModel(user);
	}

	@Override
	public ResponseModel getSellerInformationByEmailAndCompanyCode(Long userNumber, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getUserInformationByEmailAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		UserDto user = paymentRepository.getSellerInformationByEmailAndCompanyCode(userNumber, companyCode, idOperation);
		return new ResponseModel(user);
	}

	@Override
	public ResponseModel getPaymentTicketByCompanyCode(PaymentTicketDto paymentTicketDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getPaymentTicketByCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel resposneTicket = paymentRepository.getPaymentTicket(paymentTicketDto, idOperation);
		return resposneTicket;
	}

	@Override
	public ResponseModel searchPaidOrderSummaryByCompanyCode(GenericSearchPaymentDto genericSearchPaymentDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchPaidOrderSummaryByCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<PaidOrderSummaryDto> paidOrderSummaryList = paymentRepository
				.searchPaidOrderSummaryByCompanyCode(genericSearchPaymentDto, idOperation);
		return new ResponseModel(paidOrderSummaryList);
	}

	@Override
	public ResponseModel saveInvoiceReferenceByCompanyCode(InvoiceReferenceDto invoiceReference, PaymentDto paymentDto,
			String branchCode, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT saveInvoiceReferenceByCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		boolean isSaved = paymentRepository.saveInvoiceReference(invoiceReference, paymentDto, branchCode, idOperation);

		return new ResponseModel(isSaved);
	}

	@Override
	public ResponseModel getInvoiceReferenceByPaymentIdAndCompanyCode(Long paymentId, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getInvoiceReferenceByPaymentIdAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		InvoiceReferenceDto invoiceReference = paymentRepository.getInvoiceReferenceByPaymentIdAndCompanyCode(paymentId,
				idOperation);

		return new ResponseModel(invoiceReference);
	}

	@Override
	public void updatePrintStateByPaymentIdAndCompanyCode(Long paymentId, boolean printStatus, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT updatePrintStateByPaymentIdAndCompanyCode()", idOperation));
		GenericPaymentPersistenceInterface paymentRepository = paymentRepositorFactory.getRepository(companyCode);
		if (paymentRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		paymentRepository.updatePrintStateByPaymentId(paymentId, printStatus, idOperation);
	}

	@Override
	public Object orderHasPayment(BigDecimal orderNumber, String orderCode) {
		return null;
	}

}
