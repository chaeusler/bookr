package ch.haeuslers.bookr.entity;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@BookingCheck
public class Booking {

    @Id
    private String id;

    @OneToOne(optional = false)
    private Project project;

    @OneToOne(optional = false)
    private User user;

    @NotNull
    private String description;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    private boolean datesAreValid() {
        if (start == null || end == null) {
            return false;
        }
        return start.before(end);
    }
}
