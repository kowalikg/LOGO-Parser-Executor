package pl.edu.agh.to2.turtle.controller;

public class Procedure {
    private String name;
    private String code;
    private int numberOfArgs;

    public Procedure(String name, String code, int numberOfArgs) {
        this.name = name;
        this.code = code;
        this.numberOfArgs = numberOfArgs;
    }

    public String getCode() {
        return code;
    }

    public int getNumberOfArgs() {
        return numberOfArgs;
    }
}
