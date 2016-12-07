package org.jruby.truffle.parser.ast;

import org.jruby.truffle.parser.lexer.ISourcePosition;

public class Match2CaptureParseNode extends Match2ParseNode {
    // Allocated locals that the regexp will assign after performing a match
    private int[] scopeOffsets;

    public Match2CaptureParseNode(ISourcePosition position, ParseNode receiverNode, ParseNode valueNode,
                                  int[] scopeOffsets) {
        super(position, receiverNode, valueNode);

        this.scopeOffsets = scopeOffsets;
    }

    public int[] getScopeOffsets() {
        return scopeOffsets;
    }
}
