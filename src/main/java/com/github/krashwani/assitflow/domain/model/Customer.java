package com.github.krashwani.assitflow.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "t_customer")
@PrimaryKeyJoinColumn(name = "customer_id")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User{

    @Column(name = "customer_phone", nullable = false)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_address_id",nullable = false,referencedColumnName = "address_id")
    @ToString.Exclude
    private Address address;
}
