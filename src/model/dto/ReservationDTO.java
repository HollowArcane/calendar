package model.dto;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Result;

import model.Tables;
import model.tables.records.ReservationRecord;

public class ReservationDTO
{
    private Integer id;
    private String title;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Integer idWorkspace;

    public void setTitle(String title)
    { this.title = title; }

    public void setDateStart(LocalDateTime dateStart)
    { this.dateStart = dateStart; }

    public void setDateEnd(LocalDateTime dateEnd)
    { this.dateEnd = dateEnd; }

    public void setIdWorkspace(Integer idWorkspace)
    { this.idWorkspace = idWorkspace; }

    public ReservationRecord toRecord(DSLContext context)
    {
        ReservationRecord record = id == null ?
            context.newRecord(Tables.RESERVATION):
            context.fetchOne(Tables.RESERVATION, Tables.RESERVATION.ID.equal(id));
        
        record.setTitle(title);
        record.setDateStart(dateStart);
        record.setDateEnd(dateEnd);
        record.setIdWorkspace(idWorkspace);

        Result<ReservationRecord> overlap = getOverlap(context, record);
        if(overlap.size() > 0)
        { throw new IllegalArgumentException("Workspace is already used from " + overlap.get(0).getDateStart() + " to " + overlap.get(0).getDateEnd()); }

        return record;
    }

    private static Result<ReservationRecord> getOverlap(DSLContext ctx, ReservationRecord record)
    {
        return ctx.selectFrom(Tables.RESERVATION)
            .where(
                    Tables.RESERVATION.ID_WORKSPACE
                        .equal(record.getIdWorkspace())
                .and(
                    Tables.RESERVATION.DATE_END
                        .greaterThan(record.getDateStart()
                ).and(
                    Tables.RESERVATION.DATE_START
                        .lessThan(record.getDateEnd())
                )
            )
        ).fetch();
    }
}
