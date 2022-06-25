package beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Coupon> coupons=new ArrayList<>();


    public Customer(int id, String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this(firstName, lastName, email, password, coupons);
        this.id = id;
    }

    public Customer(int id, String firstName, String lastName, String email, String password) {
        this(firstName,lastName,email,password);
        this.id = id;

    }

    public Customer(String firstName, String lastName, String email, String password, List<Coupon> coupons) {
        this(firstName, lastName, email, password);
        this.firstName = firstName;

    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }
}
