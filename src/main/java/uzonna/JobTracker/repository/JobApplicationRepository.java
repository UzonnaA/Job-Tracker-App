package uzonna.JobTracker.repository;

import uzonna.JobTracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Repository interface for performing CRUD and custom filter operations
 * on JobApplication entities.
 * 
 * Extends JpaRepository to inherit standard database operations.
 */
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    /**
     * Custom query to filter job applications by optional company, status, and tag.
     * 
     * Uses dynamic filtering:
     * - If a filter (company, status, or tag) is null, that condition is skipped.
     * - If a tag is provided, it checks whether the tag exists in the tags list.
     *
     * @param company the company name to filter by (nullable)
     * @param status the application status to filter by (nullable)
     * @param tag a tag to search for in the application's tag list (nullable)
     * @return a list of matching JobApplication entities
     */
    @Query("SELECT j FROM JobApplication j WHERE " +
           "(:company IS NULL OR j.company = :company) AND " +
           "(:status IS NULL OR j.status = :status) AND " +
           "(:tag IS NULL OR :tag MEMBER OF j.tags)")
    List<JobApplication> findByFilters(@Param("company") String company,
                                       @Param("status") String status,
                                       @Param("tag") String tag);
}


