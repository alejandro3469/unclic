package mx.com.endtoend.domain.company.dto;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.roles.dto.PermissionDto;

public class CompanyDto {

	private Long id;

	private String name;

	private String rfc;

	private String code;

	private String companyNumber;

	private boolean applyInvoiceProcess;

	private List<MethodDto> methods;

	private List<BranchDto> branches;

	private List<PermissionDto> permissions;

	public CompanyDto companyDtoEmpty() {

		CompanyDto companyDto = new CompanyDto();

		companyDto.setId(null);
		companyDto.setName("");
		companyDto.setCompanyNumber("");
		companyDto.setCode("");
		companyDto.setApplyInvoiceProcess(false);
		companyDto.setRfc("");
		companyDto.setMethods(new ArrayList<>());
		companyDto.setBranches(new ArrayList<>());
		companyDto.setPermissions(new ArrayList<>());

		return companyDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<BranchDto> getBranches() {
		return branches;
	}

	public void setBranches(List<BranchDto> branches) {
		this.branches = branches;
	}

	public List<MethodDto> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodDto> methods) {
		this.methods = methods;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public boolean getApplyInvoiceProcess() {
		return applyInvoiceProcess;
	}

	public void setApplyInvoiceProcess(boolean applyInvoiceProcess) {
		this.applyInvoiceProcess = applyInvoiceProcess;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public List<PermissionDto> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDto> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "CompanyDto [id=" + id + ", name=" + name + ", rfc=" + rfc + ", code=" + code + ", companyNumber="
				+ companyNumber + ", applyInvoiceProcess=" + applyInvoiceProcess + ", methods=" + methods
				+ ", branches=" + branches + ", permissions=" + permissions + "]";
	}

}