package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories;

import java.util.List;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;

public interface CustomArticleRepository {

	List<Object[]> findArticlesByParams(GenericSerchParamsArticleDto serrchParams);
}
