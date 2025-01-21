package com.altruisticSoftwareDevelopment.Customer.Website.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
@Builder
@Getter
@Setter
@ToString(exclude = "id")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String fullName;
  private String emailAddress;
  private Integer age;
  private String address;
}
