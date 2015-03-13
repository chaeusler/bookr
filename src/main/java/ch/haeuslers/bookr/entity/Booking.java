package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@BookingCheck
@Entity
@Table(name = "BOOKR_BOOKING")
@NamedQueries({
@NamedQuery(name = "Booking.findOverlapping",
query = "SELECT b " +
"FROM Booking b " +
"WHERE b.start >= :startDate AND b.end <= :startDate AND b.start >= :endDate AND b.end >= :endDate" +
"   OR b.start <= :startDate AND b.end <= :startDate AND b.start >= :endDate AND b.end <= :endDate"),
@NamedQuery(name = "Booking.findAllForUser", query = "SELECT b FROM Booking b WHERE b.person = :user"),
@NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
@NamedQuery(name = "Booking.findAllFromDate", query = "SELECT b FROM Booking b WHERE b.start <= :fromDate"),
@NamedQuery(name = "Booking.findAllFromDateToDate", query = "SELECT b FROM Booking b WHERE b.start BETWEEN :fromDate AND :toDate")
})

public class Booking extends BaseEntity {

    @Id
    public String id;

    @OneToOne(optional = false)
    private Project project;

    @OneToOne(optional = false)
    private Person person;

    @NotNull
    private String description;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
