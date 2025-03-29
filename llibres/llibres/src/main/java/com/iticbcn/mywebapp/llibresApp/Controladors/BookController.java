package com.iticbcn.mywebapp.llibresApp.Controladors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.iticbcn.mywebapp.llibresApp.Model.Llibre;
import com.iticbcn.mywebapp.llibresApp.Model.Usuaris;
import com.iticbcn.mywebapp.llibresApp.Serveis.ProductService;

@Controller
@SessionAttributes("users")
public class BookController {

    @Autowired
    ProductService repoll;

    @GetMapping("/")
    public String iniciar(Model model) {
        return "login";
    }

    @PostMapping("/index")
    public String login(@ModelAttribute("users") Usuaris users, Model model) {

        model.addAttribute("users", users);

        if (users.getUsuari().equals("yago")
                && users.getPassword().equals("system")) {
            return "index";
        } else {
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }

    @ModelAttribute("users")
    public Usuaris getDefaultUser() {
        return new Usuaris();
    }

    @GetMapping("/index")
    public String index(@ModelAttribute("users") Usuaris users, Model model) {

        return "index";

    }

    @GetMapping("/consulta")
    public String consulta(@ModelAttribute("users") Usuaris users, Model model) {

        Set<Llibre> llibres = repoll.findAll();

        model.addAttribute("llibres", llibres);

        return "consulta";
    }

    @GetMapping("/inserir")
    public String inputInserir(@ModelAttribute("users") Usuaris users, Model model) {

        return "inserir";
    }

    @GetMapping("/cercaid")
    public String inputCerca(@ModelAttribute("users") Usuaris users, Model model) {

        Llibre llibre = new Llibre();
        model.addAttribute("llibreErr", true);
        model.addAttribute("message", "");
        model.addAttribute("llibre", llibre);

        return "cercaid";

    }

    @PostMapping("/inserir")
    public String inserir(@ModelAttribute("users") Usuaris users,
            @RequestParam(name = "titol") String titol,
            @RequestParam(name = "autor") String autor,
            @RequestParam(name = "editorial") String editorial,
            @RequestParam(name = "datapublicacio") String datapublicacio,
            @RequestParam(name = "tematica") String tematica,
            @RequestParam(name = "isbn") String isbn,
            Model model) {
        String message = "";
        boolean llibreErr = false;
        LocalDate dataPublicacio = null;
        try {
            dataPublicacio = LocalDate.parse(datapublicacio);
        } catch (DateTimeParseException e) {
            message = "La data de publicació no té el format correcte (yyyy-MM-dd)";
            llibreErr = true;
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }
        if (!repoll.validarISBN(isbn)) { 
            llibreErr = true;
            message = "ISBN invalid";
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }
        if(titol.isBlank()){
            llibreErr = true;
            message = "El titol no pot estar buit";
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }

        try{
            Llibre llibre = new Llibre();
            llibre.setTitol(titol);
            llibre.setAutor(autor);
            llibre.setEditorial(editorial);
            llibre.setDatapublicacio(dataPublicacio);
            llibre.setTematica(tematica);
            llibre.setIsbn(isbn);
            repoll.save(llibre);
        } catch(Exception e){
            llibreErr = true;
            message = "ISBN o Tiol duplicat";
            model.addAttribute("message", message);
            model.addAttribute("llibreErr", llibreErr);
            return "inserir";
        }
        

        Set<Llibre> llibres = repoll.findAll();
        model.addAttribute("llibres", llibres);

        return "consulta";
    }

    @PostMapping("/cercaid")
    public String cercaId(@ModelAttribute("users") Usuaris users,
            @RequestParam(name = "id_Llibre", required = false) String id_Llibre,
            Model model) {

        Long idLlib = null;
        String message = "";
        boolean llibreErr = false;

        if (id_Llibre == null || id_Llibre.trim().isEmpty()) {
            message = "La id del llibre no pot estar buida.";
            llibreErr = true;
        } else {
            try {
                id_Llibre = id_Llibre.trim();
                idLlib = Long.parseLong(id_Llibre);
                Optional<Llibre> llibre = repoll.findById_Llibre(idLlib);
                if (llibre.isPresent()) {
                    model.addAttribute("llibre", llibre.get());
                } else {
                    throw new Exception("No hi ha cap llibre amb aquesta id");
                }

            } catch (NumberFormatException e) {
                message = "La id de llibre ha de ser un nombre enter";
                llibreErr = true;
            } catch (Exception e) {
                // En caso de que no se encuentre el libro o cualquier otro error, mostramos el
                // mensaje de error
                message = e.getMessage();
                llibreErr = true;
            }
        }
        model.addAttribute("message", message);
        model.addAttribute("llibreErr", llibreErr);

        // Retornamos la vista cercaid (el usuario permanecerá en la misma pantalla)
        return "cercaid";
    }

}