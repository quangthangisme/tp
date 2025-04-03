package seedu.address.logic.commands.read;

import static java.util.Objects.requireNonNull;

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
 * Abstract command to display information of an {@code Item} from the model based on a given
 * {@code targetIndex}.
 *
 * @param <T> the type of {@code Item} being displayed, which must extend {@link Item}.
 */
public abstract class InfoCommand<T extends ManagerAndList<U>, U extends Item>
        extends ItemCommand<T, U> {

    public static final String COMMAND_WORD = "info";
    protected final Index targetIndex;

    /**
     * Creates an {@code InfoCommand} to display information of the item at the specified {@code targetIndex}.
     */
    public InfoCommand(Index targetIndex, Function<Model, T> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(targetIndex);
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

        U itemToDisplay = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(getSuccessMessage(itemToDisplay));
    }

    /**
     * Returns the message to be displayed when the provided {@code targetIndex} is invalid.
     */
    public abstract String getIndexOutOfRangeMessage();

    /**
     * Returns the information message to be displayed for the given {@code item}.
     */
    public abstract String getSuccessMessage(U itemToDisplay);


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoCommand<?, ?> otherInfoCommand)) {
            return false;
        }

        return targetIndex.equals(otherInfoCommand.targetIndex) && managerAndListGetter.equals(
                otherInfoCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, managerAndListGetter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("targetIndex", targetIndex).toString();
    }

}
