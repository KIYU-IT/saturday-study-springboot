package kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.builder;

import kr.co.kiyu.designpatterns.chainofresponsibility.subway.sandwich.model.dto.SandwichDTO;

public interface SandwichBuilderByChainOfResponsibility {

    SandwichBuilderByChainOfResponsibility selectBread(String bread);

    SandwichBuilderByChainOfResponsibility addCheese(String cheese, int quantity);

    SandwichBuilderByChainOfResponsibility addVegetable(String vegetable);

    SandwichBuilderByChainOfResponsibility addSauce(String sauce);

    SandwichDTO build();

}
