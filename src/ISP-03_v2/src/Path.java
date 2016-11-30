import java.util.ArrayList;
import java.util.List;

/**
 * Created by murat on 30.11.16.
 */
public class Path {
    private int length = 0;
    private List<State> states;
    private boolean empty = true;

    public Path(){
        states = new ArrayList<>();
    }

    public boolean isEmpty(){
        return empty;
    }

    public void put(State state){
        states.add(0, state);
        length++;
        empty = false;
    }

    public int getLength(){
        return length;
    }

    public State getFirstState(){
        return states.get(0);
    }

    public State getLastState(){
        return states.get(length);
    }

    public Path copy(){
        Path p = new Path();
        p.length = this.length;
        p.empty = this.empty;
        for(State z : states){
            p.states.add(z);
        }
        return p;
    }



}
