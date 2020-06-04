package pl.edu.agh.ldap.issues;

import java.time.LocalDate;
import java.util.*;

public class IssueRepository {
    private final Map<UUID, IssueDao> issues = new HashMap<>();

    public IssueRepository() {
        IssueDao issue1 = new IssueDao();
        issue1.setId(UUID.randomUUID());
        issue1.setTracker(IssueTracker.BUG);
        issue1.setSubject("Subject 1");
        issue1.setDescription("Description 1");
        issue1.setStatus(IssueStatus.NEW);
        issue1.setPriority(IssuePriority.NORMAL);
        issue1.setAssignee("Assignee 1");
        issue1.setCategory(IssueCategory.DEVELOPMENT);
        issue1.setStartDate(LocalDate.now().minusDays(1));
        issue1.setDueDate(LocalDate.now().plusDays(1));
        issue1.setEstimatedTime(100);


        IssueDao issue2 = new IssueDao();
        issue2.setId(UUID.randomUUID());
        issue2.setTracker(IssueTracker.FEATURE);
        issue2.setSubject("Subject 2");
        issue2.setDescription("Description 2");
        issue2.setStatus(IssueStatus.ASSIGNED);
        issue2.setPriority(IssuePriority.CRITICAL);
        issue2.setAssignee("Assignee 2");
        issue2.setCategory(IssueCategory.DOCUMENTATION);
        issue2.setStartDate(LocalDate.now().minusDays(1));
        issue2.setDueDate(LocalDate.now().plusDays(1));
        issue2.setEstimatedTime(200);

        issues.put(issue1.getId(), issue1);
        issues.put(issue2.getId(), issue2);
    }

    public IssueDao save(IssueDao issueDao) {
        if (issueDao.getId() == null) {
            issueDao.setId(UUID.randomUUID());
        }
        issues.put(issueDao.getId(), issueDao);
        return issueDao;
    }

    public Collection<IssueDao> getAll() {
        return issues.values();
    }

    public Optional<IssueDao> getById(String id) {
        IssueDao issueDao = issues.get(UUID.fromString(id));
        return Optional.ofNullable(issueDao);
    }

    public void deleteById(String id) {
        issues.remove(UUID.fromString(id));
    }
}
