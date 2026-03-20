package mx.com.endtoend.infrastructure.orders.carredana.business;

import mx.com.endtoend.infrastructure.orders.calzada.business.GenericOrderActionCalzadaRepository;
import mx.com.endtoend.infrastructure.orders.common.business.BaseOrderActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.repositories.StatusFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.carredana.repositories.ClientFCarRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.carredana.repositories.CreditNoteFCarRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.carredana.repositories.OrderConfigurationFCarRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.CustomDSLFCarredanaOrderRepository;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.OrderFCarRepository;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.OrderHistoryFCarRepository;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.SaleOrderFCarRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.carredana.repositories.InvoiceReferenceFCarRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.business.UserConfigurationCarredanaRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter; 
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;


@Service
public class GenericOrderActionFCarredanaRepository extends BaseOrderActionRepository {


	 static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("logo_ticket", "/tickets/carredana/logo_color_2.jpeg");
		TEMPLATES.put("ticket_template", "/tickets/carredana/ticket_carredana.jasper");
		TEMPLATES.put("document_template", "/orders/carredana/Order_report.jasper");
		TEMPLATES.put("logo_document", "/orders/carredana/logo_color_2.jpeg");
		TEMPLATES.put("logo_watermark_document", "/orders/carredana/logo_b_n.jpeg");
		TEMPLATES.put("quote_template", "/orders/carredana/Quote_order_report.jasper");
	}

	public GenericOrderActionFCarredanaRepository(UserConfigurationCarredanaRepository userConfigurationCalzadaRepository,
												  StatusFCarRepository statusRepository,
												  StatusConverter statusConverter,
												  AddressConverter addressConverter,
												  TaxConverter taxConverter,
												  OrderHistoryConverter orderHistoryConverter,
												  OrderConverter orderConverter,
												  OrderDetailConverter orderDetailConverter,
												  ClientConverter clientConverter,
												  OrderFCarRepository orderRepository,
												  OrderHistoryFCarRepository orderHistoryRepository,
												  OrderConfigurationFCarRepository orderConfigurationRepository,
												  OrderConfigurationConverter orderConfigurationConverter,
												  SaleOrderConverter saleOrderConverter,
												  SaleOrderDetailConverter saleOrderDetailConverter,
												  SaleOrderFCarRepository saleOrderRepository,
												  BranchRepository branchRepository,
												  BranchConverter branchConverter,
												  UserRepository userRepository,
												  UserConverter userConverter,
												  InvoiceReferenceFCarRepository invoiceReferenceRepository,
												  InvoiceReferenceConverter invoiceReferenceConverter,
												  ClientFCarRepository clientRepository,
												  CreditNoteFCarRepository creditNoteRepository,
												  CustomDSLFCarredanaOrderRepository customDSLFCarredanaOrderRepository) {
		super(TEMPLATES, GenericOrderActionCalzadaRepository.class, statusRepository,
				statusConverter, addressConverter,taxConverter,orderHistoryConverter,orderConverter,orderDetailConverter,clientConverter,
				orderConfigurationConverter,saleOrderConverter,saleOrderDetailConverter,saleOrderRepository,branchRepository ,branchConverter,userRepository,userConverter,
				creditNoteRepository,clientRepository,orderRepository,orderHistoryRepository,customDSLFCarredanaOrderRepository,userConfigurationCalzadaRepository,orderConfigurationRepository );
	}

}
