package pl.edu.agh.ldap.issues;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IssueReader {

    private final IssueRepository repository;

    public IssueReader(IssueRepository repository) {
        this.repository = repository;
    }

    public Collection<IssueDao> getAllIssues() {
        Collection<IssueDao> issues = repository.getAll();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority a : authorities) {
            if ("ROLE_MANAGERS".equals(a.getAuthority())) {
                isAdmin = true;
                break;
            }
        }
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
}
