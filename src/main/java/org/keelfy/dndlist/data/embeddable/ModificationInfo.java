package org.keelfy.dndlist.data.embeddable;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.keelfy.dndlist.data.Credentials;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

/**
 * @author Egor Kuzmin
 */
@Data
@Embeddable
public class ModificationInfo {

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    private Credentials createdBy;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "last_modified_by_id", referencedColumnName = "id")
    private Credentials lastModifiedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

}
