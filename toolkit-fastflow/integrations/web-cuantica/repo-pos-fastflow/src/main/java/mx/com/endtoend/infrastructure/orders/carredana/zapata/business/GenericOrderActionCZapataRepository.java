package mx.com.endtoend.infrastructure.orders.carredana.zapata.business;

import java.util.*;

import mx.com.endtoend.infrastructure.orders.common.business.BaseOrderActionRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.zapata.repositories.StatusCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.ClientCZapataRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.carredana.zapata.repositories.CreditNoteCZapataRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.carredana.zapata.repositories.OrderConfigurationCZapataRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories.CustomDSLCZapataOrderRepository;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories.OrderCZapataRepository;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories.OrderHistoryCZapataRepository;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories.SaleOrderCZapataRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.carredana.zapata.repositories.InvoiceReferenceCZapataRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.zapata.business.UserConfigurationZapataRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class GenericOrderActionCZapataRepository extends BaseOrderActionRepository {

	 static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("logo_ticket", "/tickets/zapata/logo_color_2.png");
		TEMPLATES.put("ticket_template","/tickets/zapata/ticket_carredana.jasper");
		TEMPLATES.put("document_template", "/orders/zapata/Order_report.jasper");
		TEMPLATES.put("logo_document", "/orders/zapata/logo_color_2.png");
		TEMPLATES.put("logo_watermark_document","/orders/zapata/logo_b_n.jpeg");
		TEMPLATES.put("quote_template", "/orders/zapata/Quote_order_report.jasper");
	}


	public GenericOrderActionCZapataRepository(UserConfigurationZapataRepository userConfigurationCalzadaRepository,
											   StatusCZapataRepository statusRepository,
											   StatusConverter statusConverter,
											   AddressConverter addressConverter,
											   TaxConverter taxConverter,
											   OrderHistoryConverter orderHistoryConverter,
											   OrderConverter orderConverter,
											   OrderDetailConverter orderDetailConverter,
											   ClientConverter clientConverter,
											   OrderCZapataRepository orderRepository,
											   OrderHistoryCZapataRepository orderHistoryRepository,
											   OrderConfigurationCZapataRepository orderConfigurationRepository,
											   OrderConfigurationConverter orderConfigurationConverter,
											   SaleOrderConverter saleOrderConverter,
											   SaleOrderDetailConverter saleOrderDetailConverter,
											   SaleOrderCZapataRepository saleOrderRepository,
											   BranchRepository branchRepository,
											   BranchConverter branchConverter,
											   UserRepository userRepository,
											   UserConverter userConverter,
											   InvoiceReferenceCZapataRepository invoiceReferenceRepository,
											   InvoiceReferenceConverter invoiceReferenceConverter,
											   ClientCZapataRepository clientRepository,
											   CreditNoteCZapataRepository creditNoteRepository,
											   CustomDSLCZapataOrderRepository customDSLCZapataOrderRepository){
		super(TEMPLATES, GenericOrderActionCZapataRepository.class, statusRepository,
				statusConverter, addressConverter,taxConverter,orderHistoryConverter,orderConverter,orderDetailConverter,clientConverter,
				orderConfigurationConverter,saleOrderConverter,saleOrderDetailConverter,saleOrderRepository,branchRepository ,branchConverter,userRepository,userConverter,
				creditNoteRepository,clientRepository,orderRepository,orderHistoryRepository,customDSLCZapataOrderRepository,userConfigurationCalzadaRepository,orderConfigurationRepository);
	}
}
