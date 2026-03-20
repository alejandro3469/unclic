package mx.com.endtoend.infrastructure.catalogue.address.carredana.zapata.repositories;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import java.util.List;

public interface CustomColonyCZapataRepository {

    List<ColonyEntity> findByParams(GenericSearchDirectionDto genericSearchDirectionDto);
}
