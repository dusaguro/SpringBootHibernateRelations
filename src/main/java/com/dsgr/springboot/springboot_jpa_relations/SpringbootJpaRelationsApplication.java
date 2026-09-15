package com.dsgr.springboot.springboot_jpa_relations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.dsgr.springboot.springboot_jpa_relations.entities.Address;
import com.dsgr.springboot.springboot_jpa_relations.entities.Client;
import com.dsgr.springboot.springboot_jpa_relations.entities.ClientDetails;
import com.dsgr.springboot.springboot_jpa_relations.entities.Invoice;
import com.dsgr.springboot.springboot_jpa_relations.repositories.ClientDetailsRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.ClientRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationsApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// manyToOne();
		// manyToOneFindByIdClient();
		// oneToMany();
		// oneToManyFindById();
		// removeAddress();
		// removeAddressFindById();
		// oneToManyInvoiceBidireccional();
		// oneToManyInvoiceBidireccionalFindById();
		// RemoveInvoiceBidireccionalFindById();
		// RemoveInvoiceBidireccional();
		// oneToOne();
		// oneToOneFindById();
		// oneToOneBidirectional();
		oneToOneBidirectionalFindById();
	}

	@Transactional
	private void oneToOneBidirectionalFindById() {
		clientRepository.findOne(1L).ifPresentOrElse(client -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);
			System.out.println(clientRepository.save(client.addClientDetails(clientDetails)));
		}, () -> {
			System.out.println("No existe el cliente con ese id");
		});
	}

	@Transactional
	private void oneToOneBidirectional() {
		Client client = new Client("Herba", "Pure");
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		System.out.println(clientRepository.save(client.addClientDetails(clientDetails)));
	}

	@Transactional
	private void oneToOneFindById() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);
		Optional<Client> clientOptional = clientRepository.findOne(2L);
		clientOptional.ifPresentOrElse(client -> {
			client.setClientDetails(clientDetails);
			clientRepository.save(client);
			System.out.println(client);
		}, () -> {
			System.out.println("No existe el cliente con ese id");
		});
	}

	@Transactional
	private void oneToOne() {
		// Client client = new Client("Herba", "Pure");

		// clientRepository.save(client);

		// ClientDetails clientDetails = new ClientDetails(true, 5000);
		// clientDetails.setClient(client);
		// clientDetailsRepository.save(clientDetails);

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Herba", "Pure");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);
		System.out.println(client);
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

		System.out.println(clientRepository.save(client));

	}

	@Transactional
	private void oneToManyFindById() {

		clientRepository.findById(4L).ifPresentOrElse(client -> {
			Address address1 = new Address("Tamasagra", 17);
			Address address2 = new Address("Bachue", 14);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			System.out.println(clientRepository.save(client));

		}, () -> {
			System.out.println("Id de cliente no existe");
		});

	}

	@Transactional
	private void removeAddress() {

		Client client = new Client("Frank", "Moras");

		Address address1 = new Address("El Verjel", 1234);
		Address address2 = new Address("Vasco de Gamma", 789);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		System.out.println(clientRepository.save(client));

		Optional<Client> optionalClient = clientRepository.findById(10L);
		optionalClient.ifPresentOrElse(c -> {
			c.getAddresses().remove(address1);
			System.out.println(clientRepository.save(c));
		}, () -> {
			System.out.println("Id de cliente no existe");
		});

	}

	@Transactional
	private void removeAddressFindById() {

		clientRepository.findById(4L).ifPresentOrElse(client -> {
			Address address1 = new Address("Tamasagra", 17);
			Address address2 = new Address("Bachue", 14);

			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);
			System.out.println(clientRepository.save(client));

			Optional<Client> optionalClient = clientRepository.findOneWithAddresses(4L);
			optionalClient.ifPresentOrElse(c -> {
				c.getAddresses().remove(address1);
				System.out.println(clientRepository.save(c));
			}, () -> {
				System.out.println("Id de cliente no existe");
			});

		}, () -> {
			System.out.println("Id de cliente no existe");
		});

	}

	@Transactional
	private void oneToManyInvoiceBidireccional() {

		Client client = new Client("Frank", "Moras");
		Invoice invoice1 = new Invoice("Compras oficina", 2000L);
		Invoice invoice2 = new Invoice("Compras casa", 8000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		System.out.println(clientRepository.save(client));

	}

	@Transactional
	private void oneToManyInvoiceBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Compras oficina", 2000L);
			Invoice invoice2 = new Invoice("Compras casa", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			System.out.println(clientRepository.save(client));

		}, () -> {
			System.out.println("Id de cliente no existe");
		});

	}

	@Transactional
	private void RemoveInvoiceBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Compras oficina", 2000L);
			Invoice invoice2 = new Invoice("Compras casa", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			System.out.println(clientRepository.save(client));

		}, () -> {
			System.out.println("Id de cliente no existe");
		});

		Optional<Client> optionalClientDB = clientRepository.findOne(1L);
		optionalClientDB.ifPresentOrElse(client -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);
			invoiceOptional.ifPresentOrElse(invoice -> {
				client.removeInvoice(invoice);
				System.out.println(clientRepository.save(client));
			}, () -> {
				System.out.println("Id de factura no existe");
			});
		}, () -> {
			System.out.println("Id de cliente no existe");
		});
	}

	@Transactional
	private void RemoveInvoiceBidireccional() {
		Optional<Client> optionalClient = Optional.of(new Client("Frank", "Moras"));
		optionalClient.ifPresentOrElse(client -> {
			Invoice invoice1 = new Invoice("Compras oficina", 2000L);
			Invoice invoice2 = new Invoice("Compras casa", 8000L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			System.out.println(clientRepository.save(client));

		}, () -> {
			System.out.println("Id de cliente no existe");
		});

		Optional<Client> optionalClientDB = clientRepository.findOne(10L);
		optionalClientDB.ifPresentOrElse(client -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);
			invoiceOptional.ifPresentOrElse(invoice -> {
				client.removeInvoice(invoice);
				System.out.println(clientRepository.save(client));
			}, () -> {
				System.out.println("Id de factura no existe");
			});
		}, () -> {
			System.out.println("Id de cliente no existe");
		});
	}

}
