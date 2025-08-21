package com.bill.service.serviceimple;
import com.bill.service.dto.CategoryDTO;
import com.bill.service.dto.ProductDTO;
import com.bill.service.dto.TotalBill;
import com.bill.service.extservice.CategoryServiceExt;
import com.bill.service.extservice.ProductServiceExt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.pdf.PdfContentByte;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Type;
import com.bill.service.entities.Bill;
import com.bill.service.payload.ApiResponse;
import com.bill.service.repository.BillRepository;
import com.bill.service.service.BillService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImple implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private CategoryServiceExt categoryServiceExt;

    @Autowired
    private ProductServiceExt productServiceExt;

    private static final String PDF_FOLDER = "E:\\Extracted_java_projects\\pdfFolder";

    @Override
    public ResponseEntity<ApiResponse> generateBill(Bill bill) {
        try{
            if(bill == null){
                return new ResponseEntity<>(new ApiResponse("bill nullt", false, 500), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String fileName = getUUID();
            insertBill(bill);
            String data =   "Name: " +bill.getName()+ "\n"
                          + "Contact Number: " +bill.getContactNumber()+ "\n"
                          + "Email: " +bill.getEmail()+ "\n"
                          + "Payment Mode: " +bill.getPaymentMode();

            Document document = new Document();

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PDF_FOLDER + "\\" + fileName + ".pdf"));
            document.open();
            setRectangularPdf(document,writer);

            Paragraph paragraph = new Paragraph("Cafe Management System ", getFont("Header"));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

            Paragraph para = new Paragraph(data+ "\n \n", getFont("Data"));
            document.add(para);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            addTableHeader(table);

//            JSONArray jsonArray =  getJsonArrayFromString(bill.getProductDetails());
//            for(int i =0; i<jsonArray.length(); i++){
//               Map<String, Object> itemData =  getMapFromString(jsonArray.getString(i));
//               addRowsEle(table,itemData);
//            }

            JSONArray jsonArray = getJsonArrayFromString(bill.getProductDetails());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Map<String, Object> map = new HashMap<>();
                for (String key : obj.keySet()) {
                    map.put(key, obj.get(key));
                }
                addRowsEle(table, map);
            }

            document.add(table);
            Paragraph footer =new Paragraph("Total :" +bill.getTotal()+ " \n" + "Thank you for visiting. please visit again ", getFont("Data"));
            document.add(footer);

            document.close();

            return new ResponseEntity<>(new ApiResponse("pdf generated ", true, 200), HttpStatus.OK);
        } catch (Exception e) {
            log.error("exception while generating report ");
           return new ResponseEntity<>(new ApiResponse("ception while generating report", false, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public static String getUUID() {
        return "BILL-" + System.currentTimeMillis();
    }


    private void insertBill(Bill bill){
        try{
            Bill billObj = new Bill();
            billObj.setUuid(bill.getUuid());
            billObj.setName(bill.getName());
            billObj.setEmail(bill.getEmail());
            billObj.setPaymentMode(bill.getPaymentMode());
            billObj.setTotal(bill.getTotal());
            billObj.setContactNumber(bill.getContactNumber());
            billObj.setProductDetails(bill.getProductDetails());
            billObj.setCreatedBy(bill.getCreatedBy());
            this.billRepository.save(billObj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setRectangularPdf(Document document, PdfWriter writer){
        try{
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(577,825,18,15);
            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(1);
            rect.setBorderColor(BaseColor.BLACK);
            canvas.rectangle(rect);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Font getFont(String type){
        switch (type){
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case  "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11, BaseColor.BLACK);
                return dataFont;
            default:
                return  new Font();
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.YELLOW);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });
    }


    public static JSONArray getJsonArrayFromString(String data) throws JSONException {
        return new JSONArray(data);
    }


    public static Map<String, Object> getMapFromString(String data) {
        if (!Strings.isEmpty(data)) {
            Type type = new TypeToken<Map<String, Object>>() {}.getType();
            return new Gson().fromJson(data, type);
        }
        return new HashMap<>();
    }

//    private void  addRowsEle(PdfPTable table, Map<String, Object> data){
//        table.addCell( new PdfPCell(new Phrase(String.valueOf(data.get("name")))));
//        table.addCell( new PdfPCell(new Phrase(String.valueOf(data.get("category")))));
//        table.addCell( new PdfPCell(new Phrase(String.valueOf(data.get("quantity")))));
//        table.addCell( new PdfPCell(new Phrase(String.valueOf(data.get("price")))));
//        table.addCell(  new PdfPCell(new Phrase(String.valueOf(data.get("total")))));
//    }

    private void addRowsEle(PdfPTable table, Map<String, Object> data) {
        table.addCell(String.valueOf(data.get("productName")));
        table.addCell(String.valueOf(data.get("categoryName")));
        table.addCell(String.valueOf(data.get("quantity")));
        table.addCell(String.valueOf(data.get("price")));
        table.addCell(String.valueOf(data.get("total")));
    }


    @Override
    public List<CategoryDTO> getAllCategories() {
         List<CategoryDTO> categories = this.categoryServiceExt.getAllCategories();
        return categories;
    }

    @Override
    public List<ProductDTO> getProducts() {
        List<ProductDTO> products = this.productServiceExt.getProducts();
        return products;
    }

    @Override
    public TotalBill totalBills() {
        List<Bill> bills = this.billRepository.findAll();
        TotalBill totalBill = new TotalBill();
        totalBill.setTotalBill(bills.size());
        return  totalBill;
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        String isAdmin = "ADMIN";
        String email = "user@gmail.com";
        List<Bill> list = new ArrayList<>();
        if(isAdmin.equals("ADMIN")){
            list = billRepository.findAll();
        }else{
            list = billRepository.findByEmail(email);
        }
        return  new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> deleteBill(int id) {
       Optional<Bill> billOptional = this.billRepository.findById(id);
       if(billOptional.isEmpty()){
           return new ResponseEntity<>(new ApiResponse("No bill found", false, 400), HttpStatus.BAD_REQUEST);
       }
       Bill bill = billOptional.get();
       this.billRepository.delete(bill);
       return new ResponseEntity<>(new ApiResponse("delete bill successfully", true, 200), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> mapRequest) {
        try{
            log.info("inside pdf : {} ", mapRequest);
            byte[] byteArray = new byte[0];
            if(mapRequest.isEmpty()){
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
