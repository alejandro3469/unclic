package mx.com.endtoend.infrastructure.reports.sales.branch.common.repository;

import mx.com.endtoend.domain.commons.constants.DocumentFormatEnum;
import mx.com.endtoend.domain.reports.sales.branch.dto.BranchSaleReportDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.models.*;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.payments.carredana.repositories.CustomDSLPaymentFCarredanaRepository;
import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import mx.com.endtoend.infrastructure.reports.sales.branch.carredana.SaleReportCarredanaRepository;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BaseSaleReportRepository  implements GenericSaleReportRepository {


    private final BaseCustomDSLPaymentRepository customDSLPaymentRepository;

    private final Logger LOG;

    public BaseSaleReportRepository(Class<?> loggerClass, BaseCustomDSLPaymentRepository _customDSLPaymentRepository){
        LOG = LoggerFactory.getLogger(loggerClass);
        customDSLPaymentRepository = _customDSLPaymentRepository;
    }

    @Override
    public ResponseModel generateReportBySaleBranchAndCompanyCode(BranchSaleReportDto branchSaleReport, String format,
                                                                  String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateReportBySaleBranchAndCompanyCode()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream("/reports/sales/branch/Sale_Branch.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<BranchSaleReportDto> collection = Collections.singletonList(branchSaleReport);

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
    public List<SummarySaleCashPaymentDto> searchPaymentCashSummaryByParams(
            GenericSearchSaleReportParamsDto saleReportParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchPaymentCashSummaryByParams()", idOperation));
            return customDSLPaymentRepository.findSummaryCashPaymentByParams(saleReportParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }

    }

    @Override
    public List<SummarySaleTransferPaymentDto> searchPaymentTransferSummaryByParams(
            GenericSearchSaleReportParamsDto saleReportParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchPaymentTransferSummaryByParams()", idOperation));
            return customDSLPaymentRepository.findSummaryTransferPaymentByParams(saleReportParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCheckPaymentDto> searchPaymentCheckSummaryByParams(
            GenericSearchSaleReportParamsDto saleReportParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchPaymentCheckSummaryByParams()", idOperation));
            return customDSLPaymentRepository.findSummaryCheckPaymentByParams(saleReportParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCreditCardPaymentDto> searchPaymentCreditCardSummaryByParams(
            GenericSearchSaleReportParamsDto saleReportParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchPaymentCreditCardSummaryByParams()", idOperation));
            return customDSLPaymentRepository.findSummaryCreditCardPaymentByParams(saleReportParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCreditNotePaymentDto> searchPaymentCreditNoteSummaryByParams(
            GenericSearchSaleReportParamsDto saleReportParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchPaymentCreditNoteSummaryByParams()", idOperation));
            return customDSLPaymentRepository.findSummaryCreditNotePaymentByParams(saleReportParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCreditPaymentDto> searchPaymentCreditSummaryByParams(
            GenericSearchSaleReportParamsDto saleReportParams, String idOperation) {
        return new ArrayList<>();
    }

}