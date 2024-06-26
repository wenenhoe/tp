package meditracker.command;

import java.util.Map;

import meditracker.argument.AfternoonArgument;
import meditracker.argument.ArgumentHelper;
import meditracker.argument.ArgumentList;
import meditracker.argument.ArgumentName;
import meditracker.argument.EveningArgument;
import meditracker.argument.ListIndexArgument;
import meditracker.argument.MorningArgument;
import meditracker.dailymedication.DailyMedicationManager;
import meditracker.exception.ArgumentException;
import meditracker.exception.HelpInvokedException;
import meditracker.exception.InsufficientQuantityException;
import meditracker.exception.MedicationNotFoundException;
import meditracker.exception.MedicationUnchangedException;
import meditracker.time.Period;
import meditracker.ui.Ui;

/**
 * The TakeCommand class represents a command to take a daily medication.
 * It extends the Command class.
 */
public class TakeCommand extends Command {
    public static final ArgumentList ARGUMENT_LIST = new ArgumentList(
            new ListIndexArgument(false),
            new MorningArgument(true),
            new AfternoonArgument(true),
            new EveningArgument(true)
    );
    public static final String HELP_MESSAGE = ArgumentHelper.getHelpMessage(CommandName.TAKE, ARGUMENT_LIST);
    private final Map<ArgumentName, String> parsedArguments;

    /**
     * Constructs a TakeCommand object with the specified arguments.
     *
     * @param arguments The arguments containing information to be parsed.
     * @throws HelpInvokedException When help argument is used or help message needed
     * @throws ArgumentException Argument flag specified not found,
     *              or when argument requires value but no value specified,
     *              or when unknown argument flags found in user input,
     *              or when duplicate argument flag found
     */
    public TakeCommand(String arguments) throws HelpInvokedException, ArgumentException {
        parsedArguments = ARGUMENT_LIST.parse(arguments);
    }

    /**
     * Executes the take command.
     * This method marks an existing Medication object using the provided list index.
     * It also displays a message confirming the modification of the daily medication status.
     */
    @Override
    public void execute() {
        Period period = Command.getPeriod(parsedArguments);
        if (period == Period.UNKNOWN) {
            Ui.showErrorMessage("Unable to determine time period. "
                    + "Please select only 1 of following flag: -m/-a/-e");
            return;
        }

        int listIndex = Command.getListIndex(parsedArguments);
        try {
            DailyMedicationManager.takeDailyMedication(listIndex, period);
        } catch (IndexOutOfBoundsException e) {
            Ui.showErrorMessage("Invalid index specified");
            return;
        } catch (InsufficientQuantityException e) {
            Ui.showErrorMessage(e);
            return;
        } catch (MedicationNotFoundException e) {
            Ui.showWarningMessage("Possible data corruption: Medication not found");
            return;
        } catch (MedicationUnchangedException e) {
            Ui.showSuccessMessage("Medication already taken, no changes were made");
            return;
        }

        Ui.showSuccessMessage("Medicine has been taken");
    }
}
