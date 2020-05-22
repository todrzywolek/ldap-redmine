package pl.edu.agh.ldap.issues;

import java.util.Collection;

public class IssueService {

    private final IssueCreator issueCreator;
    private final IssueReader issueReader;

    public IssueService(IssueCreator issueCreator,
                        IssueReader issueReader) {
        this.issueCreator = issueCreator;
        this.issueReader = issueReader;
    }

    public Collection<IssueDao> getAllIssues() {
        return issueReader.getAllIssues();

    }

    public IssueDao createIssue(Issue issue) {
        return issueCreator.createIssue(issue);
    }

    public IssueDao updateIssue(String id, Issue newIssue) {
        IssueDao issue = issueReader.getIssueById(id);
        return issueCreator.updateIssue(issue, newIssue);
    }
}
