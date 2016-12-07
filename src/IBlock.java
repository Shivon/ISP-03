/**
 * Created by murat on 30.11.16.
 */
public interface IBlock {
    //Clears block
    void clear() throws Exception;

    //Put block on table if block is clear
    boolean put_on_table()throws Exception;

    //Put block on given block, if block is clear
    boolean put_me_on_block(String block)throws Exception;

    String get_name();

    boolean isClear();

    boolean isOnTable();

    String blockUnder();

    String blockOver();
}
