/*
 * Copyright (c) 2013, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.core.array;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import org.jruby.truffle.Layouts;
import org.jruby.truffle.RubyContext;
import org.jruby.truffle.core.CoreLibrary;
import org.jruby.truffle.language.RubyNode;
import org.jruby.truffle.language.RubySourceSection;
import org.jruby.truffle.language.objects.AllocateObjectNode;
import org.jruby.truffle.language.objects.AllocateObjectNodeGen;

public abstract class ArrayLiteralNode extends RubyNode {

    public static ArrayLiteralNode create(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
        return new UninitialisedArrayLiteralNode(context, sourceSection, values);
    }

    @Children protected final RubyNode[] values;
    @Child protected AllocateObjectNode allocateObjectNode;

    public ArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
        super(context, sourceSection);
        this.values = values;
        this.allocateObjectNode = AllocateObjectNodeGen.create(context, (RubySourceSection) null, false, null, null);
    }

    protected DynamicObject makeGeneric(VirtualFrame frame, Object[] alreadyExecuted) {
        CompilerAsserts.neverPartOfCompilation();

        replace(new ObjectArrayLiteralNode(getContext(), getRubySourceSection(), values));

        final Object[] executedValues = new Object[values.length];

        for (int n = 0; n < values.length; n++) {
            if (n < alreadyExecuted.length) {
                executedValues[n] = alreadyExecuted[n];
            } else {
                executedValues[n] = values[n].execute(frame);
            }
        }

        return createArray(executedValues, executedValues.length);
    }

    protected DynamicObject createArray(Object store, int size) {
        return allocateObjectNode.allocate(coreLibrary().getArrayClass(), store, size);
    }

    @Override
    public abstract Object execute(VirtualFrame frame);

    @ExplodeLoop
    @Override
    public void executeVoid(VirtualFrame frame) {
        for (RubyNode value : values) {
            value.executeVoid(frame);
        }
    }

    @ExplodeLoop
    @Override
    public Object isDefined(VirtualFrame frame) {
        for (RubyNode value : values) {
            if (value.isDefined(frame) == nil()) {
                return nil();
            }
        }

        return super.isDefined(frame);
    }

    public int getSize() {
        return values.length;
    }

    public RubyNode stealNode(int index) {
        final RubyNode node = values[index];
        // Nullify it here so we make sure it's only referenced by the caller.
        values[index] = null;
        return node;
    }

    private static class EmptyArrayLiteralNode extends ArrayLiteralNode {

        public EmptyArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
            super(context, sourceSection, values);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return createArray(null, 0);
        }

    }

    private static class FloatArrayLiteralNode extends ArrayLiteralNode {

        public FloatArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
            super(context, sourceSection, values);
        }

        @ExplodeLoop
        @Override
        public Object execute(VirtualFrame frame) {
            final double[] executedValues = new double[values.length];

            for (int n = 0; n < values.length; n++) {
                try {
                    executedValues[n] = values[n].executeDouble(frame);
                } catch (UnexpectedResultException e) {
                    return makeGeneric(frame, executedValues, n);
                }
            }

            return createArray(executedValues, values.length);
        }

        private DynamicObject makeGeneric(VirtualFrame frame, final double[] executedValues, int n) {
            final Object[] executedObjects = new Object[n];

            for (int i = 0; i < n; i++) {
                executedObjects[i] = executedValues[i];
            }

            return makeGeneric(frame, executedObjects);
        }

    }

    private static class IntegerArrayLiteralNode extends ArrayLiteralNode {

        public IntegerArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
            super(context, sourceSection, values);
        }

        @ExplodeLoop
        @Override
        public Object execute(VirtualFrame frame) {
            final int[] executedValues = new int[values.length];

            for (int n = 0; n < values.length; n++) {
                try {
                    executedValues[n] = values[n].executeInteger(frame);
                } catch (UnexpectedResultException e) {
                    return makeGeneric(frame, executedValues, n);
                }
            }

            return createArray(executedValues, values.length);
        }

        private DynamicObject makeGeneric(VirtualFrame frame, final int[] executedValues, int n) {
            final Object[] executedObjects = new Object[n];

            for (int i = 0; i < n; i++) {
                executedObjects[i] = executedValues[i];
            }

            return makeGeneric(frame, executedObjects);
        }

    }

    private static class LongArrayLiteralNode extends ArrayLiteralNode {

        public LongArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
            super(context, sourceSection, values);
        }

        @ExplodeLoop
        @Override
        public Object execute(VirtualFrame frame) {
            final long[] executedValues = new long[values.length];

            for (int n = 0; n < values.length; n++) {
                try {
                    executedValues[n] = values[n].executeLong(frame);
                } catch (UnexpectedResultException e) {
                    return makeGeneric(frame, executedValues, n);
                }
            }

            return createArray(executedValues, values.length);
        }

        private DynamicObject makeGeneric(VirtualFrame frame, final long[] executedValues, int n) {
            final Object[] executedObjects = new Object[n];

            for (int i = 0; i < n; i++) {
                executedObjects[i] = executedValues[i];
            }

            return makeGeneric(frame, executedObjects);
        }

    }

    private static class ObjectArrayLiteralNode extends ArrayLiteralNode {

        public ObjectArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
            super(context, sourceSection, values);
        }

        @ExplodeLoop
        @Override
        public Object execute(VirtualFrame frame) {
            final Object[] executedValues = new Object[values.length];

            for (int n = 0; n < values.length; n++) {
                executedValues[n] = values[n].execute(frame);
            }

            return createArray(executedValues, values.length);
        }

    }

    private static class UninitialisedArrayLiteralNode extends ArrayLiteralNode {

        public UninitialisedArrayLiteralNode(RubyContext context, RubySourceSection sourceSection, RubyNode[] values) {
            super(context, sourceSection, values);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();

            final Object[] executedValues = new Object[values.length];

            for (int n = 0; n < values.length; n++) {
                executedValues[n] = values[n].execute(frame);
            }

            final DynamicObject array = createArray(storeSpecialisedFromObjects(executedValues), executedValues.length);
            final Object store = Layouts.ARRAY.getStore(array);

            if (store == null) {
                replace(new EmptyArrayLiteralNode(getContext(), getRubySourceSection(), values));
            } if (store instanceof int[]) {
                replace(new IntegerArrayLiteralNode(getContext(), getRubySourceSection(), values));
            } else if (store instanceof long[]) {
                replace(new LongArrayLiteralNode(getContext(), getRubySourceSection(), values));
            } else if (store instanceof double[]) {
                replace(new FloatArrayLiteralNode(getContext(), getRubySourceSection(), values));
            } else {
                replace(new ObjectArrayLiteralNode(getContext(), getRubySourceSection(), values));
            }

            return array;
        }

        public Object storeSpecialisedFromObjects(Object... objects) {
            if (objects.length == 0) {
                return null;
            }

            boolean canUseInteger = true;
            boolean canUseLong = true;
            boolean canUseDouble = true;

            for (Object object : objects) {
                if (object instanceof Integer) {
                    canUseDouble = false;
                } else if (object instanceof Long) {
                    canUseInteger = canUseInteger && CoreLibrary.fitsIntoInteger((long) object);
                    canUseDouble = false;
                } else if (object instanceof Double) {
                    canUseInteger = false;
                    canUseLong = false;
                } else {
                    canUseInteger = false;
                    canUseLong = false;
                    canUseDouble = false;
                }
            }

            if (canUseInteger) {
                final int[] store = new int[objects.length];

                for (int n = 0; n < objects.length; n++) {
                    final Object object = objects[n];
                    if (object instanceof Integer) {
                        store[n] = (int) object;
                    } else if (object instanceof Long) {
                        store[n] = (int) (long) object;
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }

                return store;
            } else if (canUseLong) {
                final long[] store = new long[objects.length];

                for (int n = 0; n < objects.length; n++) {
                    final Object object = objects[n];
                    if (object instanceof Integer) {
                        store[n] = (long) (int) object;
                    } else if (object instanceof Long) {
                        store[n] = (long) object;
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }

                return store;
            } else if (canUseDouble) {
                final double[] store = new double[objects.length];

                for (int n = 0; n < objects.length; n++) {
                    store[n] = CoreLibrary.toDouble(objects[n], coreLibrary().getNilObject());
                }

                return store;
            } else {
                return objects;
            }
        }

    }
}
