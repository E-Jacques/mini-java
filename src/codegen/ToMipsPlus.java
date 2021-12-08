package codegen;

import intermediate.IR;
import intermediate.ir.*;

public class ToMipsPlus extends ToMips {
    public ToMipsPlus(IR ir, Allocator allocator, MipsWriter mw) {
        super(ir, allocator, mw);
    }

    public void visit (final QAssign q) {
        defaultVisit(q);
    }

    public void visit (final QAssignArrayFrom q) {
        defaultVisit(q);
    }

    public void visit (final QAssignUnary q) {
        defaultVisit(q);
    }

    public void visit (final QAssignArrayTo q) {
        defaultVisit(q);
    }

    public void visit (final QCall q) {
        defaultVisit(q);
    }

    public void visit (final QCopy q) {
        defaultVisit(q);
    }

    public void visit (final QJump q) {
        defaultVisit(q);
    }

    public void visit (final QJumpCond q) {
        defaultVisit(q);
    }

    public void visit (final QLabelMeth q) {
        defaultVisit(q);
    }

    public void visit (final QLength q) {
        defaultVisit(q);
    }

    public void visit (final QNew q) {
        defaultVisit(q);
    }

    public void visit (final QNewArray q) {
        defaultVisit(q);
    }

    public void visit (final QReturn q) {
        defaultVisit(q);
    }
}
