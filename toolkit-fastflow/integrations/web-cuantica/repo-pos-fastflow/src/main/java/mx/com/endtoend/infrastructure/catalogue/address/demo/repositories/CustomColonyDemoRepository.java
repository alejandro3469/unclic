package mx.com.endtoend.infrastructure.catalogue.address.demo.repositories;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import java.util.List;

public interface CustomColonyDemoRepository {

    List<ColonyEntity> findByParams(GenericSearchDirectionDto genericSearchDirectionDto);
}
