package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements IStorage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r LEFT JOIN contact c on r.uuid = c.resume_uuid WHERE c.resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                addContacts(rs, resume);
                try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM resume r LEFT JOIN section s on r.uuid = s.resume_uuid WHERE s.resume_uuid = ?")) {
                 ps1.setString(1, uuid);
                 ResultSet rs1 = ps1.executeQuery();
                    if (!rs1.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    addSections(rs1, resume);
                }
                return resume;
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContactsAndSections(resume, conn);
            insertContacts(resume, conn);
            return null;
        });

    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid ASC ")) {
                ResultSet rs = ps.executeQuery();
                List<Resume> resumes = new ArrayList<>();
                while (rs.next()) {
                    String uuid = rs.getString("uuid").replaceAll("\\s", "");
                    String fullname = rs.getString("full_name");
                    sqlHelper.execute("SELECT * FROM contact c WHERE c.resume_uuid = ?", ps1 -> {
                        ps1.setString(1, uuid);
                        ResultSet rs1 = ps1.executeQuery();
                        if (!rs1.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        Resume resume = new Resume(uuid, fullname);
                        addContacts(rs1, resume);
                        resumes.add(resume);
                        return null;
                    });
                }
                return resumes;
            }
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (rs.getString("value") != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        if (rs.getString("value") != null) {
            String type = rs.getString("type");
            String value = rs.getString("value");
            switch (SectionType.valueOf(type)) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(SectionType.valueOf(type), new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(SectionType.valueOf(type), new ListSection(Arrays.asList(value.split("\n"))));
                    break;
                case EDUCATION:
                    break;
                case EXPERIENCE:
                    break;
                default:
                    //resume.addContact(ContactType.valueOf(rs.getString("type")), value);
                    break;
            }
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        List<String> list;
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                if (e.getValue() instanceof TextSection) {
                    ps.setString(3, ((TextSection) e.getValue()).getText());
                } else {
                    list = ((ListSection) e.getValue()).getItems();
                    final String[] string = {""};
                    list.forEach((str) -> {
                        string[0] += str + "\n";
                    });
                    ps.setString(3, string[0]);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContacts(ResultSet rs, Resume resume) throws SQLException {
        do {
            addContact(rs, resume);
        } while (rs.next());
    }

    private void addSections(ResultSet rs, Resume resume) throws SQLException {
        do {
            addSection(rs, resume);
        } while (rs.next());
    }

    private void deleteContactsAndSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section s WHERE s.resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

}
