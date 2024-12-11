package vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Tìm kiếm công việc theo tên công việc
    List<Job> findByJobNameContainingIgnoreCase(String jobName);

    // Tìm công việc theo công ty
    List<Job> findByCompanyName(String companyName);

    // Tìm công việc theo mô tả
    List<Job> findByJobDescContainingIgnoreCase(String jobDesc);
}