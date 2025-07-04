package com.github.krashwani.assitflow.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "t_address"
)
public class Address extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "address_sequence")
    @SequenceGenerator(name = "address_sequence",allocationSize = 1)
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Column(name = "address_street", nullable = false)
    private String street;
    @Column(name = "address_city", nullable = false)
    private String city;
    @Column(name = "address_state", nullable = false)
    private String state;
    @Column(name = "address_country", nullable = false)
    private String country;
    @Column(name = "address_zipcode", nullable = false)
    private Integer zipCode;
    @Column(name = "address_current", nullable = false)
    private Boolean current;
    @Column(name = "address_deleted", nullable = false)
    private Boolean deleted;
}
