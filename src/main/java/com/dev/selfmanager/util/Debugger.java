/*Utility class for CLI debugging. */

package com.dev.selfmanager.util;

import java.util.Scanner;
import java.lang.StringBuilder;
import java.sql.PreparedStatement;

public final class Debugger {
    
    private static final String debugMark = "<debug>"; 
    private static final String endMark = "--:";   
    private static final String emptyMark = "--is empty";
    private static final String stringMark = "--String";

    private Debugger() {
    
    }

    public static void showString(String text) {

        // Scanner debugOut = new Scanner(System.in);

        String debugMsg = "<debug>---------------: ";

        if(text.trim().isEmpty()) {
            
            System.out.println(debugMark + stringMark + emptyMark + endMark + text);

        } else {
            System.out.println(debugMark + stringMark + endMark + text);
        }
    }

    public static void showQuery(PreparedStatement queryStatement) {
        
    }
}
