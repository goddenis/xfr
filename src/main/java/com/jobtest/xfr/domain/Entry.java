package com.jobtest.xfr.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement(name = "Entry")
public class Entry {


    @Size(max = 1024,message = "Must be shorter the then 1024")
    private String content;


    private Date creationDate;

    public Entry() {
    }

    public Entry(String content, Date date) {

        this.content = content;
        this.creationDate = date;
    }

    public String getContent() {
        return content;
    }
    @XmlElement
    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    @XmlElement
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Entry{content='" + content + "\', creationDate=" + creationDate +'}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
