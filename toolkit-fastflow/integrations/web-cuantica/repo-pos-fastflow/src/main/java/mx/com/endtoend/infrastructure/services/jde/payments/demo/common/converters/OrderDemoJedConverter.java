package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.converters;

import java.math.BigDecimal;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.TaxDto;
import org.springframework.stereotype.Component;

import mx.com.endtoend.genericCommonsFileds.utilities.DateUtil;
import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F47011;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F47011Id;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F47012;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F47012Id;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4706;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4706Id;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4714;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4714Id;
import mx.com.endtoend.smart.bussiness.model.orders.dto.AddressDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

/**
 * Clase encargada de la conversión de modelos de órdenes del sistema
 * SmartBusiness al sistema JDE
 *
 * @author ddcasas
 *
 */

@Component
public class OrderDemoJedConverter {

    private DateUtil dateUtil = new DateUtil();

    private StringUtil stringUtil = new StringUtil();

    public F47011 orderDtoToF47011(OrderDto orderDto, BigDecimal an8Branch) {

        F47011 f47011 = new F47011();
        F47011Id f47011Id = new F47011Id();

        AddressDto addressDto = new AddressDto();
        for (AddressDto address : orderDto.getAddresses()) {
            if (address.getAddressType().equals("Env")) {
                addressDto = address;
            }
        }

        String applyIEPS = "V";
        for (TaxDto tax : orderDto.getTaxes()) {
            if (tax.getTaxValue().equals("IEPS") && tax.getValue().compareTo(BigDecimal.ZERO) > 0)
                applyIEPS = "V+";
        }

        f47011Id.setSyekco(orderDto.getCompanyNumber());
        f47011Id.setSyedoc(orderDto.getOrderNumber().longValue());
        f47011Id.setSyedct(orderDto.getOrderCode());

        f47011.setId(f47011Id);
        f47011.setSyedty("1");
        f47011.setSyedln(1L);
        f47011.setSyeder("R");
        f47011.setSyedsp(" ");
        f47011.setSyedbt(orderDto.getBatchFolio().toString());
        f47011.setSykcoo(orderDto.getCompanyNumber());
        f47011.setSydcto(orderDto.getOrderCode());
        f47011.setSymcu(stringUtil.autocompleteSpace(orderDto.getBranchCode().trim(), 12, false));
        f47011.setSyokco(orderDto.getCompanyNumber());
        f47011.setSyoorn(String.valueOf(orderDto.getOrderNumber().longValue()));
        f47011.setSyocto(orderDto.getOrderCode());
        f47011.setSyrkco(orderDto.getCompanyNumber());
        f47011.setSyrorn("00000000");
        f47011.setSyrcto("");
        f47011.setSyan8(orderDto.getClient().getNoClient());
        f47011.setSyshan(orderDto.getClient().getNoClient());
        f47011.setSydrqj(dateUtil.convertDateToJulianDate(orderDto.getRequestDate()));
        f47011.setSytrdj(dateUtil.convertDateToJulianDate(orderDto.getCreationDate()));
        f47011.setSyvr01(orderDto.getOrderCode() + String.valueOf(orderDto.getOrderNumber().longValue()));
        f47011.setSyasn("");
        f47011.setSystop(addressDto.getCoordinateCode() != null ? addressDto.getCoordinateCode() : "");
        f47011.setSyzon(addressDto.getFlatCode() != null ? addressDto.getFlatCode() : "");
        f47011.setSycrrm(orderDto.getCurrency().equals("MXN") ? "D" : "F");
        f47011.setSycrcd(orderDto.getCurrency());
        f47011.setSycrr(orderDto.getExchangeRate().multiply(new BigDecimal("100")).longValue());
        f47011.setSytorg(orderDto.getUserNumber().toString());
        f47011.setSyuser(orderDto.getEmployeeEmail().substring(0, 10));
        f47011.setSypid("POS");
        f47011.setSyjobn("SM-POS");
        f47011.setSyupmj(dateUtil.convertDateToJulianDate(orderDto.getCreationDate()));
        f47011.setSyhold(orderDto.getRetentionCode());
        f47011.setSyvr02("");
        f47011.setSydoco(orderDto.getOrderNumber().longValue());
        f47011.setSyexr1(applyIEPS);
        f47011.setSytxa1("IV16");
        f47011.setSypa8(an8Branch.longValue());
        f47011.setSyptc("C02");
        f47011.setSyryin("");
        f47011.setSyrcd(" ");
        f47011.setSyurrf(orderDto.getCfdiType());

        return f47011;
    }

    public F47012 orderDetailDtoToF47012(OrderDetailDto orderDetailDto, OrderDto orderDto) {

        F47012 f47012 = new F47012();
        F47012Id f47012Id = new F47012Id();

        AddressDto addressDto = new AddressDto();
        for (AddressDto address : orderDto.getAddresses()) {
            if (address.getAddressType().equals("Env")) {
                addressDto = address;
            }
        }

        String applyIEPS = "V";
        for (TaxDto tax : orderDto.getTaxes()) {
            if (tax.getTaxValue().equals("IEPS") && tax.getValue().compareTo(BigDecimal.ZERO) > 0)
                applyIEPS = "V+";
        }

        Long lineNumber = (long) (orderDetailDto.getLineNumber() * 1000);

        Long urab = 0L;
        if ((orderDto.getOrderCode().equals("VE") || orderDto.getOrderCode().equals("VW")) &&
                orderDetailDto.getLineCodeOne().equals("570") &&
                orderDetailDto.getLineCodeTwo().equals("560"))
            urab = 1L;

        f47012Id.setSzekco(orderDto.getCompanyNumber());
        f47012Id.setSzedoc(orderDto.getOrderNumber().longValue());
        f47012Id.setSzedct(orderDto.getOrderCode());
        f47012Id.setSzedln(lineNumber);

        f47012.setId(f47012Id);
        f47012.setSzedty("1");
        f47012.setSzeder("R");
        f47012.setSzedsp(" ");
        f47012.setSzedbt(orderDto.getBatchFolio().toString());
        f47012.setSzkcoo(orderDto.getCompanyNumber());
        f47012.setSzdcto(orderDto.getOrderCode());
        f47012.setSzmcu(stringUtil.autocompleteSpace(orderDetailDto.getWarehouseCode().trim(), 12, false));
        f47012.setSzokco(orderDto.getCompanyNumber());
        f47012.setSzoorn(String.valueOf(orderDto.getOrderNumber().longValue()));
        f47012.setSzocto(orderDto.getOrderCode());
        f47012.setSzrkco(orderDto.getCompanyNumber());
        f47012.setSzrorn("00000000");
        f47012.setSzrcto("");
        f47012.setSzan8(orderDto.getClient().getNoClient());
        f47012.setSzshan(orderDto.getClient().getNoClient());
        f47012.setSzdrqj(dateUtil.convertDateToJulianDate(orderDto.getRequestDate()));
        f47012.setSztrdj(dateUtil.convertDateToJulianDate(orderDto.getCreationDate()));
        f47012.setSzvr01(orderDto.getOrderCode() + String.valueOf(orderDto.getOrderNumber().longValue()));
        f47012.setSzasn("");
        f47012.setSzstop(addressDto.getCoordinateCode());
        f47012.setSzzon(addressDto.getFlatCode());
        f47012.setSzuser(orderDto.getEmployeeEmail().substring(0, 10));
        f47012.setSzpid("PID");
        f47012.setSzjobn("SM-POS");
        f47012.setSzupmj(dateUtil.convertDateToJulianDate(orderDto.getCreationDate()));
        f47012.setSzhold(orderDetailDto.getRetentionCode());
        f47012.setSzvr02("");
        f47012.setSzedst("850");
        f47012.setSzdoco(orderDto.getOrderNumber().longValue());
        f47012.setSzlnid(lineNumber);
        f47012.setSzsfxo("000");
        f47012.setSzpa8(0L);
        f47012.setSzitm(orderDetailDto.getArticleNumber().longValue());
        f47012.setSzemcu(stringUtil.autocompleteSpace(orderDto.getBranchCode(), 12, false));
        f47012.setSzuom(orderDetailDto.getUnitMeasurement());
        f47012.setSzuom4(orderDetailDto.getUnitMeasurement());
        f47012.setSzsoqs(orderDetailDto.getRequestAmount().multiply(new BigDecimal("1000")).longValue());
        f47012.setSztax1(orderDetailDto.getApplyTax().equals("Y") ? "Y" : "N");
        f47012.setSzexr1(applyIEPS);
        f47012.setSzvend(Long.parseLong(orderDetailDto.getSupplierNumber()));
        f47012.setSzlttr(orderDetailDto.getLineCodeTwo());
        f47012.setSznxtr(orderDetailDto.getLineCodeOne());
        f47012.setSzuprc(orderDetailDto.getFinalUnitPrice().multiply(new BigDecimal("1000")).longValue());
        f47012.setSzdct("");
        f47012.setSzdoc(null);
        f47012.setSzco(orderDto.getCompanyNumber());
        f47012.setSzivd(dateUtil.getCurrentTimeJulianDate());
        f47012.setSzlnty(orderDetailDto.getLineType());
        f47012.setSzuorg(orderDetailDto.getRequestAmount().multiply(new BigDecimal("1000")).longValue());
        f47012.setSzurcd("CP");
        f47012.setSzurdt(0L);
        f47012.setSzurat(0L);
        f47012.setSzurab(urab);
        f47012.setSzurrf("               ");
        f47012.setSzogno(orderDetailDto.getRequestAmount().multiply(new BigDecimal("1000")).longValue());

        return f47012;
    }

    public F4706 addressDtoToF4706(OrderDto orderDto) {

        F4706 f4706 = new F4706();
        F4706Id f4706Id = new F4706Id();

        AddressDto addressDto = new AddressDto();
        for (AddressDto address : orderDto.getAddresses()) {
            if (address.getAddressType().equals("Env")) {
                addressDto = address;
            }
        }

        String clientName = orderDto.getClient().getName() + orderDto.getClient().getFatherSurname()
                + orderDto.getClient().getMotherSurname();

        String addressNumber = "";
        addressNumber = addressDto.getOutdoorNumber() != null ? addressDto.getOutdoorNumber() + "/" : "/";
        addressNumber = addressDto.getInteriorNumber() != null ? addressNumber + addressDto.getInteriorNumber()
                : addressNumber;

        f4706Id.setZaekco(orderDto.getCompanyNumber());
        f4706Id.setZaedoc(orderDto.getOrderNumber().longValue());
        f4706Id.setZaedct(orderDto.getOrderCode());
        f4706Id.setZaedln(0L);
        f4706Id.setZafile("F47011");
        f4706Id.setZaanty("2");

        f4706.setId(f4706Id);
        f4706.setZaedty("6");
        f4706.setZaedsq(3L);
        f4706.setZaedsp(" ");
        f4706.setZadoco(orderDto.getOrderNumber());
        f4706.setZadcto(orderDto.getOrderCode());
        f4706.setZakcoo(orderDto.getCompanyNumber());
        f4706.setZaan8(orderDto.getClient().getNoClient());
        f4706.setZamlnm(clientName.isEmpty() ? orderDto.getClient().getBusinessName() : clientName);
        f4706.setZaadd1("");
        f4706.setZaadd2(addressDto.getStreet());
        f4706.setZaadd3(addressNumber);
        f4706.setZaadd4(addressDto.getColony());
        f4706.setZaaddz(addressDto.getCp());
        f4706.setZacty1(addressDto.getCity());
        f4706.setZacoun(addressDto.getDelegationCode());
        f4706.setZaadds(addressDto.getStateCode());
        f4706.setZacrte("");
        f4706.setZabkml("");
        f4706.setZactr("MX");
        f4706.setZatorg("POS");
        f4706.setZauser("POS");
        f4706.setZapid("POSMIGRA");
        f4706.setZajobn("SM-POS");
        f4706.setZaupmj(dateUtil.getCurrentJulianDate());
        f4706.setZatday(dateUtil.getCurrentTimeJulianDate());
        f4706.setZalnid(0L);
        f4706.setZagan8(0L);

        return f4706;
    }

    public F4714 generateObservationByOrderDto(OrderDto orderDto) {

        F4714 f4714 = new F4714();
        F4714Id f4714Id = new F4714Id();

        char[] lines = orderDto.getObservations().toCharArray();
        Double linesNumber = (double) (lines.length / 60);
        Long ztlins = (long) (Math.ceil(linesNumber) * 100);

        f4714Id.setZtekco(orderDto.getCompanyNumber());
        f4714Id.setZtedoc(orderDto.getOrderNumber().longValue());
        f4714Id.setZtedct(orderDto.getOrderCode());
        f4714Id.setZtedln(100L);
        f4714Id.setZtfile("F47011");
        f4714Id.setZtlins(ztlins);

        f4714.setId(f4714Id);
        f4714.setZtedbt(orderDto.getBatchFolio().toString());
        f4714.setZtkcoo(orderDto.getCompanyNumber());
        f4714.setZtdoco(orderDto.getOrderNumber().longValue());
        f4714.setZtdcto(orderDto.getOrderCode());
        f4714.setZtlnid(0L);
        f4714.setZttxln(orderDto.getObservations().length() > 59 ? orderDto.getObservations().substring(0, 59)
                : orderDto.getObservations());
        f4714.setZtpid("R47011");
        f4714.setZtjobn("ENTJDESRV0");
        f4714.setZtupmj(dateUtil.getCurrentJulianDate());
        f4714.setZttday(dateUtil.getCurrentTimeJulianDate());

        return f4714;
    }
}
