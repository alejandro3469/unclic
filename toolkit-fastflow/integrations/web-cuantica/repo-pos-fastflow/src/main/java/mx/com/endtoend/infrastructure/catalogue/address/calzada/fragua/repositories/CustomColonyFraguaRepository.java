package mx.com.endtoend.infrastructure.catalogue.address.calzada.fragua.repositories;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import java.util.List;

public interface CustomColonyFraguaRepository {

    List<ColonyEntity> findByParams(GenericSearchDirectionDto genericSearchDirectionDto);
}
