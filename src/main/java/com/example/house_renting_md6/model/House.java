package com.example.house_renting_md6.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Names cannot be left blank")
    private String name;
    @ManyToOne
    private Category category;
    @NotBlank(message = "The address cannot be left blank")
    private String address;
    @Max(value = 10, message = "Enter at most 10 bedrooms")
    @Min(value = 1, message = "Enter at least 1 bedroom")
    private int bedroom;
    @Max(value = 3, message = "Enter at most 3 rooms")
    @Min(value = 1, message = "Enter at least 1 bathroom")
    private int bathroom;
    private String description;
    @Min(value = 1, message = "The lowest price is 1")
    private Long price;
    @ManyToOne
    private User owner;
    private int status;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    private Set<Image> images = new LinkedHashSet<>();

    private String avatarHouse;

    public House() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatarHouse() {
        return avatarHouse;
    }

    public void setAvatarHouse(String avatarHouse) {
        this.avatarHouse = avatarHouse;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
