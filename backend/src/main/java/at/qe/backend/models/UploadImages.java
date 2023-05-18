package at.qe.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "uploaded_images")
public class UploadImages implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long plantId;

    @Column(nullable = false)
    private String uploadLink;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    public boolean isNew() {
        return uploadDate == null;
    }
}
