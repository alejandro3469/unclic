package mx.com.endtoend.domain.orders.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;

/**
 * Factory para la obtención de la clase coordinadora entre sistemas externos y
 * el sistema principal
 * 
 * @author ddcasas
 */
public class OrderFactory {

	private final static Logger LOG = LoggerFactory.getLogger(OrderFactory.class);

	public OrderInterface getImplementationByCode(String method,
			CustomInterfaceOrderParams customInterfaceOrderParams) {

		try {
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {

			case ORDER_BUSINESS_ONE:
				LOG.info("RETUR OrderBusinessMethodOne()");
				return new OrderBusinessMethodOne(customInterfaceOrderParams);

			case ORDER_BUSINESS_TWO:
				LOG.info("RETUR OrderBusinessMethodTwo()");
				return new OrderBusinessMethodTwo(customInterfaceOrderParams);
			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR ORDER MODULE");
			return null;
		}

	}

}
