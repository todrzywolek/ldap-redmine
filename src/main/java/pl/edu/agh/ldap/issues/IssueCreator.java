package pl.edu.agh.ldap.issues;

import pl.edu.agh.ldap.utils.DateTimeUtils;

import java.time.LocalDate;

public class IssueCreator {
    private final IssueValidator issueValidator;
    private final IssueRepository issueRepository;

    public IssueCreator(IssueValidator issueValidator,
                        IssueRepository issueRepository) {
        this.issueValidator = issueValidator;
        this.issueRepository = issueRepository;
    }

    public IssueDao createIssue(Issue issue) {
        issueValidator.validateIssue(issue);
        IssueDao issueDao = convertToDao(issue);
        return issueRepository.save(issueDao);
    }

    private IssueDao convertToDao(Issue issue) {
        IssueDao issueDao = new IssueDao();
        issueDao.setTracker(IssueTracker.valueOf(issue.getTracker().toUpperCase()));
        issueDao.setSubject(issue.getSubject());
        if (issue.getDescription() != null && !issue.getDescription().isEmpty()) {
            issueDao.setDescription(issue.getDescription());
        }
        issueDao.setStatus(issue.getStatus() != null && !issue.getStatus().isEmpty() ? IssueStatus.valueOf(issue.getStatus().toUpperCase()) : IssueStatus.NEW);
        issueDao.setPriority(issue.getPriority() != null && !issue.getPriority().isEmpty() ? IssuePriority.valueOf(issue.getPriority().toUpperCase()) : IssuePriority.NORMAL);
        if (issue.getAssignee() != null && !issue.getAssignee().isEmpty()) {
            issueDao.setAssignee(issue.getAssignee());
        } else {
            issueDao.setAssignee("Unknown");
        }
        issueDao.setCategory(issue.getAssignee() != null && !issue.getAssignee().isEmpty() ? IssueCategory.valueOf(issue.getCategory().toUpperCase()) : IssueCategory.DEVELOPMENT);
        String startDate = issue.getStartDate();
        if (startDate != null && !startDate.isEmpty()) {
            issueDao.setStartDate(DateTimeUtils.convertDate(issue.getStartDate()));
        } else {
            issueDao.setStartDate(LocalDate.now());
        }
        String dueDate = issue.getDueDate();
        if (dueDate != null && !dueDate.isEmpty()) {
            issueDao.setDueDate(DateTimeUtils.convertDate(issue.getDueDate()));
        }
        return issueDao;
    }
}
