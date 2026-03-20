package mx.com.endtoend.infrastructure.orders.ferresamano.business;

import java.util.*;

import mx.com.endtoend.infrastructure.orders.calzada.fragua.business.GenericOrderActionSFraguaRepository;
import mx.com.endtoend.infrastructure.orders.common.business.BaseOrderActionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.ferresamano.repositories.StatusFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ClientFSamanoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.ferresamano.repositories.CreditNoteFSamanoRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.ferresamano.repositories.OrderConfigurationFSamanoRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.CustomDSLFSamanoOrderRepository;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.OrderFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.OrderHistoryFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.SaleOrderFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.ferresamano.repositories.InvoiceReferenceFSamanoRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.InvoiceReferenceConverter;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.business.UserConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class GenericOrderActionCFSamanoRepository extends BaseOrderActionRepository {

	 static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("logo_ticket", "/tickets/ferresamano/logo_color_2.jpeg");
		TEMPLATES.put("ticket_template","/tickets/ferresamano/ticket_carredana.jasper");
		TEMPLATES.put("document_template", "/orders/ferresamano/Order_report.jasper");
		TEMPLATES.put("logo_document", "/orders/ferresamano/logo_color_2.jpeg");
		TEMPLATES.put("logo_watermark_document","/orders/ferresamano/logo_b_n.jpeg");
		TEMPLATES.put("quote_template", "/orders/ferresamano/Quote_order_report.jasper");
	}



	public GenericOrderActionCFSamanoRepository(UserConfigurationCFSamanoRepository userConfigurationCalzadaRepository,
												StatusFSamanoRepository statusRepository,
												StatusConverter statusConverter,
												AddressConverter addressConverter,
												TaxConverter taxConverter,
												OrderHistoryConverter orderHistoryConverter,
												OrderConverter orderConverter,
												OrderDetailConverter orderDetailConverter,
												ClientConverter clientConverter,
												OrderFSamanoRepository orderRepository,
												OrderHistoryFSamanoRepository orderHistoryRepository,
												OrderConfigurationFSamanoRepository orderConfigurationRepository,
												OrderConfigurationConverter orderConfigurationConverter,
												SaleOrderConverter saleOrderConverter,
												SaleOrderDetailConverter saleOrderDetailConverter,
												SaleOrderFSamanoRepository saleOrderRepository,
												BranchRepository branchRepository,
												BranchConverter branchConverter,
												UserRepository userRepository,
												UserConverter userConverter,
												InvoiceReferenceFSamanoRepository invoiceReferenceRepository,
												InvoiceReferenceConverter invoiceReferenceConverter,
												ClientFSamanoRepository clientRepository,
												CreditNoteFSamanoRepository creditNoteRepository,
												CustomDSLFSamanoOrderRepository customDSLFSamanoOrderRepository){
		super(TEMPLATES, GenericOrderActionCFSamanoRepository.class, statusRepository,
				statusConverter, addressConverter,taxConverter,orderHistoryConverter,orderConverter,orderDetailConverter,clientConverter,
				orderConfigurationConverter,saleOrderConverter,saleOrderDetailConverter,saleOrderRepository,branchRepository ,branchConverter,userRepository,userConverter,
				creditNoteRepository,clientRepository,orderRepository,orderHistoryRepository,customDSLFSamanoOrderRepository,userConfigurationCalzadaRepository,orderConfigurationRepository);
	}
}
