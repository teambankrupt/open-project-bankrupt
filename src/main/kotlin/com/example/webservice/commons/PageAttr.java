package com.example.webservice.commons;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageAttr {
    public static int PAGE_SIZE = 10;

    public static String SORT_BY_FIELD_ID = "id";

    public static PageRequest getPageRequest(int page){
        return PageRequest.of(Math.abs(page), PageAttr.PAGE_SIZE, Sort.Direction.DESC,PageAttr.SORT_BY_FIELD_ID);
    }

    public static PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(Math.abs(page), Math.abs(size), Sort.Direction.DESC, PageAttr.SORT_BY_FIELD_ID);
    }
}
