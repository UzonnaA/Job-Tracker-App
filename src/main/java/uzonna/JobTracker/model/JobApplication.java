package uzonna.JobTracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity // How we tell Spring this class should map to a DB table
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // forces auto-increment
    private Long id;

    private String jobTitle;
    private String company;
    private String status; // I'll do APPLIED, INTERVIEW, OFFER, REJECTED

    private LocalDate applicationDate;

    @ElementCollection
    private List<String> tags;

    // Constructors (required for JPA)
    public JobApplication() {}

    public JobApplication(String jobTitle, String company, String status, LocalDate applicationDate, List<String> tags) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.status = status;
        this.applicationDate = applicationDate;
        this.tags = tags;
    }

    // Getters and setters (required for Spring to access fields)
    public Long getId() { return id; }
    public void setId(Long id) {this.id = id;} // idk if I should do this one, meh
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

    public List<String> getTags() {return tags;}
    public void setTag(String tag) {this.tags.add(tag);}
    public void setTags(List<String> tags){this.tags = tags;}
}