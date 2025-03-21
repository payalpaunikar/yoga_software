package com.yoga.service;


import com.yoga.datamodel.Receipt;
import com.yoga.dto.request.ReceiptRequest;
import com.yoga.exception.UserNotFoundException;
import com.yoga.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReceiptService {

        private ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }


    public Receipt submitTheReceipt(ReceiptRequest receiptRequest){
        Receipt newReceipt = new Receipt();
            newReceipt =  convertReceiptRequestIntoReceipt(newReceipt,receiptRequest);

            return receiptRepository.save(newReceipt);
    }


    public List<Receipt> getAllReceipt(){
        return receiptRepository.findAll();
    }

    public Receipt getReceiptById(Long receiptId){
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(()-> new UserNotFoundException("receipt with : "+receiptId+" id is not found"));

        return receipt;
    }

    public String deleteReceipt(Long receiptId){
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(()-> new UserNotFoundException("receipt with : "+receiptId+" id is not found"));

        receiptRepository.delete(receipt);
        return "Receipt Deleted succefully";
    }


    public Receipt updateReceipt(Long receiptId,ReceiptRequest receiptRequest){
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(()-> new UserNotFoundException("receipt with : "+receiptId+" id is not found"));
        receipt = convertReceiptRequestIntoReceipt(receipt,receiptRequest);

       return receiptRepository.save(receipt);
    }

    private Receipt convertReceiptRequestIntoReceipt(Receipt receipt,ReceiptRequest receiptRequest){
        receipt.setStudentName(receiptRequest.getStudentName());
        receipt.setBankName(receiptRequest.getBankName());
        receipt.setStartingDate(LocalDate.parse(receiptRequest.getStartingDate()));
        receipt.setEndingDate(LocalDate.parse(receiptRequest.getEndingDate()));
        receipt.setRupeesInWords(receiptRequest.getRupeesInWords());
        receipt.setCheckNo(receiptRequest.getCheckNo());
        receipt.setBalanceDue(receiptRequest.getBalanceDue());
        receipt.setAmount(receiptRequest.getAmount());

        return receipt;
    }
}
