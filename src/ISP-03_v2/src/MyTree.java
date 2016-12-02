import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by murat on 30.11.16.
 */
public class MyTree {

    private List<Path> paths;
    int count = 0;
    private boolean empty;

    public MyTree(){
        paths = new ArrayList<>();
    }

    public void putAtEnd(Path path){
        paths.add(count, path);
        count++;
    }

    public void putAtFront(Path path){
        paths.add(0, path);
        count++;
    }

    public void putAt(Path path, int index){
        paths.add(index, path);
        count++;
    }

    public Path getLastPath(){
        return paths.get(count);
    }

    public Path getFirstPath(){
        return paths.get(0);
    }

    public void deleteLastPath(){
        paths.remove(count);
        count--;
    }

    public void deleteFirstPath(){
        paths.remove(0);
        count--;
    }


    public List<Path> paths(){
        return paths;
    }

    public void putExistingPathToFront(Path path){
        int index = paths.indexOf(path);
        paths.remove(index);
        paths.add(0, path);
    }

    public void putExistingPathAt(Path path, int index){
        int indexOld = paths.indexOf(path);
        paths.remove(indexOld);
        paths.add(index, path);
    }

    public boolean isEmpty() {
        return paths.isEmpty();
    }

    public int size() {
        return paths.size();
    }
}
