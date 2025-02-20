package kr.co.kiyu.datafeed.api.client.bybit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BybitKlineResponseDTO {
    @JsonProperty("retCode")
    private int retCode;

    @JsonProperty("retMsg")
    private String retMsg;

    @JsonProperty("result")
    private BybitKlineResultDTO result;

    @JsonProperty("time")
    private long time;
}
