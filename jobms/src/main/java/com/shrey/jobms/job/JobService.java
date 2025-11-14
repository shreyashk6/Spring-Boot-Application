package com.shrey.jobms.job;

import com.shrey.jobms.job.dto.JobWithcompanyDTO;

import java.util.List;

public interface JobService {//setted as interface for loose coupling
    List<JobWithcompanyDTO> findAll();//retuns all the jobs
    void createJob(Job job);

    Job getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJob(Long id, Job updatedJob);
}
