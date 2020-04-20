package pl.maciejowczarczyk.servicemanagement.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DTOUser {

    private Long id;
    private String title;
}
