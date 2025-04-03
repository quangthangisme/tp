package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path contactListFilePath = Paths.get("data", "contactlist.json");
    private Path todoListFilePath = Paths.get("data", "todolist.json");
    private Path eventListFilePath = Paths.get("data", "eventlist.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setContactListFilePath(newUserPrefs.getContactListFilePath());
        setTodoListFilePath(newUserPrefs.getTodoListFilePath());
        setEventListFilePath(newUserPrefs.getEventListFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getContactListFilePath() {
        return contactListFilePath;
    }

    public void setContactListFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.contactListFilePath = addressBookFilePath;
    }

    public Path getTodoListFilePath() {
        return todoListFilePath;
    }

    public void setTodoListFilePath(Path todoListFilePath) {
        requireNonNull(todoListFilePath);
        this.todoListFilePath = todoListFilePath;
    }

    public Path getEventListFilePath() {
        return eventListFilePath;
    }

    public void setEventListFilePath(Path eventListFilePath) {
        requireNonNull(eventListFilePath);
        this.eventListFilePath = eventListFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && contactListFilePath.equals(otherUserPrefs.contactListFilePath)
                && todoListFilePath.equals(otherUserPrefs.todoListFilePath)
                && eventListFilePath.equals(otherUserPrefs.eventListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, contactListFilePath, todoListFilePath, eventListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal contact data file location : " + contactListFilePath);
        sb.append("\nLocal todo data file location : " + todoListFilePath);
        sb.append("\nLocal event data file location : " + eventListFilePath);
        return sb.toString();
    }

}
