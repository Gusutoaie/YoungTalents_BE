package com.example.YoungTalens.repository;

import com.example.YoungTalens.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
