package mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository;

import mx.com.endtoend.domain.commons.constants.DocumentFormatEnum;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.*;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingOperationReport;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@MappedSuperclass
public class BaseClosingOperationReportRepository implements GenericClosingOperationReportRepository {

    private final BaseCustomDSLClosingOperationRepository customDSLClosingOperationRepository;

    private final Logger LOG;


    public BaseClosingOperationReportRepository(Class<?> loggerClass,BaseCustomDSLClosingOperationRepository _customDSLClosingOperationRepository){
        LOG = LoggerFactory.getLogger(loggerClass);
        customDSLClosingOperationRepository = _customDSLClosingOperationRepository;
    }

    @Override
    public ResponseModel generateReportByBranchClosing(BranchClosingOperationReport branchClosingOperationReport,
                                                       String format, String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateReportByBranchClosing()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass()
                    .getResourceAsStream("/reports/sales/closing_operation/ClosingBranchOperationReport.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<BranchClosingOperationReport> collection = Collections
                    .singletonList(branchClosingOperationReport);

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
    public ResponseModel generateReportByGeneralClosing(GeneralClosingOperationReport generalClosingOperationReport,
                                                        String format, String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateReportByGeneralClosing()", idOperation));

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass()
                    .getResourceAsStream("/reports/sales/closing_operation/ClosingGeneralOperationReport.jasper");

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<GeneralClosingOperationReport> collection = Collections
                    .singletonList(generalClosingOperationReport);

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
    public List<ClosingOperationSummarySeach> findClosingRecordsByParams(
            ClosingOperationReportParamsDto closingOperationReportParamsDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT SEARCH DATA", idOperation));
            return customDSLClosingOperationRepository
                    .findCloringOperationRecordByParams(closingOperationReportParamsDto);
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<AccountingRecordReported> findIncomeAccountingRecordsByParams(
            ClosingOperationSummarySeach closingOSummarySeach, String idOperation) {
        try {
            LOG.info(String.format("%s INIT SEARCH DATA", idOperation));
            return customDSLClosingOperationRepository
                    .findAccountinrecordDetailByOpeninigId(closingOSummarySeach.getOpeningId());
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<ClosingOperationReported> findClosingAccountingRecordsByParams(
            ClosingOperationSummarySeach closingOSummarySeach, String idOperation) {
        try {
            LOG.info(String.format("%s INIT SEARCH DATA", idOperation));
            return customDSLClosingOperationRepository
                    .findAccountingClosingRecordByOpeningId(closingOSummarySeach.getOpeningId());
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<ClosingOperationReported> findClosingRecordsByParams(ClosingOperationSummarySeach closingOSummarySeach,
                                                                     String idOperation) {
        try {
            LOG.info(String.format("%s INIT SEARCH DATA", idOperation));
            return customDSLClosingOperationRepository
                    .findClosingOperationDetailByClosingId(closingOSummarySeach.getClosingId());
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<AccountingTicketRecord> findAccountingTicketReferenceByParamsAndCompanyCode(
            ClosingOperationSummarySeach closingOSummarySeach, String idOperation) {
        try {
            LOG.info(String.format("%s INIT SEARCH DATA", idOperation));
            return customDSLClosingOperationRepository
                    .findAccountingTicketRecordByOpeningId(closingOSummarySeach.getOpeningId());
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }
    }

}