/**
 * Created by murat on 30.11.16.
 */
public class Block implements IBlock {

    private String name;
    private boolean onTable;
    private boolean clear;
    private String blockOver;
    private String blockUnder;

    private final String NOT_VALID_EXCEPTION_MESSAGE = "Block is not valid";

    public Block(String name, boolean onTable, boolean clear, String blockOver, String blockUnder){
        this.name = name;
        this.onTable = onTable;
        this.clear = clear;
        this.blockOver = blockOver;
        this.blockUnder = blockUnder;
    }

    public Block(Block block){
        this.name = block.name;
        this.onTable = block.onTable;
        this.clear = block.clear;
        this.blockOver = block.blockOver;
        this.blockUnder = block.blockUnder;
    }

    @Override
    public void clear() throws Exception {
        isValid();
        clear = true;
        blockOver = "";
        isValid();

    }

    @Override
    public boolean put_on_table() throws Exception {
        isValid();
        if(clear){
            if(!onTable){
                onTable = true;
                blockUnder = "";
                isValid();
            }
        }

        return false;
    }

    @Override
    public boolean put_me_on_block(String block) throws Exception {
        isValid();

        if(clear){
            if(!blockUnder.equals(block)){
                blockUnder = block;
                onTable = false;
                isValid();
                return true;
            }
        }
        return false;
    }


    public boolean put_block_on_me(String block) throws Exception {
        isValid();

        if(clear){
            //if(block.equals(blockOver)){
                clear = false;
                blockOver = block;
                isValid();
                return true;
            //}

        }

        return false;
    }

    @Override
    public String get_name() {
        return name;
    }

    @Override
    public boolean isClear() {
        return clear;
    }

    @Override
    public boolean isOnTable() {
        return onTable;
    }

    @Override
    public String blockUnder() {
        return blockUnder;
    }

    @Override
    public String blockOver() {
        return blockOver;
    }

    public void isValid() throws Exception {
        if(clear){
            if(!blockOver.isEmpty()){
                throw new Exception(NOT_VALID_EXCEPTION_MESSAGE + " " + name);
            }
        }
        if(onTable){
            if(!blockUnder.isEmpty()){
                throw new Exception(NOT_VALID_EXCEPTION_MESSAGE + " " + name);
            }
        }
        if(name.equals(blockUnder)){
            throw new Exception(NOT_VALID_EXCEPTION_MESSAGE + " " + name);
        }

        if(name.equals(blockOver)){
            throw new Exception(NOT_VALID_EXCEPTION_MESSAGE + " " + name);
        }

    }
}
