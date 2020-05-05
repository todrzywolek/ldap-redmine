package pl.edu.agh.ldap.issues;

import java.util.Collection;

public class IssueReader {

    private final IssueRepository repository;

    public IssueReader(IssueRepository repository) {
        this.repository = repository;
    }

    public Collection<IssueDao> getAllIssues() {
        return repository.getAll();

    }
}
