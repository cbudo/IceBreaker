package asdf.icebreakers.icebreakers.model.dto;

/**
 * Created by b on 10/16/14.
 */
public class AccountDTO {

    int accountId;

    int webId;
    int webPersonId;
    String emailAddress;
    String password;
    String status;
    String token;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWebPersonId() {
        return webPersonId;
    }

    public void setWebPersonId(int webPersonId) {
        this.webPersonId = webPersonId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
