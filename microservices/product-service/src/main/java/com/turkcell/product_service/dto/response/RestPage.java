package com.turkcell.product_service.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Jackson ile serialize/deserialize edilebilen {@link PageImpl} sarmalayıcısı.
 * Redis cache'inde Spring'in ham {@code PageImpl} sınıfı default constructor
 * olmadığı için deserialize edilemez; bu sınıf {@code @JsonCreator} ile bu
 * sorunu çözer. {@code Page} arayüzünü implemente ettiği için servis imzaları
 * değişmeden döndürülebilir.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestPage<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPage(@JsonProperty("content") List<T> content,
                    @JsonProperty("number") int number,
                    @JsonProperty("size") int size,
                    @JsonProperty("totalElements") long totalElements) {
        super(content, PageRequest.of(number, Math.max(size, 1)), totalElements);
    }

    public RestPage(Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}