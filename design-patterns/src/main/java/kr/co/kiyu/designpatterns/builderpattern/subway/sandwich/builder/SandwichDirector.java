package kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.builder;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.dto.SandwichDTO;

@Component
public class SandwichDirector {

    public SandwichDTO buildChickenTeriyaki(SandwichBuilder builder) {
        return builder
                .selectBread("WHEAT")
                .addCheese("AMERICAN_CHEESE", 2)
                .addVegetable("LETTUCE")
                .addVegetable("TOMATO")
                .addSauce("SWEET_ONION")
                .addSauce("MAYO")
                .build();
    }

    public SandwichDTO buildVeggieDelight(SandwichBuilder builder) {
        return builder
                .selectBread("WHITE")
                .addVegetable("CUCUMBER")
                .addVegetable("OLIVE")
                .addVegetable("GREEN_PEPPER")
                .addSauce("OLIVE_OIL")
                .addSauce("RED_WINE_VINEGAR")
                .build();
    }
}
