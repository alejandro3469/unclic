package mx.com.endtoend.application.companies;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.roles.ports.api.PermissionServicePort;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador para la gestión de las compañias del sistema.
 * 
 * @author labucio, ddcasas
 *
 */

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private PermissionServicePort permissionServicePort;
	
	@Autowired
	private RolePersistencePort rolePersistencePort;

	private String module = "COMPANIES";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

	/**
	 * EndPoint para la creación de compañias en el sistema.
	 * 
	 * @param companyDto modelo de datos con la información de la compañía a crear
	 * @return ResponseModel
	 */
	@PostMapping("/create")
	public ResponseEntity<ResponseModel> saveCompany(@RequestBody @Valid CompanyDto companyDto) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT saveCompany()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyDto: %s ]", idOperation, companyDto.toString()));
		ResponseModel responseModel = companyServicePort.saveCompany(companyDto, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseModel> modifyCompany(@RequestBody @Valid CompanyDto companyDto,
			@PathVariable Long id) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT modifyCompany() ", idOperation));
		LOG.info(String.format("%s PARAMS: %s ", idOperation, companyDto.toString()));
		companyDto.setId(id);
		ResponseModel responseModel = companyServicePort.modifyCompany(companyDto, rolePersistencePort, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/list/{companyCode}")
	public ResponseEntity<ResponseModel> companyList(@PathVariable String companyCode) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT companyList() ", idOperation));
		LOG.info(String.format("%s PARAMS: %s ", idOperation, companyCode));
		ResponseModel responseModel = companyServicePort.companyList(idOperation, companyCode);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/methods/{module}")
	public ResponseEntity<?> getMethodListByModule(@PathVariable String module) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT getMethodListByModule() ", idOperation));
		LOG.info(String.format("%s PARAMS: module: %s ", idOperation, module));
		ResponseModel responseModel = companyServicePort.findAllMethodByModule(module, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT getCompanyById() ", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s ", idOperation, id.toString()));
		ResponseModel responseModel = companyServicePort.findById(id, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar los permisos del sistema global para ser asignados a
	 * una compañía
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/permissions/company")
	public ResponseEntity<?> findPermissionsToClient() {
		LOG.info(String.format("INIT findPermissionsToClient()"));
		ResponseModel responseModel = permissionServicePort.findAllByClient();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}