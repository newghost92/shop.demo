package com.shop.demo.config;

public class Constants {

    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[`~!()@#$%^&*=_+\\-{}\\[\\]/\"':;.<>?]).*$";
    public static final String NUMBER_REGEX = "\\d*";
    public static final String EMAIL_REGEX_ACCEPT_BLANK = "^$|\\A[a-z0-9!#$%&'*+/=?^_‘{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_‘{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z";

    public static final String DEFAULT_LANGUAGE = "en";

    public static final int DEFAULT_CURRENT_PAGE = 1;
    public static final int DEFAULT_ROWS_PER_PAGE = 15;

    public static final class Label {

        public static final String USER = "user";
        public static final String ITEM_ID = "itemId";
        public static final String ORDER_ID = "orderId";
        public static final String STORAGE = "Storage";
        public static final String CUSTOMER_ID = "customerId";
        public static final String ITEM = "Item";
        public static final String ORDER = "Order";
        public static final String CUSTOMER = "Customer";
        public static final String CURRENT_USER = "currentUser";
        public static final String NEW_PASSWORD = "newPassword";
        public static final String CURRENT_PASSWORD = "currentPassword";
        public static final String CURRENT_PASSWORD_LOWER = "currentPasswordLower";
    }

    public static final class ErrorCode {

        /**
         * {0} not found.
         */
        public static final String E0001 = "E0001";

        /**
         * BadCredentials.
         */
        public static final String E0002 = "E0002";

        /**
         * User Inactive.
         */
        public static final String E0003 = "E0003";

        /**
         * Value {0} is invalid. {1}
         */
        public static final String E0004 = "E0004";

        /**
         * TOKEN EXPIRED.
         */
        public static final String E0005 = "E0005";

        /**
         * Account has been locked
         */
        public static final String E0006 = "E0006";

        /**
         * Validation failed.
         */
        public static final String E0007 = "E0007";

        /**
         * Incorrect {0}!
         */
        public static final String E0008 = "E0008";

        /**
         * {0} must be difference from the {1}.
         */
        public static final String E0009 = "E0009";

        /**
         * Please enter the {0}
         */
        public static final String E0010 = "E0010";

        /**
         * {0} Inactive.
         */
        public static final String E0011 = "E0011";

        /**
         * {0} out of stock.
         */
        public static final String E0012 = "E0012";

        /**
         * {0} cannot be cancelled.
         */
        public static final String E0013 = "E0013";
    }
}
