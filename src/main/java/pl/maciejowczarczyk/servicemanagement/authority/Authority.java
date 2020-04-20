package pl.maciejowczarczyk.servicemanagement.authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.role.Role;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@RequiredArgsConstructor
@Getter
@Setter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "authority", referencedColumnName = "role")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    public Authority(Role role, User user) {
        this.role = role;
        this.user = user;
    }
}
