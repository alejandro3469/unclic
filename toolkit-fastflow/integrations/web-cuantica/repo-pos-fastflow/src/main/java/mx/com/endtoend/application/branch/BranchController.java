package mx.com.endtoend.application.branch;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

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

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.api.BranchServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/branch")
public class BranchController {

	@Autowired
	private BranchServicePort branchServicePort;

	private String module = "BRANCHES";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(BranchController.class);

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createBranchByCompanyCode(@RequestBody BranchDto branchDto,
			@PathVariable String companyCode) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT createBranchByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [branchDto: %s ] ", idOperation, branchDto.toString()));
		ResponseModel responseModel = branchServicePort.createBranch(branchDto, companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	
	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateBranchByCompanyCode(@RequestBody BranchDto branchDto,
			@PathVariable String companyCode, @PathVariable Long id) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT updateBranchByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [branchDto: %s ]", idOperation, branchDto.toString()));
		branchDto.setId(id);
		ResponseModel responseModel = branchServicePort.updateBranch(branchDto, companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/list/{companyCode}/{branchCode}")
	public ResponseEntity<?> getBranchListByCompanyCode(@PathVariable String companyCode) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT getBranchListByCompanyCode(): %s", idOperation, companyCode));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = branchServicePort.getBranchListByCompanyCode(companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{branchCode}/{id}")
	public ResponseEntity<?> getBranchById(@PathVariable Long id) {
		idOperation = generateIdOperation(module);		
		LOG.info("INIT getBranchById()");
		LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id));
		ResponseModel responseModel = branchServicePort.findById(id, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}