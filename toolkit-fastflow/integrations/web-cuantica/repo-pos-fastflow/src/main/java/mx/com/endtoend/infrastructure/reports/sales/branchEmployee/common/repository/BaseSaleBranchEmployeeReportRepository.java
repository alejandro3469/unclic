package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository;

import mx.com.endtoend.domain.commons.constants.DocumentFormatEnum;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.*;
import mx.com.endtoend.domain.reports.sales.models.*;
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

import javax.persistence.MappedSuperclass;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

@MappedSuperclass
public class BaseSaleBranchEmployeeReportRepository implements GenericSaleBranchEmployeeRepository {

    private final BaseCustomDSLPaymentBranchEmployeeRepository customDSLPaymentBranchEmployeeRepository;

    private final Logger LOG;

    public BaseSaleBranchEmployeeReportRepository(Class<?> loggerClass, BaseCustomDSLPaymentBranchEmployeeRepository  _customDSLPaymentBranchEmployeeRepository){
        customDSLPaymentBranchEmployeeRepository = _customDSLPaymentBranchEmployeeRepository;
        LOG = LoggerFactory.getLogger(loggerClass);
    }


    @Override
    public ResponseModel generateSaleBranchEmployeeReport(ReportBranchEmployeeDto reportBranchEmployee, String format,
                                                          String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateSaleBranchEmployeeReport()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass()
                    .getResourceAsStream("/reports/sales/branh_employee/Sale_Branch_Employee.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<ReportBranchEmployeeDto> collection = Collections.singletonList(reportBranchEmployee);

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
    public ResponseModel generateSaleBranchEmployeeReportV2(List<ReportSalesDto> reportBranchEmployee, String format,
                                                            String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateSaleBranchEmployeeReportV2()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass()
                    .getResourceAsStream("/reports/sales/branh_employee/sales_report.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));

            Map<String, Object> reportSalesDto = new HashMap<>();
            reportSalesDto.put("summaryDetail", reportBranchEmployee);

            Collection<Map<String, Object>> collection = Collections.singletonList(reportSalesDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            byte[] finalReport = null;

            LOG.info(String.format("%s GENERATE ARRAY BYTE EXCEL", idOperation));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(output);
            exporter.exportReport();
            output.close();
            finalReport = byteArray.toByteArray();

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN generateDocument()", idOperation));
            LOG.error(e.getMessage());
            return new ResponseModel(null);
        }
    }

    @Override
    public ResponseModel generateCreditNotesReport(List<ReportNotesDto> reportBranchEmployee, String format,
                                                   String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateSaleBranchEmployeeReportV2()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass()
                    .getResourceAsStream("/reports/sales/branh_employee/notes_report.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));

            Map<String, Object> reportSalesDto = new HashMap<>();
            reportSalesDto.put("summaryDetail", reportBranchEmployee);

            Collection<Map<String, Object>> collection = Collections.singletonList(reportSalesDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            byte[] finalReport = null;
            LOG.info(String.format("%s GENERATE ARRAY BYTE EXCEL", idOperation));
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(output);
            exporter.exportReport();
            output.close();
            finalReport = byteArray.toByteArray();

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN generateDocument()", idOperation));
            LOG.error(e.getMessage());
            return new ResponseModel(null);
        }
    }

    @Override
    public List<SummarySaleCashPaymentDto> searchSaleCashByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCashByEmployeeAndParams()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryCashPaymentByEmployeeAndParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCreditCardPaymentDto> searchSaleCreditCardByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCreditCardByEmployeeAndParams()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryCreditCardPaymentByEmployeeAndParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCreditNotePaymentDto> searchSaleCredittNoteByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCredittNoteByEmployeeAndParams()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryCreditNotePaymentByEmployeeAndParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleTransferPaymentDto> searchSaleTransferByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleTransferByEmployeeAndParams()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryTransferPaymentByEmployeeAndParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCheckPaymentDto> searchSaleCheckByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCheckByEmployeeAndParams()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryCheckPaymentByEmployeeAndParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<SummarySaleCreditPaymentDto> searchSaleCreditByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCreditByEmployeeAndParams()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryCreditPaymentByEmployeeAndParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    public List<ReportSalesDto> searchSaleCreditByEmployeeAndParamsV2(
            SaleAndNotesReportParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCreditByEmployeeAndParamsV2()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findSummaryCreditPaymentByEmployeeAndParamsV2(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

    public List<ReportNotesDto> findCreditNotesByParams(
            SaleAndNotesReportParamsDto saleBranchEmployeeParams, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchSaleCreditByEmployeeAndParamsV2()", idOperation));
            return customDSLPaymentBranchEmployeeRepository
                    .findCreditNotesByParams(saleBranchEmployeeParams);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new GlobalError();
        }
    }

}