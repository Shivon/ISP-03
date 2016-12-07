import java.util.ArrayList;

/**
 * Created by murat on 30.11.16.
 */
public class Main {

    public static void main(String[] args){


        try{
            //runPraktikumBeispiel();
            //runEigenesBeispiel();
            //run5BlockBeispiel();
            run6BlockBeispiel();



        }catch (Exception e){
            System.err.print(e.getMessage());
        }
    }

    private static void run6BlockBeispiel() throws Exception {
        Block b1Start = new Block("block1", true, false, "block2", "");
        Block b2Start = new Block("block2", false, false, "block3", "block1");
        Block b3Start = new Block("block3", false, false, "block4", "block2");
        Block b4Start = new Block("block4", false, true, "", "block3");
        Block b5Start = new Block("block5", true, false, "block6", "");
        Block b6Start = new Block("block6", false, true, "", "block5");

        b1Start.isValid();
        b2Start.isValid();
        b3Start.isValid();
        b4Start.isValid();
        b5Start.isValid();
        b6Start.isValid();

        State startState = new State(b1Start,b2Start,b3Start,b4Start, b5Start, b6Start);
        startState.setAction("start state");

        Block b1Goal = new Block("block1", true, false, "block4", "");
        Block b2Goal = new Block("block2", false, false, "block6", "block4");
        Block b3Goal = new Block("block3", true, false, "block5", "");
        Block b4Goal = new Block("block4", false, false, "block2", "block1");
        Block b5Goal = new Block("block5", false, true, "", "block3");
        Block b6Goal = new Block("block6", false, true, "", "block2");

        b1Goal.isValid();
        b2Goal.isValid();
        b3Goal.isValid();
        b4Goal.isValid();
        b5Goal.isValid();
        b6Goal.isValid();


        State goalState = new State(b1Goal,b2Goal,b3Goal,b4Goal, b5Goal, b6Goal);
        goalState.setAction("goal state");


        Path path = Algorithm.generic_search(startState, goalState, Algorithm.AlgorithmType.Hill_Climbing);

        path.showPath();
        System.out.println("Geprüfte Zustände: " + Algorithm.stateCheck);
        System.out.println("Zeit: " + Algorithm.timeEnd);
    }

    private static void run5BlockBeispiel() throws Exception {
        Block b1Start = new Block("block1", true, false, "block2", "");
        Block b2Start = new Block("block2", false, false, "block3", "block1");
        Block b3Start = new Block("block3", false, false, "block4", "block2");
        Block b4Start = new Block("block4", false, true, "", "block3");
        Block b5Start = new Block("block5", true, true, "", "");

        b1Start.isValid();
        b2Start.isValid();
        b3Start.isValid();
        b4Start.isValid();
        b5Start.isValid();

        State startState = new State(b1Start,b2Start,b3Start,b4Start, b5Start);
        startState.setAction("start state");

        Block b1Goal = new Block("block1", true, false, "block4", "");
        Block b2Goal = new Block("block2", false, true, "", "block4");
        Block b3Goal = new Block("block3", true, false, "block5", "");
        Block b4Goal = new Block("block4", false, false, "block2", "block1");
        Block b5Goal = new Block("block5", false, true, "", "block3");

        b1Goal.isValid();
        b2Goal.isValid();
        b3Goal.isValid();
        b4Goal.isValid();
        b5Goal.isValid();


        State goalState = new State(b1Goal,b2Goal,b3Goal,b4Goal, b5Goal);
        goalState.setAction("goal state");


        Path path = Algorithm.generic_search(startState, goalState, Algorithm.AlgorithmType.Gierige_Bestensuche);

        path.showPath();
        System.out.println("Geprüfte Zustände: " + Algorithm.stateCheck);
        System.out.println("Zeit: " + Algorithm.timeEnd);
    }

    private static void runEigenesBeispiel() throws Exception {
        Block b1Start = new Block("block1", true, false, "block2", "");
        Block b2Start = new Block("block2", false, false, "block3", "block1");
        Block b3Start = new Block("block3", false, false, "block4", "block2");
        Block b4Start = new Block("block4", false, true, "", "block3");

        b1Start.isValid();
        b2Start.isValid();
        b3Start.isValid();
        b4Start.isValid();

        State startState = new State(b1Start,b2Start,b3Start,b4Start);
        startState.setAction("start state");

        Block b1Goal = new Block("block1", true, false, "block4", "");
        Block b2Goal = new Block("block2", false, false, "block3", "block4");
        Block b3Goal = new Block("block3", false, true, "", "block2");
        Block b4Goal = new Block("block4", false, false, "block2", "block1");

        b1Goal.isValid();
        b2Goal.isValid();
        b3Goal.isValid();
        b4Goal.isValid();


        State goalState = new State(b1Goal,b2Goal,b3Goal,b4Goal);
        goalState.setAction("goal state");


        Path path = Algorithm.generic_search(startState, goalState, Algorithm.AlgorithmType.Gierige_Bestensuche);

        path.showPath();
        System.out.println("Geprüfte Zustände: " + Algorithm.stateCheck);
        System.out.println("Zeit: " + Algorithm.timeEnd);
    }

    private static void runPraktikumBeispiel() throws Exception {

        Block b1Start = new Block("block1", false, true, "", "block2");
        Block b2Start = new Block("block2", true, false, "block1", "");
        Block b3Start = new Block("block3", true, true, "", "");
        Block b4Start = new Block("block4", true, true, "", "");

        b1Start.isValid();
        b2Start.isValid();
        b3Start.isValid();
        b4Start.isValid();

        State startState = new State(b1Start,b2Start,b3Start,b4Start);
        startState.setAction("start state");


        Block b1Goal = new Block("block1", true, false, "block4", "");
        Block b2Goal = new Block("block2", false, true, "", "block4");
        Block b3Goal = new Block("block3", true, true, "", "");
        Block b4Goal = new Block("block4", false, false, "block2", "block1");

        b1Goal.isValid();
        b2Goal.isValid();
        b3Goal.isValid();
        b4Goal.isValid();

        State goalState = new State(b1Goal,b2Goal,b3Goal,b4Goal);
        goalState.setAction("goal state");


        Path path = Algorithm.generic_search(startState, goalState, Algorithm.AlgorithmType.Gierige_Bestensuche);

        path.showPath();

        System.out.println("Geprüfte Zustände: " + Algorithm.stateCheck);
        System.out.println("Zeit: " + Algorithm.timeEnd);
    }
}
