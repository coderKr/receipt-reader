package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import generated.tables.records.ReceiptsRecord;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReceiptWithTags {
    public Integer id;
    public String merchantName;
    public BigDecimal value;
    public Time created;
    public Set<String> tags;

    public  ReceiptWithTags(ReceiptsRecord receipt, List<String> tags) {
        if (receipt != null) {
            this.id = receipt.getId();
            this.created = receipt.getUploaded();
            this.merchantName = receipt.getMerchant();
            this.value = receipt.getAmount();
            this.tags = new HashSet<String>(tags);;
        }
    }
}
