import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.validation.ValidationException;
import util.APIError;
import util.Flashdata;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

import java.time.LocalDate;
import config.ThymeleafRenderer;
import controller.CalendarController;

public class Main
{
    public static void main(String[] args)
        throws Exception
    {
        
        /*
         * model: jooq
         * controller: javalin
         * view: thymeleaf
         */

        Javalin app = Javalin.create(config -> {
            // BASIC CONFIGURATION
            configMiddleware(config);

            // ROUTE
            config.router.apiBuilder(Main::route);
        }).start(7000);

        // PRE REQUEST PROCESSING
        app.before(Flashdata::load);

        // HANDLING EXCEPTION
        app.exception(Exception.class, APIError::any)
           .exception(ValidationException.class, APIError::validation);
    }

    private static void route()
    {
        get("/", CalendarController::index);
        post("/", CalendarController::store);
    }

    private static void configMiddleware(JavalinConfig config)
    {
        config.validation.register(LocalDate.class, LocalDate::parse);
        config.staticFiles.add("static");
        config.fileRenderer(new ThymeleafRenderer());
    }
}