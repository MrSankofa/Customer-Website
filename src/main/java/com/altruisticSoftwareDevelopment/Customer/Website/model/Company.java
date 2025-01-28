package com.altruisticSoftwareDevelopment.Customer.Website.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"customer", "id"})
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String companyName;
  private Double loanCapacity;

  @OneToOne(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Customer customer;

  public Company removeCustomer() {
    this.customer = null;
    return this;
  }

}
