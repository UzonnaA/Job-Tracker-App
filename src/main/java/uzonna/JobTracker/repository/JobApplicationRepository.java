package uzonna.JobTracker.repository;

import uzonna.JobTracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    @Query("SELECT j FROM JobApplication j WHERE " +
       "(:company IS NULL OR j.company = :company) AND " +
       "(:status IS NULL OR j.status = :status) AND " +
       "(:tag IS NULL OR :tag MEMBER OF j.tags)")
    List<JobApplication> findByFilters(@Param("company") String company,
                                   @Param("status") String status,
                                   @Param("tag") String tag);
}

