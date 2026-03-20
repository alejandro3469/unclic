package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F0101Z2", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F0101Z2 {

	@EmbeddedId
	private F0101Z2Id id;

	@Column(name = "SZMCU")
	private String branchCode;

	@Column(name = "SZTYTN")
	private String sztytn;

	@Column(name = "SZEDDT")
	private String szeddt;

	@Column(name = "SZEDDL")
	private String szeddl;

	@Column(name = "SZTNAC")
	private String sztnac;

	@Column(name = "SZAN8")
	private String noClient;

	@Column(name = "SZTAX")
	private String rfc;

	@Column(name = "SZALPH")
	private String name;

	@Column(name = "SZAT1")
	private String szat1;

	@Column(name = "SZTAXC")
	private String taxPayer;

	@Column(name = "SZAT2")
	private String szat2;

	@Column(name = "SZATR")
	private String szatr;

	@Column(name = "SZAT5")
	private String szat5;

	@Column(name = "SZATP")
	private String szatp;

	@Column(name = "SZATPR")
	private String szatpr;

	@Column(name = "SZATE")
	private String szate;

	@Column(name = "SZEFTB")
	private String szeftb;

	@Column(name = "SZAN81")
	private String szan81;

	@Column(name = "SZAN82")
	private String szan82;

	@Column(name = "SZAN83")
	private String szan83;

	@Column(name = "SZAN84")
	private String szan84;

	@Column(name = "SZAN86")
	private String szan86;

	@Column(name = "SZAN85")
	private String szan85;

	@Column(name = "SZPTI")
	private String szpti;

	@Column(name = "SZPDI")
	private String szpdi;

	@Column(name = "SZURDT")
	private String szurdt;

	@Column(name = "SZURAT")
	private String szurat;

	@Column(name = "SZURAB")
	private String szurab;

	@Column(name = "SZMLNM")
	private String copyName;

	@Column(name = "SZADD1")
	private String name1;

	@Column(name = "SZADD2")
	private String street;

	@Column(name = "SZADD3")
	private String noOuNoIn;

	@Column(name = "SZADD4")
	private String colony;

	@Column(name = "SZADDZ")
	private String cp;

	@Column(name = "SZCTY1")
	private String delegationCode;

	@Column(name = "SZCTR")
	private String szctr;

	@Column(name = "SZADDS")
	private String stateCode;

	@Column(name = "SZCOUN")
	private String city;

	@Column(name = "SZCLASS05")
	private String szclass05;

	@Column(name = "SZUSER")
	private String szuser;

	@Column(name = "SZUPMJ")
	private String szupmj;

	@Column(name = "SZTDAY")
	private String sztday;

	@Column(name = "SZUPMT")
	private String szupmt;

	@Column(name = "SZPA8")
	private String szpa8;

	@Column(name = "SZEDSP")
	private String szedsp;

	@Column(name = "SZAC10")
	private String szac10;

	@Column(name = "SZTORG")
	private String sztorg;

	@Column(name = "SZPID")
	private String szpid;

	@Column(name = "SZJOBN")
	private String szjobn;

	@Column(name = "SZAC25")
	private String taxRegime;

	@Column(name = "SZAC04")
	private String company;

	public String getCp() {
		return this.cp.trim();
	}
}
