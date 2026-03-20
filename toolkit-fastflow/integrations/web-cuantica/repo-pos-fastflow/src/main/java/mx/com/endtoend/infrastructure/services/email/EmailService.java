package mx.com.endtoend.infrastructure.services.email;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sun.mail.util.MailSSLSocketFactory;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.EmailConfigurationEntity;
import mx.com.endtoend.infrastructure.company.common.repositories.EmailConfigurationRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Servicio para el envío de correos del sistema con base en la configuración
 * asociada a cada compañía registrada
 * 
 * @author ddcasas
 *
 */
@Service
public class EmailService implements EmailServicePort {

	@Autowired
	private EmailConfigurationRepository emailConfigurationRepository;

	private final Logger LOG = LoggerFactory.getLogger(EmailService.class);

	/**
	 * Método para el envio de correos con archivos adjuntos
	 * 
	 * @param emailList   lista de correos para enviar el documento adjunto
	 * @param document    archivo para adjuntar en el correo
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public ResponseModel sendDocumentByCompanyCode(List<String> emailList, File documentToSend, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT sendDocumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [emailList: %s , document: Object ,  companyCode: %s ]", idOperation,
				emailList.toString(), companyCode));

		try {

			LOG.info(String.format("%s FIND EMAIL CONFIGURATION TO COMPANY: %s", idOperation, companyCode));
			Optional<EmailConfigurationEntity> emailConfigOptional = emailConfigurationRepository
					.finActiveConfigurationByCompanyCode(true, CompanyCodes.valueOf(companyCode));

			if (!emailConfigOptional.isPresent()) {
				LOG.warn(String.format("%sEMAIL CONFIGURATION IS INCOMPLETE TO COMPANY: %s", idOperation, companyCode));
				throw new ValidationError("EMAIL CONFIGURATION INCOMPLETE");
			}

			EmailConfigurationEntity emailConfiguration = emailConfigOptional.get();

			LOG.info(String.format("%s CONFIGURATE PROPERTIES", idOperation));

			Properties emailProperties = getEmailPropertiesBySecurityConfiguration(emailConfiguration);

			LOG.info(String.format("%s CONFIGURATE SESSION", idOperation));

			Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailConfiguration.getFromEmail(),
							emailConfiguration.getPassword());
				}
			});

			session.setDebug(true);

			LOG.info(String.format("%s CONFIGURATE BODY-EMAIL", idOperation));
			MimeMessage message = new MimeMessage(session);
			StringBuilder mensaje = new StringBuilder(
					"<p>Estimado cliente, le enviamos el documento de su orden generada  </p><br><br>");

			BodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(mensaje.toString(), "text/html");

			MimeBodyPart mimeBodyPartAdjunto = new MimeBodyPart();
			mimeBodyPartAdjunto.attachFile(documentToSend);

			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(mimeBodyPartAdjunto);

			message.setContent(multipart);
			message.setFrom(new InternetAddress(emailConfiguration.getFromEmail()));

			LOG.info(String.format("%s SEND EMAIL IN THE LIST", idOperation));
			for (String email : emailList) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Documento de orden generada");
				Transport.send(message);
			}

			LOG.info(String.format("%s END PROCESS", idOperation));
			return new ResponseModel(true);

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN sendDocumentByCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			return new ResponseModel(false);
		}

	}

	/**
	 * Métood que se encargar de generar las propiedades usadas por el protocolo
	 * SMTP
	 * 
	 * @param emailConfiguration configuración de los datos operacionales de la
	 *                           compañia
	 * @return Properties basado en la configuración de la compañía
	 * @throws GeneralSecurityException
	 */
	public Properties getEmailPropertiesBySecurityConfiguration(EmailConfigurationEntity emailConfiguration)
			throws GeneralSecurityException {

		Properties emailProperties = new Properties();
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);

		if (emailConfiguration.isEnableAuthentication() && emailConfiguration.getEncryptionProtocol().equals("SSL")) {

			emailProperties.put("mail.smtp.host", emailConfiguration.getSmtpHost());
			emailProperties.put("mail.smtp.socketFactory.port", emailConfiguration.getSslPort());
			emailProperties.put("mail.smtp.ssl.socketFactory", sf);
			emailProperties.put("mail.smtp.auth", emailConfiguration.isEnableAuthentication());
			emailProperties.put("mail.smtp.port", emailConfiguration.getSmtpPort());

		}

		if (emailConfiguration.isEnableAuthentication() && emailConfiguration.getEncryptionProtocol().equals("TLS")) {

			emailProperties.put("mail.smtp.host", emailConfiguration.getSmtpHost());
			emailProperties.put("mail.smtp.port", emailConfiguration.getSmtpPort());
			emailProperties.put("mail.smtp.auth", emailConfiguration.isEnableAuthentication());
			emailProperties.put("mail.smtp.starttls.enable", "true");
		}

		if (!emailConfiguration.isEnableAuthentication()) {

			emailProperties.put("mail.smtp.host", emailConfiguration.getSmtpHost());
			emailProperties.put("mail.smtp.port", emailConfiguration.getSmtpPort());
			emailProperties.put("mail.smtp.starttls.enable", "true");
			emailProperties.put("mail.smtp.auth", emailConfiguration.isEnableAuthentication());
			emailProperties.put("mail.smtp.ssl.socketFactory", sf);

		}

		return emailProperties;
	}

	/**
	 * Método asincrono para el envío de correos con archivos adjuntos
	 * 
	 * @param emailList
	 * @param document
	 * @param companyCode
	 * @param idOperation
	 * 
	 */
	@Override
	@Async
	public void sendDocumentUnconfirmedByCompanyCode(List<String> emailList, File document, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT sendDocumentUnconfirmedByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [emailList: %s , document: Object ,  companyCode: %s ]", idOperation,
				emailList.toString(), companyCode));

		try {

			LOG.info(String.format("%s FIND EMAIL CONFIGURATION TO COMPANY: %s", idOperation, companyCode));
			Optional<EmailConfigurationEntity> emailConfigOptional = emailConfigurationRepository
					.finActiveConfigurationByCompanyCode(true, CompanyCodes.valueOf(companyCode));

			EmailConfigurationEntity emailConfiguration = emailConfigOptional.get();

			LOG.info(String.format("%s CONFIGURATE PROPERTIES", idOperation));
			Properties emailProperties = getEmailPropertiesBySecurityConfiguration(emailConfiguration);

			LOG.info(String.format("%s CONFIGURATE SESSION", idOperation));
			Session session = Session.getInstance(emailProperties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailConfiguration.getFromEmail(),
							emailConfiguration.getPassword());
				}
			});

			session.setDebug(true);

			LOG.info(String.format("%s CONFIGURATE BODY-EMAIL", idOperation));
			MimeMessage message = new MimeMessage(session);
			StringBuilder mensaje = new StringBuilder(
					"<p>Estimado cliente, le enviamos el documento de su orden generada  </p><br><br>");

			BodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(mensaje.toString(), "text/html");

			MimeBodyPart mimeBodyPartAdjunto = new MimeBodyPart();
			mimeBodyPartAdjunto.attachFile(document);

			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			multipart.addBodyPart(mimeBodyPartAdjunto);

			message.setContent(multipart);
			message.setFrom(new InternetAddress(emailConfiguration.getFromEmail()));

			LOG.info(String.format("%s SEND EMAIL IN THE LIST", idOperation));
			for (String email : emailList) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Documento de orden generada");
				Transport.send(message);
			}

			LOG.info(String.format("%s END PROCESS", idOperation));

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN sendDocumentByCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));

		}

	}

}
