package pl.maciejowczarczyk.servicemanagement.planner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class PlannerDTO {

    private Long id;
    private Long resourceId;
    private String start;
    private String end;
    private String title;

}
