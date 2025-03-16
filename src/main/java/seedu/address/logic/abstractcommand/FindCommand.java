package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command for finding items in the model that match a given {@code predicate}.
 *
 * @param <T> the type of {@code Item} being searched for, which must extend {@link Item}.
 */
public abstract class FindCommand<T extends Item> extends ItemCommand<T> {
    protected final Predicate<T> predicate;

    /**
     * Creates a {@code FindCommand} to find items that match the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} or {@code managerAndListGetter} is
     *                              {@code null}.
     */
    public FindCommand(Predicate<T> predicate,
                       Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        managerAndList.updateFilteredItemsList(predicate);
        return new CommandResult(
                getResultOverviewMessage(managerAndList.getFilteredItemsList().size()));
    }

    /**
     * Returns an overview message about the result of the find operation, including the
     * number of items that match the search criteria.
     */
    public abstract String getResultOverviewMessage(int numberOfResults);
}
