package pl.edu.agh.ldap.issues;

import java.util.Collection;

public class IssueService {

    private final IssueModifier issueModifier;
    private final IssueReader issueReader;

    public IssueService(IssueModifier issueModifier,
                        IssueReader issueReader) {
        this.issueModifier = issueModifier;
        this.issueReader = issueReader;
    }

    public Collection<IssueDao> getAllIssues() {
        return issueReader.getAllIssues();

    }

    public IssueDao createIssue(Issue issue) {
        return issueModifier.createIssue(issue);
    }

    public IssueDao updateIssue(String id, Issue newIssue) {
        IssueDao issue = issueReader.getIssueById(id);
        return issueModifier.updateIssue(issue, newIssue);
    }

    public void deleteIssue(String id) {
        issueModifier.deleteIssue(id);
    }
}
