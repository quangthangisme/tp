package seedu.address.logic.commands.read;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ItemCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ManagerAndList;
import seedu.address.ui.ListPanelViewType;

/**
 * Abstract command for filtering items in the model that match a given complex {@code predicate}.
 *
 * @param <T> the type of {@code Item} being filtered, which must extend {@link Item}.
 */
public abstract class FilterCommand<T extends ManagerAndList<U>, U extends Item>
        extends ItemCommand<T, U> {

    public static final String COMMAND_WORD = "filter";

    /**
     * Creates a {@code FilterCommand} to filter items that match the given {@code predicate}.
     */
    public FilterCommand(Function<Model, T> managerAndListGetter) {
        super(managerAndListGetter);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        T managerAndList = managerAndListGetter.apply(model);
        managerAndList.updateFilteredItemsList(createPredicate(model));

        return new CommandResult(getSuccessMessage(managerAndList.getFilteredItemsList().size()),
                getListPanelViewType());
    }

    /**
     * Creates a predicate that will be used to filter the items in the model.
     *
     * @throws CommandException if a predicate cannot be created
     */
    public abstract Predicate<U> createPredicate(Model model) throws CommandException;

    /**
     * Returns a success message to be displayed when the command executes successfully.
     */
    public String getSuccessMessage(int numberOfResults) {
        return String.format(Messages.MESSAGE_SEARCH_OVERVIEW, numberOfResults);
    }

    /**
     * Returns the appropriate ListPanelViewType for this filter command.
     * Must be implemented by subclasses to specify which view should be displayed.
     *
     * @return the ListPanelViewType to switch to
     */
    public abstract ListPanelViewType getListPanelViewType();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand<?, ?> otherFilterCommand)) {
            return false;
        }

        return managerAndListGetter.equals(otherFilterCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerAndListGetter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toFilter", managerAndListGetter)
                .toString();
    }
}
