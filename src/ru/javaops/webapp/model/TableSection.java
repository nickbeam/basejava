package ru.javaops.webapp.model;


import java.util.Date;

public class TableSection extends Section{
    private String organisation;
    private String web;
    private Date startDate;
    private Date endDate;
    private TextSection text;
    private SectionType type;

    public TableSection(String organisation, String web, Date startDate, TextSection text, SectionType type) {
        this.organisation = organisation;
        this.web = web;
        this.startDate = startDate;
        this.text = text;
        this.type = type;
    }

    public TableSection(String organisation, String web, Date startDate, Date endDate, TextSection text, SectionType type) {
        this.organisation = organisation;
        this.web = web;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.type = type;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TextSection getText() {
        return text;
    }

    public void setText(TextSection text) {
        this.text = text;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType type) {
        this.type = type;
    }
}
