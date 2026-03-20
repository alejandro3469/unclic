package mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "F4714")
@Data
public class F4714 {
	
	@EmbeddedId
	private F4714Id id;
	
	@Column(name = "ZTEDTY") 
	private String ztedty;
	
	@Column(name = "ZTEDSQ") 
	private Long ztedsq;
	
	@Column(name = "ZTEDSP") 
	private String ztedsp;
	
	@Column(name = "ZTEDBT") 
	private String ztedbt;
	
	@Column(name = "ZTKCOO") 
	private String ztkcoo;
	
	@Column(name = "ZTDOCO") 
	private Long ztdoco;
	
	@Column(name = "ZTDCTO") 
	private String ztdcto;
	
	@Column(name = "ZTLNID") 
	private Long ztlnid;
	
	@Column(name = "ZTPNTC") 
	private String ztpntc;
	
	@Column(name = "ZTTXLN") 
	private String zttxln;
	
	@Column(name = "ZTTORG") 
	private String zttorg;
	
	@Column(name = "ZTUSER") 
	private String ztuser;
	
	@Column(name = "ZTPID") 
	private String ztpid;
	
	@Column(name = "ZTJOBN") 
	private String ztjobn;
	
	@Column(name = "ZTUPMJ") 
	private Long ztupmj;
	
	@Column(name = "ZTTDAY") 
	private Long zttday;
}
