package mx.com.endtoend.infrastructure.security;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import mx.com.endtoend.infrastructure.commons.constants.PermissionEnum;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.headers(headers -> headers.frameOptions().disable());

		/*
		 * Se deberan de registrar todas las url y el permiso correspondiente
		 */

		// ACCESOS EXCLUSIVOS PARA GESTION DE ETE
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/ete/users/create")
				.hasAnyAuthority(PermissionEnum.ETE_CREATE_USER.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/ete/users/update/{id}")
				.hasAnyAuthority(PermissionEnum.ETE_UPDATE_USER_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/ete/users/enabled/{status:enable|disable}/{id}")
						.hasAnyAuthority(PermissionEnum.ETE_ENABLED_USER_BYID.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/ete/users/view/{id}")
				.hasAnyAuthority(PermissionEnum.ETE_VIEW_USER_BYID.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/ete/users/view/active/{company}")
				.hasAnyAuthority(PermissionEnum.ETE_VIEW_ACTIVE_USERS.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/ete/users/view/inactive/{company}")
				.hasAnyAuthority(PermissionEnum.ETE_VIEW_IANCTIVE_USERS.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/ete/users/find-by-params/{pageNumber}/{rows}")
						.hasAnyAuthority(PermissionEnum.ETE_VIEW_IANCTIVE_USERS.toString(),
								PermissionEnum.ETE_VIEW_ACTIVE_USERS.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/company/create")
				.hasAnyAuthority(PermissionEnum.ETE_CREATE_COMPANY.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.PUT, "/company/update/{id}")
				.hasAnyAuthority(PermissionEnum.ETE_UPDATE_COMPANY_BYID.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/company/list")
				.hasAnyAuthority(PermissionEnum.ETE_VIEW_ALL_COMPANIES.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/company/view/{id}")
				.hasAnyAuthority(PermissionEnum.ETE_VIEW_COMPANY_BYID.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/company/view/methods/{module}")
				.hasAnyAuthority(PermissionEnum.ETE_VIEW_METHODS_BY_MODULE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/company/view/permissions/company")
				.hasAnyAuthority(PermissionEnum.ETE_CREATE_COMPANY.toString(),
				PermissionEnum.VIEW_COMPANY_PANEL_MANAGEMENT.toString()));

		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/strategy/list")
				.hasAnyAuthority(PermissionEnum.ETE_STARTEGY_LIST.toString()));

		// ACCESOS PARA GESTION DE SUCURSALES
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/branch/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.BRANCHES_CREATE.toString(),
						PermissionEnum.UPDATE_EXTERNAL_SERVICE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/branch/update/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.BRANCHES_UPDATE.toString(),
						PermissionEnum.UPDATE_EXTERNAL_SERVICE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/branch/list/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.BRANCHES_VIEW_ALL.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/branch/view/{branchCode}/{id}")
				.hasAnyAuthority(PermissionEnum.BRANCHES_VIEW_BYID.toString()));

		// ACCESOS PARA GESTION DE ROLES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/roles/create/{company}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ROLES_CREATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/roles/update/{id}/{company}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ROLES_UPDATE_BYID.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/roles/enabled/{id}/{status:enable|disable}/{company}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ROLES_ENABLED_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/roles/view/{id}/{company}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ROLES_VIEW_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/roles/view/active/{company}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ROLES_VIEW_ACTIVE_LIST.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/roles/view/inactive/{company}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ROLES_VIEW_INACTIVE_LIST.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/roles/view/permissions/company/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PERMISSIONS_VIEW_CLIENT_LIST.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/roles/view/permissions/admin/{company}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PERMISSIONS_VIEW_ADMIN_LIST.toString()));

		// ACCESOS PARA LA GESTION DE LA CONFIGURACION DE EMPLEADOS
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/user-config/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_CREATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/user-config/update/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_UPDATE_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/user-config/view/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/user-config/prices/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
								PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/user-config/sale/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
								PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/user-config/credit-note/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
								PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/user-config/role-job/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
								PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/user-config/user-warehouses/{email}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
						PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/user-config/user-prices/{email}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
						PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/user-config/user-orders/{email}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
						PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/user-config/user-credit-note/{email}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString(),
						PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS_OPERATIONAL.toString()));

		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/user-config/employee/staff/{operativeRole}/{branchCode}/{companyCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/user-config/employee/boss/{operativeRole}/{branchCode}/{companyCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CONFIG_VIEW_GENERIC_LISTS.toString()));

		// ACCESOS PARA GESTION DE USUARIOS
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/users/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_CREATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/users/update/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_UPDATE_BYID.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/users/enabled/{id}/{status:enable|disable}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_ENABLED_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/users/view/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_VIEW_BYID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/users/view/active/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_VIEW_ACTIVE_LIST.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/users/view/inactive/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.USERS_VIEW_INACTIVE_LIST.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT,
						"/users/session-status/{status:enable|disable}/{email}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_CHANGE_STATUS.toString(),
						PermissionEnum.USERS_CHANGE_STATUS_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/users/find-by-params/{pageNumber}/{rows}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.USERS_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.USERS_VIEW_INACTIVE_LIST.toString()));

		// ACCESOS PARA GESTION DE CLIENTES
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/client/find-by-params/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CLIENT_LIST.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/client/view/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CLIENT_LIST_ID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/client/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CLIENT_CREATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/client/update/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CLIENT_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/client/update/shipping-address/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLIENT_SHIPPINGADDRESS.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/client/create/shipping-address/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLIENT_SHIPPINGADDRESS.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/client/view/list/shipping-address/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLIENT_SHIPPINGADDRESS.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/client/shipping-address/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLIENT_SHIPPINGADDRESS.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/client/view/all/email/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLIENT_LIST_ID.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/client/global-client/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CLIENT_LIST_ID.toString()));

		// ACCESOS PARA GESTION DE CATALOGOS CLIENTES
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/client-type/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/coordinate/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/delegation/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/flat/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/how-to-contact/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/state/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/work-type/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/country/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/update/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue/address-colony/{companyCode}/{branchCode}/{cp}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue/address/{companyCode}/{branchCode}/{colony}/{cp}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue/address-delegation/{companyCode}/{branchCode}/{state}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/status/{companyCode}/{branchCode}/{id}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/status/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/status/list/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/cfdi/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue/regime-fiscal/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue/category/{categoryCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));

		// ACCESOS PARA GESTION DE CATALOGOS - CLIENTES
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue-client/cfdi/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-client/regime-fiscal/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-client/client-type/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-client/contact-method/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-client/work-type/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/catalogue-client/update/{catalogType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));

		// ACCESOS PARA GESTION DE CATALOGOS - ARTICULOS
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue-article/brand/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-article/category/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-article/division/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue-article/family/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/catalogue-article/update/{catalogType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));

		// ACCESOS PARA GESTION DE CATALOGOS - DIRECCIONES
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-direction/coordinate/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/catalogue-direction/flat/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-direction/country/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-direction/state/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/catalogue-direction/colony/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-direction/municipality/{stateCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/catalogue-direction/update/{catalogType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CATALOGUE.toString()));

		// ACCESOS PARA GESTION DE ALMACENES
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/warehouse/view/{companyCode}")
				.hasAnyAuthority(PermissionEnum.WAREHOUSE_LIST.toString()));

		// ACCESOS PARA BUSQUEDA DE ARTICULOS
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/articles/find-by-params/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ARTICLES_FIND_BY_PARAMS.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/articles/find-by-scanner/{companyCode}/{branchCode}")
				.permitAll());
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/articles/convertion-factor/{articleNumber}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ARTICLES_FIND_BY_PARAMS.toString()));

		// ACCESOS PARA GESTION DE ORDENES
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/order/create/{mailUsuario}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/order/update/{id}/{mailUsuario}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/order/view/update/{orderNumber}/{orderType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT,
						"/order/cancel/update/{orderNumber}/{email}/{orderType}/{branchCode}/{companyCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/order/find-by-params/{email}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_SERCH.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/order/view/{orderNumber}/{orderType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_VIEW_DETAIL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/order/view/historical-detail/{orderNumber}/{orderType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_VIEW_HISTORICAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/order/cancel/{orderNumber}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_MANUAL_CANCEL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/order/approve/{orderNumber}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/order/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_VIEW_DETAIL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/order/pdf/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ORDERS_VIEW_DETAIL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/order/send/pdf/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ORDERS_VIEW_DETAIL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/order/document/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_VIEW_DETAIL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/order/convert/{orderCode}/{mailUsuario}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_CONVERT.toString()));

		// ACCESOS PARA GESTION DE CONFIGURACION DE ORDENES
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/order-config/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ORDERS_CONFIG_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/order-config/update/{orderId}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_CONFIG_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/order-config/view/{orderId}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_CONFIG_VIEW_DETAIL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/order-config/view/order-code/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.ORDERS_CONFIG_VIEW_DETAIL.toString(),
				PermissionEnum.ORDERS_VIEW_DETAIL.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/order-config/view/all/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ORDERS_CONFIG_VIEW_LIST.toString()));

		// ACCESOS PARA LA GESTION DE ARTICULOS PERSONALIZADOS - NO INVENTARIABLES
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/custom-article/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/custom-article/update/{companyCode}/{branchCode}/{id}")
				.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT,
						"/custom-article/enabled/{id}/{companyCode}/{branchCode}/{status:enable|disable}")
				.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_ENABLED_BYID.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/custom-article/view/active/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.CUST_ARTICLES_VIEW_ACTIVE_LIST_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/custom-article/view/inactive/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_VIEW_INACTIVE_LIST.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/custom-article/view/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_VIEW_BYID.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/custom-article/viwe/sale-type/{saleType}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CUST_ARTICLES_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.CUST_ARTICLES_VIEW_ACTIVE_LIST_OPERATIONAL.toString()));

		// ACCESOS PARA LA ADMINISTRACIÓN DE INSTRUMENTOS DE PAGO-APERTURAS
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/opening-instrument/create/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPEN_PAYMENT_INSTRUMENT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/opening-instrument/update/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPEN_PAYMENT_INSTRUMENT_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/opening-instrument/view/active/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPEN_PAYMENT_INSTRUMENT_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.OPEN_PAYMENT_INSTRUMENT_VIEW_ACTIVE_LIST_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/opening-instrument/view/inactive/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPEN_PAYMENT_INSTRUMENT_VIEW_INACTIVE_LIST.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/opening-instrument/view/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPEN_PAYMENT_INSTRUMENT_VIEW_BYID.toString()));

		// ACCESOS PARA LA ADMINISTRACION DE COBROS
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/payment/order-status/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PAYMENT_SEARCH_ORDER.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/payment/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PAYMENT_CREATE_ORDER.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/payment/cancel/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PAYMENT_MANUAL_CANCEL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/payment/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYMENT_CREATE_ORDER.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/payment/ticket-reprint/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYMENT_REPRINT_TICKET.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/payment/search-summary/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PAYMENT_SEARCH.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/payment/search-detail/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PAYMENT_SEARCH.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/payment/approve/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.PAYMENT_CREATE_ORDER.toString()));

		// ACCESOS PARA LA ADMINISTRACION DE APERTURAS DE OPERACINES
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/opening-operation/create/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPENING_OPERATION_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/opening-operation/status/{employeeEmail}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.OPENING_OPERATION_CREATE.toString()));

		// ACCESOS PARA LA ADMINISTRACIÓN DE INSTRUMENTOS DE PAGO-CIERRRES
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/closing-instrument/create/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLOSE_PAYMENT_INSTRUMENT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/closing-instrument/update/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLOSE_PAYMENT_INSTRUMENT_UPDATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/closing-instrument/view/active/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLOSE_PAYMENT_INSTRUMENT_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.CLOSE_PAYMENT_INSTRUMENT_VIEW_ACTIVE_LIST_OPERATIONAL.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/closing-instrument/view/inactive/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLOSE_PAYMENT_INSTRUMENT_VIEW_INACTIVE_LIST.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/closing-instrument/view/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLOSE_PAYMENT_INSTRUMENT_VIEW_BYID.toString()));

		// ACCESOS PARA LA ADMINISTRACION DE CIERRES DE OPERACINES
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/closing-operation/create/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CLOSING_OPERATION_CREATE.toString()));

		// ACCESOS PARA LA ADMINISTRACION DEL CATALOGO DE TARJETAS DE CREDITO
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/credit-card/reference/create/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CREDIT_CARD_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/credit-card/reference/update/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CREDIT_CARD_UPDATE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/credit-card/reference/view/**")
				.hasAnyAuthority(PermissionEnum.CREDIT_CARD_VIEW_BYID.toString(), PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/credit-card/reference/view/active/**")
				.hasAnyAuthority(PermissionEnum.CREDIT_CARD_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.CREDIT_CARD_VIEW_ACTIVE_LIST_OPERATIONAL.toString(),
						PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/credit-card/reference/view/inactive/**").hasAnyAuthority(
						PermissionEnum.CREDIT_CARD_VIEW_INACTIVE_LIST.toString(), PermissionEnum.CATALOGUE.toString()));

		// ACCESOS PARA LA GENERACION DE REPORTES DEL SISTEMA
		// REPORTES DE CAJA
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/report-cash/opening-operation/{emnployeeEmail}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.GENERATE_CASH_REPORT.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/report-cash/closing-operation/{emnployeeEmail}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.GENERATE_CASH_REPORT.toString()));

		// ACCESOS PARA LA ADMINISTRACION DE CORREO PARA ENVIO DE REPORTES DE CAJA
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/email-cash/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.EMAIL_CASH_CREATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.PUT, "/email-cash/update/{id}/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.EMAIL_CASH_UPDATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/email-cash/view/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.EMAIL_CASH_VIEW.toString()));

		// ACCESOS PARA LA ADMINISTRACION DEL CATALOGO DE REFERENCIAS BANCARIAS
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/bank-reference/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.BANK_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/bank-reference/update/{id}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.BANK_UPDATE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/bank-reference/view/{id}/**")
				.hasAnyAuthority(PermissionEnum.BANK_VIEW_BYID.toString(), PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/bank-reference/view/active/**")
				.hasAnyAuthority(PermissionEnum.BANK_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.BANK_VIEW_ACTIVE_LIST_OPERATIONAL.toString(),
						PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.GET, "/bank-reference/view/inactive/**").hasAnyAuthority(
						PermissionEnum.BANK_VIEW_INACTIVE_LIST.toString(), PermissionEnum.CATALOGUE.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.GET, "/bank-reference/view/use-type/**")
				.hasAnyAuthority(PermissionEnum.BANK_VIEW_ACTIVE_LIST.toString(),
						PermissionEnum.BANK_VIEW_ACTIVE_LIST_OPERATIONAL.toString(),
						PermissionEnum.CATALOGUE.toString()));

		// ACCESOS PARA LA ACTUALIZAICON DE DATOS DE SUCURSALES CON JDE
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.PUT, "/catalogue-branch/update-address/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.UPDATE_EXTERNAL_SERVICE.toString()));

		// ACCESOS PARA LA ADMINISTRACION DE NOTAS DE CREDITO
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/credit-note/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/credit-note/search-order/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_SEARCH.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/credit-note/obtain/{folio}/{creditNoteCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_SEARCH.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/credit-note/search/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_SEARCH.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/credit-note/ticket/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_CREATE.toString()));
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/credit-note/aprove/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/credit-note/view/{folio}/{creditNoteCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.CREDIT_NOTE_SEARCH.toString()));

		// ACCESOS PARA LA ADMINISTRACION DE REPORTES DE VENTAS
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report-sale/branch/**}")
				.hasAnyAuthority(PermissionEnum.GENERATE_SALE_REPORT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report-sale/branch-employee/**")
				.hasAnyAuthority(PermissionEnum.GENERATE_SALE_REPORT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report-sale/article/**")
				.hasAnyAuthority(PermissionEnum.GENERATE_SALE_REPORT.toString()));
		http.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST, "/report-sale/closing-operation/**")
				.hasAnyAuthority(PermissionEnum.GENERATE_SALE_REPORT.toString()));

		// ACCESOS PARA GENERACION DE VENTA ANUNCIO
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/sale-advertising/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.ADVERTISING_CREATE.toString()));

		// ACCESOS PARA GENERACION DE VENTA TIEMPO AIRE
		http.authorizeRequests(
				requests -> requests.antMatchers(HttpMethod.POST, "/sale-recharge/create/{companyCode}/{branchCode}")
						.hasAnyAuthority(PermissionEnum.RECHARGE_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/sale-recharge/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.RECHARGE_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/sale-recharge/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.RECHARGE_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET, "/sale-recharge/company-phone/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.RECHARGE_CREATE.toString()));

		// ACCESOS PARA ADMINISTRACION DE RGISTROS COBRO CON CREDITO
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/payment-credit/sale/request/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYEMNT_CREDIT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/payment-credit/sale/response/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYEMNT_CREDIT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/payment-credit/payment-status/request/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYEMNT_CREDIT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.POST, "/payment-credit/payment-status/response/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYEMNT_CREDIT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/payment-credit/payment-status/search/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYEMNT_CREDIT_CREATE.toString()));
		http.authorizeRequests(requests -> requests
				.antMatchers(HttpMethod.GET,
						"/payment-credit/ticket/{orderNumber}/{orderCode}/{companyCode}/{branchCode}")
				.hasAnyAuthority(PermissionEnum.PAYEMNT_CREDIT_CREATE.toString()));

		/*
		 * Se habiltan las para la obtención del token de acceso y acceso a consola h2
		 * en dev
		 */
		http.authorizeRequests(requests -> requests
				.antMatchers("/oauth/token", "/api/security/access/oauth/token", "/status/aplication-layer",
						"/status/connection", "/actuator/**")
				.permitAll().antMatchers("/v2/api-docs/**", "/configuration/**").permitAll().anyRequest()
				.authenticated()).cors(cors -> cors.configurationSource(corsConfigurationSource()));
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("POST", "DELETE", "PUT", "GET", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filter;
	}
}
