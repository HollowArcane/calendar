package util;

import java.util.Enumeration;
import java.util.HashMap;

import io.javalin.http.Context;
import jakarta.servlet.http.HttpServletRequest;

public class Request
{
    public static HashMap<String, Object> getParameterMap(Context context)
    {
        HttpServletRequest request = context.req(); 

        HashMap<String, Object> parameters = new HashMap<>();
        for(Enumeration<String> keys = request.getParameterNames(); keys.hasMoreElements();)
        {
            String key = keys.nextElement();
            if(key.endsWith("[]"))
            { parameters.put(key, context.formParam(key)); }
            else
            { parameters.put(key, context.formParams(key).toArray(String[]::new)); }
        }
        return parameters;
    }
}
