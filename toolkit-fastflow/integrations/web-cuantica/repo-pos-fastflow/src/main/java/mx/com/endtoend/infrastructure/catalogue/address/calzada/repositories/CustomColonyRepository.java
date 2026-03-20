package mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import java.util.List;

public interface CustomColonyRepository {

    List<ColonyEntity> findByParams(GenericSearchDirectionDto genericSearchDirectionDto);
}
