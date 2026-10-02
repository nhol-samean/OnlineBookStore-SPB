package com.example.project_bsdb.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "recipient name must not be blank")
    @Size(max = 150)
    private String recipientName;

    @NotBlank(message = "phone must not be blank")
    @Size(max = 20)
    private String phone;

    @NotBlank(message = "street must not be blank")
    @Size(max = 255)
    private String street;

    @NotBlank(message = "city must not be blank")
    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String province;

    @Size(max = 20)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
