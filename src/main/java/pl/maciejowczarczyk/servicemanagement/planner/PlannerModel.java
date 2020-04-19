package pl.maciejowczarczyk.servicemanagement.planner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PlannerModel {

    private List<Planner> planners;


}
