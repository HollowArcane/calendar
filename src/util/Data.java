package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Data
{
    @SuppressWarnings("unchecked")
    public static <E, T, L> Map<E, T> asMap(List<L> data, Function<L, E> key, Function<L, T> value)
    {
        HashMap<E, T> map = new HashMap<>();
        for(L item: data)
        { map.put(key.apply(item), value.apply(item)); }

        return map;
    }
}
