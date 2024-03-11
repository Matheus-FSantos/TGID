package io.github.matheusfsantos.tgid.model.service.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
	
	public void sender(String targetEmail, String msg) {
		String myEmail = "tgidtestetecnico@gmail.com";
		String myPassword = "12345678tgid"; /* Veja bem o que você vai fazer com essa senha em O.O */
		
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(myEmail, myPassword));
		email.setSSLOnConnect(true);
		
		try {
			email.setFrom(myEmail);
			email.setSubject("Tecnical Test TGID - New transaction in your acccount");
			email.setMsg(msg);
			email.addTo(targetEmail);
			email.send();
			
			EmailSenderService.logger.info("New email sent, to destination:" + targetEmail);
		} catch(Exception e) {
			/* 
				 * aparentemente ele solta um excep. pq eu tenho que habilitar a autenticação de 2 etapas, 
				 * e pegar a senha que ele me gera e passar como param. (pelo oq pude encontrar ao pesquisar) 
				 * porém, não consegui achara a opção no meu gmail, e deixei somente a implementação, 
				 * mesmo dando erro não interfere em nada no funcionamento do app, ele só não enviará o e-mail de notificação
			*/
			EmailSenderService.logger.info("An error occurred while sending the email, email target: " + targetEmail + "\nErro: " + e.getMessage());
		}
	}
	
}
