package pl.edu.agh.to2.turtle.controller;

import pl.edu.agh.to2.turtle.controller.commands.*;
import pl.edu.agh.to2.turtle.model.Tortoise;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private String mainPattern;
    private ProceduresManager procedures;


    public Parser() {
        mainPattern = "((?:np|ws|pw|lw)\\s\\d+)|((powtorz)\\s(\\d+)\\s\\[(.*)\\])|(pdn|ops)\\s*" +
                "|((oto)\\s+(\\w+)\\s+\\((.+)\\)\\s+((\\{.*\\})))";
        procedures = new ProceduresManager();
    }

    public List<Command> makeGroupCommand(String commandString, Tortoise tortoise) {
        Matcher matcher;
        Pattern group = Pattern.compile(mainPattern);
        List<Command> commands = new LinkedList<>();
        matcher = group.matcher(commandString);
        while (matcher.find()) {
            if (matcher.group(3) != null && matcher.group(3).equals("powtorz")) {
                int numOfRepeat = Integer.parseInt(matcher.group(4));
                for (int i = 0; i < numOfRepeat; i++) {
                    commands.addAll(makeGroupCommand(matcher.group(5), tortoise));
                }
            } else if (matcher.group(6) != null
                    && (matcher.group(6).equals("pdn")
                    || matcher.group(6).equals("ops"))) {
                commands.addAll(makeCommand(matcher.group(6), tortoise));
            } else if (matcher.group(8) != null) {
                mainPattern = procedures.addProcedure(mainPattern, matcher.group(9), matcher.group(10), matcher.group(12));
            } else if (matcher.group(1) != null) {
                commands.addAll(makeCommand(matcher.group(1), tortoise));
            } else {
                int currentGroup = 13;

                Procedure[] procs = procedures.getProcedures().values().toArray(new Procedure[0]);
                Integer[] argsNumbers = new Integer[procs.length];
                for (int i = 0; i < procs.length; i++) {
                    argsNumbers[i] = procs[i].getNumberOfArgs();
                }
                for (Integer argsNumber : argsNumbers) {
                    if (matcher.group().length() != 0) {
                        if (matcher.group(currentGroup) != null) {

                            String procedureName = matcher.group(currentGroup + 1);
                            String[] args = new String[argsNumber];
                            for (int j = currentGroup + 2; j < currentGroup + 2 + argsNumber; j++) {
                                args[j - currentGroup - 2] = matcher.group(j);
                            }

                            commands.addAll(compileProcedure(procedureName, args, tortoise));
                            break;
                        }
                        currentGroup += 2 + argsNumber;
                    }
                }
            }
        }
        return commands;
    }

    private List<Command> compileProcedure(String procedureName, String[] args, Tortoise tortoise) {
        String procedureCode = procedures.getProcedure(procedureName).getCode();

        for (int i = 0; i < args.length; i++) {
            procedureCode = procedureCode.replaceAll("(\\s+):" + (i + 1) + "(\\s+)", " " + args[i] + " ");
        }

        return makeGroupCommand(procedureCode, tortoise);
    }


    public List<Command> makeCommand(String commandString, Tortoise tortoise) {
        List<Command> commands = new LinkedList<>();
        Matcher matcher;

        Pattern np = Pattern.compile("np (\\d+)");
        Pattern ws = Pattern.compile("ws (\\d+)");
        Pattern pwp = Pattern.compile("pw (\\d+)");
        Pattern pwm = Pattern.compile("pw -(\\d+)");
        Pattern lwp = Pattern.compile("lw (\\d+)");
        Pattern lwm = Pattern.compile("lw -(\\d+)");
        Pattern pdn = Pattern.compile("pdn");
        Pattern ops = Pattern.compile("ops");

        matcher = np.matcher(commandString);
        if (matcher.find()) {
            commands.add(new NpCommand(Integer.parseInt(matcher.group(1)), tortoise));
        }

        matcher.usePattern(ws);
        if (matcher.find()) {
            commands.add(new WsCommand(Integer.parseInt(matcher.group(1)), tortoise));
        }

        matcher.usePattern(pwp);
        if (matcher.find()) {
            commands.add(new PwCommand(Integer.parseInt(matcher.group(1)), tortoise));
        }

        matcher.usePattern(pwm);
        if (matcher.find()) {
            commands.add(new PwCommand(-Integer.parseInt(matcher.group(1)), tortoise));
        }

        matcher.usePattern(lwp);
        if (matcher.find()) {
            commands.add(new LwCommand(Integer.parseInt(matcher.group(1)), tortoise));
        }

        matcher.usePattern(lwm);
        if (matcher.find()) {
            commands.add(new LwCommand(-Integer.parseInt(matcher.group(1)), tortoise));
        }

        matcher.usePattern(pdn);
        if (matcher.find()) {
            commands.add(new PdnCommand(tortoise));
        }

        matcher.usePattern(ops);
        if (matcher.find()) {
            commands.add(new OpsCommand(tortoise));
        }

        return commands;
    }

    public boolean getAndResetIfSetProcedure() {
        if (procedures.isSetProcedureInLastBatch()) {
            procedures.setSetProcedureInLastBatch(false);
            return true;
        } else {
            return false;
        }
    }

}
