package pl.edu.agh.ldap.issues;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssueConfig {

    @Bean
    public IssueService issueServiceBean() {
        IssueValidator validator = new IssueValidator();
        IssueRepository repository = new IssueRepository();
        IssueCreator creator = new IssueCreator(validator, repository);
        IssueReader issueReader = new IssueReader(repository);
        return new IssueService(creator, issueReader);
    }
}
