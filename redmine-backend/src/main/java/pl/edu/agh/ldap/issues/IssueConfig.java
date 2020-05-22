package pl.edu.agh.ldap.issues;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssueConfig {

    @Bean
    public IssueService issueServiceBean(DozerBeanMapper mapper) {
        IssueValidator validator = new IssueValidator();
        IssueRepository repository = new IssueRepository();
        IssueCreator creator = new IssueCreator(validator, repository, mapper);
        IssueReader issueReader = new IssueReader(repository);
        return new IssueService(creator, issueReader);
    }
}
