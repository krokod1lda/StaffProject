package com.krokod1lda.staff.repositories;

import com.krokod1lda.staff.models.Record;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {
    List<Record> findByStaffId(long staff_id);

    List<Record> findByDate(Date date);

    List<Record> findByStaffIdAndDate(long staffId, Date date);
}
