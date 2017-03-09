/*
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.language.globals;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.RubyContext;
import org.jruby.truffle.core.basicobject.BasicObjectNodes.ReferenceEqualNode;
import org.jruby.truffle.language.RubyNode;
import org.jruby.truffle.language.objects.shared.WriteBarrierNode;

@NodeChild(value = "value")
public abstract class WriteGlobalVariableNode extends RubyNode {

    private final String name;

    @Child ReferenceEqualNode referenceEqualNode = ReferenceEqualNode.create();
    @Child WriteBarrierNode writeBarrierNode = WriteBarrierNode.create();

    public WriteGlobalVariableNode(RubyContext context, SourceSection sourceSection, String name) {
        super(context, sourceSection);
        this.name = name;
    }

    @Specialization(guards = "referenceEqualNode.executeReferenceEqual(value, previousValue)",
                    assumptions = "storage.getUnchangedAssumption()")
    public Object writeTryToKeepConstant(Object value,
                    @Cached("getStorage()") GlobalVariableStorage storage,
                    @Cached("storage.getValue()") Object previousValue) {
        return previousValue;
    }

    @Specialization(guards = "storage.isAssumeConstant()",
                    assumptions = "storage.getUnchangedAssumption()")
    public Object writeAssumeConstant(Object value,
                    @Cached("getStorage()") GlobalVariableStorage storage) {
        if (getContext().getSharedObjects().isSharing()) {
            writeBarrierNode.executeWriteBarrier(value);
        }
        storage.setValueInternal(value);
        storage.updateAssumeConstant();
        return value;
    }

    @Specialization(guards = {"workaround()", "!storage.isAssumeConstant()"}, contains = "writeAssumeConstant")
    public Object write(Object value,
                    @Cached("getStorage()") GlobalVariableStorage storage) {
        if (getContext().getSharedObjects().isSharing()) {
            writeBarrierNode.executeWriteBarrier(value);
        }
        storage.setValueInternal(value);
        return value;
    }

    protected boolean workaround() {
        return true;
    }

    protected GlobalVariableStorage getStorage() {
        return getContext().getCoreLibrary().getGlobalVariables().getStorage(name);
    }

    @Override
    public Object isDefined(VirtualFrame frame) {
        return coreStrings().ASSIGNMENT.createInstance();
    }

}
