package kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.builder;

import java.util.*;

import org.springframework.stereotype.Component;

import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.dto.SandwichDTO;
import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.type.*;

@Component
public class SubwaySandwichBuilder implements SandwichBuilder {

    private BreadType bread;
    private Map<CheeseType, Integer> cheeses = new HashMap<>();
    private List<VegetableType> vegetables = new ArrayList<>();
    private Set<SauceType> sauces = new HashSet<>();

    @Override
    public SandwichBuilder selectBread(String bread) {
        this.bread = BreadType.valueOf(bread.toUpperCase());
        return this;
    }

    @Override
    public SandwichBuilder addCheese(String cheese, int quantity) {
        CheeseType cheeseType = CheeseType.valueOf(cheese.toUpperCase());
        cheeses.put(cheeseType, cheeses.getOrDefault(cheeseType, 0) + quantity);
        return this;
    }

    @Override
    public SandwichBuilder addVegetable(String vegetable) {
        vegetables.add(VegetableType.valueOf(vegetable.toUpperCase()));
        return this;
    }

    @Override
    public SandwichBuilder addSauce(String sauce) {
        sauces.add(SauceType.valueOf(sauce.toUpperCase()));
        return this;
    }

    @Override
    public SandwichDTO build() {
        return SandwichDTO.builder()
                .bread(bread)
                .cheeses(cheeses)
                .vegetables(vegetables)
                .sauces(sauces)
                .build();
    }

}
