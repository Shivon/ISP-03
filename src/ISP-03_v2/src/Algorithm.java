import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by murat on 30.11.16.
 */
public class Algorithm {

    private static List<State> closeList;
    public static void generic_search(State startState, State goalState, AlgorithmType type) throws Exception {
        closeList = new ArrayList<>();
        closeList.add(startState);

        MyTree tree = new MyTree();
        Path startPath = new Path();
        startPath.put(startState);
        tree.putAtFront(startPath);

        boolean success = false;
        int rotations = 0;
        do{
            Path path = tree.getFirstPath();
            success = goal(path.getFirstState(), goalState);
            closeList.add(path.getFirstState());
            System.out.println(path.getFirstState().showState(rotations));
            rotations++;
            if(!success){
                List<State> children = expand(path.getFirstState(), goalState);
                List<Path> newpaths = generate_new_paths(children, path);

                insertNewPaths(tree,newpaths, type);
            }

        }while(!success);
    }

    private static void insertNewPaths(MyTree tree, List<Path> newpaths, AlgorithmType type) {

        switch (type){
            case Depthsearch:
                tree.deleteFirstPath();
                for(Path p : newpaths){
                    tree.putAtFront(p);
                }
                break;
            case Heuristic:
                List<Path> newPathTmp = new ArrayList<>(newpaths);
                tree.deleteFirstPath();
                int score = Integer.MAX_VALUE;
                Path worstPath = null;
                while(!newPathTmp.isEmpty()){
                    for(Path p : newPathTmp){
                        if(p.getFirstState().getHeurisitcScore() <= score){
                            worstPath = p;
                            score = p.getFirstState().getHeurisitcScore();
                        }
                    }
                    tree.putAtFront(worstPath);
                    newPathTmp.remove(worstPath);
                    score = Integer.MAX_VALUE;
                }
                break;
        }

    }

    private static List<Path> generate_new_paths(List<State> children, Path path) {
        List<Path> newPaths = new ArrayList<>();
        Path[] paths = new Path[children.size()];
        int i = 0;
        for(State state : children){
            paths[i] = path.copy();
            paths[i].put(state);
            newPaths.add(paths[i]);
        }
        return newPaths;
    }

    private static List<State> expand(State actualState, State goalState) throws Exception {
        List<State> children = new ArrayList<>();

        Iterator iterator = actualState.getBlockIterator();

        String[] blockNames = new String[4];

        blockNames[0] = "block1";
        blockNames[1] = "block2";
        blockNames[2] = "block3";
        blockNames[3] = "block4";

        while(iterator.hasNext()){
            Block b = (Block) iterator.next();

            if(b.isClear()){
                if(!b.isOnTable()){
                    State z = actualState.put_block_on_table(b.get_name());
                    if(!closeListContains(z)){
                        z.calculateHeurisitc(goalState);
                        children.add(z);
                    }
                }
                for(String name : blockNames){
                    if(!b.get_name().equals(name)){
                        if(!actualState.getBlock(b.get_name()).blockUnder().equals(name)){
                            if(actualState.getBlock(name).isClear()){
                                State z = actualState.put_block_on_block(b.get_name(), name);
                                if(!closeListContains(z)){
                                    z.calculateHeurisitc(goalState);
                                    children.add(z);
                                }
                            }
                        }

                    }
                }
            }
        }

        return children;
    }

    private static boolean closeListContains(State z) {
        for(State state : closeList){
            if(state.equals(z)) return true;
        }

        return false;
    }

    private static boolean goal(State startState, State goalState) {
        if(startState.equals(goalState)) return true;
        return false;
    }

    public enum AlgorithmType{
        Depthsearch,
        Heuristic
    }
}
