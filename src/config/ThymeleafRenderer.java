package config;

import io.javalin.rendering.FileRenderer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

public class ThymeleafRenderer implements FileRenderer
{

    private final TemplateEngine templateEngine;

    public ThymeleafRenderer()
    {
        // Configure the template resolver
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("thymeleaf/"); // Templates directory in resources
        templateResolver.setSuffix(".html"); // File extension
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);

        // Create and configure the template engine
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String render(String filePath, Map<String, ?> model, io.javalin.http.Context contentType)
    {
        // Create the Thymeleaf context
        Context context = new Context();
        context.setVariables((Map<String, Object>)model);

        // Render the template
        return templateEngine.process(filePath, context);
    }
}