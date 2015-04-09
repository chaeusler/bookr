package ch.haeuslers.bookr.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.Date;

@BookingCheck
@Entity
@Table(name = "BOOKR_BOOKING")
@NamedQueries({
@NamedQuery(name = Booking.QUERY_FIND_OVERLAPPING,
query = "SELECT b " +
"FROM Booking b " +
"WHERE b.start >= :startDate AND b.end <= :startDate AND b.start >= :endDate AND b.end >= :endDate" +
"   OR b.start <= :startDate AND b.end <= :startDate AND b.start >= :endDate AND b.end <= :endDate"),
@NamedQuery(name = Booking.QUERY_FIND_ALL_FOR_USER, query = "SELECT b FROM Booking b WHERE b.person = :user"),
@NamedQuery(name = Booking.QUERY_FIND_ALL, query = "SELECT b FROM Booking b"),
@NamedQuery(name = Booking.QUERY_FIND_ALL_FROM_DATE, query = "SELECT b FROM Booking b WHERE b.start <= :fromDate"),
@NamedQuery(name = Booking.QUERY_FIND_ALL_FROM_DATE_TO_DATE, query = "SELECT b FROM Booking b WHERE b.start BETWEEN :fromDate AND :toDate")
})

@XmlRootElement(name = "booking")
@XmlAccessorType(XmlAccessType.FIELD)
public class Booking extends BaseEntity {

    public static final String QUERY_FIND_OVERLAPPING = "Booking.findOverlapping";
    public static final String QUERY_FIND_ALL_FOR_USER = "Booking.findAllForUser";
    public static final String QUERY_FIND_ALL = "Booking.findAll";
    public static final String QUERY_FIND_ALL_FROM_DATE = "Booking.findAllFromDate";
    public static final String QUERY_FIND_ALL_FROM_DATE_TO_DATE = "Booking.findAllFromDateToDate";

    @Id
    @XmlAttribute
    private String id;

    @ManyToOne(optional = false)
    @XmlElementRef
    private Project project;

    @ManyToOne(optional = false)
    @XmlElementRef
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (!id.equals(booking.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
