package com.dev.selfmanager;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime; 
import java.util.ArrayList;

import java.lang.IllegalArgumentException; 
import java.util.NoSuchElementException;

import com.dev.selfmanager.controller.TaskController;
import com.dev.selfmanager.model.TaskEntity;
import com.dev.selfmanager.model.TaskStatus;
import com.dev.selfmanager.util.Debugger;

public class App
{

    private static TaskController taskControl;
    public static void main( String[] args )
    {
        //Test Code 
        taskControl = new TaskController();
        Scanner in = new Scanner(System.in); 
        String choice = null;

        int session = 1;

        while(session != 0) {

            System.out.print("Select your option: \nc - create new task\nf - find task\nl - list all tasks\nu - update task\nq - exit\n");
            choice = in.nextLine(); 

            switch(choice) {
                case "c":
                    createTask(); 
                    break;
                case "f":
                    findTask();
                    break;
                case "l":
                    listAllTask();
                    break;
                case "u":
                    updateTask();
                    break;
                case "q":
                    session = 0;
                    System.out.println("Exiting...");
                default:
                    System.out.println("Invalid option, try again.");
                    break;
            }
        }
    }

    public static void createTask() {

        Scanner userIn = new Scanner(System.in); 

        System.out.println("Enter detail to create task: ");

        System.out.print("Task Name: ");
        String taskTitle = userIn.nextLine(); 
        System.out.print("Task Description: ");
        String taskDesc = userIn.nextLine(); 

        //UTC time, not local.
        LocalDate taskDate = LocalDate.now(); 
        LocalTime taskTime = LocalTime.now(); 
        
        System.out.println("NOTE: Date and Time of the task is the current time and date.");

        taskControl.createTask(taskTitle, taskDesc, taskDate, taskTime);
        System.out.println("Task Successfully created.");
    }

    public static void findTask() {
       
        Scanner userIn = new Scanner(System.in); 
        System.out.print("Task name to find: ");
        String taskName = userIn.nextLine();

        try {
            System.out.println(taskControl.findTaskByName(taskName).toString());

        } catch(IllegalArgumentException e) {
            System.out.println("Error finding task: " + e.getMessage());
        } catch(NoSuchElementException e) {
            System.out.println("Error loading task collection: " + e.getMessage());
        }

    }

    public static void listAllTask() {

        for(TaskEntity taskCollection : taskControl.getAllTasks()) {
            System.out.println(taskCollection.toString());
        }
    }

    public static void updateTask() {

        Scanner userIn = new Scanner(System.in);
        String choice = null;
        boolean valid = true;

        while(valid) {

            try {
                
                System.out.print("Write the task name to update: ");
                String taskName = userIn.nextLine(); 

                //debug 
                Debugger.showString(taskName);

                System.out.println("Choose what to update: \ns - task status");
                choice = userIn.nextLine();
                
                TaskEntity updatedTask = taskControl.findTaskByName(taskName);
                System.out.println("Current data: \n" + updatedTask.toString());
                switch (choice) {
                    case "s":
                        boolean validStatus = true; 
                        while(validStatus) {
                            System.out.println("Current status: " + updatedTask.getTaskStatus());
                            System.out.print("Please enter a new valid status: \n1 - Completed\n2 - Pending\n3- Cancelled\n>");
                            int status = userIn.nextInt();
                            switch (status) {
                                case 1: 
                                    taskControl.updateTaskStatus(updatedTask, TaskStatus.COMPLETED); 
                                    validStatus = false;
                                    break;
                                case 2: 
                                    taskControl.updateTaskStatus(updatedTask, TaskStatus.PENDING);
                                    validStatus = false;
                                    break;
                                case 3: 
                                    taskControl.updateTaskStatus(updatedTask, TaskStatus.CANCELLED);
                                    validStatus = false;
                                default:
                                    System.out.println("Invalid task status... please try again.");
                                    break;
                            }

                            //interface response
                            System.out.println("Task Successfully updated.");

                            //Show updated data.
                            System.out.println("Updated data: " + taskControl.findTaskByName(taskName).toString());
                        }
                        break;
                
                    default:
                        System.out.println("Invalid choice...");
                        valid = true;
                        break;
                }
            } catch (NoSuchElementException | IllegalArgumentException e) {
                System.out.println("Error :" + e.getMessage());
            }
        }
    }
}
