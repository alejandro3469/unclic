package mx.com.endtoend.domain.reports.sales.articles.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.reports.sales.articles.business.validations.ReportArticleSaleValidation;
import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleBranchSummaryDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleSaleDetailDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleSaleReportDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SummaryArticleSaleDto;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticlePersistencePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportSaleArticleMethodOne implements SaleReporArticleInterface {

	private BranchPersistencePort branchPersistencePort;

	private UserPersistencePort userPersistencePort;

	private ReportSaleArticlePersistencePort reportSaleArticlePersistencePort;

	public ReportSaleArticleMethodOne(SaleReportInterfaceService saleReportInterfaceService) {

		this.branchPersistencePort = saleReportInterfaceService.getBranchPersistencePort();
		this.userPersistencePort = saleReportInterfaceService.getUserPersistencePort();
		this.reportSaleArticlePersistencePort = saleReportInterfaceService.getReportSaleArticlePersistencePort();

	}

	private final Logger LOG = LoggerFactory.getLogger(ReportSaleArticleMethodOne.class);

	private ReportArticleSaleValidation reportArticleSaleValidation = new ReportArticleSaleValidation();

	List<UserDto> userList = new ArrayList<>();

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel generateReportSaleArticleByParamsAndCompanyCode(
			SaleReportArticleParamsDto saleReportArticleParamsDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateReportSaleArticleByParamsAndCompanyCode()", idOperation));

		String validations = reportArticleSaleValidation
				.validOperativeDataToGenerateBranchSaleReport(saleReportArticleParamsDto);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAMS", idOperation));
			throw new ValidationError(validations);
		}

		List<BranchDto> branchList = new ArrayList<>();
		if (!saleReportArticleParamsDto.getBranchCode().isEmpty()) {
			ResponseModel responseModel = branchPersistencePort.getBranchDetailByBranchCodeAndCompanyCode(
					saleReportArticleParamsDto.getBranchCode(), companyCode, idOperation);
			BranchDto branch = (BranchDto) responseModel.getData();
			branchList.add(branch);
		} else {
			ResponseModel responseBranchList = branchPersistencePort.getBranchListByCompanyCode(companyCode,
					idOperation);
			branchList = (List<BranchDto>) responseBranchList.getData();
		}

		UserDto userEmpty = new UserDto();
		userEmpty.setName("----");
		userEmpty.setFirstSurname("");
		userEmpty.setSecondSurname("");
		UserDto userReport = null;

		List<ArticleBranchSummaryDto> articleBranchSummaryList = new ArrayList<>();
		for (BranchDto branchDto : branchList) {

			saleReportArticleParamsDto = generateSearchParamsByRoleJob(saleReportArticleParamsDto, branchDto);
			ResponseModel responseSummaryArticle = reportSaleArticlePersistencePort
					.findArticleListByParamsAndCompanyCode(saleReportArticleParamsDto, companyCode, idOperation);
			List<SummaryArticleSaleDto> summaryArticleSaleList = (List<SummaryArticleSaleDto>) responseSummaryArticle
					.getData();

			List<ArticleSaleDetailDto> articleSaleDetailList = new ArrayList<>();
			for (SummaryArticleSaleDto summaryArticleSale : summaryArticleSaleList) {
				userReport = getUserById(idOperation, userEmpty, summaryArticleSale.getUserId());
				articleSaleDetailList.add(new ArticleSaleDetailDto(summaryArticleSale, userReport));

			}

			if(articleSaleDetailList.size() > 0) {
				ResponseModel responseModel = branchPersistencePort.getBranchDetailByBranchCodeAndCompanyCode(
						saleReportArticleParamsDto.getBranchCode(), companyCode, idOperation);
				BranchDto branch = (BranchDto) responseModel.getData();

				articleBranchSummaryList
						.add(new ArticleBranchSummaryDto(branch, saleReportArticleParamsDto, articleSaleDetailList));
			}
			

		}

		ArticleSaleReportDto articleSaleReport = new ArticleSaleReportDto(saleReportArticleParamsDto,
				articleBranchSummaryList);

		LOG.info(articleSaleReport.toString());

		return reportSaleArticlePersistencePort.generateSaleArticleReportByCompanyCode(articleSaleReport,
				saleReportArticleParamsDto.getFormat(), companyCode, idOperation);
	}

	/**
	 * Método para la obtenciópn de los datos de empleado por su ID. Se almacenan en
	 * la lista userList para tener en memoria los datos repetidos de la lista
	 * summaryArticleSaleList
	 * 
	 * @param idOperation
	 * @param userList
	 * @param userEmpty
	 * @param userId
	 * @return UserDto
	 */
	private UserDto getUserById(String idOperation, UserDto userEmpty, Long userId) {
		UserDto userReport;
		UserDto user = userList.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
		if (user != null) {
			userReport = user;
		} else {
			try {
				ResponseModel responseUser = userPersistencePort.findById(userId, idOperation);
				UserDto userBd = (UserDto) responseUser.getData();
				userList.add(userBd);
				userReport = userBd;
			} catch (Exception e) {
				userEmpty.setId(userId);
				userList.add(userEmpty);
				userReport = userEmpty;
			}
		}
		return userReport;
	}

	/**
	 * Método que evalua el rol operativa para la asinganación de valores de
	 * busqueda en rango de fechas y parámetros por defecto.
	 * 
	 * @param saleReportParams
	 * @param branchDto
	 * @return
	 */
	private SaleReportArticleParamsDto generateSearchParamsByRoleJob(
			SaleReportArticleParamsDto saleReportArticleParamsDto, BranchDto branchDto) {

		if (saleReportArticleParamsDto.getStartDate() == null)
			saleReportArticleParamsDto.setStartDate(getDefautlStartDate());

		if (saleReportArticleParamsDto.getEndDate() == null)
			saleReportArticleParamsDto.setEndDate(getDefaultEndDate());

		saleReportArticleParamsDto.setBranchCode(branchDto.getCode());

		return saleReportArticleParamsDto;
	}

	/***
	 * Método que retorna la fecha de inicio para la busqueda de datos, se toma el
	 * día primero del año en curso
	 * 
	 * @return
	 */
	private Date getDefautlStartDate() {
		Date from = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		from = cal.getTime();
		return from;
	}

	/**
	 * Método que retorna la fecha de fin para la busqueda de datos, se toma el día
	 * actual en que se genera el reporte
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Date getDefaultEndDate() {
		Date to = new Date();
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(new Date());
		calTo.set(Calendar.MONTH, to.getMonth());
		calTo.set(Calendar.HOUR_OF_DAY, 23);
		calTo.set(Calendar.MINUTE, 59);
		calTo.set(Calendar.SECOND, 59);
		calTo.set(Calendar.MILLISECOND, 59);
		to = calTo.getTime();
		return to;
	}

}
