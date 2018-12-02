package sofia.uni.fmi.jira.issues;

import sofia.uni.fmi.jira.Component;
import sofia.uni.fmi.jira.User;
import sofia.uni.fmi.jira.enums.IssuePriority;
import sofia.uni.fmi.jira.enums.IssueResolution;
import sofia.uni.fmi.jira.enums.IssueStatus;
import sofia.uni.fmi.jira.enums.IssueType;
import sofia.uni.fmi.jira.interfaces.IIssue;
import sofia.uni.fmi.jira.issues.exceptions.InvalidComponentException;
import sofia.uni.fmi.jira.issues.exceptions.InvalidDescriptionException;
import sofia.uni.fmi.jira.issues.exceptions.InvalidPriorityException;
import sofia.uni.fmi.jira.issues.exceptions.InvalidReporterException;

import java.time.LocalDateTime;

public class Issue implements IIssue {

    private static int UNIQUE_NUMBER = 0;

    private IssuePriority priority;
    private IssueStatus status;
    private IssueType type;
    private IssueResolution resolution;

    private User reporter;
    private Component component;

    private String description;
    private String uniqueId;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;


    public Issue(IssuePriority priority, Component component, User reporter, String description) throws InvalidReporterException {

        // V A L I D A T I O N
        if (reporter == null || reporter.getUsername() == null)
            throw new InvalidReporterException("Either reporter or reporter-username is null!");
        if (priority == null)
            throw new InvalidPriorityException("IssuePriority can't be null!");
        if (component == null || component.getCreator() == null || component.getName() == null || component.getShortName() == null)
            throw new InvalidComponentException("Some of Component fields is null or the component itself!");
        if (description == null)
            throw new InvalidDescriptionException("Description can't be null");
        // V A L I D A T I O N

        this.priority = priority;
        this.component = component;
        this.reporter = reporter;
        this.description = description;

        this.uniqueId = component.getShortName() + UNIQUE_NUMBER++;
        this.resolution = IssueResolution.UNRESOLVED;
        this.status = IssueStatus.OPEN;

        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();

    }

    @Override
    public void resolve(IssueResolution resolution) {
        this.resolution = resolution;
    }

    @Override
    public void setStatus(IssueStatus status) {
        this.status = status;
        this.lastModifiedAt = LocalDateTime.now();
    }

    @Override
    public String getId() {
        return uniqueId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public <T> Object getT(T type) {
        if (type instanceof IssuePriority)
            return getPriority();
        else if (type instanceof IssueStatus)
            return getStatus();
        else if (type instanceof IssueResolution)
            return getResolution();
        else if (type instanceof IssueType)
            return getType();
        return null;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public IssueType getType() {
        return type;
    }

    public IssueResolution getResolution() {
        return resolution;
    }

    public Component getComponent() {
        return component;
    }
}
