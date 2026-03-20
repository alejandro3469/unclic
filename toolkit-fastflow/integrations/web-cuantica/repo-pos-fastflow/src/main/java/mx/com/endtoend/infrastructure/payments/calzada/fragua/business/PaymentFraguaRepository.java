package mx.com.endtoend.infrastructure.payments.calzada.fragua.business;

import java.util.*;

import mx.com.endtoend.infrastructure.payments.calzada.fragua.repositories.*;
import mx.com.endtoend.infrastructure.payments.common.business.BasePaymentBusinessRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.*;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.fragua.repositories.StatusFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.ClientFraguaRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.openings.calzada.fragua.repositories.OpeningOperationFraRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.OrderFraguaRepository;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.OrderHistoryFraguaRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderHistoryConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.repositories.EmployeeFraguaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

@Service
public class PaymentFraguaRepository extends BasePaymentBusinessRepository {
 
	static final Map<String, String> TEMPLATES;
	static {
		TEMPLATES = new HashMap<>();
		TEMPLATES.put("ticket_template", "/tickets/calzada/ticket_cobro.jasper");
		TEMPLATES.put("logo_report", "/tickets/fragua/logo.jpg");
		TEMPLATES.put("company_name", "FERRETERÍA LA FRAGUA");
		TEMPLATES.put("business_name", "GRUPO FERRETERÍA LA FRAGUA");
	}

	public PaymentFraguaRepository(StatusConverter statusConverter,
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
								   BranchConverter branchConverter,
								   UserConverter userConverter,
								   CreditNotePaymentConverter creditNotePaymentConverter,
								   CheckPaymentConverter checkPaymentConverter,
								   CheckPaymentFraRepository checkPaymentRepository,
								   BranchRepository branchRepository,
								   OrderFraguaRepository orderRepository,
								   UserRepository userRepository,
								   EmployeeFraguaRepository employeeRepository,
								   PaymentFraRepository paymentRepository,
								   PaymentCashFraRepository paymentCashRepository,
								   OrderHistoryFraguaRepository orderHistoryRepository,
								   StatusFraguaRepository statusRepository,
								   OpeningOperationFraRepository openingOperationCalRepository,
								   CreditCardPaymentFraRepository creditCardPaymentRepository,
								   TransferPaymentFraRepository transferPaymentRepository,
								   CreditNotePaymentFraRepository creditNotePaymentRepository,
								   CustomDSLPaymentFraguaRepository customDSLPaymentFraguaRepository,
								   ClientFraguaRepository clientRepository,
								   CreditPaymentConverter creditPaymentConverter,
								   CreditPaymentFraRepository creditPaymentFraRepository){
		super(PaymentFraguaRepository.class, TEMPLATES,
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