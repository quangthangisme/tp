package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.ui.ListPanelViewType;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** The application should exit. */
    private final boolean exit;

    /** View type information for populating ListPanel */
    private final ListPanelViewType viewType;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean exit, ListPanelViewType viewType) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.exit = exit;
        this.viewType = viewType;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, ListPanelViewType.NONE);
    }

    /**
     * Constructs a {@code CommandResult} with view switching.
     */
    public CommandResult(String feedbackToUser, boolean exit) {
        this(feedbackToUser, exit, ListPanelViewType.NONE);
    }

    /**
     * Constructs a {@code CommandResult} with view switching.
     */
    public CommandResult(String feedbackToUser, ListPanelViewType viewType) {
        this(feedbackToUser, false, viewType);
    }


    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isExit() {
        return exit;
    }

    public ListPanelViewType getViewType() {
        return viewType;
    }

    public boolean isViewSwitchNeeded() {
        return viewType != ListPanelViewType.NONE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && exit == otherCommandResult.exit
                && viewType == otherCommandResult.viewType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, exit, viewType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("exit", exit)
                .toString();
    }
}
