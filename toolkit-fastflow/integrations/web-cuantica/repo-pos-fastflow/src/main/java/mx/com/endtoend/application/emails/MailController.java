//package mx.com.endtoend.application.emails;
//
//import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//
//import mx.com.endtoend.domain.commons.ResponseModel;
//import mx.com.endtoend.domain.emails.ports.api.MailServicePort;
//
///**
// * Controlador para la gestión de los correos de los clientes.
// * 
// * @author labucio
// *
// */
//
//@RestController
//@RequestMapping("/mail")
//public class MailController {
//	
//	@Autowired MailServicePort mailServicePort;
//
//	private String module = "EMAILS";
//	public String idOperation = "";
//	
//	private final Logger LOG = LoggerFactory.getLogger(MailController.class);
//
//	@GetMapping("/list")
//	public ResponseEntity<ResponseModel> mailList() {
//		
//		idOperation = generateIdOperation(module);
//		
//		LOG.info(String.format("%s GET LIST OF MAILS", idOperation));
//		
//		ResponseModel responseModel = mailServicePort.mailList(idOperation);
//		
//		LOG.info(String.format("%s RESPONSE FROM DOMAIN: %s", idOperation, responseModel.toString()));
//		
//		return new ResponseEntity<>(responseModel, HttpStatus.OK);
//	}
//}
