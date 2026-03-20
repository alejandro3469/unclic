package mx.com.endtoend.infrastructure.articles.calzada.oracle.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "F4101")
public class F4101 {

	@Id
	@Column(name = "IMITM")
	private BigDecimal imitm;
	
	@Column(name = "IMAITM")
	private String imaitm;
	
	@Column(name = "IMDSC1")
	private String imdsc1;
	
	@Column(name = "IMDSC2")
	private String imdsc2;
	
	@Column(name = "IMLITM")
	private String imlitm;
	
	@Column(name = "IMPRP2")
	private String imprp2;
	
	@Column(name = "IMSTKT")
	private String imstkt;
	
	@Column(name = "IMSRP1")
	private String imsrp1;
	
	@Column(name = "IMSRP2")
	private String imsrp2;
	
	@Column(name = "IMSRP3")
	private String imsrp3;
	
	@Column(name = "IMSRP4")
	private String imsrp4;
	
	@Column(name = "IMSRP5")
	private String imsrp5;
	
	@Column(name = "IMSRTX")
	private String imsrtx;
	
	@Column(name = "IMUOM1")
	private String imuom1;
	
	@Column(name = "IMUOM9")
	private String imuom9;
}
