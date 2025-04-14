package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

import org.json.JSONObject;

import database.DB;
import io.javalin.http.Context;
import model.Tables;
import model.dto.ReservationDTO;
import model.tables.records.ReservationRecord;
import util.APIResponse;
import util.Renderer;

public class CalendarController
{
    public static void index(Context context)
        throws ClassNotFoundException, SQLException, RuntimeException
    {
        Map<String, Object> data = DB.handle(ctx -> {
            return Map.of(
                "data", ctx.fetch(Tables.V_LABEL_RESERVATION),
                "workspaces", ctx.fetch(Tables.WORKSPACE)
            );
        });
        Renderer.usingDefault()
            .render("calendar/index")
            .with(context, data);
    }

    public static void store(Context context)
        throws ClassNotFoundException,
                SQLException,
                RuntimeException
    {
        JSONObject object = new JSONObject(context.body());
        ReservationDTO dto = new ReservationDTO();
        dto.setTitle(object.getString("title"));
        dto.setDateStart(LocalDateTime.parse(object.getString("dateStart")));
        dto.setDateEnd(LocalDateTime.parse(object.getString("dateEnd")));
        dto.setIdWorkspace(object.getInt("idWorkspace"));

        DB.handle(ctx -> {
            ReservationRecord record = dto.toRecord(ctx);
            return record.store();
        });

        APIResponse.success(context, 201, Map.of(
            "message", "Data inserted successfuly"
        ));
    }
}