package mx.com.endtoend.infrastructure.services.jde.clients.demo.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import mx.com.endtoend.infrastructure.configurations.databases.demo.DemoDataSourceConfigurationOracle;

@Data
@Entity
@Table(name = "F03012Z1", schema = DemoDataSourceConfigurationOracle.dynamicSchema2)
public class F03012Z1 {

	@EmbeddedId
	private F03012Z1Id id;
	
	@Column(name = "votxa1")
	private String iva;
	
	@Column(name = "voan8")
	private String noClient;
	
	private String voeddt;
	
	private String voeddl;
	
	private String voedsp;
	
	private String votnac;
	
	private String voco;
	
	private String voarc;
	
	private String vodcar;
	
	private String vocrcd;
	
	private String voexr1;
	
	private String voacl;
	
	private String vohdar;
	
	private String votrar;
	
	private String vostto;
	
	private String voryin;
	
	private String vostmt;
	
	private String voarpy;
	
	private String voatcs;
	
	private String vosito;
	
	private String vosqnl;
	
	private String voalgm;
	
	private String vobo;
	
	private String vodlc;
	
	private String vodnlt;
	
	private String vorvdj;
	
	private String vodso;
	
	private String vodlqt;
	
	private String vodlqj;
	
	private String vocoll;
	
	private String vonbr1;
	
	private String vonbr2;
	
	private String vonbr3;
	
	private String vonbcl;
	
	private String voafc;
	
	private String vofd;
	
	private String vofp;
	
	private String vocfce;
	
	private String vodt1j;
	
	private String vodfij;
	
	private String vodlij;
	
	private String voabc1;
	
	private String voabc2;
	
	private String voabc3;
	
	private String vofndj;
	
	private String vodlp;
	
	private String vodnbj;
	
	private String votwdj;
	
	private String voavd;
	
	private String vocrca;
	
	private String vopopn;
	
	private String voan8r;
	
	private String vobadt;
	
	private String voexhd;
	
	private String voaft;
	
	private String voapts;
	
	private String vosbal;
	
	private String voback;
	
	private String voporq;
	
	private String voprio;
	
	private String voarto;
	
	private String voinvc;
	
	private String voicon;
	
	private String voblfr;
	
	private String voplst;
	
	private String vomord;
	
	private String voedpm;
	
	private String voedf1;
	
	private String vosi01;
	
	private String vosi02;
	
	private String voasn;
	
	private String vodspa;
	
	private String voac10;
	
	private String vocfdf;
	
	private String vouser;
	
	private String vopid;
	
	private String vojobn;
	
	private String voupmt;
	
	private String voupmj;
	
	private String vobyal;
	
	private Date vodtee;
}