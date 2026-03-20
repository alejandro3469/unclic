package mx.com.endtoend.infrastructure.articles.common.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.articles.common.repository.CustomArticleRepositoryFactory;
import mx.com.endtoend.infrastructure.articles.common.repository.GenericCustomArticleRepository;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.domain.articles.ports.spi.CustomArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */

@Service
public class CustomArticleJpaAdapter implements CustomArticlePersistencePort {

	@Autowired
	private CustomArticleRepositoryFactory customArticleRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(CustomArticleJpaAdapter.class);

	@Override
	public ResponseModel createCustomArticle(CustomArticleDto customArticleDto, String companyCode, String brabchCode,
			String idOperation) {

		LOG.info(String.format("%s INIT createCustomArticle() ", idOperation));
		LOG.info(String.format("%s PARAMS: customArticleDto: %s companyCode: %s branchCode: %s ", idOperation,
				customArticleDto.toString(), companyCode, brabchCode));

		CustomArticleDto customArticleDtoCreated = new CustomArticleDto();
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		customArticleDtoCreated = repository.createCustomArticle(customArticleDto, idOperation);
		return new ResponseModel(customArticleDtoCreated);
	}

	@Override
	public ResponseModel updateCustomArticle(CustomArticleDto customArticleDto, String companyCode, String brabchCode,
			String idOperation) {

		LOG.info(String.format("%s INIT updateCustomArticle() ", idOperation));
		LOG.info(String.format("%s PARAMS: customArticleDto: %s companyCode: %s branchCode: %s ", idOperation,
				customArticleDto.toString(), companyCode, brabchCode));

		CustomArticleDto customArticleDtoCreated = new CustomArticleDto();
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		customArticleDtoCreated = repository.updateCustomArticle(customArticleDto, idOperation);
		return new ResponseModel(customArticleDtoCreated);
	}

	@Override
	public ResponseModel existsCustomArticleByName(String name, String companyCode, String brabchCode,
			String idOperation) {

		LOG.info(String.format("%s INIT existsCustomArticleByName() ", idOperation));
		LOG.info(String.format("%s PARAMS: name: %s companyCode: %s branchCode: %s ", idOperation, name, companyCode,
				brabchCode));

		boolean existCustomArticle = false;
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		existCustomArticle = repository.existsByName(name, idOperation);
		return new ResponseModel(existCustomArticle);
	}

	@Override
	public ResponseModel existsCustomArticleByNameAndIdNot(String name, Long id, String companyCode, String brabchCode,
			String idOperation) {

		LOG.info(String.format("%s INIT existsCustomArticleByNameAndIdNot() ", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s name: %s companyCode: %s branchCode: %s ", idOperation, id.toString(),
				name, companyCode, brabchCode));

		boolean existCustomArticle = false;
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		existCustomArticle = repository.existsByNameAndIdNot(name, id, idOperation);
		return new ResponseModel(existCustomArticle);
	}

	@Override
	public ResponseModel existsCustomArticleByArticleNumber(BigDecimal articleNumber, String companyCode,
			String brabchCode, String idOperation) {

		Log.info(String.format("%s INIT existsCustomArticleByArticleNumber() ", idOperation));
		LOG.info(String.format("%s PARAMS: articleNumber: %s companyCode: %s branchCode: %s ", idOperation,
				articleNumber.toString(), companyCode, brabchCode));

		boolean existArticleNumber = false;
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		existArticleNumber = repository.existsByArticleNumber(articleNumber, idOperation);
		return new ResponseModel(existArticleNumber);
	}

	@Override
	public ResponseModel existsCustomArticleByArticleNumberAndIdNot(BigDecimal articleNumber, Long id,
			String companyCode, String brabchCode, String idOperation) {
		LOG.info(String.format("%s INIT existsCustomArticleByArticleNumberAndIdNot() ", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s articleNumber: %s companyCode: %s branchCode: %s ", idOperation,
				id.toString(), articleNumber.toString(), companyCode, brabchCode));

		boolean existArticleNumber = false;
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		existArticleNumber = repository.existsByArticleNumberAndIdNot(articleNumber, id, idOperation);

		return new ResponseModel(existArticleNumber);
	}

	@Override
	public ResponseModel enableById(Long id, boolean enable, String companyCode, String branchCode,
			String idOperation) {

		LOG.info(String.format("%s INIT enableById() ", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s enable: %s companyCode: %s branchCode: %s ", idOperation,
				id.toString(), String.valueOf(enable), companyCode, branchCode));

		CustomArticleDto customArticleDto = new CustomArticleDto();
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		customArticleDto = repository.enableById(id, enable, idOperation);
		return new ResponseModel(customArticleDto);
	}

	@Override
	public ResponseModel findById(Long id, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT findById() ", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s companyCode: %s branchCode: %s ", idOperation, id.toString(),
				companyCode, branchCode));

		CustomArticleDto customArticleDto = new CustomArticleDto();
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		customArticleDto = repository.findById(id, idOperation);
		return new ResponseModel(customArticleDto);
	}

	@Override
	public ResponseModel findAllByEnable(boolean enable, String companyCode, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT findAllByEnable() ", idOperation));
		LOG.info(String.format("%s PARAMS: enable: %s companyCode: %s branchCode: %s ", idOperation,
				String.valueOf(enable), companyCode, branchCode));

		List<CustomArticleDto> customArticleDtoList = new ArrayList<CustomArticleDto>();
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		customArticleDtoList = repository.findAllByEnable(enable, idOperation);
		return new ResponseModel(customArticleDtoList);
	}

	@Override
	public ResponseModel findAllActiveBySaleTypeAndCompanyCode(String saleType, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT findAllActiveBySaleTypeAndCompanyCode() ", idOperation));
		GenericCustomArticleRepository repository = customArticleRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<CustomArticleDto> customArticleDtoList = repository.findAllActiveBySaleType(saleType, idOperation);
		return new ResponseModel(customArticleDtoList);
	}

}
