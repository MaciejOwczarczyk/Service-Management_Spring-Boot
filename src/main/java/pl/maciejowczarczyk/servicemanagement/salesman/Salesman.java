package pl.maciejowczarczyk.servicemanagement.salesman;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.company.Company;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "salesmen")
@Getter
@Setter
@RequiredArgsConstructor
public class Salesman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1)
    private String firstName;

    @NotBlank
    @Size(min = 1)
    private String lastName;

    @NotBlank
    @Size(min = 1)
    private String address;

    @NotBlank
    @Size(min = 1)
    private String city;

    @NotBlank
    @Size(min = 1)
    private String postalCode;

    @NotBlank
    @Size(min = 8)
    private String phone;

    @OneToMany(mappedBy = "salesman", fetch = FetchType.EAGER)
    private List<Company> companies;
}
