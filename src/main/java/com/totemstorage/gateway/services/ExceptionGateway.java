package com.totemstorage.gateway.services;


public class ExceptionGateway extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ExceptionGateway (String msg){
        super(msg);
    }
    
}
