package com.dev.selfmanager.model; 

import java.time.LocalDateTime;
import java.time.LocalDate; 
import java.time.LocalTime; 
import java.util.UUID;

public class TaskEntity {
    /*TODO: Time based field for Task date and time */ 

    private UUID id; /*Auto - Assigned */ 
    private String taskTitle;
    private String taskDescription;  
    private LocalDateTime dateTimeCreated; /*Method assigned */
    private TaskStatus taskStatus; /*default: PENDING */   

    //Time and Date for task to assign in a day. 
    private LocalDate taskDate; 
    private LocalTime taskTime; 
    
    //Consideration for Soft-Delete mechanism
    // private boolean isDeleted; 
  
    //Any VALUES should be assign by CONTROL class.
    public TaskEntity(UUID id, String taskTitle, String taskDescription, LocalDateTime dateTimeCreated, TaskStatus taskStatus, LocalDate taskDate, LocalTime taskTime) {
       this.id = id;  
       this.taskTitle = taskTitle; 
       this.taskDescription = taskDescription; 
       this.dateTimeCreated = dateTimeCreated; //UTC time, only within system concern 
       this.taskStatus = taskStatus; 
       this.taskDate = taskDate; 
       this.taskTime = taskTime; 
    }

    //-----------------------Getter and Setter-----------------------------

    public UUID getTaskID() {
        return id;
    }

    public String getTaskTitle() {
        return taskTitle; 
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle; 
    }

    public String getTaskDescription() {
        return taskDescription; 
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription; 
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus; 
    }

    public void setTaskStatus(TaskStatus taskStatus) {
       this.taskStatus = taskStatus;  
    }

    public LocalDate getTaskDate() {
        return taskDate; 
    }

    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;  
    }

    public LocalTime getTaskTime() {
        return taskTime; 
    }

    public void setTaskTime(LocalTime taskTime) {
        this.taskTime = taskTime; 
    }
 
    //-----------------------------Standard Entity Methods----------------------------------

    @Override
    public String toString() {
        return "\nID: " + id +
               "\nTask Title: " + taskTitle +
               "\nTask Description: " + taskDescription +
               "\nTask Date-Time Created: " + dateTimeCreated.toString() +
               "\nTask Status: " + taskStatus; 
    }
}
