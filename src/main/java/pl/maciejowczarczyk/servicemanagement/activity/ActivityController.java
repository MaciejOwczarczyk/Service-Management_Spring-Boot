package pl.maciejowczarczyk.servicemanagement.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.maciejowczarczyk.servicemanagement.files.DBFileRepository;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.planner.PlannerRepository;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicket;
import pl.maciejowczarczyk.servicemanagement.serviceTicket.ServiceTicketRepository;
import pl.maciejowczarczyk.servicemanagement.user.CurrentUser;
import pl.maciejowczarczyk.servicemanagement.user.User;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Currency;

@Controller
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ServiceTicketRepository serviceTicketRepository;
    private final ActivityRepository activityRepository;
    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;


    @GetMapping("/add/{plannerId}")
    public String add(@PathVariable Long plannerId, Model model) {
        model.addAttribute("activity", new Activity());
        model.addAttribute("planner", plannerRepository.findAllById(plannerId));
        return "activity/addActivity";
    }

    @PostMapping("/add/{plannerId}")
    public String add(@AuthenticationPrincipal UserDetails customUser,
                      @ModelAttribute Activity activity, @RequestParam Long plannerId,
                      Model model) {
        try {
            ServiceTicket serviceTicket = serviceTicketRepository.findAllByPlanners(plannerRepository.findAllById(plannerId));
            Planner planner = plannerRepository.findAllById(plannerId);
            String parseStart = planner.getStart().substring(0, 10);
            String parseEnd = planner.getEnd().substring(0, 10);
            LocalDate parseStartToLocalDate = LocalDate.parse(parseStart);
            LocalDate parseEndToLocalDate = LocalDate.parse(parseEnd);

            LocalTime parseStartFromBase = LocalTime.parse(activity.getStartDriveFromBase());
            LocalTime parseArriveOnSite = LocalTime.parse(activity.getArriveOnSite());
            LocalTime parseStartWorkOnSite = LocalTime.parse(activity.getStartWorkOnSite());
            LocalTime parseFinishWorkOnSite = LocalTime.parse(activity.getFinishWorkOnSite());
            LocalTime parseStartDriveFromSite = LocalTime.parse(activity.getStartDriveFromSite());
            LocalTime parseArriveToBase = LocalTime.parse(activity.getArriveToBase());
            LocalDate parseDate = LocalDate.parse(activity.getDate());

            if (parseDate.isBefore(parseStartToLocalDate) || parseDate.isAfter(parseEndToLocalDate)) {
                model.addAttribute("wrongDate", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else if (parseArriveOnSite.isBefore(parseStartFromBase)) {
                model.addAttribute("arriveOnSiteBeforeStart", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else if (parseStartWorkOnSite.isBefore(parseArriveOnSite)) {
                model.addAttribute("startWorkBeforeArrival", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else if (parseFinishWorkOnSite.isBefore(parseStartWorkOnSite)) {
                model.addAttribute("finishWorkOnSiteBeforeStartWork", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else if (parseStartDriveFromSite.isBefore(parseFinishWorkOnSite)) {
                model.addAttribute("startDriveFromSiteBeforeFinishWork", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else if (parseArriveToBase.isBefore(parseStartDriveFromSite)) {
                model.addAttribute("arriveToBaseBeforeStartFromSite", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else if ("".equals(activity.getDescription())) {
                model.addAttribute("descriptionFail", true);
                model.addAttribute("planner", plannerRepository.findAllById(plannerId));
                return "activity/addActivity";
            } else {
                activity.setUser(userRepository.findAllByUsername(customUser.getUsername()));
                activity.setServiceTicket(serviceTicket);
                activity.setPlanner(planner);
                activityRepository.save(activity);
                return "redirect:../../serviceTicket/details/" + serviceTicket.getId();
            }

        } catch (DateTimeParseException e) {
            model.addAttribute("parseTimeFail", true);
            model.addAttribute("plannerId", plannerId);
            return "activity/addActivity";
        }

    }

    @GetMapping("/edit/{id}")
    public String editActivity(@PathVariable Long id, Model model) {
        Activity activity = activityRepository.findAllById(id);
        model.addAttribute("activity", activity);
        model.addAttribute("plannerId", activity.getPlanner().getId());
        model.addAttribute("planner", plannerRepository.findAllById(id));
        return "activity/addActivity";
    }

    @PostMapping("/edit/{id}")
    public String editActivity(@AuthenticationPrincipal UserDetails customUser,
                               @ModelAttribute Activity activity,
                               @RequestParam Long plannerId, Model model) {
        try {

            Planner planner = plannerRepository.findAllById(plannerId);
            String parseStart = planner.getStart().substring(0, 10);
            String parseEnd = planner.getEnd().substring(0, 10);
            LocalDate parseStartToLocalDate = LocalDate.parse(parseStart);
            LocalDate parseEndToLocalDate = LocalDate.parse(parseEnd);

            LocalTime parseStartFromBase = LocalTime.parse(activity.getStartDriveFromBase());
            LocalTime parseArriveOnSite = LocalTime.parse(activity.getArriveOnSite());
            LocalTime parseStartWorkOnSite = LocalTime.parse(activity.getStartWorkOnSite());
            LocalTime parseFinishWorkOnSite = LocalTime.parse(activity.getFinishWorkOnSite());
            LocalTime parseStartDriveFromSite = LocalTime.parse(activity.getStartDriveFromSite());
            LocalTime parseArriveToBase = LocalTime.parse(activity.getArriveToBase());
            LocalDate parseDate = LocalDate.parse(activity.getDate());

            if (parseDate.isBefore(parseStartToLocalDate) || parseDate.isAfter(parseEndToLocalDate)) {
                model.addAttribute("wrongDate", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else if (parseArriveOnSite.isBefore(parseStartFromBase)) {
                model.addAttribute("arriveOnSiteBeforeStart", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else if (parseStartWorkOnSite.isBefore(parseArriveOnSite)) {
                model.addAttribute("startWorkBeforeArrival", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else if (parseFinishWorkOnSite.isBefore(parseStartWorkOnSite)) {
                model.addAttribute("finishWorkOnSiteBeforeStartWork", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else if (parseStartDriveFromSite.isBefore(parseFinishWorkOnSite)) {
                model.addAttribute("startDriveFromSiteBeforeFinishWork", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else if (parseArriveToBase.isBefore(parseStartDriveFromSite)) {
                model.addAttribute("arriveToBaseBeforeStartFromSite", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else if ("".equals(activity.getDescription())) {
                model.addAttribute("descriptionFail", true);
                model.addAttribute("plannerId", plannerId);
                model.addAttribute("planner", planner);
                return "activity/addActivity";
            } else {
                activity.setPlanner(planner);
                activity.setServiceTicket(activityRepository.findAllById(activity.getId()).getServiceTicket());
                activity.setUser(userRepository.findAllByUsername(customUser.getUsername()));
                activityRepository.save(activity);
                return "redirect:../../serviceTicket/details/" + activity.getServiceTicket().getId();
            }

        } catch (DateTimeParseException e) {
            model.addAttribute("parseTimeFail", true);
            model.addAttribute("plannerId", plannerId);
            return "activity/addActivity";
        }
    }

    @GetMapping("/delete/{id}")
    public String editActivity(@PathVariable Long id) {
        Activity activity = activityRepository.findAllById(id);
        Long serviceTicketId = activity.getServiceTicket().getId();
        activityRepository.delete(activity);
        return "redirect:../../serviceTicket/details/" + serviceTicketId;
    }

    @ModelAttribute("currentDate")
    public LocalDate currentDate() {
        return LocalDate.now();
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }
}
