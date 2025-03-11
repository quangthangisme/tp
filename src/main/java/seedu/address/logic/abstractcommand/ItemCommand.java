package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import seedu.address.logic.commands.Command;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class ItemCommand<T extends Item> extends Command {

    protected final Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter;

    public ItemCommand(Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        requireNonNull(managerAndListGetter);
        this.managerAndListGetter = managerAndListGetter;
    }
}
