package kr.co.kiyu.datafeed.api.client.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BybitKlineResultDTO {
    @JsonProperty("list")
    private List<List<Object>> list;  // 실제 캔들 데이터 리스트

    @JsonProperty("category")
    private String category;  // 선물(Futures)인지 스팟(Spot)인지 구분
}
