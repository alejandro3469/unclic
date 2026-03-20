package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "F47012")
@Data
public class F47012 {
	
	@EmbeddedId
	private F47012Id id;
	
	@Column(name = "SZEDTY") 
	private String szedty;
	
	@Column(name = "SZEDER") 
	private String szeder;
	
	@Column(name = "SZEDSP") 
	private String szedsp;
	
	@Column(name = "SZEDBT") 
	private String szedbt;
	
	@Column(name = "SZKCOO") 
	private String szkcoo;
	
	@Column(name = "SZDCTO") 
	private String szdcto;
	
	@Column(name = "SZMCU") 
	private String szmcu;
	
	@Column(name = "SZOKCO") 
	private String szokco;
	
	@Column(name = "SZOORN") 
	private String szoorn;
	
	@Column(name = "SZOCTO") 
	private String szocto;
	
	@Column(name = "SZRKCO") 
	private String szrkco;
	
	@Column(name = "SZRORN") 
	private String szrorn;
	
	@Column(name = "SZRCTO") 
	private String szrcto;
	
	@Column(name = "SZAN8") 
	private Long szan8;
	
	@Column(name = "SZSHAN") 
	private Long szshan;
	
	@Column(name = "SZDRQJ") 
	private Long szdrqj;
	
	@Column(name = "SZTRDJ") 
	private Long sztrdj;
	
	@Column(name = "SZVR01") 
	private String szvr01;
	
	@Column(name = "SZASN") 
	private String szasn;
	
	@Column(name = "SZSTOP") 
	private String szstop;
	
	@Column(name = "SZZON") 
	private String szzon;
	
	@Column(name = "SZUSER") 
	private String szuser;
	
	@Column(name = "SZPID") 
	private String szpid;
	
	@Column(name = "SZJOBN") 
	private String szjobn;
	
	@Column(name = "SZUPMJ") 
	private Long szupmj;
	
	@Column(name = "SZHOLD") 
	private String szhold;
	
	@Column(name = "SZVR02") 
	private String szvr02;
	
	@Column(name = "SZEDST") 
	private String szedst;
	
	@Column(name = "SZDOCO") 
	private Long szdoco;
	
	@Column(name = "SZLNID") 
	private Long szlnid;
	
	@Column(name = "SZSFXO") 
	private String szsfxo;
	
	@Column(name = "SZPA8") 
	private Long szpa8;
	
	@Column(name = "SZITM") 
	private Long szitm;
	
	@Column(name = "SZEMCU") 
	private String szemcu;
	
	@Column(name = "SZUOM") 
	private String szuom;
	
	@Column(name = "SZUOM4") 
	private String szuom4;
	
	@Column(name = "SZSOQS") 
	private Long szsoqs;
	
	@Column(name = "SZTAX1") 
	private String sztax1;
	
	@Column(name = "SZEXR1") 
	private String szexr1;
	
	@Column(name = "SZVEND") 
	private Long szvend;
	
	@Column(name = "SZLTTR") 
	private String szlttr;
	
	@Column(name = "SZNXTR") 
	private String sznxtr;
	
	@Column(name = "SZUPRC") 
	private Long szuprc;
	
	@Column(name = "SZDCT") 
	private String szdct;
	
	@Column(name = "SZDOC") 
	private Long szdoc;
	
	@Column(name = "SZCO") 
	private String szco;
	
	@Column(name = "SZIVD") 
	private Long szivd;
	
	@Column(name = "SZLNTY") 
	private String szlnty;
	
	@Column(name = "SZUORG") 
	private Long szuorg;
	
	@Column(name = "SZURCD") 
	private String szurcd;
	
	@Column(name = "SZURDT") 
	private Long szurdt;
	
	@Column(name = "SZURAT") 
	private Long szurat;
	
	@Column(name = "SZURAB") 
	private Long szurab;
	
	@Column(name = "SZURRF") 
	private String szurrf;
	
	@Column(name = "SZOGNO") 
	private Long szogno;
}
