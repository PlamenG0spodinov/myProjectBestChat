package com.theBestChat.theBestChat.Entityes;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "channels")
public class Channels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    @Column(name = "is_active")
    private int isActive = 1;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public int getIsActive() {
        return isActive;
    }

    public  void setIsActive(int isActive) {
        this.isActive = isActive;
    }


//    public String toString() {
//        return "Channels{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", owner=" + (owner != null ? owner.getId() : null) +
//                ", isActive=" + isActive +
//                '}';
//    }
}
