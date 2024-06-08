package org.juandavid.springboot.jpa.practicespringbootjpa.repository;

import org.juandavid.springboot.jpa.practicespringbootjpa.entity.Customer;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerCrudRepository extends Repository<Customer, Long> {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

}
