package org.mwg.core.task;

import org.mwg.Callback;
import org.mwg.DeferCounter;
import org.mwg.plugin.AbstractTaskAction;
import org.mwg.plugin.Job;
import org.mwg.plugin.SchedulerAffinity;
import org.mwg.task.*;

class ActionForeachPar extends AbstractTaskAction {

    private final Task _subTask;

    ActionForeachPar(final Task p_subTask) {
        super();
        _subTask = p_subTask;
    }

    @Override
    public void eval(final TaskContext context) {
        final TaskResult previousResult = context.result();
        final TaskResultIterator it = previousResult.iterator();
        final int previousSize = previousResult.size();
        if (previousSize == -1) {
            throw new RuntimeException("Foreach on non array structure are not supported yet!");
        }
        final DeferCounter waiter = context.graph().newCounter(previousSize);
        Object loop = it.next();
        while (loop != null) {
            _subTask.executeFrom(context, context.wrap(loop), SchedulerAffinity.ANY_LOCAL_THREAD, new Callback<TaskResult>() {
                @Override
                public void on(TaskResult result) {
                    if (result != null) {
                        result.free();
                    }
                    waiter.count();
                }
            });
            loop = it.next();
        }
        waiter.then(new Job() {
            @Override
            public void run() {
                context.continueTask();
            }
        });
    }

    @Override
    public String toString() {
        return "foreachPar()";
    }

}
