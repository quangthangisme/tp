package seedu.address.logic.commands.delete;

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
 * Abstract command to delete an {@code Item} from the model based on a given {@code targetIndex}.
 *
 * @param <T> the type of {@code Item} being deleted, which must extend {@link Item}.
 */
public abstract class DeleteCommand<T extends ManagerAndList<U>, U extends Item>
        extends ItemCommand<T, U> {

    public static final String COMMAND_WORD = "delete";
    protected final Index targetIndex;

    /**
     * Creates a {@code DeleteCommand} to delete the item at the specified {@code targetIndex}.
     */
    public DeleteCommand(Index targetIndex, Function<Model, T> managerAndListGetter) {
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
            throw new CommandException(String.format(getIndexOutOfRangeMessage(), targetIndex.getOneBased()));
        }

        U itemToDelete = lastShownList.get(targetIndex.getZeroBased());
        managerAndList.deleteItem(itemToDelete);
        cascade(model, itemToDelete);
        return new CommandResult(getSuccessMessage(itemToDelete));
    }

    /**
     * Propagate the result of the deletion.
     */
    public abstract void cascade(Model model, U itemToDelete);

    /**
     * Returns the message to be displayed when the provided {@code targetIndex} is out of range.
     */
    public abstract String getIndexOutOfRangeMessage();

    /**
     * Returns the success message to be displayed when an {@code Item} is successfully deleted.
     */
    public abstract String getSuccessMessage(U item);


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand<?, ?> otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && managerAndListGetter.equals(otherDeleteCommand.managerAndListGetter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, managerAndListGetter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
