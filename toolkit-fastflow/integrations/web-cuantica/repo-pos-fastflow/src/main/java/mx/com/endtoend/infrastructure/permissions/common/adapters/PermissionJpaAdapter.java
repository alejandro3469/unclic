package mx.com.endtoend.infrastructure.permissions.common.adapters;

import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class PermissionJpaAdapter extends BasePermissionJpaAdapter {

	public PermissionJpaAdapter(PermissionConverter permissionConverter, PermissionRepository permissionRepository, CompanyRepository companyRepository) {
		super(PermissionJpaAdapter.class,permissionRepository, permissionConverter,companyRepository);
	}
}
