package util;

import java.util.List;
import java.util.Map.Entry;

import io.javalin.http.Context;

public class FormData
{
    public static void load(Context context)
    {
        for(Entry<String, List<String>> param: context.formParamMap().entrySet())
        {
            context.attribute(param.getKey(), param.getValue());
        }
    }
}
