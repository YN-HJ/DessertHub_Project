package com.desserthub.dessert;
import java.util.Random;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dessert")
public class DessertController {
    
    private final DessertService dessertService;

    public DessertController(DessertService dessertService) {
        this.dessertService = dessertService;
    }

    @GetMapping
    public String getAllDesserts(Model model) {
        return "game/game-index";
    }

    @GetMapping("/tournament")
    public String getTournamentPage(Model model) {
        // スイーツのリストをロード後、トーナメント方式で出力
        List<Dessert> desserts = dessertService.getAllDesserts();        
        model.addAttribute("dessert", desserts);        
        return "game/tournament"; 
    }

    // 性格テスト
    @GetMapping("/personality-test")
    public String getPersonalityTestPage(Model model) {
        
        List<Dessert> desserts = dessertService.getAllDesserts(); 
        model.addAttribute("desserts", desserts);
        
        return "game/personality-test";
    }

    @GetMapping("/personality-test/{id}")
    @ResponseBody
    public Dessert getPersonalityResult(@PathVariable int id, Model model) {
        List<Dessert> desserts = dessertService.getAllDesserts(); 
        Dessert dessert = desserts.get(id);
        
        return dessert;    
    }    

    @GetMapping("/api/desserts/all")
    public List<Dessert> getAllDesserts() {
        return dessertService.getAllDesserts();
    }
        
    @GetMapping("/random-draw")
    public String getRandomDessert(Model model) {
        List<Dessert> desserts = dessertService.getAllDesserts();
            Random random = new Random();
            int randomIndex = random.nextInt(desserts.size());
            Dessert randomDessert = desserts.get(randomIndex);            
            // スイートをランダムでモデルに追加
            model.addAttribute("dessert", randomDessert);
        return "game/random-draw"; 
    }
}