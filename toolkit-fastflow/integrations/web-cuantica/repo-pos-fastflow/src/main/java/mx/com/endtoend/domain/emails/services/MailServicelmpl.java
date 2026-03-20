package mx.com.endtoend.domain.emails.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.emails.ports.api.MailServicePort;
import mx.com.endtoend.domain.emails.ports.spi.MailPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;


public class MailServicelmpl implements MailServicePort{
	
	private MailPersistencePort mailPersistencePort;

	public MailServicelmpl(MailPersistencePort mailPersistencePort) {
		this.mailPersistencePort = mailPersistencePort;
	}
	
	private final static Logger LOG = LoggerFactory.getLogger(MailServicelmpl.class);

	@Override
	public ResponseModel mailList(String idOperation) {

		LOG.info(String.format("%s INIT mailList()", idOperation));
		LOG.info(String.format("%s PARAMS: [idOperation] ", idOperation));

		ResponseModel responseFromPersistencePort = mailPersistencePort.mailList(idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;
	}


}
