package com.smcc.backend_process;

import java.io.*;

public class BackendProcess {

    public static String errorMessage;
    String ID, PASSWORD;
    public static boolean isLogin,isRegistered;


    //Initialize Basic Variables
    public BackendProcess() {
        errorMessage = ID = PASSWORD = "";
        isLogin=isRegistered=false;
    }

}
