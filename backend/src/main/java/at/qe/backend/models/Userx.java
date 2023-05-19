package at.qe.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
public class Userx implements Persistable<Long>, Serializable, Comparable<Userx> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,length = 100)
    private String username;
    @CreatedBy
    private String createUserUsername;
    @CreatedDate
    private Date createDate;
    @LastModifiedBy
    private String updateUserUsername;
    @LastModifiedDate
    private Date updateDate;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    boolean enabled;
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Userx_User_Role", joinColumns = @JoinColumn(name = "userx_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Greenhouse> greenhouses = new HashSet<>();

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
        if (!(obj instanceof Userx other)) {
            return false;
        }
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return "at.qe.skeleton.model.User[ idInCluster=" + username + " ]";
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }

    @Override
    public int compareTo(Userx o) {
        return this.username.compareTo(o.getUsername());
    }

    @PreRemove
    private void preRemove() {
        //Set greenhouse owner to null so a new one can be assigned
        for (var greenhouse : greenhouses) {
            greenhouse.setOwner(null);
        }
    }


}
