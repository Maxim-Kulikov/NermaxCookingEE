package com.example.nermaxcookingee;

import java.util.HashMap;

public class ControllerUsers {
    private static ControllerUsers controllerUsers;
    private HashMap<String, UserBeam> users;

    private ControllerUsers(){
        users = new HashMap<>();
    }

    public static synchronized ControllerUsers getController(){
        if(controllerUsers == null)
            controllerUsers = new ControllerUsers();
        return controllerUsers;
    }

    public void setUser(String email, UserBeam userBeam){
        users.put(email, userBeam);
    }

    public UserBeam getUser(String email){
        return users.get(email);
    }

    public HashMap<String, UserBeam> getUsers(){
        return this.users;
    }
}
