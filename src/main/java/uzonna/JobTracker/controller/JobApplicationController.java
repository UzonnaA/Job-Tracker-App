package uzonna.JobTracker.controller;

import uzonna.JobTracker.model.JobApplication;
import uzonna.JobTracker.model.User;
import uzonna.JobTracker.repository.JobApplicationRepository;
import uzonna.JobTracker.security.UserDetailsImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationRepository repository;

    public JobApplicationController(JobApplicationRepository repository) {
        this.repository = repository;
    }

    
    @PostMapping
    public ResponseEntity<JobApplication> createApplication(
        @RequestBody JobApplication application,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        application.setUser(user);
        return ResponseEntity.ok(repository.save(application));
    }

    
    @GetMapping
    public List<JobApplication> getApplications(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String tag,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
                
                User user = userDetails.getUser();
                if (company == null && status == null && tag == null) {
                    return repository.findByUser(user);
                } else {
                    return repository.findByUserAndFilters(user, company, status, tag);
                }
            }

    
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getApplicationById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        Optional<JobApplication> app = repository.findById(id);
        return app.isPresent() && app.get().getUser().getId().equals(user.getId())
                ? ResponseEntity.ok(app.get())
                : ResponseEntity.status(403).build();
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplication updatedApp,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Optional<JobApplication> existingOpt = repository.findById(id);

        if (existingOpt.isEmpty() || !existingOpt.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build();
        }

        JobApplication existingApp = existingOpt.get();
        existingApp.setJobTitle(updatedApp.getJobTitle());
        existingApp.setCompany(updatedApp.getCompany());
        existingApp.setStatus(updatedApp.getStatus());
        existingApp.setApplicationDate(updatedApp.getApplicationDate());
        existingApp.setTags(updatedApp.getTags());

        return ResponseEntity.ok(repository.save(existingApp));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
                
                User user = userDetails.getUser();
                Optional<JobApplication> app = repository.findById(id);
                if (app.isEmpty() || !app.get().getUser().getId().equals(user.getId())) {
                    return ResponseEntity.status(403).build();
                }
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }

}
