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

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


## Contact: `contact`

A contact is an entry with information of a student. It supports the following commands.

### Adding a contact: `add`
Adds a contact to the app.

Format: `contact add --id ID --name NAME --email EMAIL --course COURSE --group GROUP [--tag TAG(S)]`

**Tip:** A contact can have any number of tags (including 0)

Examples:
* `contact add --id A1234567A --name John Doe --email johnd@example.com --course CS50 --group T01 --tag friends owesMoney`

### Editing an contact: `edit`
Edits the details of the contact identified by the index number in the currently displayed contact list. Only the fields specified in the command will be updated; all others remain unchanged.

Format:
`contact edit INDEX [--id ID] [--name NAME] [--email EMAIL] [--course COURSE] [--group GROUP] [--tag TAG(S)]`

* `INDEX` refers to the contact to be edited, based on its position in the displayed contact list.

* Fields that are not included in the command will remain unchanged. You can update any combination of fields at once.

* If `--tag` is provided with no value, all tags will be cleared.

Examples:

* `contact edit 1 --email johndoe@example.com`

* `contact edit 3 --name Jack Doe`

* `contact edit 4 --group CS2103T`

### Deleting a contact: `delete`

Deletes the contact identified by the index number used in the displayed contact list.

Format: `contact delete INDEX`

Example:
* `contact delete 1`

### Displaying information of contact: `info`
Displays the complete information belonging to the contact identified by the index number used in the displayed contact list.

Format: `contact info INDEX`

Example:
* `event info 1`

### Clearing contact list: `clear`
Clears the contact list.

Format: `contact clear`

### Tagging a contact: `tag`
Adds one or more tags to the specified contact. Tags help categorize and filter contacts more easily (e.g., by priority, role, type, etc.).

Format: `contact tag INDEX --tag TAG(S)`

* `INDEX` refers to the index of the contact in the currently displayed contact list.

* Duplicate tags (already added to the contact) are not allowed.

Example:
* `contact tag 1 --tag important need-help`

### Untagging a contact: `untag`
Removes one or more tags from the specified contact.

Format: `event untag INDEX --tag TAG(S)`

* `INDEX` refers to the index of the contact in the currently displayed contact list.

* Tags are case-sensitive and must match the tag(s) assigned to the contact.

Example:
* `contact untag 1 --tag important weekly`

### Filtering contacts: `filter`
Filters the list of contacts based on one or more criteria. You can search by contacts name, time, location, tags, or linked contacts using logical operators to combine multiple values.

Format:
`contact filter --COLUMN OPERATOR: VALUE(S) [...]`

Supported columns:
* `--name`: contact name.
* `--id`: contact id.
* `--email`: contact email.
* `--course`: contact course.
* `--group`: contact group.
* `--tag`: tags.

Operators (optional):
* `and` (default): All values must match.
* `or`: At least one value must match.
* `nand`: None of the values must match.
* `nor`: Reject all values.

If an operator is not provided, it defaults to `and`. If an unrecognized operator is provided, it will be treated as a value. If multiple valid operators are provided, the first one will be applied and the rest will be treated as values.

**Value formats**:

Name, ID, email, course, group, and tags:
* Provide one or more keywords separated by spaces.
* Keywords are case-insensitive and support partial matches.

Examples:
* `contact filter --id or: 12 13`.
    * Find students with ID 12 or 13.

* `contact filter --name John Doe --course CS1010S --group or: T01 T02 T03`
    * Find contacts with both "Darren" and "Tan" in their name who enroll in course CS1010S and class T01, T02, or T03.

* `contact filter --name nand: enemy Hater --tag and: handsome smart`
    * Find contacts whose names do not contain "enemy" and "Hater" and are tagged with both "handsome" and "smart".

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

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
