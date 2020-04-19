package pl.maciejowczarczyk.servicemanagement.machine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.maciejowczarczyk.servicemanagement.company.Company;
import pl.maciejowczarczyk.servicemanagement.machineType.MachineType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "machines")
@RequiredArgsConstructor
@Getter
@Setter
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3)
    private String model;

    @NotBlank
    @Size(min = 2)
    private String serialNumber;


    private Long yearOfProduction;

    @NotNull
    @ManyToOne
    private Company company;

    @NotNull
    @ManyToOne
    private Producer producer;

    @NotNull
    @ManyToOne
    private MachineType machineType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate warrantyEnd;

}
