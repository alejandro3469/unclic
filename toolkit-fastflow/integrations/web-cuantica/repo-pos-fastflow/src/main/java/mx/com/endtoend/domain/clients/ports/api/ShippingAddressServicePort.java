package mx.com.endtoend.domain.clients.ports.api;

import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ShippingAddressServicePort {

	ResponseModel getShippingAddres(Long id);
	
	ResponseModel modificShippingAddres(ShippingAddressDto shippingAddressDto);

}
