package mx.com.endtoend.infrastructure.orderConfigurations.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.commons.constants.DocumentEnum;
import mx.com.endtoend.domain.commons.constants.PanelEnum;
import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.DocumentEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.DocumentDto;

@Component
public class DocumentConverter {

	public DocumentDto documentEntityToDocumentDto(DocumentEntity documentEntity) {

		DocumentDto documentDto = new DocumentDto();

		documentDto.setId(documentEntity.getId());
		documentDto.setDocumentType(documentEntity.getDocumentType().toString());
		documentDto.setPanelView(documentEntity.getPanelView().toString());

		return documentDto;
	}

	public DocumentEntity documentDtoToDocumentEntity(DocumentDto documentDto) {

		DocumentEntity documentEntity = new DocumentEntity();

		documentEntity.setId(documentDto.getId());
		documentEntity.setDocumentType(DocumentEnum.valueOf(documentDto.getDocumentType()));
		documentEntity.setPanelView(PanelEnum.valueOf(documentDto.getPanelView()));

		return documentEntity;
	}

	public List<DocumentDto> documentEntityListToDocumentDtoList(List<DocumentEntity> documentEntityList) {

		List<DocumentDto> documentDtoLits = new ArrayList<DocumentDto>();

		for (DocumentEntity documentEntity : documentEntityList) {
			documentDtoLits.add(documentEntityToDocumentDto(documentEntity));
		}

		return documentDtoLits;
	}

	public List<DocumentEntity> documentDtoLitsToDocumentEntityList(List<DocumentDto> documentDtoLits) {

		List<DocumentEntity> documentEntityList = new ArrayList<DocumentEntity>();

		for (DocumentDto documentDto : documentDtoLits) {
			documentEntityList.add(documentDtoToDocumentEntity(documentDto));
		}

		return documentEntityList;
	}

}
