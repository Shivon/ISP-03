/**
 * Created by murat on 30.11.16.
 */
public interface IBlock {

    //Clears block
    public void clear() throws Exception;

    //Put block on table if block is clear
    public boolean put_on_table()throws Exception;

    //Put block on given block, if block is clear
    public boolean put_me_on_block(String block)throws Exception;

    public String get_name();

    public boolean isClear();
    
    public boolean isOnTable();

    public String blockUnder();

    public String blockOver();
}
