package sofia.uni.fmi.jira.issues;

import sofia.uni.fmi.jira.Component;
import sofia.uni.fmi.jira.User;
import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.issues.exceptions.InvalidComponentException;
import sofia.uni.fmi.jira.issues.exceptions.InvalidDescriptionException;
import sofia.uni.fmi.jira.issues.exceptions.InvalidPriorityException;
import sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

public class Bug extends Issue {

    public Bug(IssuePriority priority, Component component, User reporter, String description) throws InvalidReporterException, InvalidComponentException, InvalidDescriptionException, InvalidPriorityException {
        super(priority, component, reporter, description);
    }
}
