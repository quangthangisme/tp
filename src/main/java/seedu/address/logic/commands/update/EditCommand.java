package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ItemCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command for editing an {@code Item} in the model at a specified index.
 *
 * @param <T> the type of {@code Item} being edited, which must extend {@link Item}.
 */
public abstract class EditCommand<T extends Item> extends ItemCommand<T> {
    public static final String COMMAND_WORD = "edit";
    protected final Index targetIndex;

    /**
     * Creates an {@code EditCommand} to edit an item at the specified {@code targetIndex}.
     */
    public EditCommand(Index targetIndex, Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getIndexOutOfRangeMessage());
        }

        T itemToEdit = lastShownList.get(targetIndex.getZeroBased());
        T editedItem = createEditedItem(model, itemToEdit);

        if (!managerAndList.getDuplicateChecker().check(itemToEdit, editedItem)
                && managerAndList.hasItem(editedItem)) {
            throw new CommandException(getDuplicateMessage());
        }

        managerAndList.setItem(itemToEdit, editedItem);
        managerAndList.updateFilteredItemsList(
                managerAndList.getPredicate().or(item -> item.equals(editedItem))
        );
        return new CommandResult(getSuccessMessage(editedItem));
    }

    /**
     * Creates an edited version of the given item to be applied to the list.
     */
    public abstract T createEditedItem(Model model, T itemToEdit) throws CommandException;

    /**
     * Returns the message to be displayed when the provided {@code targetIndex} is out of range.
     */
    public abstract String getIndexOutOfRangeMessage();

    /**
     * Returns the message to be displayed when the edited item is a duplicate of an existing item.
     */
    public abstract String getDuplicateMessage();

    /**
     * Returns the success message to be displayed when an {@code Item} is successfully edited.
     */
    public abstract String getSuccessMessage(T editedItem);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand<? extends Item> otherEditCommand)) {
            return false;
        }

        return managerAndListGetter.equals(otherEditCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, managerAndListGetter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toEdit", managerAndListGetter)
                .toString();
    }
}
