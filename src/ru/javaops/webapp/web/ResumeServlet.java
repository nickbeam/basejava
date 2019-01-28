package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.IStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        r.addSection(type, new TextSection(value));
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        r.addSection(type, new ListSection(value));
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        Map<String, String[]> requestParameterMap = request.getParameterMap();
                        requestParameterMap.forEach((key, value1) -> {
                            System.out.println(key + value1);
                        });
                        //r.addSection(type, new OrganisationSection(new Organisation()));
                        break;
                    }
                    default: {
                        break;
                    }
                }

            } else {
                r.getSections().remove(type);
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
                request.getRequestDispatcher(("/WEB-INF/jsp/add.jsp")).forward(request, response);
                return;
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
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
