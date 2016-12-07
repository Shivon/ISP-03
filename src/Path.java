import java.util.ArrayList;
import java.util.List;

/**
 * Created by murat on 30.11.16.
 */
public class Path {
    private int size = 0;
    private List<State> states;
    private boolean empty = true;

    public Path() {
        states = new ArrayList<>();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void put(State state) {
        states.add(0, state);
        size++;
        empty = false;
    }

    public int size() {
        return size;
    }

    public State getFirstState() {
        return states.get(0);
    }

    public State getLastState() {
        return states.get(size);
    }

    public Path copy() {
        Path p = new Path();
        p.size = this.size;
        p.empty = this.empty;
        for (State z : states) {
            p.states.add(z);
        }
        return p;
    }

    public void showPath() {
        int rotations = 0;
        for (int i = states.size()-1; i >= 0; i--) {
            System.out.println(states.get(i).showState(rotations));
            rotations++;
        }
    }
}
