package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import mx.com.endtoend.smart.bussiness.model.orders.dto.*;
import org.springframework.stereotype.Component;

import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DetalleSolTras;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DetalleSolTrasId;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DirEnvioClienteOrdenes;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.DirEnvioClienteOrdenesId;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.EncabezadoSolTraspaso;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.EncabezadoSolTraspasoId;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrden;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenBkup;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenBkupId;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblDetalleOrdenId;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrden;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenBkup;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenBkupId;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.TblEncabezadoOrdenId;

@Component
public class OrderConverterMig {

	StringUtil stringUtil = new StringUtil();

	public OrderDto TblEncabezadoOrdenToOrderDto(TblEncabezadoOrden tblEncabezadoOrden) {

		OrderDto orderDto = new OrderDto();

		orderDto.setOrderNumber(tblEncabezadoOrden.getId().getDoco());
		orderDto.setOrderCode(tblEncabezadoOrden.getId().getDcto());
		orderDto.setBranchCode(tblEncabezadoOrden.getMcu());
		orderDto.setCurrency(tblEncabezadoOrden.getCrcd());
		orderDto.setExchangeRate(tblEncabezadoOrden.getCrr());

		orderDto.setRequestDate(tblEncabezadoOrden.getDrqj1());

		orderDto.setClientTax(new BigDecimal(tblEncabezadoOrden.getTxa1()));
		orderDto.setClientReference(tblEncabezadoOrden.getVr01());
		orderDto.setUserNumber(tblEncabezadoOrden.getAn82().longValue());

		orderDto.setSubTotal(tblEncabezadoOrden.getSubTot());
		orderDto.setIvaTotal(tblEncabezadoOrden.getIva());
		orderDto.setOrderTotal(tblEncabezadoOrden.getTotal());
		orderDto.setPendingPayment(tblEncabezadoOrden.getImportePendiente());
		orderDto.setDiscountTotal(tblEncabezadoOrden.getPorcenEncabezado());
		orderDto.setRetentionCode(tblEncabezadoOrden.getRetenido());
		orderDto.setIsUpdated(tblEncabezadoOrden.isBloqueado());
		orderDto.setOrderType(tblEncabezadoOrden.getCampo1());
		orderDto.setTempMigStatus(String.valueOf(tblEncabezadoOrden.getEstatusMigrJDE()));

		orderDto.setObservations(tblEncabezadoOrden.getObservaciones());
		orderDto.setCfdiType(tblEncabezadoOrden.getCampo3());

		return orderDto;
	}

	public TblEncabezadoOrden orderDtoToTblEncabezadoOrden(OrderDto orderDto, String idUser) {

		TblEncabezadoOrden tblEncabezadoOrden = new TblEncabezadoOrden();
		TblEncabezadoOrdenId tblEncabezadoOrdenId = new TblEncabezadoOrdenId();

		tblEncabezadoOrdenId.setDoco(orderDto.getOrderNumber());
		tblEncabezadoOrdenId.setDcto(orderDto.getOrderCode());
		tblEncabezadoOrdenId.setKcooo(orderDto.getCompanyNumber());

		tblEncabezadoOrden.setId(tblEncabezadoOrdenId);
		tblEncabezadoOrden.setMcu(stringUtil.autocompleteSpace(orderDto.getBranchCode(), 12, false));
		tblEncabezadoOrden.setCrcd(orderDto.getCurrency());
		tblEncabezadoOrden.setCrr(orderDto.getExchangeRate());
		tblEncabezadoOrden.setAsn("");
		tblEncabezadoOrden.setTrar("CON");
		tblEncabezadoOrden.setTrdj1(orderDto.getCreationDate());
		tblEncabezadoOrden.setDrqj1(orderDto.getRequestDate());
		tblEncabezadoOrden.setAn8(new BigDecimal(orderDto.getClient().getNoClient().toString()));
		tblEncabezadoOrden.setAn82(new BigDecimal(orderDto.getUserNumber().toString()));
		tblEncabezadoOrden.setVr01(orderDto.getClientReference());
		tblEncabezadoOrden.setZon(" ");
		tblEncabezadoOrden.setStop(" ");
		Double iva = Double.valueOf(orderDto.getClient().getIva()) / 100;
		tblEncabezadoOrden.setTxa1(String.valueOf(iva));
		tblEncabezadoOrden.setTotalPeso(BigDecimal.ZERO);
		tblEncabezadoOrden.setSubTot(orderDto.getSubTotal());

		tblEncabezadoOrden.setIva(orderDto.getIvaTotal());

		tblEncabezadoOrden.setTotal(orderDto.getOrderTotal());
		tblEncabezadoOrden.setDocoOrigen(BigDecimal.ZERO);
		tblEncabezadoOrden.setDctoOrigen("");
		tblEncabezadoOrden.setImportePendiente(orderDto.getPendingPayment());
		tblEncabezadoOrden.setPorcenEncabezado(orderDto.getDiscountTotal());
		tblEncabezadoOrden.setAn8Autorizado(BigDecimal.ZERO);
		tblEncabezadoOrden.setEstatus(Integer.parseInt(orderDto.getStatus().getCode()));
		tblEncabezadoOrden.setRetenido(orderDto.getRetentionCode());
		tblEncabezadoOrden.setPorcVend(BigDecimal.ZERO);
		tblEncabezadoOrden.setPorcGteTienda(BigDecimal.ZERO);
		tblEncabezadoOrden.setPorcGteZona(BigDecimal.ZERO);
		tblEncabezadoOrden.setMcuSolicitante(null);
		tblEncabezadoOrden.setCampo1(orderDto.getOrderType());
		tblEncabezadoOrden.setCampo2(orderDto.getOrderNumber().toString() + orderDto.getOrderCode());
		tblEncabezadoOrden.setCampo3(orderDto.getCfdiType());
		tblEncabezadoOrden.setCampo4(null);
		tblEncabezadoOrden.setCampo5(BigDecimal.ZERO);
		tblEncabezadoOrden.setCampo6(BigDecimal.ZERO);
		tblEncabezadoOrden.setCampo7(BigDecimal.ZERO);
		tblEncabezadoOrden.setCampo8(BigDecimal.ZERO);
		tblEncabezadoOrden.setCampo9(null);
		tblEncabezadoOrden.setCampo10(null);
		tblEncabezadoOrden.setCampo11(null);
		tblEncabezadoOrden.setCampo12(null);
		tblEncabezadoOrden.setDocoJde(null);
		tblEncabezadoOrden.setDctoJde(null);
		tblEncabezadoOrden.setEstatusMigrNodoCentral(0);
		tblEncabezadoOrden.setEstatusMigrJDE(Integer.parseInt(orderDto.getTempMigStatus()));
		tblEncabezadoOrden.setBloqueado(false);
		tblEncabezadoOrden.setObservaciones(orderDto.getObservations());
		tblEncabezadoOrden.setImpresion(0);
		tblEncabezadoOrden.setPorcMaxAut(BigDecimal.ZERO);
		tblEncabezadoOrden.setModifiedBy(idUser);
		tblEncabezadoOrden.setModifiedDate(new Date());
		tblEncabezadoOrden.setModifiedOn("SMARTBUSSINES-POS");
		tblEncabezadoOrden.setCancelado(false);
		tblEncabezadoOrden.setMigJDEInt(0);
		tblEncabezadoOrden.setFacturaTope(orderDto.getIsInvoiceTop());
		tblEncabezadoOrden.setFacturaTopeMonto(orderDto.getInvoiceAmount());

		return tblEncabezadoOrden;
	}

	public TblDetalleOrden orderDetailDtoToTblDetalleOrden(OrderDto orderDto, OrderDetailDto orderDetailDto,
			String idUser) {

		TblDetalleOrden tblDetalleOrden = new TblDetalleOrden();

		TblDetalleOrdenId tblDetalleOrdenId = new TblDetalleOrdenId();

		tblDetalleOrdenId.setDoco(orderDto.getOrderNumber());
		tblDetalleOrdenId.setDcto(orderDto.getOrderCode());
		tblDetalleOrdenId.setIdLinea(orderDetailDto.getLineNumber());
		tblDetalleOrdenId.setMcuVenta(stringUtil.autocompleteSpace(orderDto.getBranchCode(), 12, false));

		tblDetalleOrden.setId(tblDetalleOrdenId);
		tblDetalleOrden.setTipoLinea(orderDetailDto.getLineType());
		tblDetalleOrden.setMcu(orderDetailDto.getWarehouseCode());
		tblDetalleOrden.setItm(orderDetailDto.getArticleNumber());
		tblDetalleOrden.setDesc1(orderDetailDto.getDescriptionOne());
		tblDetalleOrden.setDesc2(orderDetailDto.getDescriptionTwo());
		tblDetalleOrden.setUm(orderDetailDto.getUnitMeasurement());
		tblDetalleOrden.setQty(orderDetailDto.getRequestAmount());
		tblDetalleOrden.setPrecio(orderDetailDto.getFinalUnitPrice());
		tblDetalleOrden.setTotal(orderDetailDto.getSubTotal());
		tblDetalleOrden.setCrcd(orderDto.getCurrency());
		tblDetalleOrden.setTipoCambio(orderDto.getExchangeRate());
		tblDetalleOrden.setPrecioUnIva(orderDetailDto.getUnitPriceTax());
		tblDetalleOrden.setPrecioTotalIva(orderDetailDto.getSubTotalTax());

		BigDecimal articleIva = BigDecimal.ZERO;
		if (orderDetailDto.getTaxValueOne().compareTo(BigDecimal.ZERO) != -1) {
			articleIva = orderDetailDto.getFinalUnitPrice()
					.multiply(orderDetailDto.getTaxValueOne().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
					.multiply(orderDetailDto.getRequestAmount());
			articleIva = articleIva.setScale(2, RoundingMode.HALF_UP);
		}
		tblDetalleOrden.setIva(articleIva);

		BigDecimal articleIeps = BigDecimal.ZERO;
		if (orderDetailDto.getTaxValueTwo().compareTo(BigDecimal.valueOf(-1)) != 0) {
			articleIeps = orderDetailDto.getFinalUnitPrice()
					.multiply(orderDetailDto.getTaxValueTwo().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
					.multiply(orderDetailDto.getRequestAmount());
			articleIeps = articleIeps.setScale(2, RoundingMode.HALF_UP);
		}
		tblDetalleOrden.setPorIeps(articleIeps);

		tblDetalleOrden.setDocoOrigen(null);
		tblDetalleOrden.setDctoOrigen(null);
		tblDetalleOrden.setNxtr(null);
		tblDetalleOrden.setLttr(null);
		tblDetalleOrden.setNxtrPos(orderDetailDto.getLineCodeOne());
		tblDetalleOrden.setLttrPos(orderDetailDto.getLineCodeTwo());
		tblDetalleOrden.setPorcLinea(orderDetailDto.getDiscountSeller());
		tblDetalleOrden.setAn8Aut(null);
		if (orderDetailDto.getSupplierNumber() != null) {
			tblDetalleOrden.setAn8Vend(new BigDecimal(orderDetailDto.getSupplierNumber()));
		} else {
			tblDetalleOrden.setAn8Vend(null);
		}
		tblDetalleOrden.setPrecioLista(orderDetailDto.getUnitPrice());
		tblDetalleOrden.setCampo1(null);
		tblDetalleOrden.setCampo2(null);
		tblDetalleOrden.setCampo3(orderDetailDto.getArticleCode());
		tblDetalleOrden.setCampo4(null);
		tblDetalleOrden.setCampo5(BigDecimal.ZERO);
		tblDetalleOrden.setCampo6(BigDecimal.ZERO);
		tblDetalleOrden.setCampo7(BigDecimal.ZERO);
		tblDetalleOrden.setCampo8(new BigDecimal(orderDetailDto.getPriceType()));
		tblDetalleOrden.setCampo9(null);
		tblDetalleOrden.setCampo10(null);
		tblDetalleOrden.setCampo11(null);
		tblDetalleOrden.setCampo12(null);
		tblDetalleOrden.setDocoJde(null);
		tblDetalleOrden.setDctoJde(null);
		tblDetalleOrden.setRetenido(orderDetailDto.getRetentionCode());
		tblDetalleOrden.setImsrtx(orderDetailDto.getAlternateDescription());
		tblDetalleOrden.setModifiedBy(idUser);
		tblDetalleOrden.setModifiedDate(new Date());
		tblDetalleOrden.setModifiedOn("SMARTBUSSINES-POS");
		tblDetalleOrden.setFactorEmpaque(BigDecimal.ZERO);
		tblDetalleOrden.setCancelado(false);
		tblDetalleOrden.setPorcIva(orderDetailDto.getTaxValueOne().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueOne().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrden.setPorcIeps(orderDetailDto.getTaxValueTwo().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueTwo().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrden
				.setPorcIva3(orderDetailDto.getTaxValueThree().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueThree().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrden
				.setPorcIva4(orderDetailDto.getTaxValueFour().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueFour().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrden
				.setPorcIva5(orderDetailDto.getTaxValueFive().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueFive().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrden.setTasaIva(BigDecimal.ZERO);
		tblDetalleOrden.setPrecioListaOriginal(new BigDecimal(orderDetailDto.getPriceType()));
		tblDetalleOrden.setPrecioUnitario(orderDetailDto.getUnitPrice());
		tblDetalleOrden.setFactorConversion(orderDetailDto.getConversionFactor());
		tblDetalleOrden.setFechaCobro(null);

		return tblDetalleOrden;
	}

	public TblEncabezadoOrdenBkup orderDtoToTblEncabezadoOrdenBkup(OrderDto orderDto, String idUser) {

		TblEncabezadoOrdenBkup tblEncabezadoOrdenBkup = new TblEncabezadoOrdenBkup();

		TblEncabezadoOrdenBkupId tblEncabezadoOrdenBkupId = new TblEncabezadoOrdenBkupId();

		tblEncabezadoOrdenBkupId.setDoco(orderDto.getOrderNumber());
		tblEncabezadoOrdenBkupId.setDcto(orderDto.getOrderCode());
		tblEncabezadoOrdenBkupId.setKcooo(orderDto.getCompanyNumber());

		tblEncabezadoOrdenBkup.setId(tblEncabezadoOrdenBkupId);
		tblEncabezadoOrdenBkup.setMcu(stringUtil.autocompleteSpace(orderDto.getBranchCode(), 12, false));
		tblEncabezadoOrdenBkup.setCrcd(orderDto.getCurrency());
		tblEncabezadoOrdenBkup.setCrr(orderDto.getExchangeRate());
		tblEncabezadoOrdenBkup.setAsn("");
		tblEncabezadoOrdenBkup.setTrar("CON");
		tblEncabezadoOrdenBkup.setTrdj1(orderDto.getCreationDate());
		tblEncabezadoOrdenBkup.setDrqj1(orderDto.getRequestDate());
		tblEncabezadoOrdenBkup.setAn8(new BigDecimal(orderDto.getClient().getNoClient().toString()));
		tblEncabezadoOrdenBkup.setAn82(new BigDecimal(orderDto.getUserNumber().toString()));
		tblEncabezadoOrdenBkup.setVr01(orderDto.getClientReference());
		tblEncabezadoOrdenBkup.setZon(" ");
		tblEncabezadoOrdenBkup.setStop(" ");
		tblEncabezadoOrdenBkup.setTxa1(orderDto.getClient().getIva());
		tblEncabezadoOrdenBkup.setTotalPeso(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setSubTot(orderDto.getSubTotal());
		tblEncabezadoOrdenBkup.setIva(orderDto.getIvaTotal());
		tblEncabezadoOrdenBkup.setTotal(orderDto.getOrderTotal());
		tblEncabezadoOrdenBkup.setDocoOrigen(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setDctoOrigen("");
		tblEncabezadoOrdenBkup.setImportePendiente(orderDto.getPendingPayment());
		tblEncabezadoOrdenBkup.setPorcenEncabezado(orderDto.getDiscountTotal());
		tblEncabezadoOrdenBkup.setAn8Autorizado(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setEstatus(Integer.parseInt(orderDto.getStatus().getCode()));
		tblEncabezadoOrdenBkup.setRetenido(orderDto.getRetentionCode());
		tblEncabezadoOrdenBkup.setPorcVend(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setPorcGteTienda(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setPorcGteZona(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setMcuSolicitante(null);
		tblEncabezadoOrdenBkup.setCampo1(orderDto.getOrderType());
		tblEncabezadoOrdenBkup.setCampo2(orderDto.getOrderNumber().toString() + orderDto.getOrderCode());
		tblEncabezadoOrdenBkup.setCampo3(orderDto.getCfdiType());
		tblEncabezadoOrdenBkup.setCampo4(null);
		tblEncabezadoOrdenBkup.setCampo5(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setCampo6(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setCampo7(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setCampo8(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setCampo9(null);
		tblEncabezadoOrdenBkup.setCampo10(null);
		tblEncabezadoOrdenBkup.setCampo11(null);
		tblEncabezadoOrdenBkup.setCampo12(null);
		tblEncabezadoOrdenBkup.setDocoJde(null);
		tblEncabezadoOrdenBkup.setDctoJde(null);
		tblEncabezadoOrdenBkup.setEstatusMigrNodoCentral(0);
		tblEncabezadoOrdenBkup.setEstatusMigrJDE(Integer.parseInt(orderDto.getTempMigStatus()));
		tblEncabezadoOrdenBkup.setBloqueado(false);
		tblEncabezadoOrdenBkup.setObservaciones(orderDto.getObservations());
		tblEncabezadoOrdenBkup.setImpresion(0);
		tblEncabezadoOrdenBkup.setPorcMaxAut(BigDecimal.ZERO);
		tblEncabezadoOrdenBkup.setModifiedBy(idUser);
		tblEncabezadoOrdenBkup.setModifiedDate(new Date());
		tblEncabezadoOrdenBkup.setModifiedOn("SMARTBUSSINES-POS");
		tblEncabezadoOrdenBkup.setCancelado(false);
		tblEncabezadoOrdenBkup.setMigJDEInt(0);
		tblEncabezadoOrdenBkup.setFacturaTope(orderDto.getIsInvoiceTop());
		tblEncabezadoOrdenBkup.setFacturaTopeMonto(orderDto.getInvoiceAmount());

		return tblEncabezadoOrdenBkup;
	}

	public TblDetalleOrdenBkup orderDetailDtoToTblDetalleOrdenBkup(OrderDto orderDto, OrderDetailDto orderDetailDto,
			String idUser) {

		TblDetalleOrdenBkup tblDetalleOrdenBkup = new TblDetalleOrdenBkup();

		TblDetalleOrdenBkupId tblDetalleOrdenBkupId = new TblDetalleOrdenBkupId();

		tblDetalleOrdenBkupId.setDoco(orderDto.getOrderNumber());
		tblDetalleOrdenBkupId.setDcto(orderDto.getOrderCode());
		tblDetalleOrdenBkupId.setIdLinea(orderDetailDto.getLineNumber());
		tblDetalleOrdenBkupId.setMcuVenta(stringUtil.autocompleteSpace(orderDto.getBranchCode(), 12, false));

		tblDetalleOrdenBkup.setId(tblDetalleOrdenBkupId);
		tblDetalleOrdenBkup.setTipoLinea(orderDetailDto.getLineType());
		tblDetalleOrdenBkup.setMcu(orderDetailDto.getWarehouseCode());
		tblDetalleOrdenBkup.setItm(orderDetailDto.getArticleNumber());
		tblDetalleOrdenBkup.setDesc1(orderDetailDto.getDescriptionOne());
		tblDetalleOrdenBkup.setDesc2(orderDetailDto.getDescriptionTwo());
		tblDetalleOrdenBkup.setUm(orderDetailDto.getUnitMeasurement());
		tblDetalleOrdenBkup.setQty(orderDetailDto.getRequestAmount());
		tblDetalleOrdenBkup.setPrecio(orderDetailDto.getFinalUnitPrice());
		tblDetalleOrdenBkup.setTotal(orderDetailDto.getSubTotal());
		tblDetalleOrdenBkup.setCrcd(orderDto.getCurrency());
		tblDetalleOrdenBkup.setTipoCambio(orderDto.getExchangeRate());
		tblDetalleOrdenBkup.setPrecioUnIva(orderDetailDto.getUnitPriceTax());
		tblDetalleOrdenBkup.setPrecioTotalIva(orderDetailDto.getSubTotalTax());

		BigDecimal articleIva = BigDecimal.ZERO;
		if (orderDetailDto.getTaxValueOne().compareTo(BigDecimal.valueOf(-1)) != 0) {
			articleIva = orderDetailDto.getFinalUnitPrice()
					.multiply(orderDetailDto.getTaxValueOne().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
					.multiply(orderDetailDto.getRequestAmount());
			articleIva = articleIva.setScale(2, RoundingMode.HALF_UP);
		}
		tblDetalleOrdenBkup.setIva(articleIva);

		BigDecimal articleIeps = BigDecimal.ZERO;
		if (orderDetailDto.getTaxValueTwo().compareTo(BigDecimal.valueOf(-1)) != 0) {
			articleIeps = orderDetailDto.getFinalUnitPrice()
					.multiply(orderDetailDto.getTaxValueTwo().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
					.multiply(orderDetailDto.getRequestAmount());
			articleIeps = articleIeps.setScale(2, RoundingMode.HALF_UP);
		}
		tblDetalleOrdenBkup.setPorIeps(articleIeps);

		tblDetalleOrdenBkup.setDocoOrigen(null);
		tblDetalleOrdenBkup.setDctoOrigen(null);
		tblDetalleOrdenBkup.setNxtr(null);
		tblDetalleOrdenBkup.setLttr(null);
		tblDetalleOrdenBkup.setNxtrPos(orderDetailDto.getLineCodeOne());
		tblDetalleOrdenBkup.setLttrPos(orderDetailDto.getLineCodeTwo());
		tblDetalleOrdenBkup.setPorcLinea(orderDetailDto.getDiscountSeller());
		tblDetalleOrdenBkup.setAn8Aut(null);
		if (orderDetailDto.getSupplierNumber() != null) {
			tblDetalleOrdenBkup.setAn8Vend(new BigDecimal(orderDetailDto.getSupplierNumber()));
		} else {
			tblDetalleOrdenBkup.setAn8Vend(null);
		}
		tblDetalleOrdenBkup.setPrecioLista(orderDetailDto.getUnitPrice());
		tblDetalleOrdenBkup.setCampo1(null);
		tblDetalleOrdenBkup.setCampo2(null);
		tblDetalleOrdenBkup.setCampo3(orderDetailDto.getArticleCode());
		tblDetalleOrdenBkup.setCampo4(null);
		tblDetalleOrdenBkup.setCampo5(BigDecimal.ZERO);
		tblDetalleOrdenBkup.setCampo6(BigDecimal.ZERO);
		tblDetalleOrdenBkup.setCampo7(BigDecimal.ZERO);
		tblDetalleOrdenBkup.setCampo8(new BigDecimal(orderDetailDto.getPriceType()));
		tblDetalleOrdenBkup.setCampo9(null);
		tblDetalleOrdenBkup.setCampo10(null);
		tblDetalleOrdenBkup.setCampo11(null);
		tblDetalleOrdenBkup.setCampo12(null);
		tblDetalleOrdenBkup.setDocoJde(null);
		tblDetalleOrdenBkup.setDctoJde(null);
		tblDetalleOrdenBkup.setRetenido(orderDetailDto.getRetentionCode());
		tblDetalleOrdenBkup.setImsrtx(orderDetailDto.getAlternateDescription());
		tblDetalleOrdenBkup.setModifiedBy(idUser);
		tblDetalleOrdenBkup.setModifiedDate(new Date());
		tblDetalleOrdenBkup.setModifiedOn("SMARTBUSSINES-POS");
		tblDetalleOrdenBkup.setFactorEmpaque(BigDecimal.ZERO);
		tblDetalleOrdenBkup.setCancelado(false);
		tblDetalleOrdenBkup
				.setPorcIva(orderDetailDto.getTaxValueOne().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueOne().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrdenBkup
				.setPorcIeps(orderDetailDto.getTaxValueTwo().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueTwo().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrdenBkup
				.setPorcIva3(orderDetailDto.getTaxValueThree().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueThree().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrdenBkup
				.setPorcIva4(orderDetailDto.getTaxValueFour().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueFour().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrdenBkup
				.setPorcIva5(orderDetailDto.getTaxValueFive().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : orderDetailDto.getTaxValueFive().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
		tblDetalleOrdenBkup.setTasaIva(BigDecimal.ZERO);
		tblDetalleOrdenBkup.setPrecioListaOriginal(new BigDecimal(orderDetailDto.getPriceType()));
		tblDetalleOrdenBkup.setPrecioUnitario(orderDetailDto.getUnitPrice());
		tblDetalleOrdenBkup.setFactorConversion(orderDetailDto.getConversionFactor());
		tblDetalleOrdenBkup.setFechaCobro(null);

		return tblDetalleOrdenBkup;
	}

	public DirEnvioClienteOrdenes addressDtoToDirEnvioClienteOrdenes(OrderDto orderDto, AddressDto addressDto,
			String idUser) {

		DirEnvioClienteOrdenes dirEnvioClienteOrdenes = new DirEnvioClienteOrdenes();

		DirEnvioClienteOrdenesId dirEnvioClienteOrdenesId = new DirEnvioClienteOrdenesId();

		dirEnvioClienteOrdenesId.setDoco(orderDto.getOrderNumber());
		dirEnvioClienteOrdenesId.setDcto(orderDto.getOrderCode());
		dirEnvioClienteOrdenesId.setNoCliente(orderDto.getClient().getNoClient());

		dirEnvioClienteOrdenes.setId(dirEnvioClienteOrdenesId);
		dirEnvioClienteOrdenes.setCalle(addressDto.getStreet());
		dirEnvioClienteOrdenes.setNoExterior(addressDto.getOutdoorNumber());
		dirEnvioClienteOrdenes.setNoInterior(addressDto.getInteriorNumber());
		dirEnvioClienteOrdenes.setCodigoPostal(addressDto.getCp().toString());
		dirEnvioClienteOrdenes.setCiudad(addressDto.getCity());
		dirEnvioClienteOrdenes.setColonia(addressDto.getColony());
		dirEnvioClienteOrdenes.setEstado(addressDto.getStateCode());
		dirEnvioClienteOrdenes.setDesEstado(addressDto.getState());
		dirEnvioClienteOrdenes.setDelegacion(addressDto.getDelegationCode());
		dirEnvioClienteOrdenes.setDesDelegacion(addressDto.getDelegation());
		dirEnvioClienteOrdenes.setPlano(addressDto.getFlatCode());
		dirEnvioClienteOrdenes.setDesPlano(addressDto.getFlat());
		dirEnvioClienteOrdenes.setCoordenada(addressDto.getCoordinateCode());
		dirEnvioClienteOrdenes.setDesCoordenada(addressDto.getCoordinate());
		dirEnvioClienteOrdenes.setModifiedBy(idUser);
		dirEnvioClienteOrdenes.setModifiedDate(new Date());
		dirEnvioClienteOrdenes.setModifiedOn("SMARTBUSSINES-POS");

		return dirEnvioClienteOrdenes;
	}

	public EncabezadoSolTraspaso saleOrderDtoToEncabezadoSolTraspaso(SaleOrderDto saleOrderDto, String idUser) {

		EncabezadoSolTraspaso encabezadoSolTraspaso = new EncabezadoSolTraspaso();

		EncabezadoSolTraspasoId encabezadoSolTraspasoId = new EncabezadoSolTraspasoId();

		encabezadoSolTraspasoId.setDoco(saleOrderDto.getOrderNumber());
		encabezadoSolTraspasoId.setDcto(saleOrderDto.getOrderCode());
		encabezadoSolTraspasoId.setKcooo(saleOrderDto.getCompanyNumber());
		encabezadoSolTraspasoId.setMcu(saleOrderDto.getBranchCode());

		encabezadoSolTraspaso.setId(encabezadoSolTraspasoId);
		encabezadoSolTraspaso.setCrcd(saleOrderDto.getCurrency());
		encabezadoSolTraspaso.setCrr(saleOrderDto.getExchangeRate());
		encabezadoSolTraspaso.setTrdj1(saleOrderDto.getCreationDate());
		encabezadoSolTraspaso.setDrqj1(saleOrderDto.getRequestDate());
		encabezadoSolTraspaso.setAn8(new BigDecimal(saleOrderDto.getClient().getNoClient()));
		encabezadoSolTraspaso.setAn82(new BigDecimal(saleOrderDto.getUserNumber()));
		encabezadoSolTraspaso.setTxa1(String.valueOf(saleOrderDto.getClientTax()));
		encabezadoSolTraspaso.setTotalPeso(BigDecimal.ZERO);
		encabezadoSolTraspaso.setSubtot(saleOrderDto.getSubTotal());
		encabezadoSolTraspaso.setIva(saleOrderDto.getIvaTotal());
		encabezadoSolTraspaso.setTotal(saleOrderDto.getOrderTotal());
		encabezadoSolTraspaso.setDocoRel(saleOrderDto.getOrder().getOrderNumber());
		encabezadoSolTraspaso.setDctoRel(saleOrderDto.getOrder().getOrderCode());
		encabezadoSolTraspaso.setEstatus(saleOrderDto.getStatus().getCode());
		encabezadoSolTraspaso.setCampo1(" ");
		encabezadoSolTraspaso.setCampo2(" ");
		encabezadoSolTraspaso.setCampo3(" ");
		encabezadoSolTraspaso.setCampo4(" ");
		encabezadoSolTraspaso.setCampo5(BigDecimal.ZERO);
		encabezadoSolTraspaso.setCampo6(BigDecimal.ZERO);
		encabezadoSolTraspaso.setCampo7(BigDecimal.ZERO);
		encabezadoSolTraspaso.setCampo8(BigDecimal.ZERO);
		encabezadoSolTraspaso.setCampo9(null);
		encabezadoSolTraspaso.setCampo10(null);
		encabezadoSolTraspaso.setCampo11(null);
		encabezadoSolTraspaso.setCampo12(null);
		encabezadoSolTraspaso.setModifiedBy(idUser);
		encabezadoSolTraspaso.setModifiedDate(new Date());
		encabezadoSolTraspaso.setModifiedOn("SMARTBUSSINES-POS");

		return encabezadoSolTraspaso;

	}

	public DetalleSolTras saleOrderDetailDtoToDetalleSolTras(SaleOrderDto saleOrderDto,
			SaleOrderDetailDto saleOrderDetailDto, String idUser) {

		DetalleSolTras detalleSolTras = new DetalleSolTras();

		DetalleSolTrasId detalleSolTrasId = new DetalleSolTrasId();

		detalleSolTrasId.setDoco(saleOrderDto.getOrderNumber());
		detalleSolTrasId.setDcto(saleOrderDto.getOrderCode());
		detalleSolTrasId.setKcooo(saleOrderDto.getCompanyNumber());
		detalleSolTrasId.setMcu(saleOrderDto.getBranchCode());
		detalleSolTrasId.setIdLinea(saleOrderDetailDto.getLineNumber());

		detalleSolTras.setId(detalleSolTrasId);
		detalleSolTras.setTipoLinea(saleOrderDetailDto.getLineType());
		detalleSolTras.setItm(saleOrderDetailDto.getArticleNumber());
		detalleSolTras.setDesc1(saleOrderDetailDto.getArticleDescription());
		detalleSolTras.setUm(saleOrderDetailDto.getUnitMeasurement());
		detalleSolTras.setQty(saleOrderDetailDto.getRequestAmount());
		detalleSolTras.setQtry(BigDecimal.ZERO);
		detalleSolTras.setPeso(saleOrderDetailDto.getWeight());
		detalleSolTras.setPrecio(saleOrderDetailDto.getArticlePrice());
		detalleSolTras.setPrecioTot(saleOrderDetailDto.getSubTotal());
		detalleSolTras.setCrcd(saleOrderDetailDto.getCurrency());
		detalleSolTras.setTipoCambio(saleOrderDetailDto.getExchangeRate());
		detalleSolTras.setDocoOrigen(saleOrderDto.getOrder().getOrderNumber());
		detalleSolTras.setDctoOrigen(saleOrderDto.getOrderCode());
		detalleSolTras.setNxtr(saleOrderDetailDto.getLineCodeOne());
		detalleSolTras.setLttr(saleOrderDetailDto.getLineCodeTwo());
		detalleSolTras.setNxtrPos(saleOrderDetailDto.getLineCodeOne());
		detalleSolTras.setLttrPos(saleOrderDetailDto.getLineCodeTwo());
		detalleSolTras.setFactorPeso(saleOrderDetailDto.getWeightFactor());
		detalleSolTras.setCampo1(" ");
		detalleSolTras.setCampo2(" ");
		detalleSolTras.setCampo3(" ");
		detalleSolTras.setCampo4(" ");
		detalleSolTras.setCampo5(BigDecimal.ZERO);
		detalleSolTras.setCampo6(BigDecimal.ZERO);
		detalleSolTras.setCampo7(BigDecimal.ZERO);
		detalleSolTras.setCampo8(BigDecimal.ZERO);
		detalleSolTras.setCampo9(null);
		detalleSolTras.setCampo10(null);
		detalleSolTras.setCampo11(null);
		detalleSolTras.setCampo12(null);
		detalleSolTras.setModifiedBy(idUser);
		detalleSolTras.setModifiedDate(new Date());
		detalleSolTras.setModifiedOn("SMARTBUSSINES-POS");

		return detalleSolTras;
	}

}
