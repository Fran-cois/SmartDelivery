/*
 * Copyright (c) 2013, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.language.locals;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.Layouts;
import org.jruby.truffle.RubyContext;
import org.jruby.truffle.language.RubyNode;
import org.jruby.truffle.language.RubyRootNode;
import org.jruby.truffle.language.RubySourceSection;

public class InstrumentedWriteLocalVariableNode extends WriteLocalVariableNode {

    private final String name;

    public InstrumentedWriteLocalVariableNode(RubyContext context, SourceSection sourceSection, FrameSlot frameSlot, RubyNode valueNode) {
        super(context, sourceSection, frameSlot, valueNode);
        name = frameSlot.getIdentifier().toString();
    }

    public InstrumentedWriteLocalVariableNode(RubyContext context, RubySourceSection sourceSection, FrameSlot frameSlot, RubyNode valueNode) {
        super(context, sourceSection, frameSlot, valueNode);
        name = frameSlot.getIdentifier().toString();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        final Object value = super.execute(frame);
        recordWrite(value);
        return value;
    }

    @TruffleBoundary
    private void recordWrite(Object value) {
        final String type = Layouts.CLASS.getFields(getContext().getCoreLibrary().getLogicalClass(value)).getName();
        getContext().getCallGraph().recordLocalWrite((RubyRootNode) getRootNode(), name, type);
    }

}
