package mx.com.endtoend.application.catalogues;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueBranchServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/catalogue-branch")
public class CatalogueBranchController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private CatalogueJdeServicePort catalogueOracleServicePort;

	@Autowired
	private CatalogueBranchServicePort catalogueBranchServicePort;

	private static String module = "CATALOGUE-BRANCH";

	public String idOperation;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueBranchController.class);

	/**
	 * EndPoint para la actualización del los datos de dirección de las sucursales
	 * del sistema con los datos del sistema JDE
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@PutMapping("/update-address/{companyCode}/{branchCode}")
	private ResponseEntity<?> updateBranchAddressByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateBranchAddressByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}
		ResponseModel responseModel = catalogueBranchServicePort.updateBranchAddressByCompanyCode(
				catalogueOracleServicePort, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
