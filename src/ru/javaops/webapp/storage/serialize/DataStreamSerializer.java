package ru.javaops.webapp.storage.serialize;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements ISerializeStrategy {
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
            // TODO implements sections
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sType = entry.getKey();
                Section section = entry.getValue();
                switch (sType) {
                    case PERSONAL:
                        dos.writeUTF(sType.name());
                        dos.writeUTF(section.toString());
                        break;
                    case OBJECTIVE:
                        dos.writeUTF(sType.name());
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                        dos.writeUTF(sType.name());
                        writeList(dos, (ListSection) section);
                        break;
                    case QUALIFICATIONS:
                        dos.writeUTF(sType.name());
                        writeList(dos, (ListSection) section);
                        break;
                    case EXPERIENCE:
                        dos.writeUTF(sType.name());
                        writeOrg(dos, (OrganisationSection) section);
                        break;
                    case EDUCATION:
                        dos.writeUTF(sType.name());
                        writeOrg(dos, (OrganisationSection) section);
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

    private void writeOrg(DataOutputStream dos, OrganisationSection section) throws IOException {
        dos.writeInt(section.getOrganisations().size());
        for (Organisation organisation : section.getOrganisations()) {
            dos.writeUTF(organisation.getName());
            dos.writeUTF(organisation.getUrl());
            List<Organisation.Position> positions = organisation.getPositions();
            dos.writeInt(positions.size());
            for (Organisation.Position pos : positions) {
                dos.writeUTF(pos.getStartDate().toString());
                dos.writeUTF(pos.getEndDate().toString());
                dos.writeUTF(pos.getHead());
                dos.writeUTF(pos.getDescription());
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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            int secSize = dis.readInt();
            for (int j = 0; j < secSize; j++) {
                String sec = dis.readUTF();
                switch (sec) {
                    case "PERSONAL":
                        resume.addSection(SectionType.PERSONAL, new TextSection(dis.readUTF()));
                        break;
                    case "OBJECTIVE":
                        resume.addSection(SectionType.OBJECTIVE, new TextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                        List<String> listRead = getStringList(dis);
                        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(listRead));
                        break;
                    case "QUALIFICATIONS":
                        List<String> listRead1 = getStringList(dis);
                        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(listRead1));
                        break;
                    case "EXPERIENCE":
                        int orgSize = dis.readInt();
                        readOrg(dtf, dis, resume, orgSize);
                        break;
                    case "EDUCATION":
                        int orgSize1 = dis.readInt();
                        readOrg(dtf, dis, resume, orgSize1);
                        break;
                }
            }
            return resume;
        }
    }

    private void readOrg(DateTimeFormatter dtf, DataInputStream dis, Resume resume, int orgSize1) throws IOException {
        for (int y = 0; y < orgSize1; y++) {
            Organisation org = new Organisation(dis.readUTF(), dis.readUTF());
            List<Organisation.Position> positionsList = new ArrayList<>();
            int posCount = dis.readInt();
            for (int p = 0; p < posCount; p++) {
                positionsList.add(new Organisation.Position(
                        LocalDate.parse(dis.readUTF(), dtf),
                        LocalDate.parse(dis.readUTF(), dtf),
                        dis.readUTF(), dis.readUTF())
                );
            }
            resume.addSection(SectionType.EXPERIENCE, new OrganisationSection(
                    new Organisation(org.getName(), org.getUrl(), positionsList)));
        }
    }

    private List<String> getStringList(DataInputStream dis) throws IOException {
        int listSize = dis.readInt();
        List<String> listRead = new ArrayList();
        for (int k = 0; k < listSize; k++) {
            listRead.add(dis.readUTF());
        }
        return listRead;
    }
}
