/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.builtins;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.RubyContext;
import org.jruby.truffle.language.RubyNode;
import org.jruby.truffle.language.control.ReturnException;
import org.jruby.truffle.language.control.ReturnID;

public class CallPrimitiveNode extends RubyNode {

    @Child private RubyNode primitive;
    private final ReturnID returnID;

    private final ConditionProfile failedProfile = ConditionProfile.createBinaryProfile();

    public CallPrimitiveNode(RubyContext context, SourceSection sourceSection, RubyNode primitive, ReturnID returnID) {
        super(context, sourceSection);
        this.primitive = primitive;
        this.returnID = returnID;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        final Object value = primitive.execute(frame);

        // Primitives fail by returning null, allowing the method to continue (the fallback)
        if (failedProfile.profile(value == null)) {
            return nil();
        } else {
            // If the primitive didn't fail its value is returned in the calling method
            throw new ReturnException(returnID, value);
        }

    }

}
