package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonFileManager {

    LinkedHashMap<String, Object> data;

    @SuppressWarnings("null")
    public JsonFileManager(String filePath) {
        try (Reader reader = new FileReader(filePath)) {

            LinkedHashMap<String, Object> parsed = new Gson().fromJson(
                    reader,
                    new TypeToken<LinkedHashMap<String, Object>>() {
                    }.getType());
            if (parsed == null) {
                data = (LinkedHashMap<String, Object>) parsed.get("data");
            }

            Object inner = parsed.get("data");
            if (inner instanceof Map) {
                data = new LinkedHashMap<>((Map<String, Object>) inner);
            } else {
                data = parsed;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   public  Object getValue(String field) {
       if (data.containsKey(field)) {
           return data.get(field);
       }
       return null;
   }


    public String cleanNumber(Object v) {
        if (v == null) return "";
        if (v instanceof Number) {
            double d = ((Number) v).doubleValue();
            if (d == Math.rint(d)) return String.valueOf((long) d); // 200 بدل 200.0
            return String.valueOf(d);
        }
        return v.toString();
    }
}
