package pl.edu.agh.ldap.issues;

import java.util.*;

public class IssueRepository {
    private final Map<UUID, IssueDao> issues = new HashMap<>();


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
