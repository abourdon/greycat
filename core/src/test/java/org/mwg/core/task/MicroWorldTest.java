package org.mwg.core.task;

import org.mwg.Callback;
import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.Type;
import org.mwg.utility.VerbosePlugin;

import static org.mwg.task.Actions.*;

public class MicroWorldTest {

    public static void main(String[] args) {

        Graph g = new GraphBuilder()
                .withMemorySize(10000)
                .withPlugin(new VerbosePlugin())
                .build();
        g.connect(new Callback<Boolean>() {
            @Override
            public void on(Boolean isConnected) {
                loop("1","2",
                        newNode()
                        .setProperty("name", Type.STRING, "room_{{i}}")
                        .indexNode("rooms", "name")
                        .asVar("parentRoom")
                        .loop("1","3",
                                newNode()
                                .setProperty("sensor", Type.STRING, "sensor_{{i}}")
                                .addTo("sensors", "parentRoom")
                        )
                ).execute(g, null);
            }
        });
    }

}