package uzonna.JobTracker.controller;

import uzonna.JobTracker.model.JobApplication;
import uzonna.JobTracker.repository.JobApplicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Tells Spring this class handles API requests
@RequestMapping("/api/applications") // All routes will start with this
public class JobApplicationController {

    private final JobApplicationRepository repository;

    // Constructor injection (Spring will provide the repository automatically)
    public JobApplicationController(JobApplicationRepository repository) {
        this.repository = repository;
    }

    // CREATE: Add a new job application
    @PostMapping
    public JobApplication createApplication(@RequestBody JobApplication application) {
        return repository.save(application);
    }

    // READ: Get all job applications
    @GetMapping
    public List<JobApplication> getApplications(
        @RequestParam(required = false) String company,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String tag
    ) {
        if (company == null && status == null && tag == null) {
            return repository.findAll();
        } else {
            return repository.findByFilters(company, status, tag);
        }
    }

    // READ: Get one job application by ID
    @GetMapping("/{id}")
    public Optional<JobApplication> getApplicationById(@PathVariable Long id) {
        return repository.findById(id);
    }

    // UPDATE: Edit an existing application
    @PutMapping("/{id}")
    public JobApplication updateApplication(@PathVariable Long id, @RequestBody JobApplication updatedApp) {
        return repository.findById(id)
        // lambda function, take the "existingApp" or whatever name and map each of the parts
                .map(existingApp -> {
                    existingApp.setJobTitle(updatedApp.getJobTitle());
                    existingApp.setCompany(updatedApp.getCompany());
                    existingApp.setStatus(updatedApp.getStatus());
                    existingApp.setApplicationDate(updatedApp.getApplicationDate());
                    existingApp.setTags(updatedApp.getTags());
                    return repository.save(existingApp);
                })
                .orElseGet(() -> {
                    updatedApp.setId(id);
                    return repository.save(updatedApp);
                });
    }

    // DELETE: Remove a job application
    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        repository.deleteById(id);
    }

    // @GetMapping
    // public List<JobApplication> getApplications(
    //     @RequestParam(required = false) String company,
    //     @RequestParam(required = false) String status,
    //     @RequestParam(required = false) String tag
    // ) {
    //     if (company != null || status != null || tag != null) {
    //         return repository.findByFilters(company, status, tag);
    //     }
    //     return repository.findAll();
    // }


    
}
