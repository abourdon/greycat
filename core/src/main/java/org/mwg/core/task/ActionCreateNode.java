package org.mwg.core.task;

import org.mwg.Node;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;

class ActionCreateNode implements Action {

    private final String _typeNode;

    ActionCreateNode(final String typeNode) {
        this._typeNode = typeNode;
    }

    @Override
    public void eval(final TaskContext context) {
        Node newNode;
        if (_typeNode == null) {
            newNode = context.graph().newNode(context.world(), context.time());
        } else {
            String templatedType = context.template(_typeNode);
            newNode = context.graph().newTypedNode(context.world(), context.time(), templatedType);
        }
        context.continueWith(context.wrap(newNode));
    }

    @Override
    public String toString() {
        if (_typeNode != null) {
            return "createTypedNode(\'" + _typeNode + "\')";
        } else {
            return "createNode()";
        }
    }

}