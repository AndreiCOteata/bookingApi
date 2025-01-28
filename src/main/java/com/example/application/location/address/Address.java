package com.example.application.location.address;


import com.example.application.account.Account;
import com.example.application.common.model.AbstractEntity;
import com.example.application.location.city.City;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
@Entity(name = "Address")
@Table(name = "address", schema = "public")
public class Address extends AbstractEntity {

    private static final long serialVersionUID = -4435857176525535449L;

    @Id
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Long number;

    @OneToOne(mappedBy = "address")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
