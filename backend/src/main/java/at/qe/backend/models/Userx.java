package at.qe.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
public class Userx implements Persistable<String>, Serializable, Comparable<Userx> {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 100)
    private String username;
    @ManyToOne(optional = false)
    private Userx createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate=new Date();
    @ManyToOne(optional = true)
    private Userx updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    boolean enabled;
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Userx_UserRole")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "owner")
    private Set<Greenhouse> greenhouses;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Userx)) {
            return false;
        }
        final Userx other = (Userx) obj;
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return "at.qe.skeleton.model.User[ id=" + username + " ]";
    }

    @Override
    public String getId() {
        return getUsername();
    }

    public void setId(String id) {
        setUsername(id);
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }

    @Override
    public int compareTo(Userx o) {
        return this.username.compareTo(o.getUsername());
    }

}
