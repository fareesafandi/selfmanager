package com.dev.selfmanager.controller;

import com.dev.selfmanager.model.TaskSQLITERepository;
import com.dev.selfmanager.model.TaskEntity;
import com.dev.selfmanager.model.TaskListEntity;
import com.dev.selfmanager.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

/*Task Controller Class 
 * - Controller class is in the presentation layer of systems?
 * - Act as a "control"/"operation" for users: anything that users can do class
 */

public class TaskController {
   
    private TaskSQLITERepository repository;
    private ArrayList<TaskEntity> taskCollection;
    private TaskEntity task;

    public TaskController() {

        //Task repository
        repository = new TaskSQLITERepository();

        //Invoke method to retrieve all task collection.
        taskCollection = repository.getAllTasks();
    }

    public void createTask(String taskTitle, String taskDescription, LocalDate taskDate, LocalTime taskTime) {
        
        //Assign UUID
        UUID id = UUID.randomUUID();

        //Date Time of task creation 
        LocalDateTime dateTimeCreated = LocalDateTime.now();

        //Initial value.
        TaskStatus taskStatus = TaskStatus.PENDING;
        
        //Data validation, prevent null values.
        if(taskTitle == null) {
            taskTitle = "No Title";
        }
        
        if(taskDescription == null) {
            taskDescription = "No Description"; 
        }

        if(taskDate == null) {
            taskDate = LocalDate.of(0, 0, 0); //Default values, represent empty date. 
        }

        if(taskTime == null) {
            taskTime = LocalTime.of(0, 0, 0); //Default values, represent empty time.
        }

        this.task = new TaskEntity(id, taskTitle, taskDescription, dateTimeCreated, taskStatus, taskDate, taskTime);

        //Add task to current instance of task collection.
        taskCollection.add(task);

        //Save task to database.
        repository.insertTask(task);
        repository.save();

    }

    public void updateByTaskName(String taskName, String newTaskTitle, String newTaskDescription) {
        
    }

    public void deleteTaskByName(String taskName) {

    }

    //find task by full name and return the task instance matched. 
    public TaskEntity findTaskByName(String taskTitle) {
        /*Assist in viewing the list of task  
         * - To have the program return a list of task by category/day
         * use TaskList instead.
        */

        if(taskTitle == null || taskTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Task Title is null");
        }

        if(taskCollection == null) {
            throw new IllegalArgumentException("Tasks failed to fetch from database");
        }

        for(TaskEntity task : taskCollection) {
            if(task.getTaskTitle().equals(taskTitle)) {
                return task;
            } 
        }

        throw new NoSuchElementException("Task with name " + taskTitle + " not found");
    }

    public ArrayList<TaskEntity> getAllTasks() {
        return taskCollection;
    }
}
