package pl.edu.agh.ldap.issues;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import pl.edu.agh.ldap.security.RedminePrincipal;
import pl.edu.agh.ldap.utils.DateTimeUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class IssueValidator {

    public void validateIssue(Issue issue) {
        validateTracker(issue.getTracker());
        validateField(issue.getSubject(), "subject");
        validateStatus(issue.getStatus());
        validateCategory(issue.getCategory());
        validateDates(issue);
        validateEstimatedTime(issue.getEstimatedTime());
    }

    private void validateTracker(String tracker) {
        if (IssueTracker.ADMIN.name().equals(tracker.toUpperCase())) {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for (GrantedAuthority a : authorities) {
                if ("ROLE_MANAGERS".equals(a.getAuthority())) {
                    return;
                }
            }
            throw HttpClientErrorException.create(HttpStatus.FORBIDDEN, "Unauthorized", new HttpHeaders(), "{\"message\": \"User is not a manager\"}".getBytes(), StandardCharsets.UTF_8);

        }
        if (!tracker.isEmpty()) {
            String upperCaseTracker = tracker.toUpperCase();
            for (IssueTracker t : IssueTracker.values()) {
                if (upperCaseTracker.equals(t.name())) {
                    return;
                }
            }
        }

        throw new IllegalArgumentException("Tracker not allowed.");

    }

    private void validateField(String fieldValue, String fieldName) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            throw new IllegalArgumentException("Field " + fieldName + " cannot be null or empty");
        }
    }

    private void validateStatus(String status) {
        if (status != null && !status.isEmpty()) {
            String upperCaseStatus = status.toUpperCase();
            for (IssueStatus s : IssueStatus.values()) {
                if (upperCaseStatus.equals(s.name())) {
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Status not allowed.");
    }

    private void validateCategory(String category) {
        if (category != null && !category.isEmpty()) {
            String upperCaseCategory = category.toUpperCase();
            for (IssueCategory s : IssueCategory.values()) {
                if (upperCaseCategory.equals(s.name())) {
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Category not allowed.");
    }

    private void validateDates(Issue issue) {
        String startDate = issue.getStartDate();
        if (startDate != null && !startDate.isEmpty()) {
            try {
                DateTimeUtils.convertDate(startDate);
            } catch (Exception e) {
                throw new IllegalArgumentException("Start date has invalid format. " + e.getMessage());
            }
        }

        String dueDate = issue.getDueDate();
        if (dueDate != null && !dueDate.isEmpty()) {
            try {
                DateTimeUtils.convertDate(dueDate);
            } catch (Exception e) {
                throw new IllegalArgumentException("Due date has invalid format. " + e.getMessage());
            }
        }
    }

    private void validateEstimatedTime(String estimatedTime) {
        if (estimatedTime != null && !estimatedTime.isEmpty()) {
            String message = "Estimated time should be number greater than zero";
            try {
                int estimatedTimeValue = Integer.parseInt(estimatedTime);
                if (estimatedTimeValue < 0) {
                    throw new IllegalArgumentException(message);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
