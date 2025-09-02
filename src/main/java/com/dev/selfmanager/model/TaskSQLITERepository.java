package com.dev.selfmanager.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.ArrayList;

/* /SQLITE Repository Class
 * - For instance persistency can implement Context pattern.
 */
public class TaskSQLITERepository implements taskRepositoryInterface {
   
    // private static taskSQLITERepository instance; //might change the property now that only use by 1 class.
    private Connection connection; 

    //Constant Database link
    private static final String DATABASE_URL = 
    "jdbc:sqlite:/home/farees/DEV/JavaProjects/databases/selfmanagertest.db";

    //Domain object collection
    ArrayList<TaskEntity> allTasks;

    public TaskSQLITERepository() {
        try {
            //Initialization 
            this.connection = DriverManager.getConnection(DATABASE_URL);

            connection.setAutoCommit(false);

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }


    public void insertTask(TaskEntity task) {
        /* - Can the database handle null type? */

        //Prepared statement database transaction
        String insertQuery = 
        "INSERT INTO tasks(t_id, t_title, t_desc, t_date_time_created, t_status, t_date, t_time)" + 
        "VALUES(?, ?, ?, ?, ?, ?, ?);"; 

        //Mapping entity field to database variable.
        //null values may cause NullPointerException
        String t_id = task.getTaskID().toString();
        String t_title = task.getTaskTitle(); 
        String t_desc = task.getTaskDescription(); 
        String t_date_time_created = task.getDateTimeCreated().toString();
        String t_status = task.getTaskStatus().toString(); 
        String t_date = task.getTaskDate().toString(); 
        String t_time = task.getTaskTime().toString();

        try {
            
            //Transaction control, grouping insert as 1 transaction.
            Savepoint insertSave = connection.setSavepoint(); 

            PreparedStatement insertTask = connection.prepareStatement(insertQuery);
            
            insertTask.setString(1, t_id); 
            insertTask.setString(2, t_title);
            insertTask.setString(3, t_desc);
            insertTask.setString(4, t_date_time_created);
            insertTask.setString(5, t_status);
            insertTask.setString(6, t_date);
            insertTask.setString(7, t_time);

            insertTask.executeUpdate();

            //rollback can occur within try block
        } catch (SQLException e) {
            System.out.println("Error Inserting Task data: " + e.getMessage());
        } 
    }

    public void updateTask(TaskEntity updatedTask) {
        /*Method for updating data in table.
         * - The object is already valid, then the only thing to do is 
         * search through database by it's ID
        */

        String t_id = updatedTask.getTaskID().toString(); 

        String updateQuery = 
        "UPDATE tasks" +
        "SET t_title = ?," + // 1 - t_title
        "t_desc = ?," + //2 - t_desc
        "t_status = ?," + //3 - t_status
        "t_date = ?," + //4 - t_date
        "t_time = ?" + //5 - t_time
        "WHERE t_id = ?;"; // 6 - passed t_id 

        try {
            
            PreparedStatement updateTask = connection.prepareStatement(updateQuery); 

            updateTask.setString(1, updatedTask.getTaskTitle());
            updateTask.setString(2, updatedTask.getTaskDescription());
            updateTask.setString(3, updatedTask.getTaskStatus().toString());
            updateTask.setString(4, updatedTask.getTaskDate().toString());
            updateTask.setString(5, updatedTask.getTaskTime().toString());
            updateTask.setString(6, updatedTask.getTaskID().toString());

        } catch (SQLException e) {
            // TODO: handle exception
        }
    }
   
    //Find task by name will return multiple rows of tasks.    
    public ArrayList<TaskEntity> getAllTasks() {
        //Query the database to get the collection of all the tasks.
        /* Assign the task to a TaskEntity arraylist.
         * - Map from: Table Structure -> Entity objects
         * - the method might return an ArrayList of all tasks(NOTE: will cause overhead?)
         * - literally reinitializing all instance of tasks objects.
        */

        allTasks = new ArrayList<>();

        String getTaskQuery = 
        "SELECT * FROM tasks;";

        try {
            
            PreparedStatement getTasks = connection.prepareStatement(getTaskQuery);
            ResultSet taskSet = getTasks.executeQuery();

            //Mapping the data from table to entity by creating new instances.
            while(taskSet.next()) {

                //Conversion to original type.
                UUID id = UUID.fromString(taskSet.getString("t_id"));
                String taskTitle = taskSet.getString("t_title"); 
                String taskDescription = taskSet.getString("t_desc"); 
                LocalDateTime dateTimeCreated = LocalDateTime.parse(taskSet.getString("t_date_time_created"));
                TaskStatus taskStatus = TaskStatus.valueOf(taskSet.getString("t_status")); 
                LocalDate taskDate = LocalDate.parse(taskSet.getString("t_date")); 
                LocalTime taskTime = LocalTime.parse(taskSet.getString("t_time"));

                //Reinstatiation of domain object
                TaskEntity newTask = new TaskEntity(id, taskTitle, taskDescription, dateTimeCreated, taskStatus, taskDate, taskTime);

                allTasks.add(newTask);
            }
            
        } catch (SQLException e) {
            System.out.println("Error task data: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error retrieving UUID data: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date and time data: " + e.getMessage());
        }

        return allTasks;
    }

    public void save() {
        try {
            
            connection.commit();

        } catch (SQLException e) {
            System.out.println("Error committing changes: " + e.getMessage());
        }
    }
}
