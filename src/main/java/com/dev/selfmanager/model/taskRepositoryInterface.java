package com.dev.selfmanager.model;

import java.util.UUID;

/*Repository Interface Class
 * - Define the standard method for database Access in repository class
 * - Work as an abstraction layer between the entity and the data
 * - The data that are read/write should be mapped from/to entity models 
 * - Transactions will differ by methods. 
 */

public interface taskRepositoryInterface {
    
    //------Whole Object Manipulation
    /*- Commit the change to the whole object in database
     */

    public void insertTask(TaskEntity task);
   
    // public void updateTask(UUID taskID);

    // public void deleteTask(UUID taskID); 

    public void save();

    //------Search data methods
    /*- Method for searching for an object instance from a certain attribute
     */
    // public TaskEntity getTaskById(UUID taskID); 


} 