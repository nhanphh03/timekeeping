package proton.face.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proton.face.constant.Constants;
import proton.face.entity.Groups;
import proton.face.entity.People;
import proton.face.entity.Timekeeping;
import proton.face.repository.GroupRepository;
import proton.face.repository.PeopleRepository;
import proton.face.repository.TimeKeepingRepository;
import proton.face.util.StringUtils;
import proton.face.util.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Setter
@SessionScoped
@Component
@Slf4j
@Getter
public class TimekeepingController {

    private Timekeeping timekeeping;
    private Date fromDate;
    private Date toDate;
    private String peopleIdSearch;
    private StreamedContent content;

    private int groupSelected;
    private List<Groups> groups;
    private People peopleSelected;
    private List<People> peopleList;
    private List<Timekeeping> timekeepingList;
    private boolean flagLate;
    private boolean flagNoSignUp;

    private final TimeKeepingRepository timeKeepingRepository;

    private final PeopleRepository peopleRepository;

    private final GroupRepository groupRepository;

    @Autowired
    public TimekeepingController(GroupRepository groupRepository, PeopleRepository peopleRepository,
                                 TimeKeepingRepository timeKeepingRepository) {
        this.groupRepository = groupRepository;
        this.timeKeepingRepository = timeKeepingRepository;
        this.peopleRepository = peopleRepository;
        this.groups = groupRepository.getGroupList();
        this.timekeepingList = timeKeepingRepository.getListTimekeeping();
    }

    public List<Timekeeping> getListTimekeepingOfPeople() {
        if (flagLate && flagNoSignUp) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng chỉ chọn một loại", ""));
            return timekeepingList;
        }

        String strFromDate = null;
        String strToDate = null;
        try {
            strFromDate = Utils.convertDateToString(fromDate, "yyyy-MM-dd");
            strToDate = Utils.convertDateToString(toDate, "yyyy-MM-dd");
            if (StringUtils.isEmpty(strFromDate) && StringUtils.isEmpty(strToDate)) {
                strFromDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-01"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        this.timekeepingList = timeKeepingRepository.getListTimekeepingOfPeople(peopleIdSearch, groupSelected, strFromDate, strToDate, flagLate);

        // xử lí những ngày không chấm công
        if (flagNoSignUp) {
            // tạo list từ ngày đến ngày bỏ t7 và chủ nhật
            LocalDate dtFrom;
            LocalDate dtTo;
            if (fromDate == null) {
                dtFrom = YearMonth.now().atDay(1);
            } else {
                dtFrom = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            if (toDate == null) {
                dtTo = LocalDate.now();
            } else {
                dtTo = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            List<LocalDate> lsDate = new ArrayList<>();
            while (dtTo.isAfter(dtFrom) || dtTo.isEqual(dtFrom)) {
                if (!(dtTo.getDayOfWeek() == DayOfWeek.SATURDAY || dtTo.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    lsDate.add(dtTo);
                }
                dtTo = dtTo.minusDays(1);
            }

            // add list danh sách chấm công vào map với key là peopleId + ngày châm công
            Map<String, Timekeeping> timekeepingMap = new TreeMap<>();
            for (Timekeeping item : timekeepingList) {
                timekeepingMap.put(item.getPeopleId() + "_" + item.getDateTimeKeeping(), item);
            }

            // lấy tất cả nhân viên
            List<People> allPeopleId = timeKeepingRepository.getPeopeIdList(peopleIdSearch);

            // lấy những ngày không chấm công của từng mã nv
            timekeepingList = new ArrayList<>();
            for (People people : allPeopleId) {
                for (LocalDate cd : lsDate) {
                    String key = people.getPeopleId() + "_" + cd.toString();
                    if (!timekeepingMap.containsKey(key)) {
                        Timekeeping noSignUp = new Timekeeping();
                        noSignUp.setPeopleId(people.getPeopleId());
                        noSignUp.setDateTimeKeeping(cd.toString());
                        noSignUp.setDescription("Không chấm công");
                        noSignUp.setImagePath(people.getImagePath());
                        noSignUp.setFullName(people.getFullName());
                        noSignUp.setGroupName(people.getGroupName());
                        timekeepingList.add(noSignUp);
                    }
                }
            }
        }

        if (!flagLate && !flagNoSignUp && StringUtils.isEmpty(peopleIdSearch) && groupSelected == 0 && fromDate == null && toDate == null) {
            timekeepingList = timeKeepingRepository.getListTimekeeping();
        }

        return timekeepingList;
    }

    public Timekeeping getTimekeeping() {
        if (this.timekeeping == null) {
            this.timekeeping = new Timekeeping();
        }
        return timekeeping;
    }

    public void exportExcel() {
        File file = new File("formulas_output.xlsx");
        log.info(file.getAbsolutePath());
        try (InputStream is = Files.newInputStream(Paths.get(Constants.EXCEL_TEMPLATE_PATH));
             OutputStream os = Files.newOutputStream(file.toPath())) {
            Context context = new Context();
            context.putVar("list", timekeepingList);
            JxlsHelper.getInstance().processTemplate(is, os, context);
            InputStream downloadFileInputStream = new FileInputStream(file);
            this.content = DefaultStreamedContent.builder()
                    .name("time_checking.xlsx")
                    .stream(() -> downloadFileInputStream).build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void getPeopleListByPeopleId() {
        if (StringUtils.isEmpty(peopleIdSearch)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Vui lòng nhập mã nhân viên", ""));
            return;
        }
        peopleList = peopleRepository.searchPeopleList(peopleIdSearch, null, null);
        showDialog("peoplePicker");
    }

    public void showDialog(String id) {
        PrimeFaces.current().executeScript("PF('" + id + "').show();");
    }

    public void onRowSelect() {
        peopleIdSearch = peopleSelected.getPeopleId();
    }


}
