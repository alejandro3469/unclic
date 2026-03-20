package mx.com.endtoend.infrastructure.reports.sales.articles.common.repository;

import mx.com.endtoend.domain.commons.constants.DocumentFormatEnum;
import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleSaleReportDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SummaryArticleSaleDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public class BaseSaleArticleReportRepository implements GenericSaleArticleRepository {

    private final  BaseCustomDSLSaleArticleRepository customDSLSaleArticleRepository;

    private final Logger LOG;

    public BaseSaleArticleReportRepository(Class<?> loggerClass, BaseCustomDSLSaleArticleRepository _customDSLSaleArticleRepository) {
        LOG = LoggerFactory.getLogger(loggerClass);
        this.customDSLSaleArticleRepository = _customDSLSaleArticleRepository;
    }

    @Override
    public ResponseModel generateSaleArticleReport(ArticleSaleReportDto articleSaleReportDto, String format,
                                                   String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateSaleArticleReport()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream("/reports/sales/article/Sale_Article.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<ArticleSaleReportDto> collection = Collections.singletonList(articleSaleReportDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            byte[] finalReport = null;

            if (format.equalsIgnoreCase(DocumentFormatEnum.PDF.toString())) {

                LOG.info(String.format("%s GENERATE ARRAY BYTE PDF", idOperation));
                finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            } else if (format.equalsIgnoreCase(DocumentFormatEnum.XLS.toString())) {

                LOG.info(String.format("%s GENERATE ARRAY BYTE EXCEL", idOperation));
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(output);
                exporter.exportReport();
                output.close();
                finalReport = byteArray.toByteArray();

            } else {
                LOG.warn(String.format("%s INVALID FORMAT: %s", idOperation, format));
                throw new ValidationError("INVALID DOCUMENT FORMAT");
            }

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN generateDocument()", idOperation));
            LOG.error(e.getMessage());
            return new ResponseModel(null);
        }
    }

    @Override
    public List<SummaryArticleSaleDto> findArticleListByParams(SaleReportArticleParamsDto saleReportArticleParamsDto,
                                                               String idOperation) {
        try {
            LOG.info(String.format("%s INIT findArticleListByParams()", idOperation));
            return customDSLSaleArticleRepository.findSummarySaleArticleByParams(saleReportArticleParamsDto);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

}
