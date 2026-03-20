package mx.com.endtoend.infrastructure.payments.carredana.business;

import java.util.*;

import mx.com.endtoend.infrastructure.payments.carredana.repositories.*;
import mx.com.endtoend.infrastructure.payments.common.business.BasePaymentBusinessRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.*;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.repositories.StatusFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.carredana.repositories.ClientFCarRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.openings.carredana.repositories.OpeningOperationFCarRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.OrderFCarRepository;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.OrderHistoryFCarRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.EmployeeCarredanaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class PaymentFCarredanaRepository extends BasePaymentBusinessRepository {

	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/carredana/ticket_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/carredana/logo_color_2.jpeg");
		TEMPLATES.put("company_name", "FERRETERÍA LA CARREDANA");
		TEMPLATES.put("business_name", "GRUPO FERRETERÍA LA CARREDANA");
	}

	public PaymentFCarredanaRepository(StatusConverter statusConverter,
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
									   InvoiceReferenceFCarRepository invoiceReferenceRepository,
									   CheckPaymentFCarRepository checkPaymentRepository,
									   BranchConverter branchConverter,
									   UserConverter userConverter,
									   CreditNotePaymentConverter creditNotePaymentConverter,
									   BranchRepository branchRepository,
									   OrderFCarRepository orderRepository,
									   UserRepository userRepository,
									   EmployeeCarredanaRepository employeeRepository,
									   PaymentFCarRepository paymentRepository,
									   PaymentCashFCarRepository paymentCashRepository,
									   OrderHistoryFCarRepository orderHistoryRepository,
									   StatusFCarRepository statusRepository,
									   OpeningOperationFCarRepository openingOperationCalRepository,
									   CreditCardPaymentFCarRepository creditCardPaymentRepository,
									   TransferPaymentFCarRepository transferPaymentRepository,
									   CreditNotePaymentFCarRepository creditNotePaymentRepository,
									   CustomDSLPaymentFCarredanaRepository customDSLPaymentFraguaRepository,
									   ClientFCarRepository clientRepository,
									   CreditPaymentConverter creditPaymentConverter,
									   CreditPaymentFCarRepository creditPaymentFraRepository){
		super(PaymentFCarredanaRepository.class, TEMPLATES,
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
				customDSLPaymentFraguaRepository,
				creditPaymentFraRepository,
				clientRepository);
		
	}
}