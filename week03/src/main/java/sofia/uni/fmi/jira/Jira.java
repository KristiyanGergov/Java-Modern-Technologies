package sofia.uni.fmi.jira;

import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.enums.IssueResolution;
import sofia.uni.fmi.jira.enums.IssueStatus;
import sofia.uni.fmi.jira.enums.IssueType;
import sofia.uni.fmi.jira.interfaces.IssueTracker;
import sofia.uni.fmi.jira.issues.DueIssue;
import sofia.uni.fmi.jira.issues.Issue;

import java.time.LocalDateTime;

public class Jira implements IssueTracker {

    private Issue[] issues;

    public Jira(Issue[] issues) {
        this.issues = issues;
    }

    private <T> Issue[] findByType(Component component, T type) {
        Issue[] res = new Issue[issues.length];
        int count = 0;

        for (Issue issue : issues) {
            if (issue.getComponent() == component && issue.getT(type) == type) {
                res[count++] = issue;
            }
        }

        return res;
    }

    private Issue[] findByTime(LocalDateTime... time) {
        Issue[] all = new Issue[issues.length];
        int count = 0;

        if (time.length >  1) {
            for (int i = 0; i < all.length; i++) {
                if (issues[i].getCreatedAt().compareTo(time[0]) > 0 && issues[i].getCreatedAt().compareTo(time[1]) < 0)
                    all[count++] = issues[i];
            }
        } else {
            for (int i = 0; i < all.length; i++) {
                if (issues[i] instanceof DueIssue) {
                    DueIssue dueIssue = (DueIssue) issues[i];
                    if (dueIssue.getDueTime().compareTo(time[0]) < 0)
                        all[count++] = issues[i];
                }

            }
        }

        return all;
    }

    @Override
    public Issue[] findAll(Component component, IssueStatus status) {
        return findByType(component, status);
    }

    @Override
    public Issue[] findAll(Component component, IssuePriority priority) {
        return findByType(component, priority);
    }

    @Override
    public Issue[] findAll(Component component, IssueType type) {
        return findByType(component, type);
    }

    @Override
    public Issue[] findAll(Component component, IssueResolution resolution) {
        return findByType(component, resolution);
    }

    @Override
    public Issue[] findAllIssuesCreatedBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return findByTime(startTime, endTime);
    }

    @Override
    public Issue[] findAllBefore(LocalDateTime dueTime) {
        return findByTime(dueTime);
    }
}
