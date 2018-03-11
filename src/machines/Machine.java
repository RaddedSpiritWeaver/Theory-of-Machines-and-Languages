package machines;

import utils.ConsoleColors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

// todo use a flagging mechanism to state acceptance
// todo better reporting mechanisms and a better process block
public class Machine {

    private TransitionTable table = null;
    private StaticFileHolder fileHolder = null;
    private String currentProcessingLine = "";

    public Machine(){
        this.fileHolder = new StaticFileHolder();
        this.table = new TransitionTable();
    }


    public void process(){
        for(int j = 0; j < 5; j++){
            try {
                this.table.load(fileHolder.accessTo(0,j));
                BufferedReader reader = new BufferedReader(new FileReader(fileHolder.accessTo(1, j)));
                currentProcessingLine = reader.readLine();
                while (currentProcessingLine != null){
                    System.out.println("Processsing !!!!:::\t" + currentProcessingLine);
                    int initialState = convertToInt(this.table.getInitalStage());
                    String trace = "";
                    trasnsiton(initialState, 0, trace);
                    currentProcessingLine = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int j = 0; j < 5; j++){
            try {
                this.table.load(fileHolder.accessTo(2,j));
                BufferedReader reader = new BufferedReader(new FileReader(fileHolder.accessTo(3, j)));
                currentProcessingLine = reader.readLine();
                while (currentProcessingLine != null){
                    System.out.println("Processsing !!!!:::\t" + currentProcessingLine);
                    int initialState = convertToInt(this.table.getInitalStage());
                    String trace = "";
                    trasnsiton(initialState, 0, trace);
                    currentProcessingLine = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void trasnsiton(int currentState, int index, String trace){ //todo a transiton function that returns a boolean and we check it with that
        if(index  == currentProcessingLine.length()){ // reached the end of the string
//            System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "current statet : " + ConsoleColors.RESET + currentState); // this confirms that current state is true
            String currentStateString = "" + (char)(currentState + 97);
            String[] finals = this.table.getFinalStages();
            boolean isFinal = false;
            for(String s : finals){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "in the for with the string: " + ConsoleColors.RESET + s + " current state string \t" + currentStateString);
                if(s.equals(currentStateString)){
                    System.out.println("YES to " + currentProcessingLine);
                    isFinal = true;
                    break;
                }
            }
            if(!isFinal){
                System.out.println("NO to" + currentProcessingLine);
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "with this trance : : \t" + ConsoleColors.RESET + trace);
            }
            return;
        }
//        System.out.println("INDEEEXX: " + index + "\t\tline len :" + currentProcessingLine.length());
        int currentInput = Integer.parseInt(currentProcessingLine.substring(index, index + 1));
        trace += (char) (currentState + 97);
//        System.out.println("currentstate:"+currentState + "\t currentInput: " + currentInput);
        Vector<String> availableTransitions = this.table.tableHouse(currentState, currentInput);
        if(availableTransitions.isEmpty()){ // if we have no transitions we return
//            System.out.println("broke at: " + (char)(currentState + 97)+ " with this input: " + currentInput + " with this trace:");
//            System.out.println(trace);
            return;
        }
        else{
            for(String s : availableTransitions){
                int nextState = convertToInt(s);
                trasnsiton(nextState, index + 1, trace);
            }
        }
        if(index < currentProcessingLine.length()){
//            System.out.println("NO to" + currentProcessingLine);
        }
    }

    private int convertToInt(String s){
        s = s.toLowerCase(); // just to confirm every thing si in lower case :)
        return s.charAt(0) - 97;
    }

    public static void main(String[] args) {
//        String s = "012345";
//        System.out.println(s.length());
//        System.out.println(s.substring(5, 6));
        new Machine().process();
    }


}