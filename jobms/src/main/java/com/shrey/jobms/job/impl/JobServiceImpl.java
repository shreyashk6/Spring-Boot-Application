package com.shrey.jobms.job.impl;

import com.shrey.jobms.job.Job;
import com.shrey.jobms.job.JobRepository;
import com.shrey.jobms.job.JobService;
import com.shrey.jobms.job.dto.JobWithcompanyDTO;
import com.shrey.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service  //declared this class as service
public class JobServiceImpl implements JobService {
//    private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    private Long nextId=1L;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithcompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithcompanyDTO> jobWithcompanyDTOs = new ArrayList<>();

        return jobs.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private JobWithcompanyDTO convertToDTO(Job job){
            JobWithcompanyDTO jobWithcompanyDTO = new JobWithcompanyDTO();
            jobWithcompanyDTO.setJob(job);
            RestTemplate restTemplate = new RestTemplate();
            Company company = restTemplate.getForObject("http://localhost:8081/companies/"+ job.getCompanyId(),
                    Company.class);
            jobWithcompanyDTO.setCompany(company);
            return jobWithcompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        //job.setId(nextId++);
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
//        for (Job job: jobs){
//            if (job.getId().equals(id)){
//                return job;
//            }
//        }
//        return null;
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
//        Iterator<Job> iterator = jobs.iterator();
//        while (iterator.hasNext()){
//            Job job = iterator.next();
//            if(job.getId().equals(id)){
//                iterator.remove();
//                return true;
//            }
//        }
//        return false;
//        try {
//            jobRepository.deleteById(id);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
        //CUSTOM ADDED
        try {
            if (jobRepository.existsById(id)) {
                jobRepository.deleteById(id);
                return true;
            } else {
                return false; // job not found
            }
        } catch (Exception e) {
            e.printStackTrace(); // optional: log the error instead
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
//        for(Job job : jobs){
//            if(job.getId().equals(id)){
              if(jobOptional.isPresent()) {
                  Job job = jobOptional.get();
                  job.setTitle(updatedJob.getTitle());
                  job.setDescription(updatedJob.getDescription());
                  job.setMinSalary(updatedJob.getMinSalary());
                  job.setLocation(updatedJob.getLocation());
                  job.setMaxSalary(updatedJob.getMaxSalary());
                  jobRepository.save(job);
                  return true;
              }
        return false;
    }


}

