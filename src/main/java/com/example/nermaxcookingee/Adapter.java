package com.example.nermaxcookingee;

public class Adapter {
    public static String convertArrayIntoString(String ... args){
        String string = "";

        for(String s:args){
            string+=(s+'%');
        }

        return string;
    }
}
