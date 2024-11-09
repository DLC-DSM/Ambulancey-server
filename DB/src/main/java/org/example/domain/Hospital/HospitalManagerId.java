package org.example.domain.Hospital;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class HospitalManagerId implements Serializable {
    private Long userKey;
    private Long hospitalId;

    public HospitalManagerId() {}

    public HospitalManagerId(Long userKey, Long hospitalId) {
        this.userKey = userKey;
        this.hospitalId = hospitalId;
    }

    // equals()와 hashCode() 메서드 필수
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HospitalManagerId that = (HospitalManagerId) o;
        return Objects.equals(userKey, that.userKey) && Objects.equals(hospitalId, that.hospitalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userKey, hospitalId);
    }

    // Getters와 Setters
}
