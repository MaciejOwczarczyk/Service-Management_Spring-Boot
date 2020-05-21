package pl.maciejowczarczyk.servicemanagement.files;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.user.User;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DBFileStorageService {

    private final DBFileRepository dbFileRepository;


    public void storeFile(User user, MultipartFile file, ServiceTicket serviceTicket) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), serviceTicket, user);
            dbFileRepository.save(dbFile);

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));

    }

    public void deleteFile(String id) {
        dbFileRepository.deleteById(id);
    }

    public List<DBFile> loadAllByServiceTicket(ServiceTicket serviceTicket) {
        return dbFileRepository.findAllByServiceTicket(serviceTicket);
    }

    public List<DBFile> loadAllByUser(User user) {
        return dbFileRepository.findAllByUser(user);
    }

}
