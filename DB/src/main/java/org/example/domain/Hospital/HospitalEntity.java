package org.example.domain.Hospital;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;

import org.example.annotation.PhoneNumber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hospital_list")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HospitalEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private long id;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @Column(name = "hospital_text", nullable = false)
    private String hospitalDescription;

    @Column(name = "hospital_type", nullable = false)
    private String hospitalType;

    @Column(name = "hospital_open_time", nullable = false)
    private LocalTime hospitalOpenDate;

    @Column(name = "hospital_close_time", nullable = false)
    private LocalTime hospitalCloseDate;

    @PhoneNumber
    @Column(name = "hospital_phone", nullable = false)
    private String number;

    @Column(name = "hospital_address", nullable = false)
    private String address;

    @OneToMany
    private List<HospitalReviewEntity> reviews;

    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
}
