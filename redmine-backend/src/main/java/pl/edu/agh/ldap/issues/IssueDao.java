package pl.edu.agh.ldap.issues;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IssueDao {
    private UUID id;
    private IssueTracker tracker = IssueTracker.BUG;
    private String subject = "";
    private String description = "";
    private IssueStatus status = IssueStatus.NEW;
    private IssuePriority priority = IssuePriority.NORMAL;
    private String assignee = "Unknown";
    private IssueCategory category = IssueCategory.DEVELOPMENT;
    private LocalDate startDate;
    private LocalDate dueDate;
    private int estimatedTime = 0;
    private List<String> comments  = new ArrayList<>();

    public IssueTracker getTracker() {
        return tracker;
    }

    public void setTracker(IssueTracker tracker) {
        this.tracker = tracker;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public IssueCategory getCategory() {
        return category;
    }

    public void setCategory(IssueCategory category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
