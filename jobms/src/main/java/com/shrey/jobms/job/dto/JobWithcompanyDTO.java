package com.shrey.jobms.job.dto;

import com.shrey.jobms.job.Job;
import com.shrey.jobms.job.external.Company;

public class JobWithcompanyDTO {
    private Job job;
    private Company company;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
