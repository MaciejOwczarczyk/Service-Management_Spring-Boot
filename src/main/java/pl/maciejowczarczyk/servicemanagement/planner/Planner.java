package pl.maciejowczarczyk.servicemanagement.planner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;

@Entity
@Table(name = "planner")
@Getter
@Setter
@RequiredArgsConstructor
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String start;

    private String end;

    @ManyToOne
    private ServiceTicket serviceTicket;

}
