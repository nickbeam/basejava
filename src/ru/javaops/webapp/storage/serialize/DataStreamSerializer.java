package ru.javaops.webapp.storage.serialize;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

public class DataStreamSerializer implements ISerializeStrategy {
    private static final String NULL_HOLDER = "NuLl_HoLdEr";
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = r.getSections();
            writeCollection(dos, sections.entrySet(), entry -> {
                SectionType sType = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sType.name());
                switch (sType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganisationSection) section).getOrganisations(), organisation -> {
                            dos.writeUTF(organisation.getName());
                            dos.writeUTF(nullReplacer(organisation.getUrl()));
                            writeCollection(dos, organisation.getPositions(), position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getHead());
                                dos.writeUTF(nullReplacer(position.getDescription()));
                            });
                        });
                        break;
                }
            });
        }
    }

    private interface ItemWriter<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ItemWriter<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection){
            writer.accept(item);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollection(dis, () -> {
                SectionType currentSection = SectionType.valueOf(dis.readUTF());
                resume.addSection(currentSection, readSection(dis, currentSection));
            });
            return resume;
        }
    }

    interface ItemReader<T> {
        void read() throws IOException;
    }

    private void readCollection(DataInputStream dis, ItemReader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private Section readSection(DataInputStream dis, SectionType currentSection) throws IOException {
        switch (currentSection) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganisationSection(readOrganisations(dis));
            default:
                throw new IllegalArgumentException();
        }
    }

    private List<Organisation> readOrganisations(DataInputStream dis) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
