package mx.com.endtoend.domain.clients.dto;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDirectionDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientMailDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;

public class ClientIdDto {

	private Long id;

	private Long noClient;

	private String taxpayer;

	private String customerType;

	private String rfc;

	private String name;

	private String fatherSurname;

	private String motherSurname;

	private String businessName;

	private String contact;

	private String phone;

	private String cell;

	private String howToContact;

	private String workType;

	private String iva;

	private String taxRegime;

	private String nnn001;

	private ClientDirectionDto direction;

	private boolean enabled;

	private List<ShippingAddressDto> shippingAddressList;

	private List<ClientMailDto> mailList;

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public List<ShippingAddressDto> getShippingAddressList() {
		return shippingAddressList;
	}

	public void setShippingAddressList(List<ShippingAddressDto> shippingAddressList) {
		this.shippingAddressList = shippingAddressList;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoClient() {
		return noClient;
	}

	public void setNoClient(Long noClient) {
		this.noClient = noClient;
	}

	public String getTaxpayer() {
		return taxpayer;
	}

	public void setTaxpayer(String taxpayer) {
		this.taxpayer = taxpayer;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFatherSurname() {
		return fatherSurname;
	}

	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}

	public String getMotherSurname() {
		return motherSurname;
	}

	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public ClientDirectionDto getDirection() {
		return direction;
	}

	public void setDirection(ClientDirectionDto direction) {
		this.direction = direction;
	}

	public String getHowToContact() {
		return howToContact;
	}

	public void setHowToContact(String howToContact) {
		this.howToContact = howToContact;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getTaxRegime() {
		return taxRegime;
	}

	public void setTaxRegime(String taxRegime) {
		this.taxRegime = taxRegime;
	}

	public List<ClientMailDto> getMailList() {
		return mailList;
	}

	public void setMailList(List<ClientMailDto> mailList) {
		this.mailList = mailList;
	}


	public String getNnn001() {
		return nnn001;
	}

	public void setNnn001(String nnn001) {
		this.nnn001 = nnn001;
	}
}
