package pl.maciejowczarczyk.servicemanagement.company;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.NIP;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;
import pl.maciejowczarczyk.servicemanagement.province.Province;
import pl.maciejowczarczyk.servicemanagement.salesman.Salesman;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "companies")
@RequiredArgsConstructor
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @Size(min = 2, groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    private String name;

    @NotBlank(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @Size(min = 2, groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    private String address;

    @NotBlank(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @Size(min = 2, groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    private String city;

    @NotBlank(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @Size(min = 6, max = 6, groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    private String postalCode;

    @NotBlank(groups = {CompanyValidationGroup.class})
    @Size(min = 4, groups = {CompanyValidationGroup.class})
    private String cellPhone;

    @NotBlank(groups = {CompanyValidationGroup.class})
    @Size(min = 4, groups = {CompanyValidationGroup.class})
    @Email(groups = {CompanyValidationGroup.class})
    @Column(unique = true)
    private String email;

    @NIP(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @NotBlank(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @Column(unique = true)
    private String nip;


    @NotNull(groups = {CompanyValidationGroup.class})
    @ManyToOne
    private Salesman salesman;

    @NotNull(groups = {CompanyValidationGroup.class, ProspectValidationGroup.class})
    @ManyToOne
    private Province province;

    @OneToMany(mappedBy = "company")
    private List<Machine> machines;

    @OneToMany(mappedBy = "company")
    private List<ServiceTicket> serviceTickets;

    private boolean prospect;

}
