package codegen;

import intermediate.IR;
import intermediate.ir.*;
import main.CompilerException;

public class ToMipsPlus extends ToMips {

    public ToMipsPlus(IR ir, Allocator allocator, MipsWriter mw) {
        super(ir, allocator, mw);
    }

    @Override
    public void visit(final QCall q) throws CompilerException {
        String methodName = ((IRlabel) q.arg1).getName();
        int nbArg = ((IRconst) q.arg2).getValue();

        if (nbArg != params.size()) {
            throw new main.CompilerException("ToMips : Params error");
        }

        int frameSize = allocator.frameSize(methodName);
        System.out.println(methodName);
        this.callerSave();
        for (int i = NBARGS; i < nbArg; i++) {
            int sizeIdx = i - NBARGS;
            this.regLoad(Reg.T0, params.get(i));
            mw.storeOffset(Reg.T0, sizeIdx*SIZEOF, Reg.SP);
        }

        for (int i = 0; i < Math.min(NBARGS, nbArg); i++) {
            this.regLoadSaved(AREGS[i], params.get(i));
        }
        
        mw.move(Reg.FP, Reg.SP);
        mw.plus(Reg.SP, -frameSize);
        mw.jumpIn(methodName);
        mw.move(Reg.SP, Reg.FP);
        this.callerRestore();

        this.regStore(Reg.V0, q.result);

        params.clear();
    }

    @Override
    public void visit (final QLabelMeth q) {
        String methodName = ((IRlabel) q.arg1).getName();
        mw.label(methodName);
        this.calleeIn();
    }
}
