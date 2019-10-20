package projekti.harjoitus.kirpputori;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



import projekti.harjoitus.kirpputori.domain.Category;
import projekti.harjoitus.kirpputori.domain.CategoryRepository;
import projekti.harjoitus.kirpputori.domain.Product;
import projekti.harjoitus.kirpputori.domain.ProductRepository;
import projekti.harjoitus.kirpputori.domain.User;
import projekti.harjoitus.kirpputori.domain.UserRepository;

@SpringBootApplication
public class KirpputoriApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KirpputoriApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(ProductRepository repository, CategoryRepository crepository, UserRepository urepository) {
		return (args)->{
			
		//kategoriat
			crepository.save(new Category("Naiset"));
			crepository.save(new Category("Miehet"));
			crepository.save(new Category("Lapset"));
			
			
			System.out.println("save some of products");
			repository.save(new Product("Housut", "Siniset", 38, 5,crepository.findByName("Naiset").get(0)));
			repository.save(new Product("Pusero", "Keltainen", 44, 12,crepository.findByName("Miehet").get(0)));
			repository.save(new Product("Kengät", "Ruskeat", 44, 10,crepository.findByName("Miehet").get(0)));
			repository.save(new Product("Hame", "Punainen", 38, 5,crepository.findByName("Naiset").get(0)));
			repository.save(new Product("Haalari", "Vihreä", 140, 20,crepository.findByName("Lapset").get(0)));
			
			// create users-> admin/admin and user/user
			
			User user1= new User("user","$2a$10$u1Fcxn7YtI1kD/8VgwAV2eeAH4r2XwfEelU3zXWJficJ4xXzjpWea", "USER");
			User user2= new User("admin","$2a$10$T7LvxxtDMrMgayoUqHoma.ctyYlVMA5BT9.tpivPcKxJh/QsJjV5S", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			
			
			
			System.out.println("all products");
			for (Product product: repository.findAll()) {
				System.out.println("Tuote:"+ product.toString());
			}

		};
	}
}