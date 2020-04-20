package pl.maciejowczarczyk.servicemanagement.files;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@RequiredArgsConstructor
@Setter
@Getter
public class DBFile {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;

    private String fileType;

    private LocalDateTime created = LocalDateTime.now();

    @Lob
    private byte[] data;

    @ManyToOne
    private ServiceTicket serviceTicket;

    @ManyToOne
    private User user;

    public DBFile(String fileName, String fileType, byte[] data, ServiceTicket serviceTicket, User user) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.serviceTicket = serviceTicket;
        this.user = user;
    }
}
