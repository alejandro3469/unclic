package mx.com.endtoend.infrastructure.payments.demo.business;

import java.util.*;

import mx.com.endtoend.infrastructure.payments.demo.repositories.*;
import mx.com.endtoend.infrastructure.payments.common.business.BasePaymentBusinessRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.*;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.demo.repositories.StatusDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.demo.repositories.ClientDemoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.openings.demo.repositories.OpeningOperationDemoRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.orders.demo.repositories.OrderDemoRepository;
import mx.com.endtoend.infrastructure.orders.demo.repositories.OrderHistoryDemoRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.EmployeeDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class PaymentDemoBusinessRepository extends BasePaymentBusinessRepository {

	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/demo/ticket_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/demo/logo_color_2.jpeg");
		TEMPLATES.put("company_name", "COMPAÑÍA DEMO");
		TEMPLATES.put("business_name", "GRUPO COMPAÑÍA DEMO");
	}

	public PaymentDemoBusinessRepository(StatusConverter statusConverter,
								   TaxConverter taxConverter,
								   AddressConverter addressConverter,
								   OrderDetailConverter orderDetailConverter,
								   OrderConverter orderConverter,
								   ClientConverter clientConverter,
								   SaleTypeConverter saleTypeConverter,
								   PriceTypeConverter priceTypeConverter,
								   RoleJobTypeConverter roleJobTypeConverter,
								   UserConfigurationConverter userConfigurationConverter,
								   WarehouseOptionConverter warehouseOptionConverter,
								   EmployeeConverter employeeConverter,
								   PaymentConverter paymentConverter,
								   PaymentCashConverter paymentCashConverter,
								   OrderHistoryConverter orderHistoryConverter,
								   OpeningOperationConverter openingOperationConverter,
								   CreditCardPaymentConverter creditCardPaymentConverter,
								   TransferPaymentConverter transferPaymentConverter,
								   CheckPaymentConverter checkPaymentConverter,
								   InvoiceReferenceConverter invoiceReferenceConverter,
								   InvoiceReferenceDemoRepository invoiceReferenceRepository,
								   CheckPaymentDemoRepository checkPaymentRepository,
								   BranchConverter branchConverter,
								   UserConverter userConverter,
								   CreditNotePaymentConverter creditNotePaymentConverter,
								   BranchRepository branchRepository,
								   OrderDemoRepository orderRepository,
								   UserRepository userRepository,
								   EmployeeDemoRepository employeeRepository,
								   PaymentDemoRepository paymentRepository,
								   PaymentCashDemoRepository paymentCashRepository,
								   OrderHistoryDemoRepository orderHistoryRepository,
										 StatusDemoRepository statusRepository,
										 OpeningOperationDemoRepository openingOperationCalRepository,
								   CreditCardPaymentDemoRepository creditCardPaymentRepository,
								   TransferPaymentDemoRepository transferPaymentRepository,
								   CreditNotePaymentDemoRepository creditNotePaymentRepository,
								   CustomDSLPaymentDemoRepository customDSLPaymentDemoRepository,
								   ClientDemoRepository clientRepository,
								   CreditPaymentConverter creditPaymentConverter,
								   CreditPaymentDemoRepository creditPaymentRepository){
		super(PaymentDemoBusinessRepository.class, TEMPLATES,
				statusConverter,
				taxConverter,
				addressConverter,
				orderDetailConverter,
				orderConverter,
				clientConverter,
				saleTypeConverter,
				priceTypeConverter,
				roleJobTypeConverter,
				userConfigurationConverter,
				warehouseOptionConverter,
				employeeConverter,
				paymentConverter,
				paymentCashConverter,
				orderHistoryConverter,
				openingOperationConverter,
				creditCardPaymentConverter,
				transferPaymentConverter,
				userConverter,
				branchConverter,
				creditNotePaymentConverter,
				checkPaymentConverter,
				creditPaymentConverter,
				checkPaymentRepository,
				branchRepository,
				orderRepository,
				userRepository,
				employeeRepository,
				paymentRepository,
				paymentCashRepository,
				orderHistoryRepository,
				statusRepository,
				openingOperationCalRepository,
				creditCardPaymentRepository,
				transferPaymentRepository,
				creditNotePaymentRepository,
				customDSLPaymentDemoRepository,
				creditPaymentRepository,
				clientRepository);
		
	}
}