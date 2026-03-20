package mx.com.endtoend.infrastructure.users.common.repository;

import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;

import java.util.List;

public interface BaseCustomUserRepository {
    List<UserEntity> findUserLitsByParams(GenericSerchParamsUserDto genericSerchParamsUserDto);
}
