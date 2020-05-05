package pl.edu.agh.ldap.issues;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IssueRepository {
    private final Map<UUID, IssueDao> issues = new HashMap<>();


    public IssueDao save(IssueDao issueDao) {
        issueDao.setId(UUID.randomUUID());
        issues.put(issueDao.getId(), issueDao);
        return issueDao;
    }
}
