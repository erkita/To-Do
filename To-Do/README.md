## Command Line To-Do Application
### April, 2021

--------------
### Overview
This command line To-Do application enables users to add and keep track the status of their tasks by due date, category, priority, and completion status. The application stores all its tasks in "todos.csv". The CSV file is a plain text file, with its first line as the headers for each column. The task data is organized into columns separated by a comma.<br />
<img width="856" alt="Screen Shot 2021-04-30 at 3 04 17 PM" src="https://user-images.githubusercontent.com/82434097/116758634-571b7200-a9c5-11eb-8f21-24dde86c6bd0.png"><br />
The system allows the user to do the following and updates the CSV file accordingly:
* Add a new task to the To-Do list. User can specify various optional description about the task such as status of completion, due date, priority (1 - 3, 1 being the highest), and category.
* Complete an existing task.
* Display current To-Do's to terminal. Options include all To-Do's, filter incomplete To-Do's, filter To-Do's within particular category, sort by date (ascending), and sort by priority (ascending).

### Design
In our program, MVC design pattern was implemented to segregate the views from the model and controllers. MVC design allows to create separate and reusable models that can easily be updated; for example, updating the controller data affecting other packages.

Singleton pattern was also applied in the ToDoList class to have single instance of To-Do List to all clients at a time. This ensures there’s only one List of ToDo objects at all times for the program. Builder pattern was applied in ToDo class to simplify the construction of a To-Do object with various fields.

### Commands
The program will accept the following commands:<br />
* ```--csv-file <path/to/file>```: Required command. Indicates CSV file containing the To-Do's.<br />
<br />*Commands to add a new task*
* ```--add-todo```: Adds new a task. Must immediately be followed by ```--todo-text <description of To-Do>```.
* ```--todo-text <description of To-Do>```: (Optional) A description of To-Do.
* ```--completed```: (Optional) When adding a new task, sets the completed status of a new To-Do to true.
* ```--due <due date (MM/DD/YYYY)>```: (Optional) When adding a new task, sets the due date of the new To-Do.
* ```--category <category name>```: (Optional) When adding a new task, sets the category of the new To-Do.
* ```--complete-todo <id>```: (Optional) Mark the Todo with the provided ID as complete.<br />
  <br />*Commands for display options*
* ```--display```: Displays all current To-Do's, both completed and incomplete.
* ```--show-incomplete```: Displays all incomplete To-Do's.
* ```--show-category <category>```: Displays To-Do's with specified category.
* ```--sort-by-date```: Displays To-Do's sorted by ascending date order. Cannot be combined with --sort-by-priority.
* ```--sort-by-priority```: Displays To-Do's sorted by priority. Cannot be combined with --sort-by-date.

### Program Demonstration
Following command will be demonstrated: <br />
```--csv-file todos.csv --add-todo --todo-text "NEW TASK DEMO" --due 4/30/2021 --category home --complete-todo 1 --sort-by-date --display```<br />
![ezgif com-gif-maker](https://user-images.githubusercontent.com/82434097/116769345-4b917080-a9f0-11eb-95a7-174b75bf8f30.gif)
