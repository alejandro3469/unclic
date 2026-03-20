package mx.com.endtoend.domain.branch.dto;

import java.util.List;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.users.dto.UserDto;

public class BranchDto {

	private Long id;
	private String name;
	private String code;
	private String street;
	private String insideNumber;
	private String outsideNumber;
	private String cp;
	private String colony;
	private String phoneNumber;
	private CompanyDto company;
	private List<UserDto> users;

	public BranchDto() {
		super();
	}

	public BranchDto(Long id, String name, String code, String street, String insideNumber, String outsideNumber,
			String cp, String colony, String phoneNumber, CompanyDto company) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.street = street;
		this.insideNumber = insideNumber;
		this.outsideNumber = outsideNumber;
		this.cp = cp;
		this.colony = colony;
		this.phoneNumber = phoneNumber;
		this.company = company;
	}

	public BranchDto(BranchDto branchDto, String street, String insideNumber, String outsideNumber, String cp,
			String colony, String phoneNumber) {
		super();
		this.id = branchDto.getId();
		this.name = branchDto.getName();
		this.code = branchDto.getCode();
		this.street = street;
		this.insideNumber = insideNumber;
		this.outsideNumber = outsideNumber;
		this.cp = cp;
		this.colony = colony;
		this.phoneNumber = phoneNumber;
		this.company = branchDto.getCompany();
	}

	public BranchDto branchDtoEmpty() {
		BranchDto branchDtoEmpty = new BranchDto();

		branchDtoEmpty.setId(null);
		branchDtoEmpty.setName("");
		branchDtoEmpty.setCode("");
		branchDtoEmpty.setCompany(null);
		branchDtoEmpty.setUsers(null);

		return branchDtoEmpty;
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

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

	public String getStreet() {
		return street;
	}

	public String getInsideNumber() {
		return insideNumber;
	}

	public String getOutsideNumber() {
		return outsideNumber;
	}

	public String getCp() {
		return cp;
	}

	public String getColony() {
		return colony;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setInsideNumber(String insideNumber) {
		this.insideNumber = insideNumber;
	}

	public void setOutsideNumber(String outsideNumber) {
		this.outsideNumber = outsideNumber;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "BranchDto [id=" + id + ", name=" + name + ", code=" + code + ", street=" + street + ", insideNumber="
				+ insideNumber + ", outsideNumber=" + outsideNumber + ", cp=" + cp + ", colony=" + colony
				+ ", phoneNumber=" + phoneNumber + ", company=" + company + ", users=" + users + "]";
	}

}