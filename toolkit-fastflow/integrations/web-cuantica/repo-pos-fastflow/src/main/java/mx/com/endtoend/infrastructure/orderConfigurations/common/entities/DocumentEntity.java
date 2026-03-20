package mx.com.endtoend.infrastructure.orderConfigurations.common.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mx.com.endtoend.domain.commons.constants.DocumentEnum;
import mx.com.endtoend.domain.commons.constants.PanelEnum;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocumentEntity {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "document_type", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private DocumentEnum documentType;

	@Column(name = "panel_view", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PanelEnum panelView;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_configuration_id", nullable = true)
	private OrderConfigurationEntity orderConfiguration;
	
	
}
