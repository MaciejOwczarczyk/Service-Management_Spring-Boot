package pl.maciejowczarczyk.servicemanagement.role;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@RequiredArgsConstructor
@Getter
@Setter
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
