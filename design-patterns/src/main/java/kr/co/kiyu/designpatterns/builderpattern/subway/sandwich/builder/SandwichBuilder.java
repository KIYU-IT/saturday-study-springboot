package kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.builder;

import kr.co.kiyu.designpatterns.builderpattern.subway.sandwich.model.dto.SandwichDTO;

public interface SandwichBuilder {

    SandwichBuilder selectBread(String bread);

    SandwichBuilder addCheese(String cheese, int quantity);

    SandwichBuilder addVegetable(String vegetable);

    SandwichBuilder addSauce(String sauce);

    SandwichDTO build();

}
