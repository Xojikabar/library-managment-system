package com.example.library.security;


public enum UserAuthorities {
    CREATE("CREATE"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    READ("READ");
    String name;


    UserAuthorities(String  name){
        this.name =  name;
    }

    public String getName(){
        return this.name;
    }

}
