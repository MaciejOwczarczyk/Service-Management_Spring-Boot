package pl.maciejowczarczyk.servicemanagement.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import pl.maciejowczarczyk.servicemanagement.machineType.MachineType;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "skills")
@Getter
@Setter
@RequiredArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(value = 100)
    @Min(value = 0)
    private Long skillLevel;

    @ManyToOne
    private MachineType machineType;

    @ManyToOne
    private User user;
}
