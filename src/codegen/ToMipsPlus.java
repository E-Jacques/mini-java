package codegen;

import intermediate.IR;
import intermediate.ir.*;
import main.CompilerException;

public class ToMipsPlus extends ToMips {

    public ToMipsPlus(IR ir, Allocator allocator, MipsWriter mw) {
        super(ir, allocator, mw);
    }

    private Reg tmpReg(final IRvariable v, final Reg defReg) {
        final Reg reg = allocator.access(v).getRegister();
        return (reg == null) ? defReg : reg;
    }

    private Reg tmpRegLoad(final IRvariable v, final Reg defReg) {
        Reg r = tmpReg(v, defReg);
        this.regLoad(r, v);

        return r;
    }

    @Override
    public void visit(QAssign q) {
        Reg r0 = tmpRegLoad(q.arg1, Reg.V0);
        Reg r1 = tmpRegLoad(q.arg2, Reg.V1);

        switch (q.op) {
            case PLUS:
                mw.plus(r0, r1);
                break;
            case TIMES:
                mw.fois(r0, r1);
                break;
            case MINUS:
                mw.moins(r0, r1);
                break;
            case AND:
                mw.et(r0, r1);
                break;
            case LESS:
                mw.inferieur(r0, r1);
                break;
            default:
                break;
        }

        this.regStore(Reg.V0, q.result);
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
            mw.storeOffset(Reg.T0, sizeIdx * SIZEOF, Reg.SP);
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
    public void visit(final QLabelMeth q) {
        String methodName = ((IRlabel) q.arg1).getName();
        mw.label(methodName);
        this.calleeIn();
    }
}
