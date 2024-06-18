package com.example.YoungTalens.controller;

import com.example.YoungTalens.dto.JobDTO;
import com.example.YoungTalens.service.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public JobDTO createJob(@RequestPart("job") String jobData, @RequestPart("logo") MultipartFile logoFile) throws IOException {
        JobDTO jobDTO = objectMapper.readValue(jobData, JobDTO.class);

        LocalDateTime now = LocalDateTime.now();

        if (logoFile != null && !logoFile.isEmpty()) {
            String logoPath = saveFile(logoFile);
            jobDTO = new JobDTO(
                    jobDTO.id(),
                    jobDTO.title(),
                    jobDTO.company(),
                    jobDTO.location(),
                    jobDTO.description(),
                    jobDTO.responsibilities(),
                    jobDTO.mandatorySkills(),
                    jobDTO.niceToHaveSkills(),
                    logoPath,
                    now, // Set vechimeAnunt to current date and time
                    jobDTO.tipOferta(),
                    jobDTO.experienta(),
                    jobDTO.remote()
            );
        } else {
            jobDTO = new JobDTO(
                    jobDTO.id(),
                    jobDTO.title(),
                    jobDTO.company(),
                    jobDTO.location(),
                    jobDTO.description(),
                    jobDTO.responsibilities(),
                    jobDTO.mandatorySkills(),
                    jobDTO.niceToHaveSkills(),
                    jobDTO.logoUrl(),
                    now, // Set vechimeAnunt to current date and time
                    jobDTO.tipOferta(),
                    jobDTO.experienta(),
                    jobDTO.remote()
            );
        }
        return jobService.saveJob(jobDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestParam String query) {
        List<JobDTO> jobs = jobService.searchJobs(query);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping
    public List<JobDTO> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        JobDTO jobDTO = jobService.getJobById(id);
        if (jobDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobDTO);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadsDir = "uploads/";
        String absoluteUploadsDir = System.getProperty("user.dir") + "/" + uploadsDir;
        File uploadsDirFile = new File(absoluteUploadsDir);

        // Create directory if it doesn't exist
        if (!uploadsDirFile.exists()) {
            uploadsDirFile.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = uploadsDir + fileName;  // Relative path
        File dest = new File(absoluteUploadsDir + fileName);
        file.transferTo(dest);
        return filePath;
    }


}
