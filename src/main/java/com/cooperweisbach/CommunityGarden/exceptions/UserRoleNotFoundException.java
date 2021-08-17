package com.cooperweisbach.CommunityGarden.exceptions;

public class UserRoleNotFoundException extends Exception{
    public UserRoleNotFoundException() {
        super("The User Role you are searching for does not exist");
    }
}
