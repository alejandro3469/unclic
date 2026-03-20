package mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.configurations.databases.calzada.CalzadaDataSourceConfigurationOracle;

@Entity
@Table(name = "F0115Z1", schema = CalzadaDataSourceConfigurationOracle.dynamicSchema2)
public class F0115Z1 {

	@EmbeddedId
	private F0115Z1Id id;
	
	@Column(name = "piphtp")
	private String cellHouse;
	
	@Column(name = "piph1")
	private String number;
	
	@Column(name = "pian8")
	private String noClient;
	
	private String piedsp;
	
	private String pitnac;
	
	private String piidln;
	
	private String pirck7;
	
	private String piuser;
	
	private String pipid;

	private String piupmj;
	
	private String pijobn;
	
	private String piupmt;
	
	private String picfno1;
	
	private String piar1;
	
	private String piedct;
	
	private String pitytn;
	
	private String piedft;
	
	private String Pieddt;
	
	private String pidrin;

	public F0115Z1Id getId() {
		return id;
	}

	public void setId(F0115Z1Id id) {
		this.id = id;
	}

	public String getCellHouse() {
		return cellHouse;
	}

	public void setCellHouse(String cellHouse) {
		this.cellHouse = StringUtil.cleanString(cellHouse, 20);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = StringUtil.cleanString(number, 20);
	}

	public String getNoClient() {
		return noClient;
	}

	public void setNoClient(String noClient) {
		this.noClient = noClient;
	}

	public String getPiedsp() {
		return piedsp;
	}

	public void setPiedsp(String piedsp) {
		this.piedsp = piedsp;
	}

	public String getPitnac() {
		return pitnac;
	}

	public void setPitnac(String pitnac) {
		this.pitnac = pitnac;
	}

	public String getPiidln() {
		return piidln;
	}

	public void setPiidln(String piidln) {
		this.piidln = piidln;
	}

	public String getPirck7() {
		return pirck7;
	}

	public void setPirck7(String pirck7) {
		this.pirck7 = pirck7;
	}

	public String getPiuser() {
		return piuser;
	}

	public void setPiuser(String piuser) {
		this.piuser = piuser;
	}

	public String getPipid() {
		return pipid;
	}

	public void setPipid(String pipid) {
		this.pipid = pipid;
	}

	public String getPiupmj() {
		return piupmj;
	}

	public void setPiupmj(String piupmj) {
		this.piupmj = piupmj;
	}

	public String getPijobn() {
		return pijobn;
	}

	public void setPijobn(String pijobn) {
		this.pijobn = pijobn;
	}

	public String getPiupmt() {
		return piupmt;
	}

	public void setPiupmt(String piupmt) {
		this.piupmt = piupmt;
	}

	public String getPicfno1() {
		return picfno1;
	}

	public void setPicfno1(String picfno1) {
		this.picfno1 = picfno1;
	}

	public String getPiar1() {
		return piar1;
	}

	public void setPiar1(String piar1) {
		this.piar1 = piar1;
	}

	public String getPiedct() {
		return piedct;
	}

	public void setPiedct(String piedct) {
		this.piedct = piedct;
	}

	public String getPitytn() {
		return pitytn;
	}

	public void setPitytn(String pitytn) {
		this.pitytn = pitytn;
	}

	public String getPiedft() {
		return piedft;
	}

	public void setPiedft(String piedft) {
		this.piedft = piedft;
	}

	public String getPieddt() {
		return Pieddt;
	}

	public void setPieddt(String pieddt) {
		Pieddt = pieddt;
	}

	public String getPidrin() {
		return pidrin;
	}

	public void setPidrin(String pidrin) {
		this.pidrin = pidrin;
	}
	
	
	
}
