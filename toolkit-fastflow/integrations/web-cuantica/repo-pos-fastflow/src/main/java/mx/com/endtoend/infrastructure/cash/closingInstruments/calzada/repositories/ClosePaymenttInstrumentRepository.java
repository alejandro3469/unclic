package mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.repositories;

import java.util.List;
import java.util.Optional;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories.BaseClosePaymentInstrumentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;

@Repository
public interface ClosePaymenttInstrumentRepository extends BaseClosePaymentInstrumentRepository {

}
