package uzonna.JobTracker.repository;

import uzonna.JobTracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // nothing needed here
}
