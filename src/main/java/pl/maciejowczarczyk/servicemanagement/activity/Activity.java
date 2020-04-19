package pl.maciejowczarczyk.servicemanagement.activity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;

@Entity
@Table(name = "activities")
@RequiredArgsConstructor
@Getter
@Setter
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;

    private String startDriveFromBase;

    private String arriveOnSite;

    private String startWorkOnSite;

    private String finishWorkOnSite;

    private String startDriveFromSite;

    private String arriveToBase;

    private String rest;

    private Long kilometersToSite;

    private Long kilometersToBase;

    private String description;

    @ManyToOne
    private User user;

    @ManyToOne
    private Planner planner;

    @ManyToOne
    private ServiceTicket serviceTicket;

}
