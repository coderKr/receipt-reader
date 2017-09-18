package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import api.ReceiptWithTags;
import dao.ReceiptDao;
import dao.TagsDao;
import generated.tables.records.ReceiptsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;

    public ReceiptController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @POST
    public int createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        return receipts.insert(receipt.merchant, receipt.amount);
    }

    @GET
    public List<ReceiptWithTags> getReceipts() {
        List<ReceiptWithTags> allReceipts = new ArrayList<>();
        List<ReceiptsRecord> receiptRecords = receipts.getAllReceipts();
        for (ReceiptsRecord receiptsRecord : receiptRecords) {
            List<String> tags = receipts.getTagsForReceiptId(receiptsRecord.getId());
            allReceipts.add(new ReceiptWithTags(receiptsRecord, tags));
        }
        System.out.println(receiptRecords.size());
        return allReceipts;
    }


    @GET
    @Path("/{receiptId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<String> getReceiptsWithTags(@PathParam("receiptId") Integer id){
        ReceiptsRecord receipt = receipts.getReceiptWithId(id);
        List<String> tags = new ArrayList<String>();
        tags = receipts.getTagsForReceiptId(id);
       // ReceiptWithTags test = new ReceiptWithTags(receipt,tags);
        return tags;
    }

}
