package com.krokod1lda.staff.repositories;

import com.krokod1lda.staff.models.Additional;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface AdditionalRepository extends CrudRepository<Additional, Long> {
    List<Additional> findByDate(Date date);

    List<Additional> findByStaffId(long staffId);

    List<Additional> findByStaffIdAndDate(long staffId, Date date);
}
