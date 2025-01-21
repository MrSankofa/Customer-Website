package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.FinanceCompany;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.FinanceCompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FinanceCompanyServiceImp implements FinanceCompanyService{

  @Autowired
  private FinanceCompanyRepository financeCompanyRepository;

  @Override
  public List<FinanceCompany> findAllFinanceCompanies() {
    return financeCompanyRepository.findAll();
  }

  @Override
  public FinanceCompany saveFinanceCompany(FinanceCompany customer) {
    return financeCompanyRepository.save(customer);
  }

  @Override
  public FinanceCompany getFinanceCompany(Long id) {
    return financeCompanyRepository.findById(id).get();
  }

  @Override
  public void deleteFinanceCompany(Long id) {
    financeCompanyRepository.deleteById(id);
  }

  @Override
  public List<FinanceCompany> saveAllFinanceCompany(List<FinanceCompany> customers) {
    return financeCompanyRepository.saveAll(customers);
  }

  @Override
  public void deleteAllCustomers() {
    financeCompanyRepository.deleteAll();
  }

  @Override
  public Optional<FinanceCompany> updateFinanceCompanyById(Long id, FinanceCompany updatedFinanceCompany) {
    // try to retrieve the company from the db
    FinanceCompany financeCompany = getFinanceCompany(id);

    if(financeCompany != null){
      saveFinanceCompany(updatedFinanceCompany);
    }

    return Optional.ofNullable(financeCompany);
    // if present replace with given data


  }
}
