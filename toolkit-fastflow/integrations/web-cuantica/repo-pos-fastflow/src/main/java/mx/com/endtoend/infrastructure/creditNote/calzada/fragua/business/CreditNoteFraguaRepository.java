package mx.com.endtoend.infrastructure.creditNote.calzada.fragua.business;

import java.util.*;

import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.common.business.BaseCreditNoteBusinessRepository;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.OrderRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.PaymentRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.PaymentConverter;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.creditNote.calzada.business.CreditNoteCalzadaRepository;
import mx.com.endtoend.infrastructure.creditNote.calzada.fragua.repositories.CreditNoteCFraguaRepository;
import mx.com.endtoend.infrastructure.creditNote.calzada.fragua.repositories.CreditNoteHeaderCFraguaRepository;
import mx.com.endtoend.infrastructure.creditNote.calzada.fragua.repositories.CustomDSLCreditNoteCFraguaRepository;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteDetailConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteHeaderConverter;

@Service
public class CreditNoteFraguaRepository extends BaseCreditNoteBusinessRepository {

	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_nc_template",  "/tickets/fragua/ticket_nc.jasper");
		TEMPLATES.put("logo_report", "/tickets/fragua/logo.jpg");
	}

	public CreditNoteFraguaRepository(CreditNoteConverter creditNoteConverter,
									  CreditNoteHeaderConverter creditNoteHeaderConverter,
									  CreditNoteDetailConverter creditNoteDetailConverter,
									  CreditNoteCFraguaRepository creditNoteRepository,
									  CreditNoteHeaderCFraguaRepository creditNoteHeaderRepository,
									  CustomDSLCreditNoteCFraguaRepository customDSLCreditNoteRepository,
									  PaymentConverter paymentConverter,
									   PaymentRepository paymentRepository,
									   OrderRepository orderRepository,
									   OrderConverter orderConverter,
									   StatusConverter statusConverter,
									   ClientConverter clientConverter,
									   ClientRepository clientRepository,
									   AddressConverter addressConverter,
									   TaxConverter taxConverter,
									   OrderDetailConverter orderDetailConverter){
		super(CreditNoteFraguaRepository.class,TEMPLATES,
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




