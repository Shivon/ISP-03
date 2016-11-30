import java.util.ArrayList;
import java.util.List;

/**
 * Created by murat on 30.11.16.
 */
public class MyTree {

    private List<Path> paths;
    int count = 0;

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


}
