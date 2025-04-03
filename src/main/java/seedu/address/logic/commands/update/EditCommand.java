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
import seedu.address.model.item.ManagerAndList;

/**
 * Abstract command for editing an {@code Item} in the model at a specified index.
 *
 * @param <T> the type of {@code Item} being edited, which must extend {@link Item}.
 */
public abstract class EditCommand<T extends ManagerAndList<U>, U extends Item>
        extends ItemCommand<T, U> {
    public static final String COMMAND_WORD = "edit";
    protected final Index targetIndex;

    /**
     * Creates an {@code EditCommand} to edit an item at the specified {@code targetIndex}.
     */
    public EditCommand(Index targetIndex, Function<Model, T> managerAndListGetter) {
        super(managerAndListGetter);
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        T managerAndList = managerAndListGetter.apply(model);
        List<U> lastShownList = managerAndList.getFilteredItemsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(getIndexOutOfRangeMessage(),
                    targetIndex.getOneBased()));
        }

        U itemToEdit = lastShownList.get(targetIndex.getZeroBased());
        U editedItem = createEditedItem(model, itemToEdit);

        if (!managerAndList.getDuplicateChecker().check(itemToEdit, editedItem)
                && managerAndList.hasItem(editedItem)) {
            throw new CommandException(getDuplicateMessage());
        }

        managerAndList.setItem(itemToEdit, editedItem);
        cascade(model, itemToEdit, editedItem);
        return new CommandResult(getSuccessMessage(editedItem));
    }

    /**
     * Propagate the result of the edit.
     */
    public abstract void cascade(Model model, U itemToEdit, U editedItem);

    /**
     * Creates an edited version of the given item to be applied to the list.
     */
    public abstract U createEditedItem(Model model, U itemToEdit) throws CommandException;

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
    public abstract String getSuccessMessage(U editedItem);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand<?, ?> otherEditCommand)) {
            return false;
        }

        return targetIndex.equals(otherEditCommand.targetIndex)
                && managerAndListGetter.equals(otherEditCommand.managerAndListGetter);
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
