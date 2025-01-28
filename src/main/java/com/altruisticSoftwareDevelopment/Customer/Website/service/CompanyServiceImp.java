package com.altruisticSoftwareDevelopment.Customer.Website.service;

import com.altruisticSoftwareDevelopment.Customer.Website.model.Company;
import com.altruisticSoftwareDevelopment.Customer.Website.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImp implements CompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  @Override
  @Transactional
  public List<Company> findAllFinanceCompanies() {
    return companyRepository.findAll();
  }

  @Override
  @Transactional
  public Company saveFinanceCompany(Company company) {
    return companyRepository.save(company);
  }

  @Override
  @Transactional
  public Company getFinanceCompany(Long id) {
    return companyRepository.findById(id).get();
  }

  @Override
  @Transactional
  public void deleteFinanceCompany(Long id) {
    companyRepository.deleteById(id);
  }

  @Override
  @Transactional
  public List<Company> saveAllFinanceCompany(List<Company> customers) {
    return companyRepository.saveAll(customers);
  }

  @Override
  public void deleteAllCustomers() {
    companyRepository.deleteAll();
  }

  @Override
  @Transactional
  public Optional<Company> updateFinanceCompanyById(Long id, Company updatedCompany) {
    // try to retrieve the company from the db
    Company company = getFinanceCompany(id);

    if(company != null){
      saveFinanceCompany(updatedCompany);
    }

    return Optional.ofNullable(company);
    // if present replace with given data


  }
}
