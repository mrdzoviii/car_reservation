package ba.telegroup.car_reservation.model;

import ba.telegroup.car_reservation.common.interfaces.HasCompanyId;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Logger implements HasCompanyId {
    private Integer id;
    private String actionType;
    private String actionDetails;
    private String tableName;
    private Timestamp created;
    private Integer userId;
    private Byte atomic;
    private Integer companyId;

    public Logger(Integer userId, String actionType, String actionDetails, String tableName, Byte atomic, Integer companyId) {
        this.userId = userId;
        this.actionType = actionType;
        this.actionDetails = actionDetails;
        this.tableName = tableName;
        this.atomic = atomic;
        this.companyId = companyId;
    }

    public Logger() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "action_type")
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Basic
    @Column(name = "action_details")
    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    @Basic
    @Column(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "created")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "atomic")
    public Byte getAtomic() {
        return atomic;
    }

    public void setAtomic(Byte atomic) {
        this.atomic = atomic;
    }

    @Basic
    @Column(name = "company_id")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logger logger = (Logger) o;
        return Objects.equals(id, logger.id) &&
                Objects.equals(actionType, logger.actionType) &&
                Objects.equals(actionDetails, logger.actionDetails) &&
                Objects.equals(tableName, logger.tableName) &&
                Objects.equals(created, logger.created) &&
                Objects.equals(userId, logger.userId) &&
                Objects.equals(atomic, logger.atomic) &&
                Objects.equals(companyId, logger.companyId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, actionType, actionDetails, tableName, created, userId, atomic, companyId);
    }

    public enum ActionType {
        CREATE("create"),
        UPDATE("update"),
        READ("read"),
        DELETE("delete");

        private final String text;

        ActionType(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }
}
