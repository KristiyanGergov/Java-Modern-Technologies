import org.junit.BeforeClass;
import org.junit.Test;
import sofia.uni.fmi.jira.Component;
import sofia.uni.fmi.jira.Jira;
import sofia.uni.fmi.jira.User;
import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.enums.IssueStatus;
import sofia.uni.fmi.jira.issues.Bug;
import sofia.uni.fmi.jira.issues.Issue;
import sofia.uni.fmi.jira.issues.NewFeature;
import sofia.uni.fmi.jira.issues.Task;
import sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class SampleIssueTrackerTest {

    private static User user;

    private static Component component;

    private static Issue[] issues;

    private static Jira jira;

    @BeforeClass
    public static void setUp() throws InvalidReporterException {
        user = new User("gergov");

        component = new Component("Kristiyan Gergov", "kg", user);

        Issue issue1 = new Bug(IssuePriority.MAJOR, component, user, "Test1");
        Issue issue2 = new NewFeature(IssuePriority.MINOR, component, user, "Test2", LocalDateTime.of(2018, 10, 20, 13, 56));
        Issue issue3 = new Task(IssuePriority.CRITICAL, component, user, "Test3", LocalDateTime.of(2018, 10, 20, 13, 55));
        Issue issue4 = new Bug(IssuePriority.TRIVIAL, component, user, "Test4");

        issues = new Issue[]{issue1, issue2, issue3, issue4};

        jira = new Jira(issues);
    }

    @Test
    public void testFindCounts() {

        Issue[] allOpen = jira.findAll(component, IssueStatus.OPEN);
        Issue[] allMajor = jira.findAll(component, IssuePriority.MAJOR);

        assertEquals(4, countIssues(allOpen));
        assertEquals(1, countIssues(allMajor));
    }

    @Test
    public void testFindTime() {
        Issue[] allBetween = jira.findAllIssuesCreatedBetween(
                LocalDateTime.of(2018, 10, 20, 13, 54),
                LocalDateTime.of(2018, 10, 30, 18, 0));

        Issue[] allBefore = jira.findAllBefore( LocalDateTime.of(2018, 10, 20, 13, 56));

        assertEquals(4, countIssues(allBetween));
        assertEquals(1, countIssues(allBefore));

    }

    private static int countIssues(Issue[] issues) {
        int counter = 0;
        for (Issue issue : issues) {
            if (issue != null) {
                counter++;
            }
        }

        return counter;
    }
}