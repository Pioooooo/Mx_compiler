package transforms;

import ir.Module;
import transforms.util.CleanUp;

public class Optimizer {
    Module m;

    public Optimizer(Module m) {
        this.m = m;
    }

    public void run() {
        new CleanUp(m).run();
        new SCCP(m).run();
        new CleanUp(m).run();
    }
}
