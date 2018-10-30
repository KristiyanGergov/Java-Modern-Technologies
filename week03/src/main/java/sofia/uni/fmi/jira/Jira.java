package sofia.uni.fmi.jira;

import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.enums.IssueResolution;
import sofia.uni.fmi.jira.enums.IssueStatus;
import sofia.uni.fmi.jira.enums.IssueType;
import sofia.uni.fmi.jira.interfaces.IssueTracker;
import sofia.uni.fmi.jira.issues.Issue;

import java.time.LocalDateTime;

public class Jira implements IssueTracker {

    private Issue[] issues;

    public Jira(Issue[] issues) {
        this.issues = issues;
    }

    @Override
    public Issue[] findAll(Component component, IssueStatus status) {
        return new Issue[0];
    }

    @Override
    public Issue[] findAll(Component component, IssuePriority priority) {
        return new Issue[0];
    }

    @Override
    public Issue[] findAll(Component component, IssueType type) {
        return new Issue[0];
    }

    @Override
    public Issue[] findAll(Component component, IssueResolution resolution) {
        return new Issue[0];
    }

    @Override
    public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return new Issue[0];
    }

    @Override
    public Issue[] findAllBefore(LocalDateTime dueTime) {
        return new Issue[0];
    }
}
