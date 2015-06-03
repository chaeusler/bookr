package ch.haeuslers.bookr.booking.api;

import ch.haeuslers.bookr.core.api.ValidUUID;
import ch.haeuslers.bookr.person.api.Person;
import ch.haeuslers.bookr.person.api.PersonReferenceXmlAdapter;
import ch.haeuslers.bookr.project.api.Project;
import ch.haeuslers.bookr.project.api.ProjectReferenceXmlAdapter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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
@NamedQuery(name = Booking.QUERY_FIND_ALL_FOR_USERNAME, query = "SELECT b FROM Booking b WHERE b.person.name = :username"),
@NamedQuery(name = Booking.QUERY_FIND_ALL, query = "SELECT b FROM Booking b"),
@NamedQuery(name = Booking.QUERY_FIND_ALL_FROM_DATE, query = "SELECT b FROM Booking b WHERE b.start <= :fromDate"),
@NamedQuery(name = Booking.QUERY_FIND_ALL_FROM_DATE_TO_DATE, query = "SELECT b FROM Booking b WHERE b.start BETWEEN :fromDate AND :toDate")
})

@XmlRootElement(name = "booking")
@XmlAccessorType(XmlAccessType.FIELD)
public class Booking implements Serializable {

    public static final String QUERY_FIND_OVERLAPPING = "Booking.findOverlapping";
    public static final String QUERY_FIND_ALL_FOR_USER = "Booking.findAllForUser";
    public static final String QUERY_FIND_ALL_FOR_USERNAME = "Booking.findAllForUsername";
    public static final String QUERY_FIND_ALL = "Booking.findAll";
    public static final String QUERY_FIND_ALL_FROM_DATE = "Booking.findAllFromDate";
    public static final String QUERY_FIND_ALL_FROM_DATE_TO_DATE = "Booking.findAllFromDateToDate";

    @Id
    @XmlAttribute
    @ValidUUID
    private String id;

    @ManyToOne(optional = false)
    @XmlElementRef(name = "project-id")
    @XmlJavaTypeAdapter(ProjectReferenceXmlAdapter.class)
    private Project project;

    @ManyToOne(optional = false)
    @XmlElementRef(name = "person-id")
    @XmlJavaTypeAdapter(PersonReferenceXmlAdapter.class)
    private Person person;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime start;

    @NotNull
    private LocalDateTime end;

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

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
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
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}