package mx.com.endtoend.infrastructure.orders.calzada.fragua.business;

import java.util.*;

import mx.com.endtoend.infrastructure.orders.common.business.BaseOrderActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.StatusRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.ClientFraguaRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.creditNote.calzada.fragua.repositories.CreditNoteCFraguaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua.repositories.OrderConfigurationFraguaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.converters.OrderConfigurationConverter;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.CustomDSLCFraguaOrderRepository;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.OrderFraguaRepository;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.OrderHistoryFraguaRepository;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.SaleOrderFraguaRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.SaleOrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.business.UserConfigFraguaRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class GenericOrderActionSFraguaRepository extends BaseOrderActionRepository {

	 static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("logo_ticket", "/tickets/fragua/logo.jpg");
		TEMPLATES.put("ticket_template", "/tickets/fragua/ticket_calzada.jasper");
		TEMPLATES.put("document_template", "/orders/fragua/Order_report.jasper");
		TEMPLATES.put("logo_document", "/orders/fragua/logo.jpg");
		TEMPLATES.put("logo_watermark_document","/orders/fragua/marcaAgua.jpg");
		TEMPLATES.put("quote_template", "/orders/fragua/Quote_order_report.jasper");
	}

	public GenericOrderActionSFraguaRepository(UserConfigFraguaRepository userConfigurationCalzadaRepository,
											   StatusRepository statusRepository,
											   StatusConverter statusConverter,
											   AddressConverter addressConverter,
											   TaxConverter taxConverter,
											   OrderHistoryConverter orderHistoryConverter,
											   OrderConverter orderConverter,
											   OrderDetailConverter orderDetailConverter,
											   ClientConverter clientConverter,
											   OrderFraguaRepository orderRepository,
											   OrderHistoryFraguaRepository orderHistoryRepository,
											   OrderConfigurationFraguaRepository orderConfigurationRepository,
											   OrderConfigurationConverter orderConfigurationConverter,
											   SaleOrderConverter saleOrderConverter,
											   SaleOrderDetailConverter saleOrderDetailConverter,
											   SaleOrderFraguaRepository saleOrderRepository,
											   BranchRepository branchRepository,
											   BranchConverter branchConverter,
											   UserRepository userRepository,
											   UserConverter userConverter,
											   CreditNoteCFraguaRepository creditNoteRepository,
											   ClientFraguaRepository clientRepository,
											   CustomDSLCFraguaOrderRepository customDSLCFraguaOrderRepository){
		super(TEMPLATES, GenericOrderActionSFraguaRepository.class, statusRepository,
				statusConverter, addressConverter,taxConverter,orderHistoryConverter,orderConverter,orderDetailConverter,clientConverter,
				orderConfigurationConverter,saleOrderConverter,saleOrderDetailConverter,saleOrderRepository,branchRepository ,branchConverter,userRepository,userConverter,
				creditNoteRepository,clientRepository,orderRepository,orderHistoryRepository,customDSLCFraguaOrderRepository,userConfigurationCalzadaRepository,orderConfigurationRepository);
	}

}
