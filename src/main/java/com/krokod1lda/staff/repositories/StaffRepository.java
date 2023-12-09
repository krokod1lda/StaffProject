package com.krokod1lda.staff.repositories;

import com.krokod1lda.staff.models.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Long> {
}