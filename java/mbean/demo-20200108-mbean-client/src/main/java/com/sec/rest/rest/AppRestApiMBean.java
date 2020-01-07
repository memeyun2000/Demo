package com.sec.rest.rest;

public interface AppRestApiMBean {
    int getRestExecCount();
    String printHello();
    String printHello(String message);
    void setName(String name);
    String getName();
}
