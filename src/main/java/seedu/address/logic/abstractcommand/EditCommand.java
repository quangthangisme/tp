package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Function;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.DuplicateChecker;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command for editing an {@code Item} in the model at a specified index.
 *
 * @param <T> the type of {@code Item} being edited, which must extend {@link Item}.
 */
public abstract class EditCommand<T extends Item> extends ItemCommand<T> {
    private final Index index;
    private final DuplicateChecker<T> duplicateChecker;

    /**
     * Creates an {@code EditCommand} to edit an item at the specified {@code index}.
     *
     * @throws NullPointerException if {@code index}, {@code managerAndListGetter}, or
     *                               {@code duplicateChecker} is {@code null}.
     */
    public EditCommand(Index index,
                       Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter,
                       DuplicateChecker<T> duplicateChecker) {
        super(managerAndListGetter);
        requireAllNonNull(index, duplicateChecker);
        this.index = index;
        this.duplicateChecker = duplicateChecker;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        T itemToEdit = lastShownList.get(index.getZeroBased());
        T editedItem = createEditedItem(itemToEdit);

        if (!duplicateChecker.check(itemToEdit, editedItem) && managerAndList.hasItem(editedItem)) {
            throw new CommandException(getDuplicateMessage());
        }

        managerAndList.setItem(itemToEdit, editedItem);
        managerAndList.showAllItems();
        return new CommandResult(getSuccessMessage(editedItem));
    }

    /**
     * Creates an edited version of the given item to be applied to the list.
     */
    abstract T createEditedItem(T itemToEdit);

    /**
     * Returns the message to be displayed when the provided index is invalid.
     */
    abstract String getInvalidIndexMessage();

    /**
     * Returns the message to be displayed when the edited item is a duplicate of an existing item.
     */
    abstract String getDuplicateMessage();

    /**
     * Returns the success message to be displayed after successfully editing the item.
     */
    abstract String getSuccessMessage(T editedItem);
}
