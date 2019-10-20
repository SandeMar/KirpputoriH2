package projekti.harjoitus.kirpputori.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import projekti.harjoitus.kirpputori.domain.ProductRepository;
import projekti.harjoitus.kirpputori.domain.CategoryRepository;
import projekti.harjoitus.kirpputori.domain.Product;

@Controller

public class ProductController {
	//injektoi ProductRepository interface ProductController-luokan käyttöön
	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository crepository;
	
	//Aloitussivulle
		@GetMapping("aloitus")
		public String aloitus() {
			return "aloitus";
		}
	//Listaa tuotteet
	@GetMapping(value= "/productlist")//HTML-sivun nimi mistä tullaan
	public String productList(Model model) {
	
		model.addAttribute("products", repository.findAll());
		
        return "productlist";
		

}
	
	// RESTful listaa kaikki tuotteet
    @RequestMapping(value="/products", method = RequestMethod.GET)
    public @ResponseBody List<Product> productListRest() {	
        return (List<Product>) repository.findAll();
    }    

	// RESTful listaa tuotteet id mukaan
    @RequestMapping(value="/product/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Product> findProductRest(@PathVariable("id") Long productId) {	
    	return repository.findById(productId);
    }   
	// Lisää uuden tuotteen
    @PreAuthorize("hasAuthority('ADMIN')")
		 @RequestMapping(value = "/add")
		    public String addProduct(Model model){
		    	model.addAttribute("product", new Product());
		    	model.addAttribute("categories", crepository.findAll());
		        return "addproduct";
		        
		    }  
    // Tallentaa ja lisää tuotteen
   	@RequestMapping(value = "/save", method = RequestMethod.POST)
   		    public String save(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model){
   		System.out.println("save");
   		if (bindingResult.hasErrors()) {
   			if (bindingResult.getFieldError().getField().equalsIgnoreCase("price")) {
   				System.out.println("price -error haara");
   				bindingResult.rejectValue("price", "err.price", "Check price format");// jos hinta ei ole vähintään yksi euro palauttaa errorin.
   			} else {
   				System.out.println("Jokin muu vika");
   			}
   			return "redirect:add";// jos tulee väärä hinta palauttaa uudelleen "lisää tuote ikkunan"
   			

   		}
   		        
   		System.out.println("mene tallettamaan tuote");
   		repository.save(product);
   		       // return "redirect:productlist";// Palaa productlist-sivulle.
   		return "redirect:productlist";
   		    }    
		// Poistaa tuotteen
		 @PreAuthorize("hasAuthority('ADMIN')")
		 @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
		    public String deleteProduct(@PathVariable("id") Long productId, Model model) {
		    	repository.deleteById(productId);
		    	
		        return "redirect:../productlist";
		 }
		  //Tuotetta voi muokata 
		 @PreAuthorize("hasAuthority('ADMIN')")
		 @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
		    public String editProduct(@PathVariable("id") Long Id, Model model) {
		    	model.addAttribute("product", repository.findById(Id));
		    	model.addAttribute("categories", crepository.findAll());
		    	
		    	return "editproduct";
		    }
		 
		//Etsii tuotteet
		 @RequestMapping(value= "/search1", method = RequestMethod.GET)
		public String productsearch(@RequestParam("haku") String Haku, Model model) {
			 
			 System.out.println("Tulosta" + Haku);	
			 model.addAttribute("products", repository.findByProductNameStartingWith(Haku));
				
				
		        return "searchlist";
				

		}
		
		 
		
		 
		 
	}


