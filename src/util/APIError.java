package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import io.javalin.http.Context;
import io.javalin.validation.ValidationError;
import io.javalin.validation.ValidationException;

public class APIError
{
    public static void any(Exception exception, Context context)
    {
        exception.printStackTrace();
        APIResponse.error(context, 500, Map.of("message", Optional.ofNullable(exception.getMessage()).orElse("")));
    }

    public static void validation(ValidationException exception, Context context)
    {
        HashMap<String, String> errors = new HashMap<>();

        for(Entry<String, List<ValidationError<Object>>> error: exception.getErrors().entrySet())
        { errors.put(error.getKey(), error.getValue().get(0).getMessage()); }

        APIResponse.error(context, 400, Map.ofEntries(
            Map.entry("message", Optional.ofNullable(exception.getMessage()).orElse("")),
            Map.entry("details", errors)
        ));
    }
}
