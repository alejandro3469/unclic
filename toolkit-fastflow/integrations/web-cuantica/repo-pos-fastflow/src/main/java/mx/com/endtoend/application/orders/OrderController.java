package mx.com.endtoend.application.orders;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.dto.GenericActionControllOrderDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.orders.ports.OrderV2ServicePort;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

/**
 * Controlador para la administración de las ordenes de ventas y cotizacioes del
 * sistema.
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/order")
public class OrderController extends BaseOrderController {

        @Autowired
        private final OrderV2ServicePort orderV2ServicePort;

        public OrderController(OrderV2ServicePort orderV2ServicePort,
                        UserConfigurationPersistencePort userConfigurationPersistencePort,
                        CompanyPersistencePort companyPersistencePort,
                        OrderPosLegacyServicePort orderPosLegacyServicePort,
                        EmailServicePort emailServicePort,
                        CompanyServicePort companyServicePort,
                        OrderJdeServicePort orderJdeServicePort) {
                super(userConfigurationPersistencePort, companyPersistencePort, orderPosLegacyServicePort,
                                emailServicePort, companyServicePort, orderJdeServicePort);
                this.orderV2ServicePort = orderV2ServicePort;
        }

        /**
         * Valida la precisión decimal de un OrderDto según estándares SAT
         * 
         * @param orderDto DTO de orden a validar
         * @param idOperation ID de operación para logging
         */
        private void validateOrderPrecision(OrderDto orderDto, String idOperation) {
                LOG.info(String.format("%s INIT validateOrderPrecision()", idOperation));
                
                // Validar orderTotal
                if (orderDto.getOrderTotal() != null && orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
                        if (!PrecisionValidator.isValidMonetaryRange(orderDto.getOrderTotal())) {
                                LOG.error(String.format("%s ERROR: orderTotal fuera de rango válido: %s", idOperation, orderDto.getOrderTotal()));
                                throw new GlobalError();
                        }
                        if (!PrecisionValidator.isValidScale(orderDto.getOrderTotal(), 2)) {
                                LOG.error(String.format("%s ERROR: orderTotal con escala incorrecta: %s", idOperation, orderDto.getOrderTotal()));
                                throw new GlobalError();
                        }
                }
                
                // Validar pendingPayment
                if (orderDto.getPendingPayment() != null && orderDto.getPendingPayment().compareTo(BigDecimal.ZERO) != 0) {
                        if (!PrecisionValidator.isValidMonetaryRange(orderDto.getPendingPayment())) {
                                LOG.error(String.format("%s ERROR: pendingPayment fuera de rango válido: %s", idOperation, orderDto.getPendingPayment()));
                                throw new GlobalError();
                        }
                        if (!PrecisionValidator.isValidScale(orderDto.getPendingPayment(), 2)) {
                                LOG.error(String.format("%s ERROR: pendingPayment con escala incorrecta: %s", idOperation, orderDto.getPendingPayment()));
                                throw new GlobalError();
                        }
                }
                
                // Validar subTotal
                if (orderDto.getSubTotal() != null && orderDto.getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
                        if (!PrecisionValidator.isValidMonetaryRange(orderDto.getSubTotal())) {
                                LOG.error(String.format("%s ERROR: subTotal fuera de rango válido: %s", idOperation, orderDto.getSubTotal()));
                                throw new GlobalError();
                        }
                        if (!PrecisionValidator.isValidScale(orderDto.getSubTotal(), 2)) {
                                LOG.error(String.format("%s ERROR: subTotal con escala incorrecta: %s", idOperation, orderDto.getSubTotal()));
                                throw new GlobalError();
                        }
                }
                
                // Validar ivaTotal
                if (orderDto.getIvaTotal() != null && orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
                        if (!PrecisionValidator.isValidMonetaryRange(orderDto.getIvaTotal())) {
                                LOG.error(String.format("%s ERROR: ivaTotal fuera de rango válido: %s", idOperation, orderDto.getIvaTotal()));
                                throw new GlobalError();
                        }
                        if (!PrecisionValidator.isValidScale(orderDto.getIvaTotal(), 2)) {
                                LOG.error(String.format("%s ERROR: ivaTotal con escala incorrecta: %s", idOperation, orderDto.getIvaTotal()));
                                throw new GlobalError();
                        }
                }
                
                LOG.info(String.format("%s SUCCESS: OrderDto validado correctamente", idOperation));
        }

        /**
         * Aplica redondeo SAT a un OrderDto
         * 
         * @param orderDto DTO de orden a redondear
         * @param idOperation ID de operación para logging
         * @return OrderDto con valores redondeados según SAT
         */
        private OrderDto applySATRounding(OrderDto orderDto, String idOperation) {
                LOG.info(String.format("%s INIT applySATRounding()", idOperation));
                
                // Aplicar redondeo SAT a orderTotal
                if (orderDto.getOrderTotal() != null && orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal roundedOrderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getOrderTotal());
                        orderDto.setOrderTotal(roundedOrderTotal);
                        LOG.info(String.format("%s orderTotal redondeado: %s", idOperation, roundedOrderTotal));
                }
                
                // Aplicar redondeo SAT a pendingPayment
                if (orderDto.getPendingPayment() != null && orderDto.getPendingPayment().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal roundedPendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getPendingPayment());
                        orderDto.setPendingPayment(roundedPendingPayment);
                        LOG.info(String.format("%s pendingPayment redondeado: %s", idOperation, roundedPendingPayment));
                }
                
                // Aplicar redondeo SAT a subTotal
                if (orderDto.getSubTotal() != null && orderDto.getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal roundedSubTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getSubTotal());
                        orderDto.setSubTotal(roundedSubTotal);
                        LOG.info(String.format("%s subTotal redondeado: %s", idOperation, roundedSubTotal));
                }
                
                // Aplicar redondeo SAT a ivaTotal
                if (orderDto.getIvaTotal() != null && orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
                        BigDecimal roundedIvaTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getIvaTotal());
                        orderDto.setIvaTotal(roundedIvaTotal);
                        LOG.info(String.format("%s ivaTotal redondeado: %s", idOperation, roundedIvaTotal));
                }
                
                LOG.info(String.format("%s SUCCESS: OrderDto redondeado según SAT", idOperation));
                return orderDto;
        }

        /**
         * EndPonit para la creación de una orden de venta o cotización, dependiendo del
         * tipo de orden se aplicarán las validaciones correspondientes
         * 
         * @param orderDto    modelo genérico de
         * @param companyCode
         * @param branchCode
         * @return ResponseModel
         */
        @PostMapping("/create/{mailUsuario}/{companyCode}/{branchCode}")
        public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto, @PathVariable String companyCode,
                        @PathVariable String branchCode, @PathVariable boolean mailUsuario) {

                String idOperation = generateIdOperation(companyCode, branchCode, false);
                logInit("createOrder", idOperation, orderDto);

                // Validar precisión decimal del OrderDto
                validateOrderPrecision(orderDto, idOperation);
                
                // Aplicar redondeo SAT
                orderDto = applySATRounding(orderDto, idOperation);

                if (orderDto.getOrderDetail() != null && !orderDto.getOrderDetail().isEmpty()) {
                        for (OrderDetailDto detail : orderDto.getOrderDetail()) {

                                String descriptionOne = detail.getDescriptionOne();
                                String descriptionTwo = detail.getDescriptionTwo();

                                descriptionOne = (descriptionOne != null) ? descriptionOne.trim() : "";
                                descriptionTwo = (descriptionTwo != null) ? descriptionTwo.trim() : "";

                                int charsToRemove = descriptionTwo.length();

                                String newDescriptionOne;
                                if (charsToRemove > 0 && descriptionOne.length() >= charsToRemove) {
                                        newDescriptionOne = descriptionOne.substring(0,
                                                        descriptionOne.length() - charsToRemove);
                                } else {
                                        newDescriptionOne = descriptionOne;
                                }

                                detail.setDescriptionOne(newDescriptionOne);
                        }
                }

                // Validación: La orden debe contener al menos un artículo
                if (orderDto.getOrderDetail() == null || orderDto.getOrderDetail().isEmpty()) {
                        LOG.error(String.format("%s ERROR: orderDetail está vacío o es null", idOperation));
                        throw new GlobalError();
                }

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.createOrder(customParams, orderDto, method.getCode(),
                                false, mailUsuario);
                return ResponseEntity.ok(responseModel);
        }

        /**
         * Puerta de entrada para la confirmación de la actualización de una orden de
         * venta o cotización, dependiendo del tipo de orden se aplicarán las
         * validaciones correspondientes
         * 
         * @param orderDto
         * @param companyCode
         * @param branchCode
         * @param id
         * @return ResponseModel
         */
        @PutMapping("/update/{id}/{mailUsuario}/{companyCode}/{branchCode}")
        public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto, @PathVariable String companyCode,
                        @PathVariable String branchCode, @PathVariable Long id, @PathVariable boolean mailUsuario) {
                String idOperation = generateIdOperation(companyCode, branchCode, false);
                logInit("updateOrder", idOperation, orderDto);

                // Validar precisión decimal del OrderDto
                validateOrderPrecision(orderDto, idOperation);
                
                // Aplicar redondeo SAT
                orderDto = applySATRounding(orderDto, idOperation);

                orderDto.setOrderId(id);
                
                // Validación: La orden debe contener al menos un artículo
                if (orderDto.getOrderDetail() == null || orderDto.getOrderDetail().isEmpty()) {
                        LOG.error(String.format("%s ERROR: orderDetail está vacío o es null", idOperation));
                        throw new GlobalError();
                }

                MethodDto method = getMethod(companyCode, idOperation);
                String userLogged = SecurityContextHolder.getContext().getAuthentication().getName();

                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation)
                                .setUserLogged(userLogged).build();
                ResponseModel responseModel = orderV2ServicePort.updateOrder(customParams, orderDto, method.getCode(),
                                mailUsuario);
                return ResponseEntity.ok(responseModel);
        }

        /**
         * Puerta de entrada para obtener los datos de una orden para su edición si es
         * que no esta siendo editada
         * 
         * @param orderNumber
         * @param orderType
         * @param branchCode
         * @param companyCode
         * @return
         */
        @PutMapping("/view/update/{orderNumber}/{orderType}/{companyCode}/{branchCode}")
        public ResponseEntity<?> getOrderToUpdate(@PathVariable BigDecimal orderNumber, @PathVariable String orderType,
                        @PathVariable String branchCode, @PathVariable String companyCode) {
                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("getOrderToUpdate", idOperation, createLogMap(
                                "orderType", orderType,
                                "branchCode", branchCode,
                                "companyCode", companyCode));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.getOrderToUpdate(customParams, orderNumber,
                                orderType, method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        /**
         * Puerta de entrada para canelar la edición de una orden y liberar el estado de
         * bloqueo de la orden
         * 
         * @param orderNumber
         * @param orderType
         * @param branchCode
         * @param companyCode
         * @return
         */
        @PutMapping("/cancel/update/{orderNumber}/{email}/{orderType}/{branchCode}/{companyCode}")
        public ResponseEntity<?> cancelUpdateOrder(@PathVariable BigDecimal orderNumber, @PathVariable String email,
                        @PathVariable String orderType, @PathVariable String branchCode,
                        @PathVariable String companyCode) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("cancelUpdateOrder", idOperation, createLogMap(
                                "email", email,
                                "orderType", orderType,
                                "branchCode", branchCode,
                                "companyCode", companyCode));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.cancelUpdateOrder(customParams, orderNumber,
                                email, orderType, method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        /**
         * Puerta de entrada para la visualización de ordenes acorde a los parámetros de
         * búsqueda introducidos en el objeto GenericSerchParamsOrderDto as params
         * 
         * @param companyCode
         * @param branchCode
         * @param email
         * @param params
         * @return
         */
        @PostMapping("/find-by-params/{email}/{companyCode}/{branchCode}")
        public ResponseEntity<?> viewOrderListByParams(@PathVariable String companyCode,
                        @PathVariable String branchCode,
                        @PathVariable String email, @RequestBody GenericSerchParamsOrderDto params) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("viewOrderListByParams", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "params", params.toString(),
                                "email", email));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.getOrderListByParams(customParams, params, email,
                                method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        @PostMapping("/find-by-params-admin/{email}/{companyCode}/{branchCode}")
        public ResponseEntity<?> viewOrderListByParamsAdmin(@PathVariable String companyCode,
                        @PathVariable String branchCode,
                        @PathVariable String email, @RequestBody GenericSerchParamsOrderDto params) {
                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("viewOrderListByParamsAdmin", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "params", params.toString(),
                                "email", email));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.getAllOrdersListByCompany(customParams, params, email,
                                method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        /**
         * Puerta de entrada para la visualización del detalle de una orden por medio
         * del número de la misma
         * 
         * @param companyCode
         * @param branchCode
         * @param orderNumber
         * @param orderType
         * @return
         */
        @GetMapping("/view/{orderNumber}/{orderType}/{companyCode}/{branchCode}")
        public ResponseEntity<?> viewOrderDetailByOrderNumber(@PathVariable String companyCode,
                        @PathVariable String branchCode, @PathVariable BigDecimal orderNumber,
                        @PathVariable String orderType) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("viewOrderDetailByOrderNumber", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "orderType", orderType));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.viewOderDetailByOrderNumber(customParams,
                                orderNumber, orderType, method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);

        }

        /**
         * Puerta de entrada para la visualización de las acciones realizadas sobre una
         * orden por medio del numero de la misma
         * 
         * @param companyCode
         * @param branchCode
         * @param orderNumber
         * @param orderType
         * @return
         */
        @GetMapping("/view/historical-detail/{orderNumber}/{orderType}/{companyCode}/{branchCode}")
        public ResponseEntity<?> viewHistoricalOrderDetailByOrderNumber(@PathVariable String companyCode,
                        @PathVariable String branchCode, @PathVariable BigDecimal orderNumber,
                        @PathVariable String orderType) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("viewHistoricalOrderDetailByOrderNumber", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "orderType", orderType));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.viewHistoricalOrderDetailByOrderNumber(
                                customParams, orderNumber, orderType, method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        /**
         * Puerat de entrada para la cancelación de una orden por medio del número de la
         * misma
         * 
         * @param companyCode
         * @param branchCode
         * @param orderNumber
         * @param genericActionControllOrderDto
         * @return
         */
        @PutMapping("/cancel/{orderNumber}/{companyCode}/{branchCode}")
        public ResponseEntity<?> cancelOrderByOrderNumber(@PathVariable String companyCode,
                        @PathVariable String branchCode,
                        @PathVariable BigDecimal orderNumber,
                        @RequestBody GenericActionControllOrderDto genericActionControllOrderDto) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("cancelOrderByOrderNumber", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "genericActionControllOrderDto", genericActionControllOrderDto));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.cancelOrder(customParams,
                                genericActionControllOrderDto, method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        /**
         * Puerta de enatrda para la aprobación de una orden por medio del número de la
         * misma
         * 
         * @param companyCode
         * @param branchCode
         * @param orderNumber
         * @param genericActionControllOrderDto
         * @return
         */
        @PutMapping("/approve/{orderNumber}/{companyCode}/{branchCode}")
        public ResponseEntity<?> approveOrderByOrderNumber(@PathVariable String companyCode,
                        @PathVariable String branchCode, @PathVariable BigDecimal orderNumber,
                        @RequestBody GenericActionControllOrderDto genericActionControllOrderDto) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("approveOrderByOrderNumber", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "genericActionControllOrderDto", genericActionControllOrderDto));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.approveOrder(customParams,
                                genericActionControllOrderDto, method.getCode());

                return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        /**
         * Puerta de entrada para la generacion de tickets de las ordenes
         * 
         * @param companyCode
         * @param branchCode
         * @param orderNumber
         * @param orderCode
         * @return ResponseModel
         */
        @GetMapping("/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
        public ResponseEntity<?> getTicketOrder(@PathVariable String companyCode, @PathVariable String branchCode,
                        @PathVariable BigDecimal orderNumber, @PathVariable String orderCode) {
                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("getTicketOrder", idOperation, createLogMap(
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "orderCode", orderCode));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.generateTicketByOrderNumberAndOrderType(
                                customParams, orderNumber, orderCode, method.getCode());

                byte[] reporte = (byte[]) responseModel.getData();
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                                .filename(orderCode + "-" + orderNumber + ".pdf").build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
                                .headers(headers).body(new ByteArrayResource(reporte));

        }

        /**
         * Devuelve el pdf de la orden-
         * 
         * @param companyCode compañía de la que se obtendrá la información.
         * @return base64I-
         */
        @GetMapping("/document/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
        public ResponseEntity<?> getDocument(@PathVariable BigDecimal orderNumber, @PathVariable String orderCode,
                        @PathVariable String companyCode, @PathVariable String branchCode) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("getDocument", idOperation, createLogMap(
                                "orderNumber", companyCode,
                                "orderCode", branchCode,
                                "companyCode", orderCode,
                                "branchCode", branchCode));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.generateDocumentByOrderNumberAndOrderType(
                                customParams, orderNumber, orderCode, method.getCode());

                byte[] reporte = (byte[]) responseModel.getData();
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                                .filename(orderCode + "-" + orderNumber + ".pdf").build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
                                .headers(headers).body(new ByteArrayResource(reporte));

        }

        /**
         * Manda el PDF por correo-
         * 
         * @param companyCode compañía de la que se obtendrá la información.
         */
        @PostMapping("/send-document/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
        public ResponseEntity<?> sendDocumentByEmail(@PathVariable BigDecimal orderNumber,
                        @PathVariable String orderCode,
                        @PathVariable String companyCode, @PathVariable String branchCode,
                        @RequestBody List<String> emails) {

                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("sendDocumentByEmail", idOperation, createLogMap(
                                "orderNumber", companyCode,
                                "orderCode", branchCode,
                                "companyCode", orderCode,
                                "branchCode", branchCode,
                                "emails", emails));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.sendDocumentByEmailAndCompanyCode(customParams,
                                emails, orderNumber, orderCode, method.getCode());

                return ResponseEntity.status(HttpStatus.OK).body(responseModel);
        }

        @PostMapping("/convert/{orderCode}/{mailUsuario}/{companyCode}/{branchCode}")
        public ResponseEntity<?> convertOrder(@RequestBody OrderDto orderDto, @PathVariable String companyCode,
                        @PathVariable String branchCode, @PathVariable String orderCode,
                        @PathVariable boolean mailUsuario) {
                String idOperation = generateIdOperation(companyCode, branchCode, true);
                logInit("convertOrder", idOperation, createLogMap(
                                "orderDto", orderDto,
                                "companyCode", companyCode,
                                "branchCode", branchCode,
                                "orderCode", orderCode));

                MethodDto method = getMethod(companyCode, idOperation);
                CustomInterfaceOrderParams customParams = buildCustomParams(companyCode, idOperation).build();

                ResponseModel responseModel = orderV2ServicePort.convertQuoteOrderToSaleOrder(customParams,
                                orderDto, orderCode, method.getCode(), mailUsuario);

                return ResponseEntity.status(HttpStatus.OK).body(responseModel);
        }
}