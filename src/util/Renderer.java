package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import io.javalin.http.Context;

public class Renderer
{
    private static final String PAGE_ROOT = "/pages/";
    private static final String LAYOUT_ROOT = "/layouts/";

    private static final String DEFAULT_LAYOUT = "main";

    private String layout;
    private String page;
    private String title;

    private Renderer()
    {
        this.layout = DEFAULT_LAYOUT;
        this.title = "<insert-title-here>";
    }

    public static Renderer using(String layout)
    {
        Objects.requireNonNull(layout);
        
        Renderer renderer = new Renderer();
        renderer.layout = layout;

        return renderer;
    }

    public static Renderer usingDefault()
    { return new Renderer(); }

    public Renderer render(String page)
    {
        Objects.requireNonNull(page);
        this.page = page;
        return this;
    }

    public Renderer title(String title)
    {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }

    public void with(Context context, Map<String, Object> parameters)
    {
        Objects.requireNonNull(context);
        Objects.requireNonNull(parameters);

        Map<String, Object> additionals = new HashMap<>(parameters); 
        additionals.put("page", PAGE_ROOT + page);
        additionals.put("title", title);

        additionals.putAll(context.attributeMap());

        context.render(LAYOUT_ROOT + layout, additionals);
    }

    public void with(Context context)
    {
        Objects.requireNonNull(context);

        Map<String, Object> additionals = new HashMap<>(); 
        additionals.put("page", PAGE_ROOT + page);
        additionals.put("title", title);

        additionals.putAll(context.attributeMap());

        context.render(LAYOUT_ROOT + layout, additionals);
    }
}
