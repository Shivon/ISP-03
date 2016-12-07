import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by murat on 30.11.16.
 */
public class Algorithm {

    private static List<State> closeList;
    private static AlgorithmType algorithmType;
    private static int depth;
    private static State startState;
    public static int stateCheck = 0;
    public static long timeEnd;

    public static Path generic_search(State startingState, State goalState, AlgorithmType type) throws Exception {
        startState = startingState;
        closeList = new ArrayList<>();
        closeList.add(startState);
        algorithmType = type;
        depth = 1;
        MyTree tree = new MyTree();
        Path startPath = new Path();
        startState.calculateHeurisitc(goalState);
        startPath.put(startState);
        tree.putAtFront(startPath);

        boolean success = false;
        int rotations = 0;
        Path path;
        long timeStart = System.currentTimeMillis();
        do{

            path = tree.getFirstPath();
            success = goal(path.getFirstState(), goalState);
            closeList.add(path.getFirstState());


            // Zeigt den Suchbaum an
            //System.out.println(path.getFirstState().showState(rotations));

            rotations++;
            if (!success) {
                List<State> children = expand(path.getFirstState(), goalState);
                List<Path> newpaths = generate_new_paths(children, path);

                insertNewPaths(tree,newpaths, path.getFirstState(), goalState);
            }

        }while (!success);
        timeEnd = System.currentTimeMillis() - timeStart;
        return path;
    }

    private static void insertNewPaths(MyTree openList, List<Path> newpaths, State actualState, State goalState) throws Exception {

        Path worstPath = null;
        List<Path> newPathTmp = new ArrayList<>(newpaths);
        openList.deleteFirstPath();


        switch (algorithmType) {
            case Depthsearch:
                for (Path p : newpaths) {
                    openList.putAtFront(p);
                }
                break;
            case Iterativ_Depthsearch:
                for (Path p : newpaths) {
                    if (p.size() <= depth + 1) { // +1, weil elternknoten auch im pfad enthalten ist
                        openList.putAtFront(p);
                    }
                }

                if (openList.isEmpty()) {
                    depth++;
                    Path startPath = new Path();
                    startPath.put(startState);
                    openList.putAtFront(startPath);
                    closeList.clear();
                }
                break;
            case BergsteigenBacktracking:
                while (!newPathTmp.isEmpty()) {
                    worstPath = getWorstPath(newPathTmp);
                    openList.putAtFront(worstPath);
                }
                break;
            case Hill_Climbing:
                while (!newPathTmp.isEmpty()) {
                    worstPath = getWorstPath(newPathTmp);
                    openList.putAtFront(worstPath);
                }

                //lokales maximum erreicht, zufälliges springen
                if (openList.getFirstPath().getFirstState().getHeuristic() >= actualState.getHeuristic()) {
                    int rand = ThreadLocalRandom.current().nextInt(0, openList.size());
                    openList.putExistingPathToFront(openList.paths().get(rand));
                }
                break;
            case A_Star:
                while (!newPathTmp.isEmpty()) {
                    //Findet den schlechtesten Kindknoten um ihn anschließend an die richtige position in der openlist zu legen
                    worstPath = getWorstPath(newPathTmp);
                    int index = -1;
                    boolean statesAreEqual = false;
                    for (Path p : openList.paths()) {
                        //Vergleich gefunden Zustand mit den bereits in der openlist enthaltenen Zuständen
                        if (!worstPath.getFirstState().equals(p.getFirstState())) {
                            if (worstPath.getFirstState().getHeuristic() <= p.getFirstState().getHeuristic()) {
                                index = openList.paths().indexOf(p);
                                break;
                            }


                        } else {
                            statesAreEqual = true;
                            //else -> nicht notwendig, weil wenn beide Zustände gleich sind, impliziert es, dass der bereits in der openlist
                            // enthaltene pfad der bessere ist. Der nicht enthaltene pfad wird nicht in die openlist genommen.
                        }

                    }

                    if (!statesAreEqual) {
                        if (index == -1) {
                            openList.putAtEnd(worstPath);
                        } else {
                            openList.putAt(worstPath, index);
                        }
                    }
                }
                break;
            case Gierige_Bestensuche:
                while (!newPathTmp.isEmpty()) {
                    //Findet den schlechtesten Kindknoten um ihn anschließend an die richtige position in der openlist zu legen
                    worstPath = getWorstPath(newPathTmp);
                    int index = -1;
                    boolean statesAreEqual = false;
                    for (Path p : openList.paths()) {
                        //Gleich wie A-Stern nur das bisherige Kosten nicht betrachtet werden. D.h. Alle neuen Pfade werden
                        //in die openlist gelegt.
                            if (worstPath.getFirstState().getHeuristic() <= p.getFirstState().getHeuristic()) {
                                index = openList.paths().indexOf(p);
                                break;
                            }
                    }

                    if (index == -1) {
                        openList.putAtEnd(worstPath);
                    } else {
                        openList.putAt(worstPath, index);
                    }
                }
                break;

        }

    }

    private static Path getWorstPath(List<Path> newPathTmp) {
        Path worstPath = null;
        int heuristic = Integer.MIN_VALUE;
        for (Path p : newPathTmp) {

            if (p.getFirstState().getHeuristic() >= heuristic) {

                worstPath = p;
                heuristic = p.getFirstState().getHeuristic();
            }
        }
        newPathTmp.remove(worstPath);
        return worstPath;
    }


    private static List<Path> generate_new_paths(List<State> children, Path path) {
        List<Path> newPaths = new ArrayList<>();
        Path[] paths = new Path[children.size()];
        int i = 0;
        for (State state : children) {
            paths[i] = path.copy();
            paths[i].put(state);
            newPaths.add(paths[i]);
        }
        return newPaths;
    }

    private static List<State> expand(State actualState, State goalState) throws Exception {
        List<State> children = new ArrayList<>();

        Iterator iterator = actualState.getBlockIterator();



        while (iterator.hasNext()) {
            Block b = (Block) iterator.next();

            if (b.isClear()) {
                if (!b.isOnTable()) {
                    State z = actualState.put_block_on_table(b.get_name());
                    if (!closeListContains(z)) {
                        z.setDepth(actualState.getDepth());
                        if (algorithmType != AlgorithmType.Depthsearch) z.calculateHeurisitc(goalState);
                        children.add(z);
                    }
                }
                for (String name : actualState.getBlockNames()) {
                    if (!b.get_name().equals(name)) {
                        if (!actualState.getBlock(b.get_name()).blockUnder().equals(name)) {
                            if (actualState.getBlock(name).isClear()) {
                                State z = actualState.put_block_on_block(b.get_name(), name);
                                if (!closeListContains(z)) {
                                    z.setDepth(actualState.getDepth());
                                    if (algorithmType != AlgorithmType.Depthsearch) z.calculateHeurisitc(goalState);
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
        for (State state : closeList) {
            if (state.equals(z)) return true;
        }

        return false;
    }

    private static boolean goal(State startState, State goalState) {
        stateCheck++;
        if (startState.equals(goalState)) return true;
        return false;
    }

    public enum AlgorithmType{
        Depthsearch,
        A_Star,
        Iterativ_Depthsearch,
        Hill_Climbing,
        Gierige_Bestensuche,
        BergsteigenBacktracking
    }
}
