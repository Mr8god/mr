package cn.mr8god.gchapterseven;

import static net.mindview.util.Print.print;

class WithFinals{
    private final void f(){ print("WithFinals.f()");}
    private void g(){ print("WithFinals.g()");}
}

class OverridingPrivate extends WithFinals{
    private final void f(){
        print("OverridingPrivate.f()");
    }

    private void g(){
        print("OverridingPrivate.g()");
    }
}

class OverridingPrivate2 extends OverridingPrivate{
    public final void f(){
        print("OverridingPrivate2.f()");
    }

    public void g(){
        print("OverridingPrivate2.g()");
    }
}
/**
 * @author Mr8god
 * @date 2020/4/26
 * @time 14:04
 */
public class FinalOverridingIllusion {
    public static void main(String[] args) {
        OverridingPrivate2 op2 = new OverridingPrivate2();
        op2.f();
        op2.g();

        OverridingPrivate op = op2;
        //! op.f();
        //! op.g();

        WithFinals wf = op2;
        //! wf.f();
        //! wf.g();
    }
}
