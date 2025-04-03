---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# TutorConnect User Guide

TutorConnect is a **CLI-based student management tool designed for tutors handling multiple courses across different platforms.**
It provides a centralized solution for organizing student contacts and managing tasks—without the complexity of a full-fledged learning management system.
If you can type fast, TutorConnect can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TutorConnect.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all contacts.

    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

    * `delete 3` : Deletes the 3rd contact shown in the current list.

    * `clear` : Deletes all contacts.

    * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Commands working with item will be of format `ITEM COMMAND PARAMETERS`.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add --name NAME`, `NAME` is a parameter which can be used as `add --name John Doe`.

* Parameters in square brackets are optional.<br>
  e.g `--name NAME [--tag TAG(S)]` can be used as `--name John Doe --tag friend`, `--name John Doe`, or `--name John Doe --tag enemy must-be-destroyed`.

* Parameters with optional plural markers can be specified a positive integer number of times.
  Note that this rule can be combined with the previous one to represent a parameter that can be specified a non-negative integer number of times.<br>
  e.g. `--contact CONTACT_INDEX/INDICES` can be used as `--contact 123` or `--contact 4 5 6`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `--name NAME --location LOCATION`, `--location LOCATION --name NAME ` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `todo list`, `exit` and `event clear`) will be ignored.<br>
  e.g. if the command specifies `todo list 123`, it will be interpreted as `todo list`.

* Constraints for common parameters:
    * Most text-based parameters can accept multiple words as values, but the words cannot start with a `-` to avoid clashing with our prefixes. This applies to `NAME`, `ID`, `COURSE`, `GROUP`, and `LOCATION`.
      e.g. `NUS Science`, `E.T. the Extra-Terrestrial`, `#?!@-*#$` are acceptable, but `1 - 1 = 2` is not.
    * Datetime-based parameters must be of format `YY-MM-DD HH:MM`, with exactly one space between `DD` and `HH`, and where `HH` is in the 24-hour format.
    * `TAG` parameters must be a single word not starting with `-`.
    * Index-based parameters must be a positive integer not larger than //TODO.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

## Todo: `todo`

A todo is a task with a deadline that can be associated with multiple contacts to indicate their involvement. It supports the following commands.

### Adding a todo: `add`
Adds a todo to the app.

Format: `todo add --name NAME --location LOCATION --deadline DEADLINE [--tag TAG(S)]`

**Tip:** A todo can have any number of tags (including 0)

Examples:
* `todo add --name Final Submission --deadline 24-08-26 12:00 --location NUS SoC COM1 --tag CS1234`
* `todo add --name CS1010S mission --deadline 24-05-26 12:00 --location Coursemology --tag urgent struggling`

### Editing an todo: `edit`
Edits the details of the todo identified by the index number in the currently displayed todo list. Only the fields specified in the command will be updated; all others remain unchanged.

Format:
`event edit INDEX [--name NAME] [--deadline DEADLINE] [--location LOCATION] [--status STATUS] [--contact CONTACT_INDEX/INDICES] [--tag TAG(S)]`

* `INDEX` refers to the todo to be edited, based on its position in the displayed todo list.

* `STATUS` can only be `true` or `false`.

* `CONTACT_INDEX/INDICES` refers to the index(es) of the contact(s) in the currently displayed contact list, which will be linked to the todo.

* Fields that are not included in the command will remain unchanged. You can update any combination of fields at once.

* If `--tag` is provided with no value, all tags will be cleared.

Examples:

* `todo edit 1 --name Project Meeting`

* `todo edit 2 --deadline 25-03-15 10:00 --location COM2-0208 --tag`

* `todo edit 4 --name Training --contact 1 2 3`

### Deleting a todo: `delete`

Deletes the todo identified by the index number used in the displayed todo list.

Format: `todo delete INDEX`

Example:
* `todo delete 1`

### Displaying information of todo: `info`
Displays the complete information belonging to the todo identified by the index number used in the displayed todo list.

Format: `todo info INDEX`

Example:
* `event info 1`

### Clearing todo list: `clear`
Clears the todo list.

Format: `todo clear`

### Linking contacts: `link`
Associates one or more contacts to a todo. Useful for keeping track of which contacts are involved in a particular todo.

Format: `todo link TODO_INDEX --contact CONTACT_INDEX/INDICES`

* `TODO_INDEX` refers to the todo of the event in the displayed todo list.

* `CONTACT_INDEX/INDICES` refers to the index(es) of the contact(s) in the currently displayed contact list. You can link multiple contacts by providing more than one contact index.

Examples:
* `todo link 1 --contact 1 3 4`

### Unlinking contacts: `unlink`
Removes the association between one or more contacts and a specific todo.

Format: `todo unlink TODO_INDEX --contact CONTACT_INDEX/INDICES`

* `TODO_INDEX` refers to the index of the todo in the displayed event todo.

* `CONTACT_INDEX/INDICES` refers to the index(es) of the contact(s) to be unlinked from the event based on the list displayed by the `info` command. You can unlink multiple contacts by providing more than one contact index.

Examples:
* `todo unlink 1 --contact 3 4`

### Tagging a todo: `tag`
Adds one or more tags to the specified todo. Tags help categorize and filter todos more easily (e.g., by priority, role, type, etc.).

Format: `todo tag INDEX --tag TAG(S)`

* `INDEX` refers to the index of the todo in the currently displayed todo list.

* Duplicate tags (already added to the todo) are not allowed.

Example:
* `todo tag 1 --tag important need-help`

### Untagging a todo: `untag`
Removes one or more tags from the specified todo.

Format: `event untag INDEX --tag TAG(S)`

* `INDEX` refers to the index of the todo in the currently displayed todo list.

* Tags are case-sensitive and must match the tag(s) assigned to the todo.

Example:
* `todo untag 1 --tag important weekly`

### Mark a todo as done: `log`
Mark a todo as completed.

Format: `todo mark INDEX`

* `INDEX` refers to the index of the todo in the displayed todo list.

* The todo must not be already marked as done.

Example:
* `todo mark 1`

### Mark a todo as not done: `unmark`
Mark a todo as not done. This reverses the effect of the `mark` command.

Format: `todo unmark INDEX`

* `INDEX` refers to the index of the todo in the displayed todo list.

* The todo must not be already marked as not done.

Example:
`todo unmark 20`

### Filtering todos: `filter`
Filters the list of todos based on one or more criteria. You can search by todos name, time, location, tags, or linked contacts using logical operators to combine multiple values.

Format:
`todo filter --COLUMN OPERATOR: VALUE(S) [...]`

Supported columns:
* `--name`: Todo name.
* `--deadline`: Todo deadline.
* `--location`: Todo location.
* `--status`: Todo status
* `--tag`: Tags.
* `--contact`: Linked contacts.

Operators (optional):
* `and` (default): All values must match.
* `or`: At least one value must match.
* `nand`: None of the values must match.
* `nor`: Reject all values.

If an operator is not provided, it defaults to `and`.

**Value formats**:

Name, Location, and Tags:
* Provide one or more keywords separated by spaces.
* Keywords are case-insensitive and support partial matches.

DEADLINE:
* Provide one or more closed intervals, each written as: `[<INTERVAL_START>/<INTERVAL_END>]`.
* Each datetime must follow the format: `YY-MM-DD HH:MM`.
* You can use `-` to replace the `START` or the `END` to signify no lower/upper bound. At least one of the two bounds must be specified.

Contact:
* Provide contact indices (as shown in the current contact list).

Examples:
* `todo filter --name or: Exam REPORT`.
    * Find todos whose name contains at least one of the keywords "exam" or "report."

* `todo filter --name CS1010S grading --deadline or: [25-04-13 23:59/25-04-20 23:59] [25-04-27 23:59/-]`
    * Find todos whose name contains both the keywords `"CS1010S"` and `"grading"` and whose start time is between `25-04-13 23:59` and `25-04-20 23:59`(inclusive) or after `25-04-27 23:59` (inclusive).

* `todo filter --location nand: NUS Science --contact 1 2 3`
    * Find todos whose location does not contain the keywords "NUS" or "Science" and which are linked to the people currently at index 1, 2 and 3.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List**   | `list`
**Help**   | `help`
