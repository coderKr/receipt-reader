package dao;

import generated.tables.Receipts;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagsDao {
    DSLContext dsl;

    public TagsDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public void insertTag(String tag, Integer id) {
        if(verifyId(id).size() > 0) {
            List<TagsRecord> tagsInfo = getTag(tag, id);
            if (tagsInfo.size() > 0) {
                delete(tag, id);
            } else {
                dsl.insertInto(TAGS, TAGS.ID, TAGS.TAG).values(id, tag).execute();
            }
        }
    }

    public void delete(String tag, Integer id){
        dsl.deleteFrom(TAGS).where(TAGS.ID.eq(id)).and(TAGS.TAG.eq(tag)).execute();
    }

    public List<TagsRecord> getTag(String tag, Integer id) {
        return dsl.selectFrom(TAGS).where(TAGS.ID.eq(id)).and(TAGS.TAG.eq(tag)).fetch();
    }

    public List<ReceiptsRecord> getReceiptsWithTag(String tag){
        return dsl.select(TAGS.ID,RECEIPTS.MERCHANT,RECEIPTS.AMOUNT, RECEIPTS.UPLOADED).from(TAGS).join(RECEIPTS).on(TAGS.ID.eq(RECEIPTS.ID)).where(TAGS.TAG.eq(tag)).fetchInto(ReceiptsRecord.class);
    }

    public ReceiptsRecord verifyId(Integer id){
        return dsl.selectFrom(RECEIPTS).where(RECEIPTS.ID.eq(id)).fetchOne();
    }

    public List<String> getTagsForReceiptId(Integer receiptId) {
        List<String> tags = null;
        if (receiptId != null) {
            tags = dsl
                    .select(TAGS.TAG).from(TAGS)
                    .where(TAGS.ID.eq(receiptId)).fetch(TAGS.TAG);
        }
        return tags;
    }
}
