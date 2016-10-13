package com.swiftpot.swiftalertmain.models;

import com.swiftpot.swiftalertmain.db.model.MessagesDetailedReportDoc;

import java.util.List;

/**
 * @author Ace Programmer Rbk
 *         <Rodney Kwabena Boachie at [rodney@swiftpot.com,rbk.unlimited@gmail.com]> on
 *         13-Oct-16 @ 8:09 AM
 */
public class MessagesDetailedReportResponse {

    String message ;

    List<MessagesDetailedReportDoc> reportDetailedList;

    public MessagesDetailedReportResponse(){}

    public MessagesDetailedReportResponse(String message, List<MessagesDetailedReportDoc> reportDetailedList) {
        this.message = message;
        this.reportDetailedList = reportDetailedList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MessagesDetailedReportDoc> getReportDetailedList() {
        return reportDetailedList;
    }

    public void setReportDetailedList(List<MessagesDetailedReportDoc> reportDetailedList) {
        this.reportDetailedList = reportDetailedList;
    }
}
