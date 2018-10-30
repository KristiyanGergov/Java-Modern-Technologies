package sofia.uni.fmi.jira;

import sofia.uni.fmi.jira.enums.*;
import sofia.uni.fmi.jira.issues.*;

import java.time.LocalDateTime;

public class Start {
    public static void main(String[] args) {

        User user = new User("gergov");

        Component component = new Component("testov", "test", user);


        Issue issue1 = new Bug(IssuePriority.MINOR, component, user, "Testov opit");
        Issue issue2 = new NewFeature(IssuePriority.MAJOR, component, user, "vtori opit", LocalDateTime.of(2018, 10, 30 , 20, 20));

        Issue[] issues = {issue1, issue2};

        Jira jira = new Jira(issues);
        jira.findAll(component, IssueStatus.OPEN);

        jira.findAll(component, IssueResolution.UNRESOLVED);
        jira.findAll(component, IssuePriority.MAJOR);

    }
}
