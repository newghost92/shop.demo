package com.shop.demo.domain.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public class PageResponse<T> {

    private Integer totalPages;
    private Integer currentPage;
    private Integer rowsPerPage;
    private Long totalElements;
    private List<T> data;

    public static <T> PageResponse<T> of(
        Integer totalPages,
        Integer currentPage,
        Integer rowsPerPage,
        Long totalElements,
        List<T> data
    ) {
        PageResponse<T> response = new PageResponse<>();
        response.setTotalPages(totalPages);
        response.setCurrentPage(currentPage);
        response.setRowsPerPage(rowsPerPage);
        response.setTotalElements(totalElements);
        response.setData(data);
        return response;
    }

    public static <T> PageResponse<T> of(
        Integer currentPage,
        Integer rowPerPage,
        Long totalElements,
        List<T> data
    ) {
        PageResponse<T> response = new PageResponse<>();
        long totalPages = rowPerPage <= 0 ? 0L :
            (totalElements / rowPerPage) + (totalElements % rowPerPage > 0 ? 1 : 0);
        response.setTotalPages((int) totalPages);
        response.setCurrentPage(currentPage);
        response.setRowsPerPage(rowPerPage);
        response.setTotalElements(totalElements);
        response.setData(data);
        return response;
    }

    public static <T> PageResponse<T> of(Page<T> tPage) {
        return of(tPage.getNumber() + 1, tPage.getSize(), tPage.getTotalElements(), tPage.getContent());
    }

    public PageResponse() {
    }

    public PageResponse(Integer totalPages, Integer currentPage, Integer rowsPerPage, Long totalElements, List<T> data) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.rowsPerPage = rowsPerPage;
        this.totalElements = totalElements;
        this.data = data;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(Integer rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageResponse{" +
            "totalPages=" + totalPages +
            ", currentPage=" + currentPage +
            ", rowsPerPage=" + rowsPerPage +
            ", totalElements=" + totalElements +
            ", data=" + data +
            '}';
    }
}
