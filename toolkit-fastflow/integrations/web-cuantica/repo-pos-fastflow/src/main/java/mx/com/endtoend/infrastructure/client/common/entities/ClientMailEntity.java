package mx.com.endtoend.infrastructure.client.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "clients_emails")
public class ClientMailEntity {

	 @Id
	 @Column(name = "id", unique = true, nullable = false)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private String mail;
	 
	 @JoinColumn(name = "client_id")
	 @ManyToOne(optional = false, fetch = FetchType.EAGER)
	 private ClientEntity idClient;
}
