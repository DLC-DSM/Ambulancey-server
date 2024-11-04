package org.example.domain.Hospital;

import jakarta.persistence.*;
import lombok.Data;
import org.example.annotation.PhoneNumber;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hospital_list")
@Data
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hospital_id")
    private long id;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospital_text")
    private String hospitalDescription;

    @Column(name = "hospital_type")
    private String hospitalType;

    @Column(name = "hospital_open_time")
    private Date hospitalOpenDate;

    @Column(name = "hospital_close_time")
    private Date hospitalCloseDate;

    @Column(name = "hospital_open")
    private boolean isOpen;

    @PhoneNumber
    @Column(name = "hospital_phone")
    private String number;

    @Column(name = "hospital_address")
    private String address;

    @OneToMany
    private List<HospitalReviewEntity> reviews;
}
