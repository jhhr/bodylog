#Getting Started
Movements are what you add session data for. Without any movements saved, you cannot do anything. First open the movement editing window from the menu through Menu-->Add/Edit Movements.

##Creating & Editing Movements

In the movement editor window you begin editing by selecting an existing movement from the list or pressing "New Movement". This opens an editor with which you can edit and save the chosen movement. You can only have one editor open for a movement. Within the movement editor you can do the following:

###Set Movement Name:
- click the text field and type into it, then press enter to execute the name change; if successful, the name displayed in the upper left corner of the editor will change
- hovering over the field with the mouse will display a tooltip on what characters are not allowed in the name
###Add a Variable:
- the "Add Variable" button will add a new variable to edit for the movement
###Remove a Variable:
- the "Remove Variable" button will remove the last variable in the editor
- confirmation will be asked if the variable wasn't blank
- you cannot remove a variable if there is only one variable left
###Save to File:
- the "Save to File" button will attempt to save the movement to file
- confirmation will be asked if there already exists a file for a movement with the same name
- you cannot save the movement if the name is blank or any of the variables is not proper (see editing variables)
###Close editor:
- the "Close Editor" button will close the editor

##Edit Variables:

###Set Variable Name:
- double click a cell under the "Name" column and type into it, to execute the name change press enter or click another cell; if successful, the cell will display the new name
- hovering over any cell will display a tooltip on what characters are allowed in the name or choices
###Set Variable Type:
- click a cell under the "Type" column and select type from the list (see Proper Variables and Editing Sessions on what the type does)
###Set Variable Choices:
- click the cell under the "Choices" column, this opens the choices editor
 - press "Add Choice" to add a new blank choice
 - press "Remove Last" to remove the last choice
 - to edit the choice, double click a cell and type into it, press enter or click another cell to execute the change; if successful, the cell will display the new choice (the same characters restrictions that apply to the variable name apply here)
 - press "Done" when you are finished editing the choices and want to save them to the variable
 - to cancel simply close the choice editor
###Proper Variables:
 - a variable with a blank name is not proper
 - a variable of numerical or checkbox type with more than zero choices is not proper
 - a variable of optional or mandatory choice type with less than one choices is not proper
 - a variable with any blank choices is not proper
 - a variable with any duplicate choices is not proper

##Editing Sessions

Open the session editing window from the menu through Menu-->Edit Sessions. Open a session editor for a movement by selecting it from the list. You can only have one editor open for a movement. Within the session editor you can do the following:

###Set Session Date:
- click the text field and edit the date, then press enter to execute the change; if successful the date displayed on the upper left corner of the editor will change
- hovering over the field will display a tooltip on how the date must be written
###Add a Set:
- the "Add Set" button will add a new set to edit for the session
###Remove a Variable:
- the "Remove Set" button will remove the last set in the editor
- confirmation will be asked if the set has been edited
###Save to file:
- the "Save to file" button will attempt to save the session to file
- confirmation will be asked if there already exists a file for a session with the same date
###Close editor:
- the "Close Editor" button will the close the editor

##Edit session:

###Set value for a variable:
- depending on the variable type:
 - if type is numerical, double click on the cell and type the number you want, to execute the change press enter or click another cell; if unsuccessful the cell border will become red and you will not be able to select another cell until you input something proper
 - if type is checkbox, check or uncheck the box
 - if type is optional or mandatory choice, click the cell to show the drop down list and select one choice
- hovering over the column header will show a tooltip on how to edit that variable

##Viewing Sessions