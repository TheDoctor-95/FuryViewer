package com.furyviewer.service;

import com.furyviewer.domain.Company;
import com.furyviewer.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    public Company importCompany(String name){

        Company c;

        Optional<Company> optionalCompany = companyRepository.findCompanyByName(name);

        if(optionalCompany.isPresent()){
            c = optionalCompany.get();
        }else{
            c = new Company();
            c.setName(name);
            c = companyRepository.save(c);
        }

        return c;
    }

}
