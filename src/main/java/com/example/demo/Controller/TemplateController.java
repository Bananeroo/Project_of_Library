package com.example.demo.Controller;

import com.example.demo.Book.Book;
import com.example.demo.Book.BookService;
import com.example.demo.BookOrder.BookOrder;
import com.example.demo.BookOrder.BookOrderService;
import com.example.demo.Client.Client;
import com.example.demo.Client.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static java.lang.Math.abs;

@Controller
@RequestMapping("/")
public class TemplateController {

    private final ClientService clientService;
    private final BookService bookService;
    private final PasswordEncoder passwordEncoder;
    private final BookOrderService bookOrderService;
    public static long roundUp(long num, long divisor) {
        int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
        return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
    }

    public TemplateController(ClientService clientService, BookService bookService, PasswordEncoder passwordEncoder, BookOrderService bookOrderService) {
        this.clientService = clientService;
        this.bookService = bookService;
        this.passwordEncoder = passwordEncoder;
        this.bookOrderService = bookOrderService;
    }


    @GetMapping("login")
    public String getLoginView(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null &&
                ( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                        || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))))
            return "redirect:/view";
        return "login";

    }
    @GetMapping("register")
    public String getregisterView(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null &&
                ( auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                        || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))))
            return "redirect:/view";
        return "register";

    }
    @PostMapping("register")
    public String showUpdateForm(@Valid Client client, BindingResult result, Model model) {
        if (result.hasErrors()||client.getEmail()==null) {
            model.addAttribute("code",result.getAllErrors().get(0).getDefaultMessage());
            return "register";
        }
        try {
            client.setAdminRole("user");
            clientService.addNewClient(client);
        }catch (IllegalStateException e){
            model.addAttribute("code","Email Used!");
            return "register";
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        model.addAttribute("code","Registration Complete");
        return "register";
    }
    @GetMapping (path="/static/image/{pathed}")
    public ResponseEntity<byte[]> getQRImage(@PathVariable final String pathed) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of("./src/main/resources/static/image/"+pathed+".png"));// Generate the image based on the id

        // Set headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED);
    }
    @GetMapping("/book/{id}")
    public String displayBookGet(@PathVariable("id") Long id,Model model) {
        Book book= bookService.getBook(id);
        List<Book> tmp= bookService.getBookByTitle(book.getTitle());
        Enumeration<Book> enm = Collections.enumeration(tmp);
        int i=0;
        while(enm.hasMoreElements()) {
            Book tmpBook = enm.nextElement();
            if(tmpBook.getBorrowed()==false)
            {
                i++;
            }

        }
        model.addAttribute("counter",i);
        model.addAttribute("found", (Book)book);
           return "book";


       }

       @GetMapping("/view/{page}")
    public String showUpdateForm(@PathVariable("page") int page, Model model) {
        List<Book> book= bookService.getBooksDistinct();
        List<Book> final_book= new ArrayList<Book>();
        List<String> tmp= new ArrayList<String>();

        int display=9;

        Enumeration<Book> enm = Collections.enumeration(book);
        while(enm.hasMoreElements()) {
            Book tmpBook = enm.nextElement();
            if(!tmp.contains(tmpBook.getTitle()))
            {
                final_book.add(tmpBook);
                tmp.add(tmpBook.getTitle());
            }
        }
        int pagenumber=(int)roundUp(final_book.stream().count(),display);
           if(page<1L) page=pagenumber;
        int startpage=(int)(page*display-display);
        if(startpage>=(int)final_book.stream().count())page=1;
        startpage=(int)(page*display-display);
        model.addAttribute("stronabeg", page);
        model.addAttribute("stronaend", pagenumber);

        int endpage=(int)(page*display);
        if(endpage>final_book.stream().count())endpage=(int)final_book.stream().count();

        model.addAttribute("books",final_book.subList(startpage,endpage));



        return "view";
    }

    @Transactional
    @PostMapping(path="/orders/order/{title}")
    public String orderBook(@PathVariable("title")String title, @ModelAttribute("mapping1Form") final Object mapping1FormObject,
                            final BindingResult mapping1BindingResult,Model model,final RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Client client =clientService.findClientByEmailNotOptional(auth.getName());

        List<Book> tmp=bookService.getBookByTitle(title);
        Enumeration<Book> enm = Collections.enumeration(tmp);
        Book tmpBook = null;
        Book sav = null;
        int i=0;
        while(enm.hasMoreElements()) {
           tmpBook= enm.nextElement();
            if(tmpBook.getBorrowed()==false)
            {
                bookService.borrowBook(true, tmpBook.getId());
                bookOrderService.addNewOrder(new BookOrder(
                        client.getId(),
                        tmpBook.getId(),
                        client.getEmail(),
                        tmpBook.getTitle(),
                        false
                ));
                model.addAttribute("found",tmpBook);
                sav=tmpBook;
                break;
            }

        }
        if(sav==null){
            redirectAttributes.addFlashAttribute("found", tmp.get(0));
            redirectAttributes.addFlashAttribute("counter", 0);
            redirectAttributes.addFlashAttribute("borrow", "Can't Order");
            return "redirect:/book";
        }
        while(enm.hasMoreElements()) {
            tmpBook= enm.nextElement();
            if(tmpBook.getBorrowed()==false)
                i++;
        }
        redirectAttributes.addFlashAttribute("found", sav);
        redirectAttributes.addFlashAttribute("counter", i);
        redirectAttributes.addFlashAttribute("borrow", "Order Successful");

        return "redirect:/book";
    }

    @PostMapping("/book")
    public String displayBookPost(Model model) {

        return "book";


    }

    @GetMapping("/book")
    public String displayBookGet( @ModelAttribute("found") final Book mapping1FormObject,@ModelAttribute("borrow") final String mapping1FormObject1,@ModelAttribute("counter") final int mapping1FormObject2,
                                  final BindingResult mapping1BindingResult,Model model) {
        model.addAttribute("found", mapping1FormObject);
        model.addAttribute("borrow", mapping1FormObject1);
        model.addAttribute("counter", mapping1FormObject2);


        return "book";
    }

    @PostMapping("/search")
    public String searchBookTitle(@RequestParam("title")String title,Model model){
        List<Book> tmp = bookService.getBookByTitle(title);
        if(tmp.isEmpty()){
            return "redirect:view/1";
        }
        Enumeration<Book> enm = Collections.enumeration(tmp);
        int i=0;
        while(enm.hasMoreElements()) {
            Book tmpBook = enm.nextElement();
            if(tmpBook.getBorrowed()==false)
            {
                i++;
            }

        }
        model.addAttribute("counter",i);

        model.addAttribute("found",tmp.get(0));

        return "book";
    }

    @Transactional
    @PostMapping("/addbook")
    public String uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("bookDescription") String bookDescription,
            @RequestParam("ilosc") int ilosc,
            Model model) throws Exception {
        if (file.isEmpty()) {
            model.addAttribute("message", "File Error");
            return "redirect:/add_file";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get("./src/main/resources/static/image/" + title+".png");
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<ilosc;i++){
            Book book=new Book(title,author,bookDescription,false);
            bookService.addNewBook(book);
        }

        model.addAttribute("message", "Book Added");


        return "add_file";
    }

    @GetMapping("/add_file")
    public String addfile(){
        return "add_file";
    }

    @GetMapping("/orders")
    public String displayOrders(Model model) {

        List<BookOrder> bookOrders=bookOrderService.getOrders();
        model.addAttribute("zamowienia",bookOrders);


        return "orders";


    }

    @Transactional
    @PostMapping("/returned")
    public String updateOrder(@RequestParam("id")Long id,@RequestParam("id_book")Long id_book){
        bookOrderService.BooksetID(true,id);
        bookService.borrowBook(false,id_book);
        return   "redirect:/orders";
    }
    @Transactional
    @PostMapping("/deleted")
    public String deleteOrder(@RequestParam("id_del")Long id){
        bookOrderService.BookdeleteID(id);
        return   "redirect:/orders";
    }
    @Transactional
    @GetMapping("/users")
    public String displayUsers(Model model) {

        List<Client> clientList=  clientService.getClients();
        model.addAttribute("users",clientList);
        return "users";
    }

    @Transactional
    @PostMapping("/update")
    public String updateClients(
            @RequestParam("id")Long id,
            @RequestParam("adminRole")String adminRole,
            @RequestParam("name")String name,
            @RequestParam("password")String password,
            @RequestParam("email")String email,
            @RequestParam("dob") String dob,
            @RequestParam("age")int age,
            Model model
            ){
            if(!adminRole.equals("user") && !adminRole.equals("admin")){
                model.addAttribute("userinfo","Role Not Correct");
                List<Client> list= clientService.getClients();
                model.addAttribute("users",list);
                return "users";
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dob, formatter);
            clientService.UpdateClient(adminRole,name,password,email,localDate,age,id);

        model.addAttribute("userinfo","Updated");
        List<Client> list= clientService.getClients();
        model.addAttribute("users",list);
        return "users";
    }

    @Transactional
    @PostMapping("/delete")
    public String updateClients( @RequestParam("id_del")Long id_del,Model model){
        clientService.DeleteClient(id_del);

        model.addAttribute("users",clientService.getClients());
        model.addAttribute("userinfo","Client Deleted");

        return "users";

    }


}
