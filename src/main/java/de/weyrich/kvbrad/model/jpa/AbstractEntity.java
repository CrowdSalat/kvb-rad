package de.weyrich.kvbrad.model.jpa;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity  {

    @GeneratedValue( generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid"
    )
    @Id
    @Column(name="uuid", updatable = false, nullable = false)
    private String id;

    @CreatedDate
    @Column(name = "date_created", updatable = false, nullable = false)
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "date_modified", nullable = false)
    private Date lastModified;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String guid) {
        this.id = guid;
    }
}
