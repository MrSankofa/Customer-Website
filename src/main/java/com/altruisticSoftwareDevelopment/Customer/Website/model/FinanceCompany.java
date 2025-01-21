package com.altruisticSoftwareDevelopment.Customer.Website.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/*
* Finance company - Banker
* */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = "id")
public class FinanceCompany {

  @Id
  @GeneratedValue( strategy = GenerationType.IDENTITY)
  private Long id;

  private String companyName;
  private Double loanCapacity;

}
