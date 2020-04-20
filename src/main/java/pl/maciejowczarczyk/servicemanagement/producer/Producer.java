package pl.maciejowczarczyk.servicemanagement.producer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.country.Country;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "producers")
@RequiredArgsConstructor
@Getter
@Setter
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotBlank
    @Size(min = 4)
    private String address;

    @NotBlank
    @Size(min = 3)
    private String city;

    @NotBlank
    @Size(min = 3)
    private String postalCode;

    @NotBlank
    @Size(min = 9)
    private String phone;

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "producer", fetch = FetchType.EAGER)
    private List<Machine> machines;

    @NotBlank
    @Size(min = 10)
    private String vatNumber;

    @NotBlank
    @Email
    private String email;
}
