/**
 * Created by murat on 30.11.16.
 */
public class MyTupel {
    String under;
    String over;

    public MyTupel(String under, String over){
        this.under = under;
        this.over = over;
    }

    public String getUnder(){
        return under;
    }

    public String getOver(){
        return over;
    }

    public boolean containsBlock(String block){
        if(block.equals(under)) return true;
        if(block.equals(over)) return true;
        return false;
    }

    public boolean equals(MyTupel tupel){
        if(this.under.equals(tupel.under) && this.over.equals(tupel.over)) return true;
        return false;

    }
}
