package cn.mr8god.fchaptersix;

/**
 * @author Mr8god
 * @date 2020/4/16
 * @time 0:43
 */

class Sundae{
    private Sundae(){}
    static Sundae makeASundae(){
        return new Sundae();
    }
}
public class IceCream {
    public static void main(String[] args) {
        //! Sundae x = new Sundae();
        Sundae x = Sundae.makeASundae();
    }
}
