package com.fourseers.parttimejob.arrangement.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(value = "search_result", description = "Search result for available jobs.")
public class SearchResultDto {
    @ApiModelProperty(value = "Fetched search result for given query.")
    List<Map<String, Object>> content;

    @ApiModelProperty(value = "Total hits of given query.")
    Long totalHits;

    public List<Map<String, Object>> getContent() {
        return content;
    }

    public void setContent(List<Map<String, Object>> content) {
        this.content = content;
    }

    public Long getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Long totalHits) {
        this.totalHits = totalHits;
    }
}
