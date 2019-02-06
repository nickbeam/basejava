package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.IStorage;
import ru.javaops.webapp.util.DateUtil;
import ru.javaops.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private IStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        Resume r;
        if (uuid.equals("") && fullName.trim().equals("")) {
            response.sendRedirect("resume");
            return;
        } else if (uuid.equals("") && !fullName.equals("")) {
            r = new Resume(fullName);
            storage.save(r);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            //if (value != null && value.trim().length() != 0) {
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        r.setSection(type, new TextSection(value));
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        r.setSection(type, new ListSection(value.split("\\n")));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        List<Organisation> organisations = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Organisation.Position> positions = new ArrayList<>();
                                String prefix = type.name() + i;
                                String[] startDates = request.getParameterValues(prefix + "startDate");
                                String[] endDates = request.getParameterValues(prefix + "endDate");
                                String[] heads = request.getParameterValues(prefix + "head");
                                String[] descriptions = request.getParameterValues(prefix + "description");
                                for (int j = 0; j < heads.length; j++) {
                                    if (!HtmlUtil.isEmpty(heads[j])) {
                                        positions.add(new Organisation.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), heads[j], descriptions[j]));
                                    }
                                }
                                organisations.add(new Organisation(name, urls[i], positions));
                            }
                        }
                        r.setSection(type, new OrganisationSection(organisations));
                        break;
                    }
                }
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "add":
                r = Resume.EMPTY;
                break;
//                request.getRequestDispatcher(("/WEB-INF/jsp/add.jsp")).forward(request, response);
//                return;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    Section section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganisationSection orgSection = (OrganisationSection) section;
                            List<Organisation> emptyFirstOrganisations = new ArrayList<>();
                            emptyFirstOrganisations.add(Organisation.EMPTY);
                            if (orgSection != null) {
                                for (Organisation organisation : orgSection.getOrganisations()) {
                                    List<Organisation.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organisation.Position.EMPTY);
                                    emptyFirstPositions.addAll(organisation.getPositions());
                                    emptyFirstOrganisations.add(new Organisation(organisation.getName(), organisation.getUrl(), emptyFirstPositions));
                                }
                            }
                            section = new OrganisationSection(emptyFirstOrganisations);
                            break;
                    }
                    r.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
