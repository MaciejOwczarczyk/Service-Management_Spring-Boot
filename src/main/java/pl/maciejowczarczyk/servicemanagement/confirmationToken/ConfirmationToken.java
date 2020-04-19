package pl.maciejowczarczyk.servicemanagement.confirmationToken;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "confirmation_token")
@RequiredArgsConstructor
@Getter
@Setter
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;


}
