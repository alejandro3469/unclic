package mx.com.endtoend.infrastructure.payments.carredana.zapata.business;

import java.util.*;

import mx.com.endtoend.infrastructure.payments.carredana.zapata.repositories.*;
import mx.com.endtoend.infrastructure.payments.common.business.BasePaymentBusinessRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.*;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.zapata.repositories.StatusCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.ClientCZapataRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.openings.carredana.zapata.repositories.OpeningOperationCZapataRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories.OrderCZapataRepository;
import mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories.OrderHistoryCZapataRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.zapata.repositories.EmployeeCZapataRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class PaymentZapataRepository extends BasePaymentBusinessRepository {
 
	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/zapata/ticket_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/zapata/logo_color_2.png");
		TEMPLATES.put("company_name", "FERRETERÍA ZAPATA");
		TEMPLATES.put("business_name", "GRUPO FERRETERÍA ZAPATA");
	}

	public PaymentZapataRepository(
			StatusConverter statusConverter,
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
			InvoiceReferenceCZapataRepository invoiceReferenceRepository,
			CheckPaymentCZapataRepository checkPaymentRepository,
			BranchConverter branchConverter,
			UserConverter userConverter,
			CreditNotePaymentConverter creditNotePaymentConverter,
			BranchRepository branchRepository,
			OrderCZapataRepository orderRepository,
			UserRepository userRepository,
			EmployeeCZapataRepository employeeRepository,
			PaymentCZapataRepository paymentRepository,
			PaymentCashCZapataRepository paymentCashRepository,
			OrderHistoryCZapataRepository orderHistoryRepository,
			StatusCZapataRepository statusRepository,
			OpeningOperationCZapataRepository openingOperationCalRepository,
			CreditCardPaymentCZapataRepository creditCardPaymentRepository,
			TransferPaymentCZapataRepository transferPaymentRepository,
			CreditNotePaymentCZapataRepository creditNotePaymentRepository,
			CustomDSLPaymentCZapataRepository customDSLPaymentFraguaRepository,
			ClientCZapataRepository clientRepository,
			CreditPaymentCZapataRepository creditPaymentCZapataRepository,
			CreditPaymentConverter creditPaymentConverter) {
		super(PaymentZapataRepository.class, TEMPLATES,
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
				creditPaymentCZapataRepository,
				clientRepository);
	}
}