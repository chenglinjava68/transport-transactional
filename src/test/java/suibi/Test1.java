package suibi;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by m on 2016/12/27.
 */
public class Test1 {
    @Test
    public void test1() {
        BaseFather baseFather = new BaseFather();
        baseFather.getSys();
        new Son(baseFather).getSys();
    }

    @Test
    public void test2() {
        Set<String> strs = new HashSet<String>();
        strs.add("a");
        strs.add("a");
        strs.add("a");
        System.out.println(strs.size());
    }
}

abstract class Father {
    Father() {
    }

    void getSys() {
        System.out.println("father");
    }

    abstract void doSelf();
}

class BaseFather extends Father {
    private final Father parent;
    private static Father sys;

    BaseFather() {
        this(getDefault());
    }

    BaseFather(Father parent) {
        this.parent = parent;
    }

    final Father getParent() {
        return parent;
    }

    void doDeal() {
    }

    public static Father getDefault() {
        if (sys == null)
            sys = new Default();
        return sys;
    }

    @Override
    void getSys() {
        if (parent != null) {
            parent.getSys();
            return;
        }
        doSelf();
    }

    @Override
    void doSelf() {

    }
}

class Default extends Father {
    public Default() {
        System.out.println("Default");
    }

    @Override
    void getSys() {
        System.out.println("default");
    }

    @Override
    void doSelf() {
        System.out.println("doSelf default");
    }
}

class FirstFather extends BaseFather {
    FirstFather(Father father) {
        super(father);
    }

    void getSys() {
        System.out.println("FirstFather");
    }
}

class Son extends FirstFather {
    Son(Father father) {
        super(father);
    }

    void getSys() {
        System.out.println("son");
    }
}
