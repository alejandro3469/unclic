package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;

@Entity
@Table(name = "Clientes")
public class ClientSqlEntity {

	@Column(name = "NombreCliente")
	private String businessName;

	@Column(name = "Celular")
	private String cell;

	@Column(name = "Ciudad")
	private String city;

	@Column(name = "Colonia")
	private String colony;

	@Column(name = "Contacto")
	private String contact;

	@Column(name = "Coordenada")
	private String coordinatesCode;

	@Column(name = "CodigoPostal")
	private String cp;

	@Column(name = "TipoCliente")
	private String customerType;

	@Column(name = "Delegacion")
	private String delegationCode;

	@Column(name = "ApellidoPaterno")
	private String fatherSurname;

	@Column(name = "Plano")
	private String flatCode;

	@Column(name = "ComoContacto")
	private String howToContact;

	private String iva;

	@Column(name = "Correo")
	private String mail;

	@Column(name = "moneda")
	private String money;

	@Column(name = "ApellidoMaterno")
	private String motherSurname;

	@Column(name = "Nombre")
	private String name;

	@Id
	@Column(name = "NoCliente")
	private Long noClient;

	@Column(name = "NoInterior")
	private String noInterior;

	@Column(name = "NoExterior")
	private String noOutdoor;

	@Column(name = "TelefonoFijo")
	private String phone;

	@Column(name = "RFC")
	private String rfc;

	@Column(name = "Estado")
	private String stateCode;

	@Column(name = "Calle")
	private String street;

	@Column(name = "TipoPersona")
	private String taxpayer;

	@Column(name = "TipoObra")
	private String workType;

	private String estatusEnvio;

	private String tipoIVA;

	private String listaPrecios;

	private String asn;

	private String puntos;

	private String CodigoBarras;

	private String RegimenFiscal;

	public String getRegimenFiscal() {
		return RegimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		RegimenFiscal = regimenFiscal;
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getCell() {
		return cell;
	}

	public String getCity() {
		return city;
	}

	public String getColony() {
		return colony;
	}

	public String getContact() {
		return contact;
	}

	public String getCoordinatesCode() {
		return coordinatesCode;
	}

	public String getCp() {
		return cp;
	}

	public String getCustomerType() {
		return customerType;
	}

	public String getDelegationCode() {
		return delegationCode;
	}

	public String getFatherSurname() {
		return fatherSurname;
	}

	public String getFlatCode() {
		return flatCode;
	}

	public String getHowToContact() {
		return howToContact;
	}

	public String getIva() {
		return iva;
	}

	public String getMail() {
		return mail;
	}

	public String getMoney() {
		return money;
	}

	public String getMotherSurname() {
		return motherSurname;
	}

	public String getName() {
		return name;
	}

	public Long getNoClient() {
		return noClient;
	}

	public String getNoInterior() {
		return noInterior;
	}

	public String getNoOutdoor() {
		return noOutdoor;
	}

	public String getPhone() {
		return phone;
	}

	public String getRfc() {
		return rfc;
	}

	public String getStateCode() {
		return stateCode;
	}

	public String getStreet() {
		return street;
	}

	public String getTaxpayer() {
		return taxpayer;
	}

	public String getWorkType() {
		return workType;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setCell(String cell) {
		this.cell = StringUtil.cleanString(cell, 20);
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setCoordinatesCode(String coordinatesCode) {
		this.coordinatesCode = coordinatesCode;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setDelegationCode(String delegationCode) {
		this.delegationCode = delegationCode;
	}

	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}

	public void setFlatCode(String flatCode) {
		this.flatCode = flatCode;
	}

	public void setHowToContact(String howToContact) {
		this.howToContact = howToContact;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNoClient(Long noClient) {
		this.noClient = noClient;
	}

	public void setNoInterior(String noInterior) {
		this.noInterior = noInterior;
	}

	public void setNoOutdoor(String noOutdoor) {
		this.noOutdoor = noOutdoor;
	}

	public void setPhone(String phone) {
		this.phone = StringUtil.cleanString(phone, 20);
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setTaxpayer(String taxpayer) {
		this.taxpayer = taxpayer;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getEstatusEnvio() {
		return estatusEnvio;
	}

	public void setEstatusEnvio(String estatusEnvio) {
		this.estatusEnvio = estatusEnvio;
	}

	public String getTipoIVA() {
		return tipoIVA;
	}

	public void setTipoIVA(String tipoIVA) {
		this.tipoIVA = tipoIVA;
	}

	public String getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(String listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

	public String getAsn() {
		return asn;
	}

	public void setAsn(String asn) {
		this.asn = asn;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getCodigoBarras() {
		return CodigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		CodigoBarras = codigoBarras;
	}
}
