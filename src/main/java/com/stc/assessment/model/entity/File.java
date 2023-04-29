package com.stc.assessment.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file_data")
@Data
@NoArgsConstructor
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "binary_file")
    @Lob
    private byte[] binary;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
    public File(byte[] binary, Item item){
        this.binary = binary;
        this.item = item;
    }
}