package mx.com.endtoend.domain.orders.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.factories.orderBusiness.quote.QuoteMethodOne;
import mx.com.endtoend.domain.orders.factories.orderBusiness.saleCash.SaleCashMethodOne;
import mx.com.endtoend.domain.orders.factories.orderBusiness.saleCashCounter.SaleCashCounetrMethodOne;
import mx.com.endtoend.domain.orders.factories.orderBusiness.saleCounter.SaleCounterMethodOne;
import mx.com.endtoend.domain.orders.factories.orderBusiness.saleDirect.SaleDirectMethodOne;
import mx.com.endtoend.domain.orders.factories.orderBusiness.saleHomeDelivery.SaleHomeDeliveryMethodOne;

/**
 * Factoy para la obtnción de la lógica de negocio correspondiente a los
 * procesos de creación/actualización de los distintos tipos de órdenes en el
 * sistema
 * 
 * @author ddcasas
 */
public class OrderBusinessFactoty {

	private final Logger LOG = LoggerFactory.getLogger(OrderBusinessFactoty.class);

	public OrderBusinessInterface getOderBusinessByMethod(String method,
			CustomInterfaceOrderParams customInterfaceOrderParams) {
		try {
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);
			switch (value) {
			case QUOTE_ONE:
				LOG.info("RETUR QuoteMethodOne()");
				return new QuoteMethodOne(customInterfaceOrderParams);
			case SALE_CASH_ONE:
				LOG.info("RETUR SaleCashMethodOne()");
				return new SaleCashMethodOne(customInterfaceOrderParams);
			case SALE_COUNTER_ONE:
				LOG.info("RETUR SaleCounterMethodOne()");
				return new SaleCounterMethodOne(customInterfaceOrderParams);
			case SALE_DIRECT_ONE:
				LOG.info("RETUR SaleDirectMethodOne()");
				return new SaleDirectMethodOne(customInterfaceOrderParams);
			case SALE_CASH_COUNTER_ONE:
				LOG.info("RETUR SaleCashCounetrMethodOne()");
				return new SaleCashCounetrMethodOne(customInterfaceOrderParams);
			case SALE_HOME_DELIVERY_ONE:
				LOG.info("RETUR SaleHomeDeliveryMethodOne()");
				return new SaleHomeDeliveryMethodOne(customInterfaceOrderParams);
			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR ORDER-BUSINESS MODULE");
			return null;
		}
	}
}
