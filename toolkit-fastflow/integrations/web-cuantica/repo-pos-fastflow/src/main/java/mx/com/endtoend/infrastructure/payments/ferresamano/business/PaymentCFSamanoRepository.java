package mx.com.endtoend.infrastructure.payments.ferresamano.business;

import java.util.*;

import mx.com.endtoend.infrastructure.payments.ferresamano.repositories.*;
import mx.com.endtoend.infrastructure.payments.common.business.BasePaymentBusinessRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.*;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.ferresamano.repositories.StatusFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ClientFSamanoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.openings.ferresamano.repositories.OpeningOperationFSamanoRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.OrderFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.ferresamano.repositories.OrderHistoryFSamanoRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.EmployeeFSamanoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class PaymentCFSamanoRepository extends BasePaymentBusinessRepository {

	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/ferresamano/ticket_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/ferresamano/logo_color_2.jpeg");
		TEMPLATES.put("company_name", "FERRETERÍA FERRESAMNO");
		TEMPLATES.put("business_name", "GRUPO FERRETERÍA FERRESAMANO");
	}

	public PaymentCFSamanoRepository(StatusConverter statusConverter,
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
									 InvoiceReferenceFSamanoRepository invoiceReferenceRepository,
									 CheckPaymentFSamanoRepository checkPaymentRepository,
									 BranchConverter branchConverter,
									 UserConverter userConverter,
									 CreditNotePaymentConverter creditNotePaymentConverter,
									 BranchRepository branchRepository,
									 OrderFSamanoRepository orderRepository,
									 UserRepository userRepository,
									 EmployeeFSamanoRepository employeeRepository,
									 PaymentFSamanoRepository paymentRepository,
									 PaymentCashFSamanoRepository paymentCashRepository,
									 OrderHistoryFSamanoRepository orderHistoryRepository,
									 StatusFSamanoRepository statusRepository,
									 OpeningOperationFSamanoRepository openingOperationCalRepository,
									 CreditCardPaymentFSamanoRepository creditCardPaymentRepository,
									 TransferPaymentFSamanoRepository transferPaymentRepository,
									 CreditNotePaymentFSamanoRepository creditNotePaymentRepository,
									 CustomDSLPaymentFSamanoRepository customDSLPaymentFraguaRepository,
									 ClientFSamanoRepository clientRepository,
									 CreditPaymentFSamanoRepository creditPaymentFSamanoRepositoryF,
									 CreditPaymentConverter creditPaymentConverter){
		super(PaymentCFSamanoRepository.class, TEMPLATES,
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
				creditPaymentFSamanoRepositoryF,
				clientRepository);
	}
}