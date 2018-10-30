package sofia.uni.fmi.jira.issues;

import sofia.uni.fmi.jira.Component;
import sofia.uni.fmi.jira.User;
import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class DueIssue extends Issue {

    private LocalDateTime dueTime;

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    DueIssue(IssuePriority priority, Component component, User reporter, String description, LocalDateTime dueTime) throws InvalidReporterException {
        super(priority, component, reporter, description);
        this.dueTime = dueTime;
    }
}
