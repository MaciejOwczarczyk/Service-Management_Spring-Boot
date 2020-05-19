package pl.maciejowczarczyk.servicemanagement.confirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken findByConfirmationToken(String token);
    void saveConfirmationToken(ConfirmationToken confirmationToken);
}
