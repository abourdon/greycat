package org.mwdb.manager;

import org.mwdb.Constants;
import org.mwdb.KCallback;
import org.mwdb.chunk.KBuffer;
import org.mwdb.plugin.KStorage;
import org.mwdb.utility.Buffer;
import org.mwdb.utility.PrimitiveHelper;

public class NoopStorage implements KStorage {

    @Override
    public void get(long[] keys, KCallback<KBuffer[]> callback) {
        KBuffer[] result = new KBuffer[keys.length / Constants.KEYS_SIZE];
        callback.on(result);
    }

    @Override
    public void atomicGetIncrement(long[] key, KCallback<Short> callback) {
        callback.on(new Short("0"));
    }

    @Override
    public void put(long[] keys, KBuffer[] values, KCallback<Boolean> callback, int excludeListener) {
        if (PrimitiveHelper.isDefined(callback)) {
            callback.on(true);
        }
    }

    @Override
    public void remove(long[] keys, KCallback<Boolean> callback) {
        callback.on(true);
    }

    @Override
    public void connect(KCallback<Boolean> callback) {
        callback.on(true);
    }

    @Override
    public void disconnect(KCallback<Boolean> callback) {
        callback.on(true);
    }
    
}
