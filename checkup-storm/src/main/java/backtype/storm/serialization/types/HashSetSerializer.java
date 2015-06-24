package backtype.storm.serialization.types;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import java.util.Collection;
import java.util.HashSet;


public class HashSetSerializer extends CollectionSerializer {
    @Override
    public Collection create(Kryo kryo, Input input, Class<Collection> type) {
        return new HashSet();
    }       
}
