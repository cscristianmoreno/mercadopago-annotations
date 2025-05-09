package io.github.cscristianmoreno.dto.mercadopago;

public class BackUrlsDTO {
    private String redirectSuccessUrl;
    private String redirectFailureUrl;
    private String redirectPendingUrl;

    public String getSuccessUrl() {
        return redirectSuccessUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.redirectSuccessUrl = successUrl;
    }

    public String getFailureUrl() {
        return redirectFailureUrl;
    }

    public void setFailureUrl(String failureUrl) {
        this.redirectFailureUrl = failureUrl;
    }

    public String getPendingUrl() {
        return redirectPendingUrl;
    }
    
    public void setPendingUrl(String pendingUrl) {
        this.redirectPendingUrl = pendingUrl;
    }
}
