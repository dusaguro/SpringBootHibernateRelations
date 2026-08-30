package com.dsgr.springboot.springboot_jpa_relations;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.dsgr.springboot.springboot_jpa_relations.entities.Address;
import com.dsgr.springboot.springboot_jpa_relations.entities.Client;
import com.dsgr.springboot.springboot_jpa_relations.entities.Invoice;
import com.dsgr.springboot.springboot_jpa_relations.repositories.ClientRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationsApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// manyToOne();
		// manyToOneFindByIdClient();
		// oneToMany();
		oneToManyFindById();

	}

	@Transactional
	private void manyToOne() {

		Client client = new Client("Jhon", "Doe");
		System.out.println(clientRepository.save(client));

		Invoice invoice = new Invoice("Compras oficina", 2000L);
		invoice.setClient(client);
		System.out.println(invoiceRepository.save(invoice));

	}

	@Transactional
	private void manyToOneFindByIdClient() {
		Optional<Client> optionalClient = clientRepository.findById(1L);
		if (optionalClient.isPresent()) {
			Invoice invoice = new Invoice("Compras oficina", 2000L);
			invoice.setClient(optionalClient.orElseThrow());
			System.out.println(invoiceRepository.save(invoice));
		} else {
			System.out.println("No existe el cliente con ese id");
		}
	}

	@Transactional
	private void oneToMany() {

		Client client = new Client("Frank", "Moras");

		Address address1 = new Address("El Verjel", 1234);
		Address address2 = new Address("Vasco de Gamma", 789);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

	}

	@Transactional
	private void oneToManyFindById() {

		clientRepository.findById(4L).ifPresentOrElse(client -> {
			Address address1 = new Address("Tamasagra", 17);
			Address address2 = new Address("Bachue", 14);

			client.setAddresses(Arrays.asList(address1, address2));

			clientRepository.save(client);

			System.out.println(client);

		}, () -> {
			System.out.println("Id de cliente no existe");
		});

	}

}
