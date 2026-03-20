package mx.com.endtoend.infrastructure.payments.calzada.business;

import java.util.*;

import mx.com.endtoend.infrastructure.payments.common.business.BasePaymentBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.StatusRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.openings.calzada.repositories.OpeningOperationCalRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.OrderHistoryRepository;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.OrderRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.CheckPaymentRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.CreditCardPaymentRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.CreditNotePaymentRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.CreditPaymentRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.CustomDSLPaymentRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.PaymentCashRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.PaymentRepository;
import mx.com.endtoend.infrastructure.payments.calzada.repositories.TransferPaymentRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.CheckPaymentConverter;
import mx.com.endtoend.infrastructure.payments.common.converters.CreditCardPaymentConverter;
import mx.com.endtoend.infrastructure.payments.common.converters.CreditNotePaymentConverter;
import mx.com.endtoend.infrastructure.payments.common.converters.CreditPaymentConverter;
import mx.com.endtoend.infrastructure.payments.common.converters.PaymentCashConverter;
import mx.com.endtoend.infrastructure.payments.common.converters.PaymentConverter;
import mx.com.endtoend.infrastructure.payments.common.converters.TransferPaymentConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.EmployeeRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class PaymentCalzadaRepository extends BasePaymentBusinessRepository {
 
	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>(); 
		TEMPLATES.put("ticket_template", "/tickets/calzada/ticket_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/calzada/logo.jpg");
		TEMPLATES.put("company_name", "FERRETERÍA CALZADA");
		TEMPLATES.put("business_name", "GRUPO FERRETERÍA CALZADA"); 
	}

	public PaymentCalzadaRepository(StatusConverter statusConverter,
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
									UserConverter userConverter,
									BranchConverter branchConverter,
									CreditNotePaymentConverter creditNotePaymentConverter,
									CheckPaymentConverter checkPaymentConverter,
									CheckPaymentRepository checkPaymentRepository,
									BranchRepository branchRepository,
									OrderRepository orderRepository,
									UserRepository userRepository,
									EmployeeRepository employeeRepository,
									PaymentRepository paymentRepository,
									PaymentCashRepository paymentCashRepository,
									OrderHistoryRepository orderHistoryRepository,
									StatusRepository statusRepository,
									OpeningOperationCalRepository openingOperationCalRepository,
									CreditCardPaymentRepository creditCardPaymentRepository,
									TransferPaymentRepository transferPaymentRepository,
									CreditNotePaymentRepository creditNotePaymentRepository,
									CustomDSLPaymentRepository customDSLPaymentRepository,
									CreditPaymentConverter creditPaymentConverter,
									CreditPaymentRepository creditPaymentRepository,
									ClientRepository clientRepository){
		super(PaymentCalzadaRepository.class,TEMPLATES,
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
				 customDSLPaymentRepository,
				 creditPaymentRepository,
				 clientRepository);
	}

}