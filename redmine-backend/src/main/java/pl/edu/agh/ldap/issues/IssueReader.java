package pl.edu.agh.ldap.issues;

import pl.edu.agh.ldap.security.AuthUtils;

import java.util.*;

public class IssueReader {

    private final IssueRepository repository;

    public IssueReader(IssueRepository repository) {
        this.repository = repository;
    }

    public Collection<IssueDao> getAllIssues() {
        Collection<IssueDao> issues = repository.getAll();

        boolean isAdmin = AuthUtils.checkIfAdmin();

        List<IssueDao> selectedIssues = new ArrayList<>();
        for (IssueDao issue : issues) {
            if (IssueTracker.ADMIN == (issue.getTracker())) {
                if (isAdmin) {
                    selectedIssues.add(issue);
                }
            } else {
                selectedIssues.add(issue);
            }
        }

        return selectedIssues;
    }

    public IssueDao getIssueById(String id) {
        Optional<IssueDao> issueOpt = repository.getById(id);
        IssueDao issue = issueOpt.orElseThrow();
        if (IssueTracker.ADMIN == (issue.getTracker())) {
            if (!AuthUtils.checkIfAdmin()) {
                throw new NoSuchElementException();
            }
        }
        return issue;
    }
}
