package mx.com.endtoend.infrastructure.orders.demo.business;

import mx.com.endtoend.infrastructure.orders.common.business.BaseOrderActionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.demo.repositories.StatusDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.demo.repositories.ClientDemoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.demo.repositories.CreditNoteDemoRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.demo.repositories.OrderConfigurationDemoRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.demo.repositories.CustomDSLDemoOrderRepository;
import mx.com.endtoend.infrastructure.orders.demo.repositories.OrderDemoRepository;
import mx.com.endtoend.infrastructure.orders.demo.repositories.OrderHistoryDemoRepository;
import mx.com.endtoend.infrastructure.orders.demo.repositories.SaleOrderDemoRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.demo.repositories.InvoiceReferenceDemoRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverter;
import mx.com.endtoend.infrastructure.userConfiguration.demo.business.UserConfigurationDemoBusinessRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter; 
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class GenericOrderActionDemoRepository extends BaseOrderActionRepository {

	 static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("logo_ticket", "/tickets/demo/logo_color_2.jpg");
		TEMPLATES.put("ticket_template", "/tickets/demo/ticket_demo.jasper");
		TEMPLATES.put("document_template", "/orders/demo/Order_report.jasper");
		TEMPLATES.put("logo_document", "/orders/demo/logo_color_2.jpeg");
		TEMPLATES.put("logo_watermark_document", "/orders/demo/logo_b_n.jpeg");
		TEMPLATES.put("quote_template", "/orders/demo/Quote_order_report.jasper");
	}

	public GenericOrderActionDemoRepository(UserConfigurationDemoBusinessRepository userConfigurationDemoBusinessRepository,
											  StatusDemoRepository statusRepository,
											  StatusConverter statusConverter,
											  AddressConverter addressConverter,
											  TaxConverter taxConverter,
											  OrderHistoryConverter orderHistoryConverter,
											  OrderConverter orderConverter,
											  OrderDetailConverter orderDetailConverter,
											  ClientConverter clientConverter,
											  OrderDemoRepository orderRepository,
											  OrderHistoryDemoRepository orderHistoryRepository,
											  OrderConfigurationDemoRepository orderConfigurationRepository,
											  OrderConfigurationConverter orderConfigurationConverter,
											  SaleOrderConverter saleOrderConverter,
											  SaleOrderDetailConverter saleOrderDetailConverter,
											  SaleOrderDemoRepository saleOrderRepository,
											  BranchRepository branchRepository,
											  BranchConverter branchConverter,
											  UserRepository userRepository,
											  UserConverter userConverter,
											  InvoiceReferenceDemoRepository invoiceReferenceRepository,
											  InvoiceReferenceConverter invoiceReferenceConverter,
											  ClientDemoRepository clientRepository,
											  CreditNoteDemoRepository creditNoteRepository,
											  CustomDSLDemoOrderRepository customDSLDemoOrderRepository) {
		super(TEMPLATES, GenericOrderActionDemoRepository.class, statusRepository,
				statusConverter, addressConverter,taxConverter,orderHistoryConverter,orderConverter,orderDetailConverter,clientConverter,
				orderConfigurationConverter,saleOrderConverter,saleOrderDetailConverter,saleOrderRepository,branchRepository ,branchConverter,userRepository,userConverter,
				creditNoteRepository,clientRepository,orderRepository,orderHistoryRepository,customDSLDemoOrderRepository,userConfigurationDemoBusinessRepository,orderConfigurationRepository );
	}

}
