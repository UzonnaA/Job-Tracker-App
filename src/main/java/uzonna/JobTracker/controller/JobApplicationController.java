package uzonna.JobTracker.controller;

import uzonna.JobTracker.model.JobApplication;
import uzonna.JobTracker.repository.JobApplicationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationRepository repository;

    public JobApplicationController(JobApplicationRepository repository) {
        this.repository = repository;
    }

    // Create a new job application
    @PostMapping
    public JobApplication createApplication(@RequestBody JobApplication application) {
        return repository.save(application);
    }

    // Get all applications, or filter by company, status, or tag if provided
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

    // Get a specific application by ID
    @GetMapping("/{id}")
    public Optional<JobApplication> getApplicationById(@PathVariable Long id) {
        return repository.findById(id);
    }

    // Update an existing application, or create it if it doesn’t exist
    @PutMapping("/{id}")
    public JobApplication updateApplication(@PathVariable Long id, @RequestBody JobApplication updatedApp) {
        return repository.findById(id)
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

    // Delete an application by ID
    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
