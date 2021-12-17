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

    // @Override
    // public void visit(final QJumpCond q) {
    // Reg r = this.tmpRegLoad(q.arg2, Reg.V0);
    // mw.jumpIfNot(r, q.arg1.getName());
    // }

    // @Override
    // public void visit(final QAssignUnary q) {
    // Reg r = this.tmpRegLoad(q.arg1, Reg.V0);
    // if (q.op == main.EnumOper.NOT) {
    // mw.not(r);
    // }

    // this.regStore(Reg.V0, q.result);
    // }

    // @Override
    // public void visit(QAssign q) {
    // Reg r0 = tmpRegLoad(q.arg1, Reg.V0);
    // Reg r1 = tmpRegLoad(q.arg2, Reg.V1);

    // switch (q.op) {
    // case PLUS:
    // mw.plus(r0, r1);
    // break;
    // case TIMES:
    // mw.fois(r0, r1);
    // break;
    // case MINUS:
    // mw.moins(r0, r1);
    // break;
    // case AND:
    // mw.et(r0, r1);
    // break;
    // case LESS:
    // mw.inferieur(r0, r1);
    // break;
    // default:
    // break;
    // }

    // this.regStore(r0, q.result);
    // }

    @Override
    public void visit(final QAssignArrayFrom q) {
        push(Reg.T0, Reg.T1, Reg.T2, Reg.T3, Reg.A0);

        this.regLoad(Reg.T0, q.arg1);
        this.regLoad(Reg.T1, q.arg2);

        mw.loadOffset(Reg.T2, 0, Reg.T0);
        mw.inRange(Reg.T3, Reg.T1, Reg.T2);
        mw.load(Reg.A0, 404);
        mw.jumpIfNot(Reg.T3, "_system_exit");

        mw.fois4(Reg.T1);
        mw.plus(Reg.T0, Reg.T1);

        mw.loadOffset(Reg.V0, 4, Reg.T0);
        this.regStore(Reg.V0, q.result);

        pop(Reg.T0, Reg.T1, Reg.T2, Reg.T3, Reg.A0);
    }

    @Override
    public void visit(final QAssignArrayTo q) {
        push(Reg.T0, Reg.T1, Reg.T2, Reg.T3, Reg.A0);

        this.regLoad(Reg.T0, q.result);
        this.regLoad(Reg.T1, q.arg2);

        mw.loadOffset(Reg.T2, 0, Reg.T0);
        mw.inRange(Reg.T3, Reg.T1, Reg.T2);
        mw.load(Reg.A0, 404);
        mw.jumpIfNot(Reg.T3, "_system_exit");

        mw.fois4(Reg.T1);
        mw.plus(Reg.T0, Reg.T1);

        this.regLoad(Reg.V0, q.arg1);
        mw.storeOffset(Reg.V0, 4, Reg.T0);

        pop(Reg.T0, Reg.T1, Reg.T2, Reg.T3, Reg.A0);
    }

    @Override
    public void visit(final QCall q) throws CompilerException {
        String methodName = ((IRlabel) q.arg1).getName();
        int nbArg = ((IRconst) q.arg2).getValue();

        if (nbArg != params.size()) {
            throw new main.CompilerException("ToMips : Params error");
        }

        int frameSize = allocator.frameSize(methodName);
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
