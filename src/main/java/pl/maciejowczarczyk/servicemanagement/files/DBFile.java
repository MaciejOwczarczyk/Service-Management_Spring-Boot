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

    public DBFile() {
    }

    public DBFile(String fileName, String fileType, byte[] data, ServiceTicket serviceTicket, User user) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.serviceTicket = serviceTicket;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ServiceTicket getServiceTicket() {
        return serviceTicket;
    }

    public void setServiceTicket(ServiceTicket serviceTicket) {
        this.serviceTicket = serviceTicket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
