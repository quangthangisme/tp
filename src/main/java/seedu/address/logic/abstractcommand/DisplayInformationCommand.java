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

public abstract class DisplayInformationCommand<T extends Item> extends ItemCommand<T> {
    private final Index index;

    public DisplayInformationCommand(Index index, Function<Model,
                ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        T itemToDisplay = lastShownList.get(index.getZeroBased());
        return new CommandResult(getInformationMessage(itemToDisplay));
    }

    abstract String getInvalidIndexMessage();

    abstract String getInformationMessage(T editedItem);
}
