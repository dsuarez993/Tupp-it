package com.app.tuppit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by David on 19/9/16.
 */


public class FormValidation {

    /*
    *
    * Funcion que valida el email
    *
     */
    public static boolean isEmailValid (String e_mail){

        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = e_mail;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;

    }

    /*
    *
    * Funcion que valida el password
    *
     */
    public static boolean isPasswordValid (String password){

        //TODO validar password

        /*String regExpn =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        CharSequence inputStr = password;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
            */
        return true;

    }


    /*
    *
    * Funcion que valida el name
    *
     */
    public static boolean isNameValid (String name){

        //TODO validar name
        String regExpn = "^[\\p{L} .'-]+$";

        CharSequence inputStr = name;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;

    }


    /*
    *
    * Funcion que valida el lastname
    *
     */
    public static boolean isLastNameValid (String lastname){

        //TODO validar last name
        String regExpn = "^[\\p{L} .'-]+$";

        CharSequence inputStr = lastname;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;

    }


    /*
    *
    * Funcion que valida el password
    *
     */
    public static boolean arePasswordsEquals (String password, String repassword){

        if(password.equals(repassword))
            return true;
        else
            return false;

    }


}
