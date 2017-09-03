package controllers;


import api.ReceiptResponse;
import dao.TagsDao;
import generated.tables.records.ReceiptsRecord;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final TagsDao tags;

    public TagController(TagsDao tags) {
        this.tags = tags;
    }
    @PUT
    @Path("/{tag}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void toggleTag(@PathParam("tag") String tagName, Integer id) {
        tags.insertTag(tagName,id);
    }

    @GET
    @Path("/{tag}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptRecords = tags.getReceiptsWithTag(tagName);
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}
