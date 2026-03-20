package mx.com.endtoend.infrastructure.company.common.adapters;

import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.converters.MethodConverter;
import mx.com.endtoend.infrastructure.company.common.converters.PermissionCompanyConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.company.common.repositories.MethodRepository;
import mx.com.endtoend.infrastructure.company.common.repositories.PermissionCompanyRepository;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.repository.PermissionRepository;

/**
 * 
 * @author labucio, ddcasas
 *
 */

public class CompanyJpaAdapter extends BaseCompanyJpaAdapter {

	 public CompanyJpaAdapter(CompanyRepository _companyRepository,
							  CompanyConverter _companyConverter,
							  MethodRepository _methodRepository,
							  MethodConverter _methodConverter,
							  PermissionCompanyConverter _permissionCompanyConverter,
							  PermissionCompanyRepository _permissionCompanyRepository,
							  PermissionRepository _permissionRepository,
							  PermissionConverter _permissionConverter) {
		 super(CompanyJpaAdapter.class,
				 _companyRepository,
				 _companyConverter,
				 _methodRepository,
				 _methodConverter,
				 _permissionCompanyConverter,
				 _permissionCompanyRepository,
				 _permissionRepository,
				 _permissionConverter );
	 }
}