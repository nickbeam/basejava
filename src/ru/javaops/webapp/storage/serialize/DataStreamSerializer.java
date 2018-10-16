package ru.javaops.webapp.storage.serialize;

import ru.javaops.webapp.model.*;

import java.io.*;
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
                            dos.writeUTF(organisation.getUrl());
                            writeCollection(dos, organisation.getPositions(), position -> {
                                dos.writeUTF(position.getStartDate().toString());
                                dos.writeUTF(position.getEndDate().toString());
                                dos.writeUTF(position.getHead());
                                dos.writeUTF(position.getDescription());
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

    private interface ItemReader {
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
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganisationSection(
                        readList(dis, () -> new Organisation(dis.readUTF(), dis.readUTF(),
                                readList(dis, () -> new Organisation.Position(
                                        parseLocalDate(dis.readUTF()),
                                        parseLocalDate(dis.readUTF()),
                                        dis.readUTF(), dis.readUTF())
                                )
                        )
                ));
            default:
                throw new IllegalStateException();
        }
    }

    private interface ElemReader<T>{
        T read() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ElemReader<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private LocalDate parseLocalDate(String string){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(string, dtf);
    }
}
