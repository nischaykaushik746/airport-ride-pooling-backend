package com.nischay.airpool.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class SearchRequest {

    public String airport;
    public Instant start;
    public Instant end;
    public int seats = 1;

    public int page = 0;
    public int size = 10;

    public String sort = "departureTime,asc";
}
