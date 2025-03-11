package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Function;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class DeleteCommand<T extends Item> extends ItemCommand<T> {
    private final Index targetIndex;

    public DeleteCommand(Index targetIndex,
                         Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        T itemToDelete = lastShownList.get(targetIndex.getZeroBased());
        managerAndList.deleteItem(itemToDelete);
        return new CommandResult(getSuccessMessage(itemToDelete));
    }

    abstract String getInvalidIndexMessage();

    abstract String getSuccessMessage(T item);
}
