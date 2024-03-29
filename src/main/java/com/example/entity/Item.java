package com.example.entity;

import com.example.constant.ItemSellStatus;
import com.example.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.example.dto.ItemFormDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
// 상품
public class Item extends BaseEntity{
    // 상품 코드
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 상품명
    @Column(nullable = false, length = 50)
    private String itemNm;

    // 가격
    @Column(name = "price", nullable = false)
    private int price;

    // 재고 수량
    @Column(nullable = false)
    private int stockNumber;

    // 상품 상세 설명
    @Lob
    @Column(nullable = false)
    private String itemDetail;

    // 상품 판매 상태
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: "+this.stockNumber+")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }
}
