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

public abstract class EditCommand<T extends Item> extends ItemCommand<T> {
    private final Index index;

    private final DuplicateChecker<T> duplicateChecker;

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

    abstract T createEditedItem(T itemToEdit);

    abstract String getInvalidIndexMessage();

    abstract String getDuplicateMessage();

    abstract String getSuccessMessage(T editedItem);
}
