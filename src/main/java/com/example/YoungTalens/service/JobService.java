package com.example.YoungTalens.service;

import com.example.YoungTalens.dto.JobDTO;
import com.example.YoungTalens.entity.Job;
import com.example.YoungTalens.mapper.JobMapper;
import com.example.YoungTalens.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMapper jobMapper;

    public JobDTO saveJob(JobDTO jobDTO) {
        Job job = jobMapper.toEntity(jobDTO);
        job = jobRepository.save(job);
        return jobMapper.toDto(job);
    }

    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

    public JobDTO getJobById(Long id) {
        return jobRepository.findById(id)
                .map(jobMapper::toDto)
                .orElse(null);
    }

    public List<JobDTO> searchJobs(String query) {
        return jobRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList());
    }

}
