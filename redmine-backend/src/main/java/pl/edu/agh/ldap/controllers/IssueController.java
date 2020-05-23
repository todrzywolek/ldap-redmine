package pl.edu.agh.ldap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ldap.issues.Issue;
import pl.edu.agh.ldap.issues.IssueDao;
import pl.edu.agh.ldap.issues.IssueService;

import java.util.Collection;

@RestController
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public ResponseEntity<?> getAllIssues() {
        Collection<IssueDao> issues = issueService.getAllIssues();
        return ResponseEntity.ok(issues);
    }

    @PostMapping("/issues")
    public ResponseEntity<?> createIssue(@RequestBody Issue issue) {
        issueService.createIssue(issue);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/issues/{id}")
    public ResponseEntity<?> updateIssue(@PathVariable String id, @RequestBody Issue newIssue) {
        issueService.updateIssue(id, newIssue);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable String id) {
        issueService.deleteIssue(id);
        return ResponseEntity.ok().build();
    }
}
