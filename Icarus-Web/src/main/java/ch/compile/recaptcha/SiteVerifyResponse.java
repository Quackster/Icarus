package ch.compile.recaptcha;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SiteVerifyResponse {

    private boolean success;
    private Date challenge_ts;
    private String hostname;

    @SerializedName("error-codes")
    private List<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getChallenge() {
        return challenge_ts;
    }

    public void setChallenge(Date challenge_ts) {
        this.challenge_ts = challenge_ts;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
