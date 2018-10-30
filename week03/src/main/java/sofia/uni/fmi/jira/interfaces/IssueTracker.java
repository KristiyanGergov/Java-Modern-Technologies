package sofia.uni.fmi.jira.interfaces;

import sofia.uni.fmi.jira.Component;
import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.enums.IssueResolution;
import sofia.uni.fmi.jira.enums.IssueStatus;
import sofia.uni.fmi.jira.enums.IssueType;
import sofia.uni.fmi.jira.issues.Issue;

import java.time.LocalDateTime;

public interface IssueTracker {
    public Issue[] findAll(Component component, IssueStatus status);

    public Issue[] findAll(Component component, IssuePriority priority);

    public Issue[] findAll(Component component, IssueType type);

    public Issue[] findAll(Component component, IssueResolution resolution);

    public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime);

    public Issue[] findAllBefore(LocalDateTime dueTime);
}
