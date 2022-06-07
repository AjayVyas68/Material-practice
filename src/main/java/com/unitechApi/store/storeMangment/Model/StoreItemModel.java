package com.unitechApi.store.storeMangment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unitechApi.AuditingAndResponse.Audit;
import com.unitechApi.purchase.RawMaterial.vendor.model.VendorModel;
import com.unitechApi.store.issue.model.IssueItem;
import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.productCategory.model.ProductCategory;
import com.unitechApi.store.unit.model.Unit;
import com.unitechApi.user.model.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "item", schema = "store_management")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoreItemModel extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    @Column(unique = true)
    private String itemName;
    private String itemDescription;
    @Column(unique = true)
    private String drawingNo;
    @Column(unique = true)
    private String catalogNo;
    private long frequency;
    
    private int RemainingItem;
    private int paytax;
    private Date created;

    public Date getCreated() {
        return created;
    }
    @PrePersist
    public void setCreated() {
        this.created = new Date();
    }

    private Boolean activation;

    private int expiryDays;
    private long quantity;

    @OneToMany(mappedBy = "storeItemModel",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItemModel","indents","usageItems","emp","itemRequest","issueItemsData"})
    private Set<IssueItem> issueItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "p_id"), name = "p_id", referencedColumnName = "pid")
    @JsonIgnoreProperties({"item","unit"})
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(foreignKey = @ForeignKey(name = "u_id"), name = "u_id", referencedColumnName = "uid")
    @JsonIgnoreProperties({"itemunit"})
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "emp_id"), name = "emp_id", referencedColumnName = "user_profile_id")
    @JsonIgnoreProperties({"itemModelSet","issueItemsData","itemRequest"})
    private User employe;
    @OneToMany(mappedBy = "storeItem", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"storeItem","employee"})
    private Set<Indent> itemRequest;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "store_management",name = "item_Vendor_details",
            joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "vendor_id"))
    @JsonIgnoreProperties({"contractModels","dataVendorAndItem"})
    private Set<VendorModel> dataVendorAndItem=new HashSet<>();

    public Set<VendorModel> getDataVendorAndItem() {
        return dataVendorAndItem;
    }

    public void setDataVendorAndItem(Set<VendorModel> dataVendorAndItem) {
        this.dataVendorAndItem = dataVendorAndItem;
    }

    public User getEmploye() {
        return employe;
    }

    public void setEmploye(User employe) {
        this.employe = employe;
    }



    public Set<IssueItem> getIssueItem() {
        return issueItem;
    }

    public void setIssueItem(Set<IssueItem> issueItem) {
        this.issueItem = issueItem;
    }

    public Set<Indent> getItemRequest() {
        return itemRequest;//.stream().sorted((o1, o2) -> o1.getItemId().compareTo(o2.getItemId())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setItemRequest(Set<Indent> itemRequest) {
        this.itemRequest = itemRequest;
    }

    public String getDrawingNo() {
        return drawingNo;
    }

    public void setDrawingNo(String drawingNo) {
        this.drawingNo = drawingNo;
    }

    public String getCatalogNo() {
        return catalogNo;
    }

    public void setCatalogNo(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }


    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }


    public int getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(int expiryDays) {
        this.expiryDays = expiryDays;
    }



    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public int getRemainingItem() {
        return RemainingItem;
    }

    public void setRemainingItem(int remainingItem) {
        RemainingItem = remainingItem;
    }

    public int getPaytax() {
        return paytax;
    }

    public void setPaytax(int paytax) {
        this.paytax = paytax;
    }

    public void deleteVendor(VendorModel vendorModel) {
        dataVendorAndItem.remove(vendorModel);
    }
    @Override
    public String toString() {
        return "StoreItemModel{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", drawingNo='" + drawingNo + '\'' +
                ", catalogNo='" + catalogNo + '\'' +
                ", frequency='" + frequency + '\'' +
                ", RemainingItem=" + RemainingItem +
                ", paytax=" + paytax +
                ", created=" + created +
                ", activation=" + activation +
                ", expiryDate=" + expiryDays +
                ", quantity=" + quantity +
                ", productCategory=" + productCategory +
                ", unit=" + unit +
                ", itemRequest=" + itemRequest +
                '}';
    }


}
