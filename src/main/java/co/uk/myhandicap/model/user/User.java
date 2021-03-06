package main.java.co.uk.myhandicap.model.user;

import main.java.co.uk.myhandicap.model.user.address.Address;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User Entity.
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 02/07/14
 * @project MyHandicapApp
 */
@Entity
@Table(name="USERS")
public class User {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name="USERNAME")
    private String username;

    @Column(name="EMAIL")
    private String email;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="PASSWORD")
    private String password;

    // Used to store a separate ID for the embedded collection
    // @GenericGenerator(name = "hilo-gen", strategy = "hilo")
    // @CollectionId(columns = {@Column(name = "USER_ROLE_ID")}, generator = "hilo-gen", type = @Type(type = "long"))

    @Column(name="ROLES")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name="USER_ID"))
    private List<UserRole> userRoles = new ArrayList<UserRole>();

    // One-to-One Mapping example (Entity to Entity)
    // @OneToOne(cascade = {CascadeType.ALL})
    // @JoinColumn(name = "ADDRESS_ID")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(joinColumns = @JoinColumn(name="USER_ID"), inverseJoinColumns = @JoinColumn(name="ADDRESS_ID"))
    private List<Address> address = new ArrayList<Address>();

    @Column(name="ACTIVE")
    private String active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", userRoles=" + userRoles +
                ", address=" + address +
                ", active='" + active + '\'' +
                '}';
    }

}
