package com.shop.demo.domain.dto.request;

import com.shop.demo.config.Constants;
import com.shop.demo.utils.NumberUtil;

public abstract class BaseSearchRequestDTO {

    private String currentPage;

    private String rowsPerPage;

    public Integer getCurrentPage() {
        Integer convertCurrentPage;
        try {
            convertCurrentPage = NumberUtil.convertStringToInteger(this.currentPage);
        } catch (NumberFormatException ex) {
            convertCurrentPage = Constants.DEFAULT_CURRENT_PAGE;
        }
        return convertCurrentPage == null || convertCurrentPage < 0
            ? Constants.DEFAULT_CURRENT_PAGE
            : convertCurrentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getRowsPerPage() {
        Integer convertRowsPerPage;
        try {
            convertRowsPerPage = NumberUtil.convertStringToInteger(this.rowsPerPage);
        } catch (NumberFormatException ex) {
            convertRowsPerPage = Constants.DEFAULT_ROWS_PER_PAGE;
        }
        return convertRowsPerPage == null || convertRowsPerPage < 0
            ? Constants.DEFAULT_ROWS_PER_PAGE
            : convertRowsPerPage;
    }

    public void setRowsPerPage(String rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public Integer getRealCurrentPage() {
        return getCurrentPage() == 0 ? 0 : getCurrentPage() - 1;
    }
}
