package ru.javaops.webapp.storage.serialize;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements ISerializeStrategy {
    private static final String NULL_HOLDER = "NuLl_HoLdEr";
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sType = entry.getKey();
                Section section = entry.getValue();
                switch (sType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(sType.name());
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeUTF(sType.name());
                        writeList(dos, (ListSection) section);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeUTF(sType.name());
                        writeOrganisations(dos, (OrganisationSection) section);
                        break;
                }
            }
        }
    }

    private void writeList(DataOutputStream dos, ListSection section) throws IOException {
        dos.writeInt(section.getItems().size());
        for (String str : section.getItems()) {
            dos.writeUTF(str);
        }
    }

    private void writeOrganisations(DataOutputStream dos, OrganisationSection section) throws IOException {
        dos.writeInt(section.getOrganisations().size());
        for (Organisation organisation : section.getOrganisations()) {
            dos.writeUTF(organisation.getName());
            dos.writeUTF(nullReplacer(organisation.getUrl()));
            List<Organisation.Position> positions = organisation.getPositions();
            dos.writeInt(positions.size());
            for (Organisation.Position pos : positions) {
                dos.writeUTF(pos.getStartDate().toString());
                dos.writeUTF(pos.getEndDate().toString());
                dos.writeUTF(pos.getHead());
                dos.writeUTF(nullReplacer(pos.getDescription()));
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsCount = dis.readInt();
            for (int i = 0; i < contactsCount; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            int sectionsCount = dis.readInt();
            for (int j = 0; j < sectionsCount; j++) {
                String currentSection = dis.readUTF();
                switch (currentSection) {
                    case "PERSONAL":
                        resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                        break;
                    case "OBJECTIVE":
                        resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(readList(dis)));
                        break;
                    case "QUALIFICATIONS":
                        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(readList(dis)));
                        break;
                    case "EXPERIENCE":
                        resume.addSection(SectionType.EXPERIENCE, new OrganisationSection(readOrganisations(dtf, dis)));
                        break;
                    case "EDUCATION":
                        resume.addSection(SectionType.EDUCATION, new OrganisationSection(readOrganisations(dtf, dis)));
                        break;
                }
            }
            return resume;
        }
    }

    private List<Organisation> readOrganisations(DateTimeFormatter dtf, DataInputStream dis) throws IOException {
        int organisationsCount = dis.readInt();
        List<Organisation> listOrg = new ArrayList<>();
        for (int y = 0; y < organisationsCount; y++) {
            Organisation org = new Organisation(dis.readUTF(), nullReplacer(dis.readUTF()));
            List<Organisation.Position> positionsList = new ArrayList<>();
            int positionsCount = dis.readInt();
            for (int p = 0; p < positionsCount; p++) {
                positionsList.add(new Organisation.Position(
                        LocalDate.parse(dis.readUTF(), dtf),
                        LocalDate.parse(dis.readUTF(), dtf),
                        dis.readUTF(), nullReplacer(dis.readUTF()))
                );
            }
            listOrg.add(new Organisation(org.getName(), org.getUrl(), positionsList));
        }
        return listOrg;
    }

    private List<String> readList(DataInputStream dis) throws IOException {
        int listSize = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int k = 0; k < listSize; k++) {
            list.add(dis.readUTF());
        }
        return list;
    }

    private String nullReplacer(String description){
        if (description == null){
            return NULL_HOLDER;
        } else if (description.equals(NULL_HOLDER)) {
            return null;
        }
        return description;
    }
}
