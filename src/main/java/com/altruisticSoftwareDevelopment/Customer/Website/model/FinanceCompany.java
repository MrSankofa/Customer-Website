package com.altruisticSoftwareDevelopment.Customer.Website.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"customer", "id"})
public class FinanceCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String companyName;
  private Double loanCapacity;

  @OneToOne(mappedBy = "financeCompany", cascade = CascadeType.ALL)
  private Customer customer;

}
