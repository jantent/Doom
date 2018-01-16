package springBoot.domain.bean;

public class Admin {
    private int adminId;
    private String adminAccount;
    private String adminPassword;
    private String address;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminCount='" + adminAccount + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
