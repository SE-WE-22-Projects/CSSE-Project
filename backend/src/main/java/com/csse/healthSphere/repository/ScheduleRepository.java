package com.csse.healthSphere.repository;

import com.csse.healthSphere.model.Report;
import com.csse.healthSphere.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
