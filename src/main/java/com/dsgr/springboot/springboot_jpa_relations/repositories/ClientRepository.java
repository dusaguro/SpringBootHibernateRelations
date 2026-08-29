package com.dsgr.springboot.springboot_jpa_relations.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dsgr.springboot.springboot_jpa_relations.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
