package pl.edu.agh.ldap.issues;

public class IssueService {

    private IssueCreator issueCreator;

    public IssueService(IssueCreator issueCreator) {
        this.issueCreator = issueCreator;
    }

    public IssueDao createIssue(Issue issue) {
        return issueCreator.createIssue(issue);
    }
}
