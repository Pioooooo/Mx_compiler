package transforms;

import ir.IRPrinter;
import ir.Module;
import transforms.util.CleanUp;

public class Optimizer {
    Module m;

    public Optimizer(Module m) {
        this.m = m;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            new CleanUp(m).run();
            new SCCP(m).run();
            new CleanUp(m).run();
            new CSE(m).run();
            new CleanUp(m).run();
            new ADCE(m).run();
            new CleanUp(m).run();
            new TCO(m).run();
            new CleanUp(m).run();
            new Inliner(m).run();
            new CleanUp(m).run();
            new LICM(m).run();
        }
        new CleanUp(m).run();
        new IRPrinter().print(m, System.err);
    }
}
