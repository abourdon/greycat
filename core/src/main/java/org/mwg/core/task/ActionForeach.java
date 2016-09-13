package org.mwg.core.task;

import org.mwg.Callback;
import org.mwg.plugin.AbstractTaskAction;
import org.mwg.plugin.Job;
import org.mwg.plugin.SchedulerAffinity;
import org.mwg.task.*;

import java.util.concurrent.atomic.AtomicInteger;

class ActionForeach extends AbstractTaskAction {

    private final Task _subTask;

    ActionForeach(final Task p_subTask) {
        super();
        _subTask = p_subTask;
    }

    @Override
    public void eval(final TaskContext context) {
        final ActionForeach selfPointer = this;
        final TaskResult previousResult = context.result();
        if (previousResult == null) {
            context.continueTask();
        } else {
            final TaskResultIterator it = previousResult.iterator();
            final Callback[] recursiveAction = new Callback[1];
            recursiveAction[0] = new Callback<TaskResult>() {
                @Override
                public void on(final TaskResult res) {
                    //we don't keep result
                    if (res != null) {
                        res.free();
                    }
                    Object nextResult = it.next();
                    if (nextResult == null) {
                        context.continueTask();
                    } else {
                        selfPointer._subTask.executeFrom(context, context.wrap(nextResult), SchedulerAffinity.SAME_THREAD, recursiveAction[0]);
                    }
                }
            };
            Object nextRes = it.next();
            if (nextRes != null) {
                context.graph().scheduler().dispatch(SchedulerAffinity.SAME_THREAD, new Job() {
                    @Override
                    public void run() {
                        _subTask.executeFrom(context, context.wrap(nextRes), SchedulerAffinity.SAME_THREAD, recursiveAction[0]);
                    }
                });
            } else {
                context.continueTask();
            }
        }
    }

    @Override
    public String toString() {
        return "foreach()";
    }


}
