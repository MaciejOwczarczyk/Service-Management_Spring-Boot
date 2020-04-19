package pl.maciejowczarczyk.servicemanagement.serviceTicket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.activity.Activity;
import pl.maciejowczarczyk.servicemanagement.company.Company;
import pl.maciejowczarczyk.servicemanagement.files.DBFile;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "service_tickets")
@Getter
@Setter
@RequiredArgsConstructor
public class ServiceTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = {OpenTicketValidationGroup.class})
    @Size(min = 5, max = 200, groups = {OpenTicketValidationGroup.class})
    private String title;

    @Size(min = 10)
    private String solution;

    private String openDate;

    private String closeDate;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Machine machine;

    @ManyToOne
    private TicketStatus ticketStatus;

    @ManyToOne
    private TicketType ticketType;

    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "serviceTicket")
    private List<DBFile> dbFileList;

    @OneToMany(mappedBy = "serviceTicket")
    private List<Planner> planners;

    @OneToMany(mappedBy = "serviceTicket")
    private List<Activity> activities;
}
