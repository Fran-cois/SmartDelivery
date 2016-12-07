package org.jruby.specialized;

import org.jcodings.specific.USASCIIEncoding;
import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyComparable;
import org.jruby.RubyFixnum;
import org.jruby.RubyString;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.Block;
import org.jruby.runtime.Constants;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.invokedynamic.MethodNames;
import org.jruby.util.io.EncodingUtils;

import static org.jruby.RubyEnumerator.enumeratorizeWithSize;
import static org.jruby.runtime.Helpers.arrayOf;
import static org.jruby.runtime.Helpers.invokedynamic;

/**
 * Two object version of RubyArraySpecialized.
 */
public class RubyArrayTwoObject extends RubyArraySpecialized {
    private IRubyObject car;
    private IRubyObject cdr;

    public RubyArrayTwoObject(Ruby runtime, IRubyObject car, IRubyObject cdr) {
        // packed arrays are omitted from ObjectSpace
        super(runtime, false);
        this.car = car;
        this.cdr = cdr;
        this.realLength = 2;
    }

    public RubyArrayTwoObject(RubyClass otherClass, IRubyObject car, IRubyObject cdr) {
        // packed arrays are omitted from ObjectSpace
        super(otherClass, false);
        this.car = car;
        this.cdr = cdr;
        this.realLength = 2;
    }

    RubyArrayTwoObject(RubyArrayTwoObject other) {
        this(other.getMetaClass(), other.car, other.cdr);
    }

    RubyArrayTwoObject(RubyClass metaClass, RubyArrayTwoObject other) {
        this(metaClass, other.car, other.cdr);
    }

    @Override
    public final IRubyObject eltInternal(int index) {
        if (!packed()) return super.eltInternal(index);
        else if (index == 0) return car;
        else if (index == 1) return cdr;
        throw new ArrayIndexOutOfBoundsException(index);
    }

    @Override
    public final IRubyObject eltInternalSet(int index, IRubyObject value) {
        if (!packed()) return super.eltInternalSet(index, value);
        if (index == 0) return this.car = value;
        if (index == 1) return this.cdr = value;
        throw new ArrayIndexOutOfBoundsException(index);
    }

    @Override
    protected void finishUnpack(IRubyObject nil) {
        car = cdr = nil;
    }

    @Override
    public RubyArray aryDup() {
        if (!packed()) return super.aryDup();
        return new RubyArrayTwoObject(getRuntime().getArray(), this);
    }

    @Override
    public IRubyObject rb_clear() {
        if (!packed()) return super.rb_clear();

        modifyCheck();

        // fail packing, but defer [] creation in case it is never needed
        IRubyObject nil = getRuntime().getNil();
        car = cdr = nil;
        values = IRubyObject.NULL_ARRAY;
        realLength = 0;

        return this;
    }

    @Override
    public IRubyObject collect(ThreadContext context, Block block) {
        if (!packed()) return super.collect(context, block);

        return new RubyArrayTwoObject(getRuntime(), block.yield(context, car), block.yield(context, cdr));
    }

    @Override
    public void copyInto(IRubyObject[] target, int start) {
        if (!packed()) {
            super.copyInto(target, start);
            return;
        }
        target[start] = car;
        target[start + 1] = cdr;
    }

    @Override
    public void copyInto(IRubyObject[] target, int start, int len) {
        if (!packed()) {
            super.copyInto(target, start, len);
            return;
        }
        if (len != 2) {
            unpack();
            super.copyInto(target, start, len);
            return;
        }
        target[start] = car;
        target[start + 1] = cdr;
    }

    @Override
    public IRubyObject dup() {
        if (!packed()) return super.dup();
        return new RubyArrayTwoObject(this);
    }

    @Override
    public IRubyObject each(ThreadContext context, Block block) {
        if (!packed()) return super.each(context, block);

        if (!block.isGiven()) return enumeratorizeWithSize(context, this, "each", enumLengthFn());

        block.yield(context, car);
        block.yield(context, cdr);

        return this;
    }

    @Override
    protected IRubyObject fillCommon(ThreadContext context, int beg, long len, Block block) {
        if (!packed()) return super.fillCommon(context, beg, len, block);

        unpack();
        return super.fillCommon(context, beg, len, block);
    }

    @Override
    protected IRubyObject fillCommon(ThreadContext context, int beg, long len, IRubyObject item) {
        if (!packed()) return super.fillCommon(context, beg, len, item);

        unpack();
        return super.fillCommon(context, beg, len, item);
    }

    @Override
    public boolean includes(ThreadContext context, IRubyObject item) {
        if (!packed()) return super.includes(context, item);

        if (equalInternal(context, car, item)) return true;
        if (equalInternal(context, cdr, item)) return true;

        return false;
    }

    @Override
    public int indexOf(Object element) {
        if (!packed()) return super.indexOf(element);

        if (element != null) {
            IRubyObject convertedElement = JavaUtil.convertJavaToUsableRubyObject(getRuntime(), element);

            if (convertedElement.equals(car)) return 0;
            if (convertedElement.equals(cdr)) return 1;
        }
        return -1;
    }

    @Override
    protected IRubyObject inspectAry(ThreadContext context) {
        if (!packed()) return super.inspectAry(context);

        final Ruby runtime = context.runtime;
        RubyString str = RubyString.newStringLight(runtime, DEFAULT_INSPECT_STR_SIZE, USASCIIEncoding.INSTANCE);
        EncodingUtils.strBufCat(runtime, str, OPEN_BRACKET);
        boolean tainted = isTaint();

        RubyString s1 = inspect(context, car);
        RubyString s2 = inspect(context, cdr);
        if (s1.isTaint()) tainted = true;
        else str.setEncoding(s1.getEncoding());
        str.cat19(s1);

        EncodingUtils.strBufCat(runtime, str, COMMA_SPACE);

        if (s2.isTaint()) tainted = true;
        else str.setEncoding(s2.getEncoding());
        str.cat19(s2);

        EncodingUtils.strBufCat(runtime, str, CLOSE_BRACKET);

        if (tainted) str.setTaint(true);

        return str;
    }

    @Override
    protected IRubyObject internalRotate(ThreadContext context, int cnt) {
        if (!packed()) return super.internalRotate(context, cnt);

        if (cnt % 2 == 1) return new RubyArrayTwoObject(context.runtime, cdr, car);
        return new RubyArrayTwoObject(context.runtime, car, cdr);
    }

    @Override
    protected IRubyObject internalRotateBang(ThreadContext context, int cnt) {
        if (!packed()) return super.internalRotateBang(context, cnt);

        modifyCheck();

        if (cnt % 2 == 1) {
            IRubyObject tmp = car;
            car = cdr;
            cdr = tmp;
        }

        return context.nil;
    }

    @Override
    public IRubyObject op_plus(IRubyObject obj) {
        if (!packed()) return super.op_plus(obj);
        RubyArray y = obj.convertToArray();
        if (y.size() == 0) return new RubyArrayTwoObject(this);
        return super.op_plus(y);
    }

    @Override
    public IRubyObject replace(IRubyObject orig) {
        if (!packed()) return super.replace(orig);

        modifyCheck();

        RubyArray origArr = orig.convertToArray();

        if (this == orig) return this;

        if (origArr.size() == 2) {
            car = origArr.eltInternal(0);
            cdr = origArr.eltInternal(1);
            return this;
        }

        unpack();

        return super.replace(origArr);
    }

    @Override
    public IRubyObject reverse_bang() {
        if (!packed()) return super.reverse_bang();

        IRubyObject tmp = car;
        car = cdr;
        cdr = tmp;

        return this;
    }

    @Override
    protected RubyArray safeReverse() {
        if (!packed()) return super.safeReverse();

        return new RubyArrayTwoObject(getMetaClass(), cdr, car);
    }

    @Override
    protected IRubyObject sortInternal(ThreadContext context, Block block) {
        if (!packed()) return super.sortInternal(context, block);


        IRubyObject ret = block.yieldArray(context, newArray(context.runtime, car, cdr), null);
        //TODO: ary_sort_check should be done here
        int compare = RubyComparable.cmpint(context, ret, car, cdr);
        if (compare > 0) reverse_bang();
        return this;
    }

    @Override
    protected IRubyObject sortInternal(final ThreadContext context, boolean honorOverride) {
        if (!packed()) return super.sortInternal(context, honorOverride);

        Ruby runtime = context.runtime;

        // One check per specialized fast-path to make the check invariant.
        final boolean fixnumBypass = !honorOverride || runtime.getFixnum().isMethodBuiltin("<=>");
        final boolean stringBypass = !honorOverride || runtime.getString().isMethodBuiltin("<=>");

        IRubyObject o1 = car;
        IRubyObject o2 = cdr;
        int compare;
        if (fixnumBypass && o1 instanceof RubyFixnum && o2 instanceof RubyFixnum) {
            compare = compareFixnums((RubyFixnum) o1, (RubyFixnum) o2);
        } else if (stringBypass && o1 instanceof RubyString && o2 instanceof RubyString) {
            compare = ((RubyString) o1).op_cmp((RubyString) o2);
        } else {
            compare = compareOthers(context, o1, o2);
        }

        if (compare > 0) reverse_bang();

        return this;
    }

    @Override
    public IRubyObject store(long index, IRubyObject value) {
        if (!packed()) return super.store(index, value);

        switch ((int) index) {
            case 0: return car = value;
            case 1: return cdr = value;
        }

        unpack();
        return super.store(index, value);
    }

    @Override
    public IRubyObject subseq(RubyClass metaClass, long beg, long len, boolean light) {
        if (!packed()) return super.subseq(metaClass, beg, len, light);

        Ruby runtime = getRuntime();

        if (beg > 2 || beg < 0 || len < 0) return runtime.getNil();

        if (len == 0 || beg == 2) return new RubyArray(runtime, metaClass, IRubyObject.NULL_ARRAY);

        if (beg == 0) {
            if (len == 1) return new RubyArrayOneObject(metaClass, car);
            return new RubyArrayTwoObject(metaClass, this);
        }

        // beg == 1, len >= 1
        return new RubyArrayOneObject(metaClass, cdr);
    }

    @Override
    public IRubyObject[] toJavaArray() {
        if (!packed()) return super.toJavaArray();

        return arrayOf(car, cdr);
    }

    @Override
    public IRubyObject uniq(ThreadContext context) {
        if (!packed()) return super.uniq(context);

        if (invokedynamic(context, car, MethodNames.HASH).equals(invokedynamic(context, cdr, MethodNames.HASH)) &&
                (car == cdr || invokedynamic(context, car, MethodNames.EQL, cdr).isTrue())) {
            // Use cdr because it would have been inserted into RubyArray#uniq's RubyHash last
            return new RubyArrayOneObject(getMetaClass(), cdr);
        } else {
            return new RubyArrayTwoObject(this);
        }
    }
}
