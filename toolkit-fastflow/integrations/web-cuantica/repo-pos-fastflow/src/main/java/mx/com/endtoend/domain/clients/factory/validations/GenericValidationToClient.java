package mx.com.endtoend.domain.clients.factory.validations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;

/**
 * Clase que contiene todas las validaciones empleadas en el módulo de clientes
 * 
 * @author ddcasas
 *
 */
public class GenericValidationToClient {

	private final static Logger LOG = LoggerFactory.getLogger(GenericValidationToClient.class);

	public String vaidInputParamsToSerchClient(FiltersClientDto filtersClientDto, String idOperation) {

		LOG.info(String.format("%s INIT vaidInputParamsToSerchClient()", idOperation));
		LOG.info(String.format("PARAMS:[filtersClientDto: %s]", idOperation, filtersClientDto.toString()));

		String validationResult = "";
		boolean validName = true;
		boolean validClientNumber = true;
		boolean validRfc = true;

		validName = (filtersClientDto.getName() == null ? false
				: (filtersClientDto.getName().isEmpty() ? false : true));

		validClientNumber = (filtersClientDto.getNoClient() == null ? false : true);

		validRfc = (filtersClientDto.getRfc() == null ? false : (filtersClientDto.getRfc().isEmpty() ? false : true));

		validationResult = (!validName && !validClientNumber && !validRfc) ? "EMPTY SEARCH PARAMETERS" : "";

		return validationResult;

	}
}
