package pl.maciejowczarczyk.servicemanagement.serviceTicket;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.maciejowczarczyk.servicemanagement.activity.Activity;
import pl.maciejowczarczyk.servicemanagement.activity.ActivityServiceImpl;
import pl.maciejowczarczyk.servicemanagement.company.Company;
import pl.maciejowczarczyk.servicemanagement.company.CompanyServiceImpl;
import pl.maciejowczarczyk.servicemanagement.files.DBFile;
import pl.maciejowczarczyk.servicemanagement.files.DBFileStorageService;
import pl.maciejowczarczyk.servicemanagement.machine.Machine;
import pl.maciejowczarczyk.servicemanagement.machine.MachineServiceImpl;
import pl.maciejowczarczyk.servicemanagement.planner.Planner;
import pl.maciejowczarczyk.servicemanagement.planner.PlannerServiceImpl;
import pl.maciejowczarczyk.servicemanagement.ticketStatus.TicketStatus;
import pl.maciejowczarczyk.servicemanagement.ticketStatus.TicketStatusServiceImpl;
import pl.maciejowczarczyk.servicemanagement.ticketType.TicketType;
import pl.maciejowczarczyk.servicemanagement.ticketType.TicketTypeRepository;
import pl.maciejowczarczyk.servicemanagement.user.User;
import pl.maciejowczarczyk.servicemanagement.user.UserRepository;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/serviceTicket")
@SessionAttributes({"ticketId", "serviceTicket"})
@RequiredArgsConstructor
public class ServiceTicketController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ServiceTicketServiceImpl serviceTicketService;
    private final CompanyServiceImpl companyService;
    private final TicketTypeRepository ticketTypeRepository;
    private final MachineServiceImpl machineService;
    private final UserRepository userRepository;
    private final TicketStatusServiceImpl ticketStatusService;
    private final DBFileStorageService dbFileStorageService;
    private final PlannerServiceImpl plannerService;
    private final ActivityServiceImpl activityService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/addStep1")
    public String add(Model model, HttpSession session) {
        ServiceTicket serviceTicket = (ServiceTicket) session.getAttribute("serviceTicket");
        if (serviceTicket == null) {
            model.addAttribute("serviceTicket", new ServiceTicket());
            return "serviceTicket/addTicketStep1";
        }
        model.addAttribute("serviceTicket", serviceTicket);
        return "serviceTicket/addTicketStep1";
    }

    @GetMapping("/addStep2")
    public String add(@AuthenticationPrincipal UserDetails customUser,
                      @ModelAttribute @Validated({OpenTicketValidationGroup.class})
                              ServiceTicket serviceTicket,
                      BindingResult result,
                      Model model) {

        if (null == serviceTicket.getCompany().getId()) {
            model.addAttribute("companyFail", true);
            return "serviceTicket/addTicketStep1";
        } else if (result.hasErrors()) {
            return "serviceTicket/addTicketStep1";
        }

        List<User> users = Collections.singletonList(userRepository.findByUsername(customUser.getUsername()));
        serviceTicket.setUsers(users);
        serviceTicket.setTicketStatus(ticketStatusService.findTicketStatusByName("Open"));
        model.addAttribute("machines", findMachines(serviceTicket));
        model.addAttribute("serviceTicket", serviceTicket);
        return "serviceTicket/addTicketStep2";
    }

    @GetMapping("/addStep3")
    public String addNext(@ModelAttribute ServiceTicket serviceTicket, BindingResult result, Model model) {

        if (serviceTicket.getMachine().getId() == null) {
            model.addAttribute("machineFail", true);
            model.addAttribute("machines", findMachines(serviceTicket));
            return "serviceTicket/addTicketStep2";
        } else if (serviceTicket.getTicketType().getId() == null) {
            model.addAttribute("ticketTypeFail", true);
            model.addAttribute("machines", findMachines(serviceTicket));
            return "serviceTicket/addTicketStep2";
        } else {
            serviceTicket.setOpenDate(LocalDateTime.now().format(formatter));
            serviceTicketService.saveServiceTicket(serviceTicket);
            model.addAttribute("serviceTicket", new ServiceTicket());
            return "redirect:showAllOpen";
        }
    }

    @GetMapping("/toClose/{id}")
    public String toClose(@PathVariable Long id) {
        ServiceTicket serviceTicket = serviceTicketService.findServiceTicketById(id);
        serviceTicket.setTicketStatus(ticketStatusService.findTicketStatusByName("To Close"));
        serviceTicketService.saveServiceTicket(serviceTicket);
        return "redirect:../showAllOpen";
    }

    @GetMapping("/close/{id}")
    public String close(@PathVariable Long id, Model model) {
        ServiceTicket serviceTicket = serviceTicketService.findServiceTicketById(id);
        model.addAttribute("serviceTicket", serviceTicket);
        model.addAttribute("company", serviceTicket.getCompany());
        return "serviceTicket/closeServiceTicket";
    }

    @PostMapping("/close/{id}")
    public String close(@ModelAttribute ServiceTicket serviceTicket, BindingResult result, Model model) {
        if (serviceTicket.getSolution().length() < 10) {
            model.addAttribute("closeFailed", true);
            model.addAttribute("serviceTicket", serviceTicket);
            return "serviceTicket/closeServiceTicket";
        }

        ServiceTicket serviceTicket1 = serviceTicketService.findServiceTicketById(serviceTicket.getId());
        serviceTicket1.setSolution(serviceTicket.getSolution());
        serviceTicket1.setTicketStatus(ticketStatusService.findTicketStatusByName("Closed"));
        serviceTicket1.setCloseDate(LocalDateTime.now().format(formatter));
        serviceTicketService.saveServiceTicket(serviceTicket1);
        return "redirect:../showAllOpen";
    }

    @GetMapping("/showAllOpen")
    public String showAllOpen(@AuthenticationPrincipal UserDetails customUser, Model model) {
        Set<ServiceTicket> serviceTickets = new HashSet<>();
        boolean check = customUser.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));
        if (check) {
            serviceTickets = serviceTicketService.findAllServiceTicketsByTicketStatusName("Open");
            model.addAttribute("serviceTickets", serviceTickets);
        } else {
            List<Planner> plannerList = plannerService.findAllPlanners();
            for (Planner planner : plannerList) {
                if (planner.getUser().getUsername().equals(customUser.getUsername())) {
                    serviceTickets.add(planner.getServiceTicket());
                }
            }
            List<ServiceTicket> tempServiceTicketList = serviceTickets.stream().filter(o -> o.getTicketStatus().getName().equals("Open")).collect(Collectors.toList());
            model.addAttribute("serviceTickets", tempServiceTicketList);
        }
        Set<ServiceTicket> serviceTicketSet = serviceTicketService.findAllServiceTicketsByTicketStatusName("To close");
        if (serviceTicketSet.size() != 0) {
            model.addAttribute("size", true);
        }
        return "serviceTicket/showAllOpenServiceTickets";
    }

    @GetMapping("/showAllToClose")
    public String showAllToClose(Model model) {
        Set<ServiceTicket> serviceTickets = serviceTicketService.findAllServiceTicketsByTicketStatusName("To close");
        model.addAttribute("serviceTickets", serviceTickets);
        return "serviceTicket/showAllToCloseTicket";
    }

    @GetMapping("/showAllClosed")
    public String showAllClosed(Model model) {
        Set<ServiceTicket> serviceTickets = serviceTicketService.findAllServiceTicketsByTicketStatusName("Closed");
        model.addAttribute("serviceTickets", serviceTickets);
        return "serviceTicket/showAllClosedServiceTickets";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ServiceTicket serviceTicket = serviceTicketService.findServiceTicketById(id);
        model.addAttribute("serviceTicket", serviceTicket);
        model.addAttribute("machines", findMachines(serviceTicket));
        Company company = companyService.findCompanyByServiceTicket(serviceTicket);
        model.addAttribute("company", company);
        return "serviceTicket/editServiceTicket";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute ServiceTicket serviceTicket) {
        serviceTicketService.saveServiceTicket(serviceTicket);
        return "redirect:../details/" + id;
    }

    @GetMapping("/editClosed/{id}")
    public String editClosed(@PathVariable Long id, Model model) {
        model.addAttribute("serviceTicket", serviceTicketService.findServiceTicketById(id));
        return "serviceTicket/editClosedTicket";
    }

    @PostMapping("/editClosed/{id}")
    public String editClosed(@PathVariable Long id, @ModelAttribute ServiceTicket serviceTicket) {
        ServiceTicket serviceTicket1 = serviceTicketService.findServiceTicketById(serviceTicket.getId());
        serviceTicket1.setTicketStatus(serviceTicket.getTicketStatus());
        serviceTicket1.setSolution(null);
        serviceTicketService.saveServiceTicket(serviceTicket1);
        return "redirect:../showAllClosed";
    }


    @GetMapping("/details/{id}")
    public String serviceTicketDetails(@AuthenticationPrincipal UserDetails customerUser, @PathVariable Long id, Model model, HttpSession session) {
        ServiceTicket serviceTicket = serviceTicketService.findServiceTicketById(id);
        List<Activity> activities;
        Set<Planner> planners = plannerService.findPlannersByServiceTicket(serviceTicket)
                .stream()
                .filter(o -> o.getUser().getUsername().equals(customerUser.getUsername()))
                .collect(Collectors.toSet());


        boolean check = customerUser.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals("ROLE_ADMIN") || o.getAuthority().equals("ROLE_USER"));
        if (check) {
            activities = activityService.findAllByServiceTicket(serviceTicket);
        } else {
            activities = activityService.findAllByServiceTicketAndUser(serviceTicket, userRepository.findByUsername(customerUser.getUsername()));
        }

        List<Activity> sortedActivities = activities.stream().sorted(Comparator.comparing(Activity::getId)).collect(Collectors.toList());

        long sumOfWork = 0L;
        long sumOfBreak = 0L;
        long sumOfDriveTime = 0L;
        long sumOfKm = 0;

        for (Activity activity : activities) {
            sumOfKm += activity.getKilometersToBase() + activity.getKilometersToSite();
            LocalTime finishWorkOnSite = LocalTime.parse(activity.getFinishWorkOnSite());
            LocalTime startWorkOnSite = LocalTime.parse(activity.getStartWorkOnSite());
            LocalTime startDriveFromBase = LocalTime.parse(activity.getStartDriveFromBase());
            LocalTime arriveToSite = LocalTime.parse(activity.getArriveOnSite());
            LocalTime startDriveFromSite = LocalTime.parse(activity.getStartDriveFromSite());
            LocalTime arriveToBase = LocalTime.parse(activity.getArriveToBase());
            long sumOfDriveTimeToSite = Duration.between(startDriveFromBase, arriveToSite).toMinutes();
            long sumOfDriveTimeToBase = Duration.between(startDriveFromSite, arriveToBase).toMinutes();
            sumOfDriveTime = sumOfDriveTime + sumOfDriveTimeToBase + sumOfDriveTimeToSite;
            long parseBreakHour = LocalTime.parse(activity.getRest()).getHour();
            long parseBreakMinute = LocalTime.parse(activity.getRest()).getMinute();
            long getMinutesFromHours = parseBreakHour * 60;
            long getTotalBreak = parseBreakMinute + getMinutesFromHours;
            long sumOfWorkTimeParse = Duration.between(startWorkOnSite, finishWorkOnSite).toMinutes();
            sumOfWork = sumOfWork + sumOfWorkTimeParse;
            sumOfBreak = sumOfBreak + getTotalBreak;
        }

        long totalWorkTime = sumOfWork - sumOfBreak;
        long getHoursFromTotalWorkTime;

        if (totalWorkTime < 60) {
            getHoursFromTotalWorkTime = 0L;
        } else {
            getHoursFromTotalWorkTime = totalWorkTime / 60;
        }
        long getMinutesFromTotalWorkTime = totalWorkTime % 60;
        long getHoursFromTotalDriveTime = sumOfDriveTime / 60;
        long getMinutesFromTotalDriveTime =  sumOfDriveTime % 60;
        long hoursTotalWorkTime = getHoursFromTotalWorkTime + getHoursFromTotalDriveTime;
        long minutesTotalWorkTime = getMinutesFromTotalWorkTime + getMinutesFromTotalDriveTime;

        Duration totalWorkTimeDuration = Duration.ofHours(getHoursFromTotalWorkTime).plusMinutes(getMinutesFromTotalWorkTime);
        Duration totalDriveTimeDuration = Duration.ofHours(getHoursFromTotalDriveTime).plusMinutes(getMinutesFromTotalDriveTime);
        Duration totalTimeDuration = Duration.ofHours(hoursTotalWorkTime).plusMinutes(minutesTotalWorkTime);

        String formatTotalTimeDuration = String.format("%02d:%02d", totalTimeDuration.toHours(), totalTimeDuration.toMinutesPart());
        String formatTotalWorkTimeDuration = String.format("%02d:%02d", totalWorkTimeDuration.toHours(), totalWorkTimeDuration.toMinutesPart());
        String formatTotalDriveTimeDuration = String.format("%02d:%02d", totalDriveTimeDuration.toHours(), totalDriveTimeDuration.toMinutesPart());

        if (check) {
            model.addAttribute("files", dbFileStorageService.loadAllByServiceTicket(serviceTicket));
        } else {
            User user = userRepository.findByUsername(customerUser.getUsername());
            List<DBFile> files = dbFileStorageService.loadAllByUser(user);
            model.addAttribute("files", files);
        }
        model.addAttribute("sumOfWorkTime", formatTotalWorkTimeDuration);
        model.addAttribute("sumOfDriveTime", formatTotalDriveTimeDuration);
        model.addAttribute("totalTime", formatTotalTimeDuration);
        model.addAttribute("sumOfKm", sumOfKm);
        model.addAttribute("activities", sortedActivities);
        model.addAttribute("planners", planners);
        model.addAttribute("serviceTicket", serviceTicket);
        ServiceTicket serviceTicket1 = new ServiceTicket();
        serviceTicket1.setId(id);
        model.addAttribute("ticketId", serviceTicket1);
        return "serviceTicket/serviceTicketDetails";
    }

    @PostMapping("/details/{id}")
    public String serviceTicketDetailsFileUpload(@AuthenticationPrincipal UserDetails customUser, @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        ServiceTicket serviceTicket = serviceTicketService.findServiceTicketById(id);
        User user = userRepository.findByUsername(customUser.getUsername());
        dbFileStorageService.storeFile(user, file, serviceTicket);
        return "redirect:../details/" + id;
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @RequestMapping(value = "/getFile/{id}")
    public void getStudentPhoto(HttpServletResponse response, @PathVariable String id) throws IOException {
        response.setContentType("image/jpeg");
        DBFile dbFile = dbFileStorageService.getFile(id);
        byte[] bytes = dbFile.getData();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping("/deleteFile/{id}/{serviceTicketIds}")
    public String deletePhoto(@PathVariable String id, @PathVariable String serviceTicketIds) {
        dbFileStorageService.deleteFile(id);
        return "redirect:../../details/" + serviceTicketIds;
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(MultipartException.class)
        public String handleError1(MultipartException e, RedirectAttributes redirectAttributes, HttpSession session) {
            redirectAttributes.addFlashAttribute("overSize", true);
            ServiceTicket serviceTicket = (ServiceTicket) session.getAttribute("ticketId");
            Long id = serviceTicket.getId();
            return "redirect:../details/" + id;

        }

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public String handleError2(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes, HttpSession session) {
            redirectAttributes.addFlashAttribute("overSize", true);
            ServiceTicket serviceTicket = (ServiceTicket) session.getAttribute("ticketId");
            Long id = serviceTicket.getId();
            return "redirect:../details/" + id;
        }
    }

    @ModelAttribute("companies")
    public List<Company> fetchAllCompanies() {
        return companyService.findAllCompanies();
    }

    @ModelAttribute("ticketTypes")
    public List<TicketType> fetchAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    @ModelAttribute("ticketStatuses")
    public List<TicketStatus> fetchAllTicketStatuses() {
        return ticketStatusService.findAllTicketStatuses();
    }

    @ModelAttribute("userDetails")
    public UserDetails custom(@AuthenticationPrincipal UserDetails customUser) {
        return customUser;
    }

    private List<Machine> findMachines(ServiceTicket serviceTicket) {
        return machineService.findAllMachinesByCompany(serviceTicket.getCompany());
    }



}
