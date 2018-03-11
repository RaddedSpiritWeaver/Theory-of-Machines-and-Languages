package machines;

import java.io.File;
import java.util.logging.FileHandler;

public class StaticFileHolder {

    private File[][] tablesAndTests = new File[4][5];

    public StaticFileHolder(){
        try{
            // the DFAs:
            tablesAndTests[0][0] = new File("DFA/First_DFA.txt");
            tablesAndTests[0][1] = new File("DFA/Second_DFA.txt");
            tablesAndTests[0][2] = new File("DFA/Third_DFA.txt");
            tablesAndTests[0][3] = new File("DFA/Fourth_DFA.txt");
            tablesAndTests[0][4] = new File("DFA/Fifth_DFA.txt");
            // tests for DFAs :
            tablesAndTests[1][0] = new File("DFA/Strings_for_first_DFA.txt");
            tablesAndTests[1][1] = new File("DFA/Strings_for_second_DFA.txt");
            tablesAndTests[1][2] = new File("DFA/Strings_for_third_DFA.txt");
            tablesAndTests[1][3] = new File("DFA/Strings_for_fourth_DFA.txt");
            tablesAndTests[1][4] = new File("DFA/Strings_for_fifth_DFA.txt");
            // The NDFAs:
            tablesAndTests[2][0] = new File("NFA/First_NFA.txt");
            tablesAndTests[2][1] = new File("NFA/Second_NFA.txt");
            tablesAndTests[2][2] = new File("NFA/Third_NFA.txt");
            tablesAndTests[2][3] = new File("NFA/Fourth_NFA.txt");
            tablesAndTests[2][4] = new File("NFA/Fifth_NFA.txt");
            // tests for DFAs :
            tablesAndTests[3][0] = new File("NFA/Strings_for_first_NFA.txt");
            tablesAndTests[3][1] = new File("NFA/Strings_for_second_NFA.txt");
            tablesAndTests[3][2] = new File("NFA/Strings_for_third_NFA.txt");
            tablesAndTests[3][3] = new File("NFA/Strings_for_fourth_NFA.txt");
            tablesAndTests[3][4] = new File("NFA/Strings_for_fifth_NFA.txt");

        }catch (Exception e){
            System.err.println("EXCEPTION");
        }
    }

    public File accessTo(int i, int j){
        try{
            return tablesAndTests[i][j];
        }catch (IndexOutOfBoundsException e){
            System.err.println("out of bounds exception");
        }
        return null;
    }

}
