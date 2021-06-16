package com.sec.schedule.interpreter;

public class InterpreterFactory {
    public static Interpreter interpreter = new Interpreter();
    public static Interpreter getInterpreterDefault() {
        return interpreter;
    }
}
