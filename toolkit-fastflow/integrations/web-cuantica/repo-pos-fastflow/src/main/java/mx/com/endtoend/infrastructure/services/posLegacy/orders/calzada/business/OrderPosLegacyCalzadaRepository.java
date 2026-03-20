package mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.business;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import mx.com.endtoend.smart.bussiness.model.orders.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.StatusRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;
import mx.com.endtoend.infrastructure.services.jde.orders.common.service.JdeOrderService;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.GenericOrderPosLegacyRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.converters.OrderConverterMig;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DetalleSolTras;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DirEnvioClienteOrdenes;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.EncabezadoSolTraspaso;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrden;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenBkup;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrden;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenBkup;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.Usuarios;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.DetalleSolTrasRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.DirEnvioClienteOrdenesRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.EncabezadoSolTraspasoRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.TblDetalleOrdenBkupRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.TblDetalleOrdenRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.TblEncabezadoOrdenBkupRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.TblEncabezadoOrdenRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.UsuariosRepository;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;

/**
 * Implementación concreta de la interfaz GenericOrderPosLegacyRepository para
 * el cliente Calzada.
 * 
 * @author ddcasas
 *
 */
@ConditionalOnProperty(name = "app.calzadaSQL.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class OrderPosLegacyCalzadaRepository implements GenericOrderPosLegacyRepository {

	@Autowired(required = false)
	private TblEncabezadoOrdenRepository tblEncabezadoOrdenRepository;

	@Autowired(required = false)
	private TblEncabezadoOrdenBkupRepository tblEncabezadoOrdenBkupRepository;

	@Autowired(required = false)
	private TblDetalleOrdenRepository tblDetalleOrdenRepository;

	@Autowired(required = false)
	private TblDetalleOrdenBkupRepository tblDetalleOrdenBkupRepository;

	@Autowired(required = false)
	private DirEnvioClienteOrdenesRepository dirEnvioClienteOrdenesRepository;

	@Autowired
	private OrderConverterMig orderConverterMig;

	@Autowired
	private StatusConverter statusConverter;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired(required = false)
	private EncabezadoSolTraspasoRepository encabezadoSolTraspasoRepository;

	@Autowired(required = false)
	private DetalleSolTrasRepository detalleSolTrasRepository;

	@Autowired(required = false)
	private UsuariosRepository usuariosRepository;

	@Autowired
	private JdeOrderService jdeOrderService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${app.calzada.queue.oreder.name}")
	private String queueCreateOrder;

	@Value("${app.calzada.queue.oreder.direct.name}")
	private String queueCreateOrderDirect;

	@Value("${app.calzada.queue.approve.oreder.name}")
	private String queueApproveOrder;

	@Value("${app.calzada.queue.cancel.oreder.name}")
	private String queueCancelOrder;

	@Value("${app.calzada.queue.status.oreder.name}")
	private String queueUpdateStatus;

	private final Logger LOG = LoggerFactory.getLogger(OrderPosLegacyCalzadaRepository.class);

	private String companyKey = "00001";

	String usernamePL = "";

	/**
	 * Método asíncrono para la creación de ordenes del sistema en la BD SQL Server
	 * 
	 * @param orderDto    modelo de datos de las ordenes del sistema
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Async
	@Transactional
	@Override
	public void createOrder(String companyCode, OrderDto orderDto, String idOperation) {

		try {

			LOG.info(String.format("%s INIT createOrder()", idOperation));
			LOG.info(String.format("%s PARAMS: [orderDto: %s ]", idOperation, orderDto.toString()));

			if (orderDto.getClient().getId() == 1) {

				LOG.info(String.format("%s IS GENERIC CLIENT, FIND AND SET GENERIC CLIENT NUMBER", idOperation));

				BigDecimal genericClientNumber = (BigDecimal) jdeOrderService
						.getGenericClientNumberByCompanyCodeAndWarehouseCode(companyCode, orderDto.getCompanyNumber(),
								orderDto.getBranchCode(), idOperation)
						.getData();

				orderDto.getClient().setNoClient(genericClientNumber.longValue());
			}

			LOG.info(String.format("%s FIND USERNAME IN POSLEGACY: %s", idOperation,
					orderDto.getUserNumber().toString()));
			Optional<Usuarios> usuarioOptional = usuariosRepository.findByAn8(orderDto.getUserNumber());

			if (usuarioOptional.isPresent()) {

				LOG.info(String.format("%s USER EXISTS BY USERNUMBER, SET USERNAME-POSLEGACY", idOperation));
				usernamePL = usuarioOptional.get().getIdUsuario();

			} else {

				LOG.warn(String.format("%s USER NOT EXISTS BY USERNUMBER, SET EMPOOYEE EMAIL", idOperation));
				usernamePL = orderDto.getEmployeeEmail();
			}

			LOG.info(String.format("%s CONVERT AND SAVE ORDER-HEADER", idOperation));
			TblEncabezadoOrden tblEncabezadoOrden = orderConverterMig.orderDtoToTblEncabezadoOrden(orderDto,
					usernamePL);
			tblEncabezadoOrden = tblEncabezadoOrdenRepository.save(tblEncabezadoOrden);

			LOG.info(String.format("%s CONVERT AND SAVE ORDER-DETAIL, ITEMS: %d ", idOperation,
					orderDto.getOrderDetail().size()));

			for (OrderDetailDto orderDetailDto : orderDto.getOrderDetail()) {

				LOG.info(String.format("%s LINE %d", idOperation, orderDetailDto.getLineNumber()));

				TblDetalleOrden tblDetalleOrden = orderConverterMig.orderDetailDtoToTblDetalleOrden(orderDto,
						orderDetailDto, usernamePL);

				tblDetalleOrden = tblDetalleOrdenRepository.save(tblDetalleOrden);

			}

			LOG.info(String.format("%s CONVERT AND SAVE SHIPPING-ADDRESS CLIENT", idOperation));

			for (AddressDto addressDto : orderDto.getAddresses()) {

				if (addressDto.getAddressType().contains("Env")) {

					DirEnvioClienteOrdenes dirEnvioClienteOrdenes = orderConverterMig
							.addressDtoToDirEnvioClienteOrdenes(orderDto, addressDto, usernamePL);

					dirEnvioClienteOrdenes = dirEnvioClienteOrdenesRepository.save(dirEnvioClienteOrdenes);

				}

			}

			LOG.info(String.format("%s CONVERT AND SAVE ORDER-HEADER BACKUP", idOperation));
			TblEncabezadoOrdenBkup tblEncabezadoOrdenBkup = orderConverterMig.orderDtoToTblEncabezadoOrdenBkup(orderDto,
					usernamePL);
			tblEncabezadoOrdenBkup = tblEncabezadoOrdenBkupRepository.save(tblEncabezadoOrdenBkup);

			LOG.info(String.format("%s CONVERT AND SAVE ORDER-DETAIL BACKUP, ITEMS: %d ", idOperation,
					orderDto.getOrderDetail().size()));

			for (OrderDetailDto orderDetailDto : orderDto.getOrderDetail()) {

				LOG.info(String.format("%s LINE %d", idOperation, orderDetailDto.getLineNumber()));

				TblDetalleOrdenBkup tblDetalleOrdenBkup = orderConverterMig
						.orderDetailDtoToTblDetalleOrdenBkup(orderDto, orderDetailDto, usernamePL);

				tblDetalleOrdenBkup = tblDetalleOrdenBkupRepository.save(tblDetalleOrdenBkup);

			}

			LOG.info(String.format("%s SAVE ORDER COMPLETE", idOperation));

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN createOrder(). EXCEPTION: %s", idOperation, e.getMessage()));

		}

	}

	/**
	 * Método para la actualización de ordenes del sistema en la BD SQL Server
	 * 
	 * @param orderDto    modelo de datos de las ordenes del sistema
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Async
	@Transactional
	@Override
	public void updateOrder(String companyCode, OrderDto orderDto, String idOperation) {

		try {

			LOG.info(String.format("%s INIT updateOrder()", idOperation));
			LOG.info(String.format("%s PARAMS: [orderDto: %s ]", idOperation, orderDto.toString()));

			LOG.info(String.format("%s DELTE FROM tblEncabezadoOrden", idOperation));
			tblEncabezadoOrdenRepository.deleteToUpdate(orderDto.getOrderNumber(), orderDto.getOrderCode());

			LOG.info(String.format("%s DELTE FROM tblDetalleOrden", idOperation));
			tblDetalleOrdenRepository.deleteToUpdate(orderDto.getOrderNumber(), orderDto.getOrderCode());

			LOG.info(String.format("%s DELTE FROM dirEnvioClienteOrdenes", idOperation));
			dirEnvioClienteOrdenesRepository.deleteToUpdate(orderDto.getOrderNumber(), orderDto.getOrderCode());

			LOG.info(String.format("%s DELTE FROM tblEncabezadoOrdenBkup", idOperation));
			tblEncabezadoOrdenBkupRepository.deleteToUpdate(orderDto.getOrderNumber(), orderDto.getOrderCode());

			LOG.info(String.format("%s DELTE FROM tblDetalleOrdenBkup", idOperation));
			tblDetalleOrdenBkupRepository.deleteToUpdate(orderDto.getOrderNumber(), orderDto.getOrderCode());

			LOG.info(String.format("%s CREATE UPDATED ORDER", idOperation));
			createOrder(companyCode, orderDto, idOperation);

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN updateOrder(). EXCEPTION: %s", idOperation, e.getMessage()));

		}
	}

	/**
	 * Método para actualizar el estado de las ordenes del sistema en la BD de SQL
	 * Server
	 * 
	 * @param orderNumber número de identificador de orden
	 * @param orderType   tipo de orden del sistema
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Transactional
	@Override
	public void updateStatusOrderActive(BigDecimal orderNumber, boolean status, String orderType, String idOperation) {
		LOG.info(String.format("%s INIT updateStatusOrderActive()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderType: %s ]", idOperation, orderNumber.toString(),
				orderType));
		try {
			int afectedRowsSecondaryDB = tblEncabezadoOrdenRepository
					.updateStatusOrderActiveByDocoAndDctoAndKoooo(status, orderNumber, orderType, companyKey);
			if (afectedRowsSecondaryDB > 0) {
				LOG.info(String.format("%s ORDER UPDATE OK", idOperation));
			} else {
				LOG.info(String.format("%s ERROR IN UPDATE ORDER STATUS", idOperation));
			}
		} catch (Exception e) {
			LOG.error(
					String.format("%s ERROR IN updateStatusOrderActive(). EXCEPTION: %s", idOperation, e.getMessage()));
		}
	}

	/**
	 * Método para cambio de estado de orden a cancelación en la BD SQL Server
	 * 
	 * @param statusOrder código de estado de la orden
	 * @param orderNumber número de identificador de orden
	 * @param orderType   tipo de orden del sistema
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Transactional
	@Override
	public void cancelOrder(BigDecimal orderNumber, String orderType, String idOperation) {
		try {
			LOG.info(String.format("%s INIT cancelOrder()", idOperation));
			LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderType: %s ]", idOperation, orderNumber.toString(),
					orderType));
			int cancelStatusCode = Integer.parseInt(StatusOrder.MANUAL_CANCELLATION.getValue());
			int cancelOk = tblEncabezadoOrdenRepository.cancelOrderByDocoAndDctoAndKoooo(cancelStatusCode, orderNumber,
					orderType, companyKey);
			if (cancelOk > 0) {
				LOG.info(String.format("%s CANCEL ORDER IN SECONDARY DB OK", idOperation));
			} else {
				LOG.info(String.format("%s ERROR CANCEL ORDER", idOperation));
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN cancelOrder(). EXCEPTION: %s", idOperation, e.getMessage()));
		}
	}

	/**
	 * Método para cambio de estado de orden a aprobación en la BD SQL Server
	 * 
	 * @param statusOrder código de estado de la orden
	 * @param orderNumber número de identificador de orden
	 * @param orderType   tipo de orden del sistema
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Transactional
	@Override
	public void approveOrder(String statusOrder, BigDecimal orderNumber, String orderType, String idOperation) {
		try {
			LOG.info(String.format("%s INIT approveOrder()", idOperation));
			LOG.info(String.format("%s PARAMS: [statusOrder: %s , orderNumber: %s , orderType: %s ]", idOperation,
					statusOrder, orderNumber.toString(), orderType));
			int approveOrder = tblEncabezadoOrdenRepository.approveOrderByDocoAndDctoAndKoooo(statusOrder, orderNumber,
					orderType, companyKey);
			if (approveOrder > 0) {
				LOG.info(String.format("%s UPDATED ORDER IN SECONDARY DB OK", idOperation));
			} else {
				LOG.info(String.format("%s ERROR UPDATED ORDER IN SECONDARY DB", idOperation));
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN approveOrder(). EXCEPTION: %s", idOperation, e.getMessage()));
		}
	}

	/**
	 * Método que recupera el estado actual de cobro de las ordenes del sistema
	 * desde la BD de SQL Server.
	 * 
	 * @param orderNumber número de identificador de orden
	 * @param orderType   tipo de orden del sistema
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return oderDto modelo de datos de las ordenes del sistema
	 */
	@Transactional
	@Override
	public OrderDto getOrderDetailByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType,
			String idOperation) {

		LOG.info(String.format("%s INIT getOrderDetailByOrderNumberAndOrderType()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderType: %s ]", idOperation, orderNumber.toString(),
				orderType));

		try {

			StatusDto statusDto = null;

			Optional<TblEncabezadoOrden> encabezadoOrden = tblEncabezadoOrdenRepository
					.findOrderByDocoAndDctoAndKoooo(orderNumber, orderType, companyKey);

			if (encabezadoOrden.isPresent()) {

				TblEncabezadoOrden tblEncabezadoOrden = encabezadoOrden.get();

				Optional<StatusEntity> status = statusRepository
						.findByCode(String.valueOf(tblEncabezadoOrden.getEstatus()));

				if (status.isPresent()) {
					LOG.info(String.format("%s STATUS CORRECT FOUND", idOperation));
					statusDto = statusConverter.statusEntityToStatusDto(status.get());
				}

				LOG.info(String.format("%s INIT CONVERT ORDER TO DTO", idOperation));
				LOG.info(String.format("%s PARAMS: %s ", idOperation, tblEncabezadoOrden.toString()));
				OrderDto orderDto = orderConverterMig.TblEncabezadoOrdenToOrderDto(tblEncabezadoOrden);
				orderDto.setStatus(statusDto);

				LOG.info(String.format("%s RETURN ORDER HEDER", idOperation));
				return orderDto;

			} else {

				LOG.warn(String.format("%s ORDER: %s NOT FOUND IN SECONDARY DB", idOperation, orderNumber.toString()));
				return null;
			}

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN getOrderDetailByOrderNumberAndOrderType(). EXCEPTION: %s . CAUSE: %s ",
					idOperation, e.getMessage(), e.getCause()));

			return null;
		}

	}

	/**
	 * Método para la creación de ordenes directas del sistema, en la BD SQL Server
	 * 
	 * @param saleOrderDto modelo de datos de las ordenes directas del sistema
	 * @param idOperation  traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Transactional
	@Override
	public void createSaleOD(SaleOrderDto saleOrderDto, String idOperation) {

		try {

			LOG.info(String.format("%s INIT createSaleOD()", idOperation));
			LOG.info(String.format("%s PARAMS: [saleOrderDto: %s ]", idOperation, saleOrderDto.toString()));

			LOG.info(String.format("%s CONVERT AND SAVE ENCABEZADO", idOperation));

			LOG.info(String.format("%s FIND USERNAME IN POSLEGACY", idOperation));
			Optional<Usuarios> usuarioOptional = usuariosRepository.findByAn8(saleOrderDto.getUserNumber());

			if (usuarioOptional.isPresent()) {

				LOG.info(String.format("%s USER EXISTS BY USERNUMBER, SET USERNAME-POSLEGACY", idOperation));
				usernamePL = usuarioOptional.get().getIdUsuario();

			} else {

				LOG.warn(String.format("%s USER NOT EXISTS BY USERNUMBER, SET EMPOOYEE EMAIL", idOperation));
				usernamePL = saleOrderDto.getUsername();
			}

			EncabezadoSolTraspaso encabezadoSolTraspaso = orderConverterMig
					.saleOrderDtoToEncabezadoSolTraspaso(saleOrderDto, usernamePL);

			encabezadoSolTraspaso = encabezadoSolTraspasoRepository.save(encabezadoSolTraspaso);

			LOG.info(String.format("%s CONVERT AND SAVE DETALLE-ORDEN", idOperation));

			for (SaleOrderDetailDto saleOrderDetailDto : saleOrderDto.getSaleOrderDetail()) {

				LOG.info(String.format("%s LINE %d", idOperation, saleOrderDetailDto.getLineNumber()));

				DetalleSolTras detalleSolTras = orderConverterMig.saleOrderDetailDtoToDetalleSolTras(saleOrderDto,
						saleOrderDetailDto, usernamePL);

				detalleSolTras = detalleSolTrasRepository.save(detalleSolTras);

			}

			LOG.info(String.format("%s SAVE ORDER COMPLETE", idOperation));
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN createSaleOD(). EXCEPTION: %s", idOperation, e.getMessage()));
		}
	}

	/**
	 * Método para la creación de ordenes directas del sistema, en la BD SQL Server
	 * 
	 * @param saleOrderDto modelo de datos de las ordenes directas del sistema
	 * @param idOperation  traza de identificación de operación
	 * 
	 * @return valor boolean para indicar el resultado de la operación
	 */
	@Transactional
	@Override
	public void updateSaleOD(SaleOrderDto saleOrderDto, String idOperation) {

		try {

			LOG.info(String.format("%s INIT updateSaleOD()", idOperation));
			LOG.info(String.format("%s PARAMS: [saleOrderDto: %s ]", idOperation, saleOrderDto.toString()));

			LOG.info(String.format("%s DELTE FROM EncabezadoSolTraspaso", idOperation));
			encabezadoSolTraspasoRepository.deleteToUpdate(saleOrderDto.getOrderNumber(), saleOrderDto.getOrderCode(),
					companyKey);

			LOG.info(String.format("%s DELTE FROM DetalleSolTras", idOperation));
			detalleSolTrasRepository.deleteToUpdate(saleOrderDto.getOrderNumber(), saleOrderDto.getOrderCode(),
					companyKey);

			LOG.info(String.format("%s CREATE UPDATED ORDER", idOperation));
			createSaleOD(saleOrderDto, idOperation);

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateSaleOD(). EXCEPTION: %s", idOperation, e.getMessage()));
		}
	}

	/**
	 * Método que envía la orden a la cola de RabbitMQ para ser procesado por un
	 * agente externo para el almacenamiento o actualización de órdenes
	 */
	@Override
	public void sendOrderToSave(OrderDto orderDto) {
		try {
			rabbitTemplate.convertAndSend(queueCreateOrder, orderDto);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN updateSaleOD() : Exception: %s", e.getMessage()));
		}

	}

	/**
	 * Método que envía las Ordenes de compra Directa a la cola de RabbitMQ para ser
	 * procesado por un agente externo y almacenar su información
	 */
	@Override
	public void sendOrderODToSave(SaleOrderDto saleOrderDto) {
		try {
			rabbitTemplate.convertAndSend(queueCreateOrderDirect, saleOrderDto);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN updateSaleOD() : Exception: %s", e.getMessage()));
		}
	}

	/**
	 * Método que envía los datos de las órdenes a la cola de RabbitMQ para el
	 * cambio de estado de actividad de las órdenes
	 */
	@Override
	public void sendOrderToChangeStatus(BigDecimal orderNumber, boolean status, String orderType, String idOperation) {
		try {
			OrderPosLegacyParams orderPosLegacyParams = new OrderPosLegacyParams(orderNumber, status, orderType,
					companyKey, null);
			rabbitTemplate.convertAndSend(queueUpdateStatus, orderPosLegacyParams);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToChangeStatus() : Exception: %s", e.getMessage()));
		}

	}

	/**
	 * Método que envía los datos de las órdenes a la cola de RabbitMQ para la
	 * cancelación de las órdenes
	 */
	@Override
	public void sendOrderToCancelProcess(BigDecimal orderNumber, String orderType, String idOperation) {
		try {
			OrderPosLegacyParams orderPosLegacyParams = new OrderPosLegacyParams(orderNumber, false, orderType,
					companyKey, null);
			rabbitTemplate.convertAndSend(queueCancelOrder, orderPosLegacyParams);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToCancelProcess() : Exception: %s", e.getMessage()));
		}
	}

	/**
	 * Método que envía los datos de las órdenes a la cola de RabbitMQ para la
	 * cancelación de las órdenes
	 */
	@Override
	public void sendOrderToApproveProcess(String statusOrder, BigDecimal orderNumber, String orderType,
			String idOperation) {
		try {
			OrderPosLegacyParams orderPosLegacyParams = new OrderPosLegacyParams(orderNumber, false, orderType,
					companyKey, statusOrder);
			rabbitTemplate.convertAndSend(queueApproveOrder, orderPosLegacyParams);
		} catch (Exception e) {
			LOG.error(String.format("ERROR IN sendOrderToApproveProcess() : Exception: %s", e.getMessage()));
		}
	}

}
