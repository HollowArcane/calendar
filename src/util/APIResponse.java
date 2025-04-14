package util;

import io.javalin.http.ContentType;
import io.javalin.http.Context;

public class APIResponse
{
    private String status;
    private int code;
    private Object data;
    private Object error;

    private APIResponse(String status, int code, Object data, Object error)
    {
        this.status = status;
        this.code = code;
        this.data = data;
        this.error = error;
    }

    public static void success(Context context, int code, Object data)
    {
        context.status(code)
               .contentType(ContentType.APPLICATION_JSON)
               .json(new APIResponse("success", code, data, null));
    } 

    public static void error(Context context, int code, Object error)
    {
        context.status(code)
               .contentType(ContentType.APPLICATION_JSON)
               .json(new APIResponse("error", code, null, error));
    } 

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public Object getError() {
        return error;
    }
    
}
