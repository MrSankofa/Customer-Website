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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;
  private String emailAddress;
  private Integer age;
  private String address;

  @OneToOne(cascade = CascadeType.ALL) // Cascade operations and enable orphan removal
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;


  public Customer removeCompany() {
    this.company = null;
    return this;
  }

}
