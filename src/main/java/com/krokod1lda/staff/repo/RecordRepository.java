package com.krokod1lda.staff.repo;

import com.krokod1lda.staff.models.Record;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<Record, Long> {
}
