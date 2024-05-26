package com.example.YoungTalens.mapper;

import com.example.YoungTalens.dto.JobDTO;
import com.example.YoungTalens.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobDTO toDto(Job job) {
        return new JobDTO(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getDescription(),
                job.getResponsibilities(),
                job.getMandatorySkills(),
                job.getNiceToHaveSkills(),
                job.getLogoUrl(),
                job.getVechimeAnunt(),
                job.getTipOferta(),
                job.getExperienta(),
                job.getRemote()
        );
    }

    public Job toEntity(JobDTO dto) {
        Job job = new Job();
        job.setId(dto.id());
        job.setTitle(dto.title());
        job.setCompany(dto.company());
        job.setLocation(dto.location());
        job.setDescription(dto.description());
        job.setResponsibilities(dto.responsibilities());
        job.setMandatorySkills(dto.mandatorySkills());
        job.setNiceToHaveSkills(dto.niceToHaveSkills());
        job.setLogoUrl(dto.logoUrl());
        job.setVechimeAnunt(dto.vechimeAnunt());
        job.setTipOferta(dto.tipOferta());
        job.setExperienta(dto.experienta());
        job.setRemote(dto.remote());
        return job;
    }
}
