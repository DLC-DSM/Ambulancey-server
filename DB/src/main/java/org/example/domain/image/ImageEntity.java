package org.example.domain.image;

import jakarta.persistence.*;

@Entity
@Table(name = "image_data")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long hospitalId;
    private String url;
}
