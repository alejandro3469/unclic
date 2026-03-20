package mx.com.endtoend.infrastructure.creditNote.ferresamano.business;

import java.util.*;

import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ClientFSamanoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.common.business.BaseCreditNoteBusinessRepository;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.OrderFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.ferresamano.repositories.PaymentFSamanoRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.PaymentConverter;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.creditNote.ferresamano.repositories.CreditNoteFSamanoRepository;
import mx.com.endtoend.infrastructure.creditNote.ferresamano.repositories.CreditNoteHeaderFSamanoRepository;
import mx.com.endtoend.infrastructure.creditNote.ferresamano.repositories.CustomDSLCreditNoteFSamanoRepository;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteDetailConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteHeaderConverter;

@Service
public class CreditNoteCFSamanoRepository extends BaseCreditNoteBusinessRepository {

	static final Map<String, String> TEMPLATES;

	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_nc_template", "/tickets/ferresamano/ticket_nc.jasper");
		TEMPLATES.put("logo_report", "/tickets/ferresamano/logo_color_2.jpeg");
	}

	public CreditNoteCFSamanoRepository(CreditNoteConverter creditNoteConverter,
										CreditNoteHeaderConverter creditNoteHeaderConverter,
										CreditNoteDetailConverter creditNoteDetailConverter,
										CreditNoteFSamanoRepository creditNoteRepository,
										CreditNoteHeaderFSamanoRepository creditNoteHeaderRepository,
										CustomDSLCreditNoteFSamanoRepository customDSLCreditNoteRepository,
										PaymentConverter paymentConverter,
									   PaymentFSamanoRepository paymentRepository,
									   OrderFSamanoRepository orderRepository,
									   OrderConverter orderConverter,
									   StatusConverter statusConverter,
									   ClientConverter clientConverter,
									   ClientFSamanoRepository clientRepository,
									   AddressConverter addressConverter,
									   TaxConverter taxConverter,
									   OrderDetailConverter orderDetailConverter) {
		super(CreditNoteCFSamanoRepository.class, TEMPLATES,
				creditNoteConverter,
				creditNoteHeaderConverter,
				creditNoteDetailConverter,
				creditNoteRepository,
				creditNoteHeaderRepository,
				customDSLCreditNoteRepository,
				paymentConverter,
				paymentRepository,
				orderRepository,
				orderConverter,
				statusConverter,
				clientConverter,
				clientRepository,
				addressConverter,
				taxConverter,
				orderDetailConverter);
	}
}