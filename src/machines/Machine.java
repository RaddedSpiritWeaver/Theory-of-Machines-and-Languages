package machines;

import utils.ConsoleColors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

// todo use a flagging mechanism to state acceptance.
// todo add a file writing system.
// todo better reporting mechanisms and a better process block.
public class Machine {

    private TransitionTable table = null;
    private StaticFileHolder fileHolder = null;
    private String currentProcessingLine = ""; //todo try to make this not a class filed but something that is passed between calls


    public Machine(){
        this.fileHolder = new StaticFileHolder();
        this.table = new TransitionTable();
    }


    public void process(int s){
        for(int j = 0; j < 5; j++){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Machine Number:" + (j + 1) + ConsoleColors.RESET);
            try {
                this.table.load(fileHolder.accessTo(2 * s,j));
                BufferedReader reader = new BufferedReader(new FileReader(fileHolder.accessTo(2 * s + 1, j)));
                currentProcessingLine = reader.readLine();
                while (currentProcessingLine != null){
//                    System.out.println("Processing:\t" + currentProcessingLine);
                    int initialState = convertToInt(this.table.getInitalStage());
                    String trace = "";
                    boolean acceptance = transition(initialState, 0, trace);
                    checkIfAccepted(acceptance, currentProcessingLine);
                    currentProcessingLine = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean transition(int currentState, int index, String trace){ //todo a transition function that returns a boolean and we check it with that
        if(index  == currentProcessingLine.length()){ // reached the end of the string
//            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "current statet : " + ConsoleColors.RESET + currentState); // this confirms that current state is true
            String currentStateString = "" + (char)(currentState + 97);
            String[] finals = this.table.getFinalStages();
            for(String s : finals){
//                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "in the for with the string: " + ConsoleColors.RESET + s + " current state string \t" + currentStateString);
                if(s.equals(currentStateString)){
//                    System.out.println("YES to " + currentProcessingLine);
                    return true;
                }
            }
            return false;
        }
//        System.out.println("INDEEEXX: " + index + "\t\tline len :" + currentProcessingLine.length());
        int currentInput = Integer.parseInt(currentProcessingLine.substring(index, index + 1));
        trace += (char) (currentState + 97);
//        System.out.println("currentstate:"+currentState + "\t currentInput: " + currentInput);
        Vector<String> availableTransitions = this.table.tableHouse(currentState, currentInput);
        if(availableTransitions.isEmpty()){ // if we have no transitions we return
//            System.out.println("broke at: " + (char)(currentState + 97)+ " with this input: " + currentInput + " with this trace:");
//            System.out.println(trace);
            return false;
        }
        else{
            for(int i = 0; i < availableTransitions.size(); i++){
                String s = availableTransitions.elementAt(i);
                int nextState = convertToInt(s);
                boolean transitionWorked = transition(nextState, index + 1, trace);
                if(transitionWorked)
                    return true;
            }
        }
        return false;
    }

    private void checkIfAccepted(boolean acceptance, String line){
        if(acceptance)
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "ACCEPTED : " + ConsoleColors.RESET + line);
        else
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "NOT ACCEPTED : " + ConsoleColors.RESET + line);
    }

    private int convertToInt(String s){
        s = s.toLowerCase(); // just to confirm every thing is in lower case :)
        return s.charAt(0) - 97;
    }

    public static void main(String[] args) {
        new Machine().process(0);
        System.out.println(ConsoleColors.PURPLE_BACKGROUND +  "\n\n" + ConsoleColors.RESET);
        new Machine().process(1);
    }


}