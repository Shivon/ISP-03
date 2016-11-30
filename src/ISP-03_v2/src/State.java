import java.util.*;

/**
 * Created by murat on 30.11.16.
 */
public class State implements IZustand{

    private HashMap<String, Block> blocks;
    private List<MyTupel> relations;
    private String action = "";

    private final String TABLE = "table";
    private final String BLOCK_IS_NOT_CLEAR = "blocknotclear";
    private final String STATE_IS_NOT_VALID = "State is not valid";
    private final String ROTATE_ERROR = "Rotate to same block not possible";

    public State(Block b1, Block b2, Block b3, Block b4){


        blocks = new HashMap<>();
        blocks.put(b1.get_name(), new Block(b1));
        blocks.put(b2.get_name(), new Block(b2));
        blocks.put(b3.get_name(), new Block(b3));
        blocks.put(b4.get_name(), new Block(b4));



        relations = new ArrayList<>();

        buildRelations();


    }

    private void buildRelations() {
        relations = new ArrayList<>();
        for(Block b : blocks.values()){
            try {
                b.isValid();
                if(b.isOnTable()) relations.add(new MyTupel(TABLE, b.get_name()));
                if(!b.isClear()) relations.add(new MyTupel(b.get_name(), b.blockOver()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals() {
        return false;
    }

    @Override
    public State copy() throws Exception {
        Block[] blocksArray = new Block[4];
        int i = 0;
        for(Block b : blocks.values()){
            blocksArray[i] = b;
            i++;
        }

        State z =  new State(blocksArray[0], blocksArray[1], blocksArray[2], blocksArray[3]);

        z.isValid();

        return z;
    }

    public State put_block_on_block(String blockToRotate, String onBlock) throws Exception {

        if(blocks.get(blockToRotate).isClear()){
            State newState = this.copy();

            newState.isValid();


            Block blockRotate, newBlockUnderBlockRotate, oldBlockUnderBlockRotate;
            blockRotate = newState.blocks.get(blockToRotate);
            newBlockUnderBlockRotate = newState.blocks.get(onBlock);
            oldBlockUnderBlockRotate = newState.blocks.get(blockRotate.blockUnder());

            if(newBlockUnderBlockRotate.equals(oldBlockUnderBlockRotate)) throw new Exception(ROTATE_ERROR);
            if(!newBlockUnderBlockRotate.isClear()) throw new Exception(BLOCK_IS_NOT_CLEAR);

            blockRotate.put_me_on_block(newBlockUnderBlockRotate.get_name());
            newBlockUnderBlockRotate.put_block_on_me(blockRotate.get_name());

            if(oldBlockUnderBlockRotate != null) oldBlockUnderBlockRotate.clear();

            newState.buildRelations();

            newState.isValid();

            if(oldBlockUnderBlockRotate == null){
                newState.setAction("puted " + blockRotate.get_name() + " from table to " + newBlockUnderBlockRotate.get_name());
            }else{
                newState.setAction("puted " + blockRotate.get_name() + " from " + oldBlockUnderBlockRotate.get_name() + " to " + newBlockUnderBlockRotate.get_name());
            }
            return newState;
        }
        throw new Exception(BLOCK_IS_NOT_CLEAR);
    }

    public void setAction(String action){
        this.action = action;
    }

    public State put_block_on_table(String block) throws Exception{

        if(blocks.get(block).isClear()){
            State newState = this.copy();

            newState.isValid();

            Block blockRotate, oldBlockUnderBlock;

            blockRotate = newState.blocks.get(block);
            oldBlockUnderBlock = newState.blocks.get(blockRotate.blockUnder());

            blockRotate.put_on_table();
            oldBlockUnderBlock.clear();

            newState.buildRelations();

            newState.isValid();

            newState.setAction("puted " + block + " on table");
            return newState;
        }

        throw new Exception(BLOCK_IS_NOT_CLEAR);
    }

    public void isValid() throws Exception{

        for(Block b : blocks.values()){
            b.isValid();
            for(MyTupel tupel : relations){
                if(tupel.containsBlock(b.get_name())){
                    if(tupel.getUnder().equals(b.get_name())){
                        if(b.isClear()){
                            throw new Exception(BLOCK_IS_NOT_CLEAR + " " + b.get_name());
                        }
                        if(tupel.getOver().equals(b.get_name())) throw new Exception(STATE_IS_NOT_VALID);
                    }else{
                        if(!b.isClear()){
                            //throw new Exception(BLOCK_IS_NOT_CLEAR + " " + b.get_name());
                        }
                        if(tupel.getUnder().equals(b.get_name())) throw new Exception(STATE_IS_NOT_VALID);
                    }
                }
            }
        }
    }

    public String showState(int rotations){
        String out = "";
        out += "--- State-Output-Begin---\n";

        out += "\t Rotations : " + rotations + "\n\n";
        out += action + "\n\n";
        for(MyTupel tupel : relations){
            out +=tupel.getUnder();
            out +=" --> ";
            out +=tupel.getOver();
            out +="\n";
        }

        out += "\n--- State-Output-End---\n\n";
        return out;
    }

    public boolean equals(State state){

        boolean result = true;
        for(MyTupel tupelThis : relations) {
            boolean equal = false;
            for (MyTupel tupelState : state.relations) {
                if(tupelThis.equals(tupelState)){
                    equal = true;
                    break;
                }

            }
            if(!equal){
                result = false;
                break;
            }
        }

        for(MyTupel tupelState : state.relations) {
            boolean equal = false;
            for (MyTupel tupelThis : relations) {
                if(tupelThis.equals(tupelState)) {
                    equal = true;
                    break;
                }

            }
            if(!equal){
                result = false;
                break;
            }
        }

        return result;
    }

    public Iterator getBlockIterator(){
        return blocks.values().iterator();
    }

    public Block getBlock(String name){
        return blocks.get(name);
    }
}
