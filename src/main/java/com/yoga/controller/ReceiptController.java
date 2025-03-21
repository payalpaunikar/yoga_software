package com.yoga.controller;


import com.yoga.datamodel.Receipt;
import com.yoga.dto.request.ReceiptRequest;
import com.yoga.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipt")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
@CrossOrigin(origins = "*")
public class ReceiptController {

    private ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/")
    private ResponseEntity<?> createdNewReceipt(@RequestBody ReceiptRequest receiptRequest){
        return ResponseEntity.ok(receiptService.submitTheReceipt(receiptRequest));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllReceipt(){
        return ResponseEntity.ok(receiptService.getAllReceipt());
    }

    @GetMapping("{receiptId}")
    public ResponseEntity<?> getReceiptById(@PathVariable("receiptId")Long receiptId){
        return ResponseEntity.ok(receiptService.getReceiptById(receiptId));
    }

    @DeleteMapping("{receiptId}")
    public ResponseEntity<?> deleteReceipt(@PathVariable("receiptId")Long receiptId){
        return ResponseEntity.ok(receiptService.deleteReceipt(receiptId));
    }

    @PutMapping("/{receiptId}")
    public ResponseEntity<?> updateReceipt(@PathVariable("receiptId")Long receiptId,@RequestBody ReceiptRequest receiptRequest){
        return ResponseEntity.ok(receiptService.updateReceipt(receiptId,receiptRequest));
    }
}
