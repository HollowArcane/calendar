package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.javalin.http.Context;

public class Flashdata
{
    private Flashdata() {}

    public static void set(Context context, String key, Object value)
    {
        @SuppressWarnings("unchecked")
        Map<String, Object> flashdata = (Map<String, Object>)context.sessionAttribute("flashdata");
        if(flashdata == null)
        {
            flashdata = new HashMap<String, Object>();
            context.sessionAttribute("flashdata", flashdata);
        }

        flashdata.put(key, value);
    }    

    public static void load(Context context)
    {
        @SuppressWarnings("unchecked")
        Map<String, Object> flashdata = (Map<String, Object>)context.sessionAttribute("flashdata");
        if(flashdata == null)
        { return; }
        
        for(Entry<String, Object> data: flashdata.entrySet())
        {
            context.sessionAttribute(data.getKey(), null);
            context.attribute(data.getKey(), data.getValue());
        }
    }    
}
