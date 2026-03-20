package mx.com.endtoend.infrastructure.accountingRecord.common.repository;

import mx.com.endtoend.infrastructure.accountingRecord.common.entities.AccountingRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BaseAccountingRecordRepository extends JpaRepository<AccountingRecordEntity, Long> {

    @Query("FROM AccountingRecordEntity ar WHERE ar.openingId=:opening")
    List<AccountingRecordEntity> findAllByOpeningId(@Param("opening") Long openingId);

}
