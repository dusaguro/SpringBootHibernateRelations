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
import com.dsgr.springboot.springboot_jpa_relations.entities.Course;
import com.dsgr.springboot.springboot_jpa_relations.entities.Invoice;
import com.dsgr.springboot.springboot_jpa_relations.entities.Student;
import com.dsgr.springboot.springboot_jpa_relations.repositories.ClientDetailsRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.ClientRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.CourseRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.InvoiceRepository;
import com.dsgr.springboot.springboot_jpa_relations.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationsApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

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
		// oneToOneBidirectionalFindById();
		// manyToMany();
		// manyToManyFindById();
		// manyToManyRemoveFindById();
		// manyToManyRemove();
		// manyToManyBidirectional();
		manyToManyRemoveBidirectional();
	}

	@Transactional
	private void manyToManyRemoveBidirectional() {
		Student student1 = new Student("Frank", "Moras");
		Student student2 = new Student("Jhon", "Doe");
		Student student3 = new Student("Jane", "Doe");

		Course course1 = new Course("Java", "Instructor 1");
		Course course2 = new Course("Python", "Instructor 2");
		Course course3 = new Course("JavaScript", "Instructor 3");

		student1.addCourse(course1).addCourse(course2);
		student2.addCourse(course1);
		student3.addCourse(course2).addCourse(course3);

		studentRepository.saveAll(Set.of(student1, student2, student3));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);

		Optional<Student> studentDB = studentRepository.findOne(10L);
		studentDB.ifPresentOrElse(student -> {
			Optional<Course> courseDB = courseRepository.findOne(4L);
			courseDB.ifPresentOrElse(course -> {
				student.removeCourse(course);
				System.out.println(studentRepository.save(student));
			}, () -> {
				System.out.println("No existe el curso con ese id");
			});
		}, () -> {
			System.out.println("No existe el estudiante con ese id");
		});
	}

	@Transactional
	private void manyToManyBidirectional() {
		Student student1 = new Student("Frank", "Moras");
		Student student2 = new Student("Jhon", "Doe");
		Student student3 = new Student("Jane", "Doe");

		Course course1 = new Course("Java", "Instructor 1");
		Course course2 = new Course("Python", "Instructor 2");
		Course course3 = new Course("JavaScript", "Instructor 3");

		student1.addCourse(course1).addCourse(course2);
		student2.addCourse(course1);
		student3.addCourse(course2).addCourse(course3);

		studentRepository.saveAll(Set.of(student1, student2, student3));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);

	}

	@Transactional
	private void manyToManyRemove() {
		Student student1 = new Student("Frank", "Moras");
		Student student2 = new Student("Jhon", "Doe");
		Student student3 = new Student("Jane", "Doe");

		Course course1 = new Course("Java", "Instructor 1");
		Course course2 = new Course("Python", "Instructor 2");
		Course course3 = new Course("JavaScript", "Instructor 3");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course1));
		student3.setCourses(Set.of(course2, course3));

		studentRepository.saveAll(Set.of(student1, student2, student3));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);

		Optional<Student> studentDB = studentRepository.findOne(10L);
		studentDB.ifPresentOrElse(student -> {
			Optional<Course> courseDB = courseRepository.findById(4L);
			courseDB.ifPresentOrElse(course -> {
				student.getCourses().remove(course);
				System.out.println(studentRepository.save(student));
			}, () -> {
				System.out.println("No existe el curso con ese id");
			});
		}, () -> {
			System.out.println("No existe el estudiante con ese id");
		});
	}

	@Transactional
	private void manyToManyRemoveFindById() {
		Student student1 = studentRepository.findById(1L).orElseThrow();
		Student student2 = studentRepository.findById(2L).orElseThrow();
		Student student3 = studentRepository.findById(3L).orElseThrow();

		Course course1 = courseRepository.findById(1L).orElseThrow();
		Course course2 = courseRepository.findById(2L).orElseThrow();
		Course course3 = courseRepository.findById(3L).orElseThrow();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course1));
		student3.setCourses(Set.of(course2, course3));

		studentRepository.saveAll(Set.of(student1, student2, student3));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);

		Optional<Student> studentDB = studentRepository.findOne(1L);
		studentDB.ifPresentOrElse(student -> {
			Optional<Course> courseDB = courseRepository.findById(1L);
			courseDB.ifPresentOrElse(course -> {
				student.getCourses().remove(course);
				System.out.println(studentRepository.save(student));
			}, () -> {
				System.out.println("No existe el curso con ese id");
			});
		}, () -> {
			System.out.println("No existe el estudiante con ese id");
		});
	}

	@Transactional
	private void manyToManyFindById() {
		Student student1 = studentRepository.findById(1L).orElseThrow();
		Student student2 = studentRepository.findById(2L).orElseThrow();
		Student student3 = studentRepository.findById(3L).orElseThrow();

		Course course1 = courseRepository.findById(1L).orElseThrow();
		Course course2 = courseRepository.findById(2L).orElseThrow();
		Course course3 = courseRepository.findById(3L).orElseThrow();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course1));
		student3.setCourses(Set.of(course2, course3));

		studentRepository.saveAll(Set.of(student1, student2, student3));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);

	}

	@Transactional
	private void manyToMany() {
		Student student1 = new Student("Frank", "Moras");
		Student student2 = new Student("Jhon", "Doe");
		Student student3 = new Student("Jane", "Doe");

		Course course1 = new Course("Java", "Instructor 1");
		Course course2 = new Course("Python", "Instructor 2");
		Course course3 = new Course("JavaScript", "Instructor 3");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course1));
		student3.setCourses(Set.of(course2, course3));

		studentRepository.saveAll(Set.of(student1, student2, student3));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);

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
