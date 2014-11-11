package org.tmf.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.commons.Utilities;
import org.tmf.dsmapi.commons.annotation.EntityReferenceProperty;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "id": "42",
 *     "version": "3.43",
 *     "href": "http://serverlocation:port/catalogManagement/productOffering/42",
 *     "name": "Virtual Storage Medium",
 *     "description": "Virtual Storage Medium",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "isBundle": "true",
 *     "category": [
 *         {
 *             "id": "12",
 *             "version": "2.0",
 *             "href": "http://serverlocation:port/catalogManagement/category/12",
 *             "name": "Cloud offerings"
 *         }
 *     ],
 *     "channel": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/marketSales/channel/13",
 *             "name": "Online Channel"
 *         }
 *     ],
 *     "place": [
 *         {
 *             "id": "12",
 *             "href": "http://serverlocation:port/marketSales/place/12",
 *             "name": "France"
 *         }
 *     ],
 *     "bundledProductOffering": [
 *         {
 *             "id": "15",
 *             "href": "http://serverlocation:port/catalogManagement/productOffering/15",
 *             "lifecycleStatus": "Active",
 *             "name": "Offering 15"
 *         },
 *         {
 *             "id": "64",
 *             "href": "http://serverlocation:port/catalogManagement/productOffering/64",
 *             "lifecycleStatus": "Active",
 *             "name": "Offering 64"
 *         }
 *     ],
 *     "serviceLevelAgreement": {
 *         "id": "28",
 *         "href": "http://serverlocation:port/slaManagement/serviceLevelAgreement/28",
 *         "name": "Standard SLA"
 *     },
 *     "productSpecification": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/productSpecification/13",
 *             "version": "2.0",
 *             "name": "specification product 1"
 *         }
 *     ],
 *     "serviceCandidate": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/serviceCandidate/13",
 *             "version": "2.0",
 *             "name": "specification service 1"
 *         }
 *     ],
 *     "resourceCandidate": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/resourceCandidate/13",
 *             "version": "2.0",
 *             "name": "specification resource 1"
 *         }
 *     ],
 *     "productOfferingTerm": [
 *         {
 *             "name": "12 Month",
 *             "description": "12 month contract",
 *             "duration": "12",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": "2013-06-19T00:00:00-04:00"
 *             }
 *         }
 *     ],
 *     "productOfferingPrice": [
 *         {
 *             "id": "15",
 *             "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/15",
 *             "name": "Monthly Price",
 *             "description": "monthlyprice",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": "2013-06-19T00:00:00-04:00"
 *             },
 *             "priceType": "recurring",
 *             "unitOfMeasure": "",
 *             "price": {
 *                 "taxIncludedAmount": "12.00",
 *                 "dutyFreeAmount": "10.00",
 *                 "taxRate": "20.00",
 *                 "currencyCode": "EUR"
 *             },
 *             "recurringChargePeriod": "monthly"
 *         },
 *         {
 *             "id": "17",
 *             "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/17",
 *             "name": "Usage Price",
 *             "description": "usageprice",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": "2013-06-19T00:00:00-04:00"
 *             },
 *             "priceType": "usage",
 *             "unitOfMeasure": "second",
 *             "price": {
 *                 "taxIncludedAmount": "12.00",
 *                 "dutyFreeAmount": "10.00",
 *                 "taxRate": "20.00",
 *                 "currencyCode": "EUR"
 *             },
 *             "recurringChargePeriod": ""
 *         }
 *     ]
 * }
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_PRODUCT_OFFERING")
public class ProductOffering extends AbstractCatalogEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ProductOffering.class.getName());

    @Column(name = "IS_BUNDLE", nullable = true)
    private Boolean isBundle;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_CATEGORY", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    @EntityReferenceProperty(classId=Category.class)
    private List<CatalogReference> category;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_CHANNEL", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Channel> channel;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PLACE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Place> place;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PRODUCT_OFFER", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    @EntityReferenceProperty(classId=ProductOffering.class)
    private List<BundledProductReference> bundledProductOffering;

    @Embedded
    private ServiceLevelAgreement serviceLevelAgreement;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PRODUCT_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    @EntityReferenceProperty(classId=ProductSpecification.class)
    private List<CatalogReference> productSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_SERVICE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    @EntityReferenceProperty(classId=ServiceCandidate.class)
    private List<CatalogReference> serviceCandidate;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_RESOURCE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    @EntityReferenceProperty(classId=ResourceCandidate.class)
    private List<CatalogReference> resourceCandidate;

    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_OFFERING_TERM", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<ProductOfferingTerm> productOfferingTerm;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PRICE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<ProductOfferingEmbeddedPriceData> productOfferingPrice;

    public ProductOffering() {
    }

    public Boolean getIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public List<CatalogReference> getCategory() {
        return category;
    }

    public void setCategory(List<CatalogReference> category) {
        this.category = category;
    }

    public List<Channel> getChannel() {
        return channel;
    }

    public void setChannel(List<Channel> channel) {
        this.channel = channel;
    }

    public List<Place> getPlace() {
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }

    public List<BundledProductReference> getBundledProductOffering() {
        return bundledProductOffering;
    }

    public void setBundledProductOffering(List<BundledProductReference> bundledProductOffering) {
        this.bundledProductOffering = bundledProductOffering;
    }

    public ServiceLevelAgreement getServiceLevelAgreement() {
        return serviceLevelAgreement;
    }

    public void setServiceLevelAgreement(ServiceLevelAgreement serviceLevelAgreement) {
        this.serviceLevelAgreement = serviceLevelAgreement;
    }

    public List<CatalogReference> getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(List<CatalogReference> productSpecification) {
        this.productSpecification = productSpecification;
    }

    public List<CatalogReference> getServiceCandidate() {
        return serviceCandidate;
    }

    public void setServiceCandidate(List<CatalogReference> serviceCandidate) {
        this.serviceCandidate = serviceCandidate;
    }

    public List<CatalogReference> getResourceCandidate() {
        return resourceCandidate;
    }

    public void setResourceCandidate(List<CatalogReference> resourceCandidate) {
        this.resourceCandidate = resourceCandidate;
    }

    public List<ProductOfferingTerm> getProductOfferingTerm() {
        return productOfferingTerm;
    }

    public void setProductOfferingTerm(List<ProductOfferingTerm> productOfferingTerm) {
        this.productOfferingTerm = productOfferingTerm;
    }

    public List<ProductOfferingEmbeddedPriceData> getProductOfferingPrice() {
        return productOfferingPrice;
    }

    public void setProductOfferingPrice(List<ProductOfferingEmbeddedPriceData> productOfferingPrice) {
        this.productOfferingPrice = productOfferingPrice;
    }

    @JsonProperty(value = "category")
    public List<CatalogReference> categoryToJson() {
        return (category != null && category.size() > 0) ? category : null;
    }

    @JsonProperty(value = "channel")
    public List<Channel> channelToJson() {
        return (channel != null && channel.size() > 0) ? channel : null;
    }

    @JsonProperty(value = "place")
    public List<Place> placeToJson() {
        return (place != null && place.size() > 0) ? place : null;
    }

    @JsonProperty(value = "bundledProductOffering")
    public List<BundledProductReference> bundledProductOfferingToJson() {
        return (bundledProductOffering != null && bundledProductOffering.size() > 0) ? bundledProductOffering : null;
    }

    @JsonProperty(value = "productSpecification")
    public List<CatalogReference> productSpecificationToJson() {
        return (productSpecification != null && productSpecification.size() > 0) ? productSpecification : null;
    }

    @JsonProperty(value = "serviceCandidate")
    public List<CatalogReference> serviceCandidateToJson() {
        return (serviceCandidate != null && serviceCandidate.size() > 0) ? serviceCandidate : null;
    }

    @JsonProperty(value = "resourceCandidate")
    public List<CatalogReference> resourceCandidateToJson() {
        return (resourceCandidate != null && resourceCandidate.size() > 0) ? resourceCandidate : null;
    }

    @JsonProperty(value = "productOfferingTerm")
    public List<ProductOfferingTerm> productOfferingTermToJson() {
        return (productOfferingTerm != null && productOfferingTerm.size() > 0) ? productOfferingTerm : null;
    }

    @JsonProperty(value = "productOfferingPrice")
    public List<ProductOfferingEmbeddedPriceData> productOfferingPriceToJson() {
        return (productOfferingPrice != null && productOfferingPrice.size() > 0) ? productOfferingPrice : null;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 73 * hash + super.hashCode();

        hash = 73 * hash + (this.isBundle != null ? this.isBundle.hashCode() : 0);
        hash = 73 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 73 * hash + (this.channel != null ? this.channel.hashCode() : 0);
        hash = 73 * hash + (this.place != null ? this.place.hashCode() : 0);
        hash = 73 * hash + (this.bundledProductOffering != null ? this.bundledProductOffering.hashCode() : 0);
        hash = 73 * hash + (this.serviceLevelAgreement != null ? this.serviceLevelAgreement.hashCode() : 0);
        hash = 73 * hash + (this.productSpecification != null ? this.productSpecification.hashCode() : 0);
        hash = 73 * hash + (this.serviceCandidate != null ? this.serviceCandidate.hashCode() : 0);
        hash = 73 * hash + (this.resourceCandidate != null ? this.resourceCandidate.hashCode() : 0);
        hash = 73 * hash + (this.productOfferingTerm != null ? this.productOfferingTerm.hashCode() : 0);
        hash = 73 * hash + (this.productOfferingPrice != null ? this.productOfferingPrice.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass() || super.equals(object) == false) {
            return false;
        }

        final ProductOffering other = (ProductOffering) object;
        if (Utilities.areEqual(this.isBundle, other.isBundle) == false) {
            return false;
        }

        if (Utilities.areEqual(this.category, other.category) == false) {
            return false;
        }

        if (Utilities.areEqual(this.channel, other.channel) == false) {
            return false;
        }

        if (Utilities.areEqual(this.place, other.place) == false) {
            return false;
        }

        if (Utilities.areEqual(this.bundledProductOffering, other.bundledProductOffering) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceLevelAgreement, other.serviceLevelAgreement) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productSpecification, other.productSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceCandidate, other.serviceCandidate) == false) {
            return false;
        }

        if (Utilities.areEqual(this.resourceCandidate, other.resourceCandidate) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productOfferingTerm, other.productOfferingTerm) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productOfferingPrice, other.productOfferingPrice) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductOffering{<" + super.toString() + ">, isBundle=" + isBundle + ", category=" + category + ", channel=" + channel + ", place=" + place + ", bundledProductOffering=" + bundledProductOffering + ", serviceLevelAgreement=" + serviceLevelAgreement + ", productSpecification=" + productSpecification + ", serviceCandidate=" + serviceCandidate + ", resourceCandidate=" + resourceCandidate + ", productOfferingTerm=" + productOfferingTerm + ", productOfferingPrice=" + productOfferingPrice + '}';
    }

    @Override
    @JsonIgnore
    public Logger getLogger() {
        return logger;
    }

    @Override
    @JsonIgnore
    public void setCreateDefaults() {
        super.setCreateDefaults();

        if (isBundle == null) {
            isBundle = false;
        }
    }

    public void edit(ProductOffering input) {
        if (input == null || input == this) {
            return;
        }

        super.edit(input);

        if (this.isBundle == null) {
            this.isBundle = input.isBundle;
        }

        if (this.category == null) {
            this.category = input.category;
        }

        if (this.channel == null) {
            this.channel = input.channel;
        }

        if (this.place == null) {
            this.place = input.place;
        }

        if (this.bundledProductOffering == null) {
            this.bundledProductOffering = input.bundledProductOffering;
        }

        if (this.serviceLevelAgreement == null) {
            this.serviceLevelAgreement = input.serviceLevelAgreement;
        }

        if (this.productSpecification == null) {
            this.productSpecification = input.productSpecification;
        }

        if (this.serviceCandidate == null) {
            this.serviceCandidate = input.serviceCandidate;
        }

        if (this.resourceCandidate == null) {
            this.resourceCandidate = input.resourceCandidate;
        }

        if (this.productOfferingTerm == null) {
            this.productOfferingTerm = input.productOfferingTerm;
        }

        if (this.productOfferingPrice == null) {
            this.productOfferingPrice = input.productOfferingPrice;
        }
    }

    @Override
    @JsonIgnore
    public boolean isValid() {
        logger.log(Level.FINE, "ProductOffering:valid ()");

        if (super.isValid() == false) {
            return false;
        }

        if (this.isBundle == Boolean.TRUE) {
            if (Utilities.hasContents(this.bundledProductOffering) == false) {
                logger.log(Level.FINE, " invalid: bundledProductOffering must be specified when isBundle is true");
                return false;
            }
        }
        else {
            if (Utilities.hasContents(this.bundledProductOffering) == true) {
                logger.log(Level.FINE, " invalid: bundledProductOffering must not be specififed when isBundle is false");
                return false;
            }
        }

        return true;
    }

    public static ProductOffering createProto() {
        ProductOffering productOffering = new ProductOffering();

        productOffering.setId("id");
        productOffering.setVersion("3.43");
        productOffering.setHref("href");
        productOffering.setName("name");
        productOffering.setDescription("description");
        productOffering.setLastUpdate(new Date());
        productOffering.setLifecycleStatus(LifecycleStatus.ACTIVE);
        productOffering.setValidFor(TimeRange.createProto());

        productOffering.isBundle = true;

        productOffering.category = new ArrayList<CatalogReference>();
        productOffering.category.add(CatalogReference.createProto());

        productOffering.channel = new ArrayList<Channel>();
        productOffering.channel.add(Channel.createProto());

        productOffering.place = new ArrayList<Place>();
        productOffering.place.add(Place.createProto());

        productOffering.bundledProductOffering = new ArrayList<BundledProductReference>();
        productOffering.bundledProductOffering.add(BundledProductReference.createProto());

        productOffering.serviceLevelAgreement = ServiceLevelAgreement.createProto();

        productOffering.productSpecification = new ArrayList<CatalogReference>();
        productOffering.productSpecification.add(CatalogReference.createProto());

        productOffering.serviceCandidate = new ArrayList<CatalogReference>();
        productOffering.serviceCandidate.add(CatalogReference.createProto());

        productOffering.resourceCandidate = new ArrayList<CatalogReference>();
        productOffering.resourceCandidate.add(CatalogReference.createProto());

        productOffering.productOfferingTerm = new ArrayList<ProductOfferingTerm>();
        productOffering.productOfferingTerm.add(ProductOfferingTerm.createProto());

        productOffering.productOfferingPrice = new ArrayList<ProductOfferingEmbeddedPriceData>();
        productOffering.productOfferingPrice.add(ProductOfferingEmbeddedPriceData.createProto());

        return productOffering;
    }

}
