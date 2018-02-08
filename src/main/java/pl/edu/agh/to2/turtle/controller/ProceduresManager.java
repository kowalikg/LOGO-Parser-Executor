package pl.edu.agh.to2.turtle.controller;

import java.util.HashMap;
import java.util.Map;

public class ProceduresManager {

    private Map<String,Procedure> procedures;
    private boolean firstProcedure;
    private boolean setProcedureInLastBatch;

    ProceduresManager(){
        procedures = new HashMap<>();
        firstProcedure = true;
        setProcedureInLastBatch = false;
    }

    String addProcedure(String mainPattern, String procedureName, String procedureArgs, String code) {
        String[] args = procedureArgs.split("\\s*,\\s*");

        for(int i = 0; i < args.length ; i++){
            args[i] = args[i].trim();
        }

        code = code.replaceAll("\\{"," ");
        code = code.replaceAll("}"," ");
        for(int i = 1; i <= args.length ; i++){
            code = code.replaceAll("(\\s+)" + args[i - 1] + "(\\s+)"," :" + i + " ");
        }

        String regex = String.format("(%s)", procedureName);
        for (int i = 0 ; i < args.length ; i++) {
            regex = regex + "\\s+(\\d+)";
        }
        if(firstProcedure){
            mainPattern = mainPattern + "|";
            firstProcedure = false;
        }
        mainPattern = mainPattern + "(" + regex + ")|";
        setProcedureInLastBatch = true;

        Procedure newProcedure = new Procedure(procedureName, code, args.length);
        procedures.put(procedureName, newProcedure);
        return mainPattern;
    }

    Procedure getProcedure(String name){
        return procedures.get(name);
    }


    public Map<String, Procedure> getProcedures(){
        return procedures;
    }

    public boolean isSetProcedureInLastBatch() {
        return setProcedureInLastBatch;
    }

    public void setSetProcedureInLastBatch(boolean setProcedureInLastBatch) {
        this.setProcedureInLastBatch = setProcedureInLastBatch;
    }
}


