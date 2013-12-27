package org.netbeans.gradle.project.model.issue;

import org.openide.util.Parameters;

public final class DependencyResolutionIssue {
    public enum DependencyKind {
        RUNTIME,
        COMPILE;
    }

    private final String projectName;
    private final String sourceSetName;
    private final DependencyKind dependencyKind;
    private final Throwable stackTrace;

    public DependencyResolutionIssue(
            String projectName,
            String sourceSetName,
            DependencyKind dependencyKind,
            Throwable stackTrace) {

        Parameters.notNull("projectName", projectName);
        Parameters.notNull("sourceSetName", sourceSetName);
        Parameters.notNull("dependencyKind", dependencyKind);
        Parameters.notNull("stackTrace", stackTrace);

        this.projectName = projectName;
        this.sourceSetName = sourceSetName;
        this.dependencyKind = dependencyKind;
        this.stackTrace = stackTrace;
    }

    public static DependencyResolutionIssue compileIssue(String projectName, String sourceSetName, Throwable stackTrace) {
        return new DependencyResolutionIssue(projectName, sourceSetName, DependencyKind.COMPILE, stackTrace);
    }

    public static DependencyResolutionIssue runtimeIssue(String projectName, String sourceSetName, Throwable stackTrace) {
        return new DependencyResolutionIssue(projectName, sourceSetName, DependencyKind.RUNTIME, stackTrace);
    }

    public String getProjectName() {
        return projectName;
    }

    public String getSourceSetName() {
        return sourceSetName;
    }

    public DependencyKind getDependencyKind() {
        return dependencyKind;
    }

    public Throwable getStackTrace() {
        return stackTrace;
    }

    public String getMessage() {
        // TODO: I18N
        switch (dependencyKind) {
            case RUNTIME:
                return "Compile time dependencies of " + projectName + " [" + sourceSetName + "] could not be resolved.";
            case COMPILE:
                return "Runtime dependencies of " + projectName + " [" + sourceSetName + "] could not be resolved.";
            default:
                throw new AssertionError(dependencyKind.name());
        }
    }
}
