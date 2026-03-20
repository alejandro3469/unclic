package mx.com.endtoend.infrastructure.creditNote.demo.business;

import java.util.*;

import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.demo.repositories.ClientDemoRepository;
import mx.com.endtoend.infrastructure.creditNote.common.business.BaseCreditNoteBusinessRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.orders.demo.repositories.OrderDemoRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.PaymentConverter;
import mx.com.endtoend.infrastructure.payments.demo.repositories.PaymentDemoRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.creditNote.demo.repositories.CreditNoteDemoRepository;
import mx.com.endtoend.infrastructure.creditNote.demo.repositories.CreditNoteHeaderDemoRepository;
import mx.com.endtoend.infrastructure.creditNote.demo.repositories.CustomDSLCreditNoteDemoRepository;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteDetailConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteHeaderConverter;

@Service
public class CreditNoteDemoBusinessRepository extends BaseCreditNoteBusinessRepository {

    static final Map<String, String> TEMPLATES;

    static {
        TEMPLATES = new HashMap<>();
        TEMPLATES.put("ticket_nc_template", "/tickets/demo/ticket_nc.jasper");
        TEMPLATES.put("logo_report", "/tickets/demo/logo_color_2.jpeg");
    }

    public CreditNoteDemoBusinessRepository(CreditNoteConverter creditNoteConverter,
                                            CreditNoteHeaderConverter creditNoteHeaderConverter,
                                            CreditNoteDetailConverter creditNoteDetailConverter,
                                            CreditNoteDemoRepository creditNoteRepository,
                                            CreditNoteHeaderDemoRepository creditNoteHeaderRepository,
                                            CustomDSLCreditNoteDemoRepository customDSLCreditNoteRepository,
                                            PaymentConverter paymentConverter,
                                            PaymentDemoRepository paymentRepository,
                                            OrderDemoRepository orderRepository,
                                            OrderConverter orderConverter,
                                            StatusConverter statusConverter,
                                            ClientConverter clientConverter,
                                            ClientDemoRepository clientRepository,
                                            AddressConverter addressConverter,
                                            TaxConverter taxConverter,
                                            OrderDetailConverter orderDetailConverter) {
        super(CreditNoteDemoBusinessRepository.class, TEMPLATES,
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