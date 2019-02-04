package com.example.webservice.commons;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageAttr {
    public static int PAGE_SIZE = 10;

    public static String SORT_BY_FIELD_ID = "id";

    public static PageRequest getPageRequest(int page){
        return new PageRequest(page, PageAttr.PAGE_SIZE, Sort.Direction.DESC,PageAttr.SORT_BY_FIELD_ID);
    }

    public static PageRequest getPageRequest(int page, int size) {
        if (size <= 0) size = 10;
        return new PageRequest(page, size, Sort.Direction.DESC, PageAttr.SORT_BY_FIELD_ID);
    }
}
