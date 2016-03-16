package graph;

import org.junit.Test;
import org.mwdb.*;
import org.mwdb.chunk.heap.HeapChunkSpace;
import org.mwdb.chunk.offheap.OffHeapChunkSpace;
import org.mwdb.manager.NoopScheduler;

public class BenchmarkTest {

    @Test
    public void heapTest() {
        test(GraphBuilder.builder().withScheduler(new NoopScheduler()).withSpace(new HeapChunkSpace(100_000, 10_000)).buildGraph());
    }

    @Test
    public void offHeapTest() {
        test(GraphBuilder.builder().withScheduler(new NoopScheduler()).withSpace(new OffHeapChunkSpace(100_000, 10_000)).buildGraph());
    }

    final int valuesToInsert = 10_000_000;
    final long timeOrigin = 1000;

    private void test(KGraph graph) {
        graph.connect(new KCallback<Boolean>() {
            @Override
            public void on(Boolean result) {
                final long before = System.currentTimeMillis();
                KNode node = graph.newNode(0, 0);
                final KDeferCounter counter = graph.counter(valuesToInsert);
                for (long i = 0; i < valuesToInsert; i++) {


                    if (i % 1000 == 0) {
                        node.free();
                        node = graph.newNode(0, 0);
                    }


                    if (i % 1_000_000 == 0) {
                        System.out.println(">" + i + " " + (System.currentTimeMillis() - before) / 1000 + "s");
                    }


                    final double value = i * 0.3;
                    final long time = timeOrigin + i;
                    graph.lookup(0, time, node.id(), new KCallback<KNode>() {
                        @Override
                        public void on(KNode timedNode) {
                            timedNode.attSet("value", KType.DOUBLE, value);
                            counter.count();
                            timedNode.free();//free the node, for cache management
                        }
                    });
                }
                counter.then(new KCallback() {
                    @Override
                    public void on(Object result) {
                        System.out.println("end>" + " " + (System.currentTimeMillis() - before) / 1000 + "s");
                    }
                });

            }
        });
    }

}
