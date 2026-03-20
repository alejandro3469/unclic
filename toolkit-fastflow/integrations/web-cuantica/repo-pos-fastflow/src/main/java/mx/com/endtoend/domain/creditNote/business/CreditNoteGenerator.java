package mx.com.endtoend.domain.creditNote.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDetailDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSummary;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDetailDto;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.genericCommonsFileds.utilities.WrittenCurrency;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.TaxDto;

/**
 * Clase para la construcción de los datos operativos de notas de crédito
 * 
 * @author ddcasas
 *
 */
public class CreditNoteGenerator {

	private WrittenCurrency writtenCurrency = new WrittenCurrency();

	public CreditNoteTickteDto generateTicketModel(UserDto userDto, CreditNoteHeaderDto creditNoteHeaderDto,
			CreditNoteDto creditNote, BranchDto branchDto) {
		String letterAmount = writtenCurrency.enterNumber(creditNoteHeaderDto.getTotalAmount().toString());
		List<CreditNoteTickteDetailDto> creditNoteTickteDetailList = new ArrayList<>();
		for (CreditNoteDetailDto creditNoteDetailDto : creditNoteHeaderDto.getCreditNoteDetail()) {
			creditNoteTickteDetailList.add(new CreditNoteTickteDetailDto(creditNoteDetailDto));
		}
		List<TaxDto> taxes = generateTaxList(creditNoteHeaderDto);
		Double ivaTotal = obtainIvaTotal(creditNoteHeaderDto);
		CreditNoteTickteDto creditNoteTickte = new CreditNoteTickteDto(branchDto, userDto, creditNoteHeaderDto,
				creditNote, taxes, ivaTotal, letterAmount);
		creditNoteTickte.setDetail(creditNoteTickteDetailList);
		return creditNoteTickte;
	}

	private Double obtainIvaTotal(CreditNoteHeaderDto creditNoteHeaderDto) {
		Double ivaTotal = 0.0;
		for (CreditNoteDetailDto creditNoteDetailDto : creditNoteHeaderDto.getCreditNoteDetail()) {
			double subTotalByLine = 0.0;
			subTotalByLine = creditNoteDetailDto.getRequestAmount().multiply(creditNoteDetailDto.getFinalUnitPrice()).doubleValue();
			double tax1 = creditNoteDetailDto.getTaxOne().compareTo(BigDecimal.valueOf(-1)) != 0 ? (creditNoteDetailDto.getTaxOne().doubleValue() / 100) : 0;
			double tax2 = creditNoteDetailDto.getTaxTwo().compareTo(BigDecimal.valueOf(-1)) != 0 ? (creditNoteDetailDto.getTaxTwo().doubleValue() / 100) : 0;
			Double taxByLine = tax1 + tax2;
			ivaTotal = ivaTotal + subTotalByLine * taxByLine;
		}
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		return ivaTotal;
	}

	private List<TaxDto> generateTaxList(CreditNoteHeaderDto creditNoteHeaderDto) {
		List<TaxDto> taxes = new ArrayList<TaxDto>();
		TaxDto tax0 = new TaxDto("0", BigDecimal.ZERO);
		BigDecimal temTax0 = BigDecimal.ZERO;
		TaxDto tax16 = new TaxDto("16", BigDecimal.ZERO);
		BigDecimal temTax16 = BigDecimal.ZERO;
		TaxDto taxIEPS = new TaxDto("IEPS", BigDecimal.ZERO);
		BigDecimal temTaxIEPS = BigDecimal.ZERO;
		for (CreditNoteDetailDto creditNoteDetailDto : creditNoteHeaderDto.getCreditNoteDetail()) {
			BigDecimal subTotalByLine = BigDecimal.ZERO;
			subTotalByLine = creditNoteDetailDto.getRequestAmount().multiply(creditNoteDetailDto.getFinalUnitPrice());
			subTotalByLine = subTotalByLine.setScale(2, RoundingMode.HALF_UP);


			// ASIGNACIÓN DE IMPUESTOS AL 0% POR CONFIGURACION
			if (creditNoteDetailDto.getTaxOne().compareTo(BigDecimal.ZERO) == 0) {
				temTax0 = tax0.getValue();
				temTax0 = temTax0.add(subTotalByLine);
				tax0.setValue(temTax0);
			}
			// ASIGNACION DE IMPUESTOS GRABABLES POR IEPS
			if (creditNoteDetailDto.getTaxTwo().compareTo(BigDecimal.valueOf(-1)) != 0) {
				// Obtener el valor actual y sumar el nuevo impuesto
				BigDecimal currentTaxIEPS = taxIEPS.getValue();
				BigDecimal taxAmountIEPS = subTotalByLine.multiply(creditNoteDetailDto.getTaxTwo())
						.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

				BigDecimal newTaxIEPS = currentTaxIEPS.add(taxAmountIEPS)
						.setScale(2, RoundingMode.HALF_UP);

				taxIEPS.setValue(newTaxIEPS);
			}
			// ASIGNACION DE IMPUESTOS GRABABLES AL 16% (DEFECTO)
			if ((creditNoteDetailDto.getTaxOne().compareTo(BigDecimal.valueOf(-1)) != 0 &&
					creditNoteDetailDto.getTaxOne().compareTo(BigDecimal.ZERO) != 0)) {

				// Obtener el valor actual y sumar el subtotal
				BigDecimal currentTax16 = tax16.getValue();
				BigDecimal newTax16 = currentTax16.add(subTotalByLine)
						.setScale(2, RoundingMode.HALF_UP);

				tax16.setValue(newTax16);
			}
		}
		taxes.add(tax0);
		taxes.add(tax16);
		taxes.add(taxIEPS);
		return taxes;
	}

	public CreditNoteSummary generateCreditNoteSummary(CreditNoteHeaderDto creditNoteHeaderDto) {

		CreditNoteSummary creditNoteSummary = new CreditNoteSummary(creditNoteHeaderDto.getFolio(),
				creditNoteHeaderDto.getCreditNoteCode(), creditNoteHeaderDto.getTotalAmount(),
				creditNoteHeaderDto.getUsedAmountM(), creditNoteHeaderDto.getPendingAmount(),
				creditNoteHeaderDto.getCreationDate(), creditNoteHeaderDto.getClientNumber(),
				creditNoteHeaderDto.getCurrency(), creditNoteHeaderDto.getExchangeRate());

		return creditNoteSummary;
	}

	public CreditNoteDto generateCreditNoteByOrderAndCreditNote(
        Long employeeId, OrderDto orderDto, CreditNoteDto creditNoteDto, BigDecimal folio, String creditNoteCode) {

   	    List<CreditNoteDetailDto> creditNoteDetailList = orderDto.getOrderDetail()
            .stream()
            .map(CreditNoteDetailDto::new)
            .collect(Collectors.toList());

        double totalAmount = creditNoteDetailList.stream()
                .mapToDouble(creditNoteDetailDto -> {
                    double tax1 = creditNoteDetailDto.getTaxOne().compareTo(BigDecimal.valueOf(-1)) != 0 ? creditNoteDetailDto.getTaxOne().doubleValue() / 100 : 0;
                    double tax2 = creditNoteDetailDto.getTaxTwo().compareTo(BigDecimal.valueOf(-1)) != 0 ? creditNoteDetailDto.getTaxTwo().doubleValue() / 100 : 0;
                    double subtotal = creditNoteDetailDto.getRequestAmount().multiply(creditNoteDetailDto.getFinalUnitPrice()).doubleValue();
                    return subtotal * (1 + tax1 + tax2);
                })
                .sum();

        totalAmount = Math.round(totalAmount * 100.0) / 100.0;

        CreditNoteHeaderDto creditNoteHeaderDto = new CreditNoteHeaderDto(
                folio,
                creditNoteCode,
                BigDecimal.valueOf(totalAmount),
                orderDto.getClient().getId(),
                orderDto.getClient().getNoClient(),
                orderDto.getIdUser(),
                creditNoteDetailList,
                orderDto.getCurrency(),
                orderDto.getExchangeRate()
        );

        List<CreditNoteHeaderDto> creditNoteHeaderDtoList = Collections.singletonList(creditNoteHeaderDto);

        if (creditNoteDto == null) {
            creditNoteDto = new CreditNoteDto(
                    employeeId,
                    orderDto.getOrderNumber(),
                    orderDto.getOrderCode(),
                    orderDto.getOrderId(),
                    orderDto.getOrderTotal(),
                    BigDecimal.valueOf(totalAmount),
                    creditNoteHeaderDtoList
            );
        } else {
            BigDecimal updatedCreditNoteTotal = creditNoteDto.getCreditNoteTotal().add(BigDecimal.valueOf(totalAmount));
            boolean isTotal = (creditNoteDto.getOrderTotal().subtract(updatedCreditNoteTotal).compareTo(BigDecimal.valueOf(0.01)) <= 0);

            creditNoteDto.getCreditNoteHeaderList().add(creditNoteHeaderDto);
            creditNoteDto.setCreditNoteTotal(updatedCreditNoteTotal);
            creditNoteDto.setIsTotal(isTotal);
        }

		creditNoteDto.setHeaderCreationDate(creditNoteHeaderDto.getCreationDate());

        return creditNoteDto;
    }

}