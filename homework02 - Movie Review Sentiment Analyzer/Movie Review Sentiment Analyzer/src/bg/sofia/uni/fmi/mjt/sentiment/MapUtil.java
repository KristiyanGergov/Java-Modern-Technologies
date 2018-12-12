package bg.sofia.uni.fmi.mjt.sentiment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.reverseOrder;

public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, int n) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(reverseOrder(Map.Entry.comparingByValue()));
        Map<K, V> result = new LinkedHashMap<>();

        int count = 0;
        for (Map.Entry<K, V> entry : list) {
            if (count++ == n)
                break;
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}