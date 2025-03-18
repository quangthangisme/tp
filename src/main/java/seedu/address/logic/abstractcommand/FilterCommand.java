package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command for filtering items in the model that match a given complex {@code predicate}.
 *
 * @param <T> the type of {@code Item} being filtered, which must extend {@link Item}.
 */
public abstract class FilterCommand<T extends Item> extends ItemCommand<T> {

    protected final Predicate<T> predicate;

    /**
     * Creates a {@code FilterCommand} to filter items that match the given {@code predicate}.
     *
     * @param predicate the predicate used to filter items
     * @param managerAndListGetter function that returns the item manager and filtered list
     * @throws NullPointerException if {@code predicate} or {@code managerAndListGetter} is
     *                              {@code null}
     */
    public FilterCommand(Predicate<T> predicate,
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
     * Returns an overview message about the result of the filter operation, including the
     * number of items that match the filter criteria.
     *
     * @param numberOfResults the number of items that match the filter criteria
     * @return a string containing the overview message
     */
    public abstract String getResultOverviewMessage(int numberOfResults);
}
