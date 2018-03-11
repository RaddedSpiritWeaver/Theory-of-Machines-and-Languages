package machines;

import java.io.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// todo general refinement needed ...
public class TransitionTable {

    private Vector[][] transitionTable = null;
    private int stateCount = 0, eventCount = 0;
    private String regexForTable = "}(,)?"; // regex to split the table
    private String initalStage = "";
    private String[] finalStages = null;

    public void load(File file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine(); // first line is the states //todo make a function for determining the sizes
            String[] tokens = null;
            line = groupByComma(line);
            tokens = line.split(",");
            stateCount = tokens.length;
            line = reader.readLine(); // the second line is the event line
            line = groupByComma(line);
            tokens = line.split(",");
            eventCount = tokens.length;
            transitionTable = new Vector[stateCount][eventCount];
            this.vectorInit();
            for(int i = 0; i < stateCount; i++){
                line = reader.readLine();
//                System.out.println(" this is table line NO." + i + "\t" + line);
                tokens = line.split(regexForTable);
                for(int j = 0; j < eventCount; j++){
                    String token = tokens[j];
                    token = groupByComma(token);
//                    System.out.println("grouped token + token number: " + token + "\t" + j);
                    if(!token.equals("")){
                        String[] moveables = token.split(",");
                        for(String s : moveables)
                            transitionTable[i][j].addElement(s);
                    }
                }
            }
            line = reader.readLine();
            line = groupByComma(line);
            initalStage = line;
            line = reader.readLine();
            line = groupByComma(line);
//            System.out.println("final line: " + line);
            finalStages = line.split(",");
            printTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void vectorInit(){
        for (int i = 0; i < stateCount; i++)
            for(int j = 0; j < eventCount; j++)
                transitionTable[i][j] = new Vector<String>();
    }

    private String groupByComma(String s){
        String regex = "(([a-z0-9]+,?)+)"; // the regex to group words and numbers with commas
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        if(m.find()){
            int start = m.start();
            int end = m.end();
            String out = s.substring(start, end);
//            System.out.println("found and group to make this string: " + out);
            return out;
        }
        return "";
    }

    private void printTable(){
        for (int i = 0; i < stateCount; i++){
            for(int j = 0; j < eventCount; j++){
                System.out.print("{");
                for(int z = 0; z < transitionTable[i][j].size(); z++)
                    System.out.print("," + transitionTable[i][j].elementAt(z));
                System.out.print("}\t");
            }
            System.out.println();
        }
        System.out.println("initial:" + this.initalStage);
        System.out.println("final:");
        for(int i = 0; i < this.finalStages.length; i++){
            System.out.print(this.finalStages[i] + ",");
        }
        System.out.println();
    }

    // getters  :
    public Vector[][] getTransitionTable() {
        return transitionTable;
    }

    public int getStateCount() {
        return stateCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    public String getInitalStage() {
        return initalStage;
    }

    public String[] getFinalStages() {
        return finalStages;
    }

    public Vector<String> tableHouse(int i, int j){
        try{
            return transitionTable[i][j];
        }catch (IndexOutOfBoundsException e){
            System.err.println("out of transition table bounds");
        }
        return null;
    }

    public static void main(String[] args) {
        new TransitionTable().load(new File("NFA/Fifth_NFA.txt"));
    }

}
