/**
 * Created by murat on 30.11.16.
 */
public class Main {

    public static void main(String[] args){


        try{
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


            Algorithm.generic_search(startState, goalState);
        }catch (Exception e){
            System.err.print(e.getMessage());
        }
    }
}
