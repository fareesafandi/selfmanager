package com.dev.selfmanager.model;

import java.util.UUID;
import java.util.ArrayList; 
import java.time.LocalDateTime; 

/*TaskList Entity 
 * - A collection of tasks
 * - grouped category for a list of tasks
 */

public class TaskListEntity {
   
    private UUID id; 
    private String taskListName; 
    private String taskListDescription; 
    private LocalDateTime dateTimeCreated; /* Audit purposes */

    private ArrayList<TaskEntity> taskCollection; 

    public TaskListEntity(String taskListName, String taskListDescription, ArrayList<TaskEntity> taskCollection) {

        this.id = UUID.randomUUID(); 
        this.taskListName = taskListName; 
        this.taskListDescription = taskListDescription; 
        
        this.taskCollection = taskCollection; //Task inside may be empty during creation

        this.dateTimeCreated = LocalDateTime.now(); //Current time of instance creation 
    }

    //-----------------getter and setter--------------------------

    public String getTaskListName() {
        return taskListName; 
    }

    public void setTaskListName(String taskListName) {
        this.taskListName = taskListName;  
    }

    public String getTaskListDescription() {
        return taskListDescription; 
    }

    public void setTaskListDescription(String taskListDescription) {
        this.taskListDescription = taskListDescription; 
    }

    public ArrayList<TaskEntity> getTaskCollection() {
        return taskCollection; 
    }

    public void setTaskCollection(ArrayList<TaskEntity> taskCollection) {
        this.taskCollection = taskCollection; 
    }

    //------------------Standard Entity Methods-------------------------

    @Override
    public String toString() {
        return "Task List Name: " + taskListName +
               "Task List Description: " + taskListDescription + 
               "Task List Collection: " + taskCollection; 
    }
}
