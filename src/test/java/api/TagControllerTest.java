package api;

import dao.TagsDao;
import controllers.TagController;
import generated.tables.Receipts;
import generated.tables.records.ReceiptsRecord;
import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TagControllerTest {
    @Test
    public void testGetReceiptsWithTag() {
        TagsDao mockDao = mock(TagsDao.class);
        TagController tags = new TagController(mockDao);
        List<ReceiptsRecord> testReceipt = new ArrayList<ReceiptsRecord>();
        ReceiptsRecord testRecord = new ReceiptsRecord();
        testRecord.setMerchant("Zalora");
        testRecord.setId(1);
        testReceipt.add(testRecord);
        when(mockDao.getReceiptsWithTag("clothes")).thenReturn(testReceipt);
        List<ReceiptResponse> testResponse = tags.getReceipts("clothes");
        assert(testResponse.get(0).id == testRecord.getId());

    }
}
