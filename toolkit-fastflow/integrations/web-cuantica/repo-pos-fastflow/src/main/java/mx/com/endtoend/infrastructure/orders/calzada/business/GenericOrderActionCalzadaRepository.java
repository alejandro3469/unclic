package mx.com.endtoend.infrastructure.orders.calzada.business;

import mx.com.endtoend.infrastructure.orders.calzada.repositories.SaleOrderRepository;
import mx.com.endtoend.infrastructure.orders.common.business.BaseOrderActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.StatusRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.calzada.repositories.CreditNoteRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.calzada.repositories.OrderConfigurationRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.CustomDSLFCalzadaOrderRepository;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.OrderHistoryRepository;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.OrderRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.business.UserConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;


@Service
public class GenericOrderActionCalzadaRepository extends BaseOrderActionRepository {

	private static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("logo_ticket", "/tickets/calzada/logo.jpg");
		TEMPLATES.put("ticket_template", "/tickets/calzada/ticket_calzada.jasper");
		TEMPLATES.put("document_template", "/orders/calzada/Orden_report.jasper");
		TEMPLATES.put("logo_document", "/orders/calzada/logo.jpg");
		TEMPLATES.put("logo_watermark_document", "/orders/calzada/marcaAgua.jpg");
		TEMPLATES.put("quote_template", "/orders/calzada/Quote_order_report.jasper");
	}

	@Autowired
	public GenericOrderActionCalzadaRepository(StatusRepository statusRepository,StatusConverter statusConverter,
												AddressConverter addressConverter,
												TaxConverter taxConverter,
												OrderHistoryConverter orderHistoryConverter,
												OrderConverter orderConverter,
												OrderDetailConverter orderDetailConverter,
												ClientConverter clientConverter,
												OrderRepository orderRepository,
												OrderHistoryRepository orderHistoryRepository,
												OrderConfigurationRepository orderConfigurationRepository,
												OrderConfigurationConverter orderConfigurationConverter,
											   UserConfigurationCalzadaRepository userConfigurationCalzadaRepository,
											   SaleOrderConverter saleOrderConverter,
											   SaleOrderDetailConverter saleOrderDetailConverter,
											   SaleOrderRepository saleOrderRepository,
											   BranchRepository branchRepository,
											   BranchConverter branchConverter,
											   UserRepository userRepository,
											   UserConverter userConverter,
											   CreditNoteRepository creditNoteRepository,
											   ClientRepository clientRepository,
											   CustomDSLFCalzadaOrderRepository customDSLFCalzadaOrderRepository
			) {
		super(TEMPLATES, GenericOrderActionCalzadaRepository.class,statusRepository,
				statusConverter, addressConverter,taxConverter,orderHistoryConverter,orderConverter,orderDetailConverter,clientConverter,
				orderConfigurationConverter,saleOrderConverter,saleOrderDetailConverter,saleOrderRepository,branchRepository ,branchConverter,userRepository,userConverter,
				creditNoteRepository,clientRepository,orderRepository,orderHistoryRepository,customDSLFCalzadaOrderRepository,userConfigurationCalzadaRepository,orderConfigurationRepository);
	}
}
