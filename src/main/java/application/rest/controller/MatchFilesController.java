package application.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import application.model.Record;
import application.response.ResponseMessage;
import application.response.ResponseMessageRegister;
import application.util.CSVUtil;

/**
 * @author Rodrigues Targino 
 * Controller for process reconciliation strategy
 * 
 * */
@RestController
@RequestMapping("/api/csv")
public class MatchFilesController {
	
	List<ResponseMessageRegister> response = new ArrayList<ResponseMessageRegister>();
	
	int countRecordsUnMached1 = 0;
	int countRecordsUnMached2 = 0;
	int totalRecordsList1 = 0;
	int totalRecordsList2 = 0;
	int totalRecordsMatch = 0;

	
	/**
	 *	Main method for process both files
	 *	@author Rodrigues Targino 
	 *
	 *  @param MultipartFile
	 *  @param MultipartFile
	 *  @exception ResponseStatusException
	 * 
	 */
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file1") @Valid MultipartFile file1,
			@RequestParam("file2") @Valid MultipartFile file2) throws ResponseStatusException{
		String message = "";
		
		if(file1.isEmpty() || file2.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not Found File. Please send a CSV file.");
		} else if (CSVUtil.hasCSVFormat(file1) && CSVUtil.hasCSVFormat(file2)) {
			try {
				List<Record> list1 = CSVUtil.csvToRecords(file1.getInputStream());
				List<Record> list2 = CSVUtil.csvToRecords(file2.getInputStream());
				
				totalRecordsList1 = list1.size();
				totalRecordsList2 = list2.size();
				processUploadFileUnMatch(list1, list2, null);

				message = "Uploaded the file successfully";
				totalRecordsMatch = totalRecordsList1 - countRecordsUnMached1;
				

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, countRecordsUnMached1,countRecordsUnMached2,
						totalRecordsMatch, totalRecordsList1,totalRecordsList2));

			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not upload the file");
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please upload a csv file!");
		}
	}
	
	/**
	 *	Main method for recover unmached files
	 *	@author Rodrigues Targino 
	 */
	@GetMapping("/recover")
	public ResponseEntity<List<ResponseMessageRegister>> recoverFile() {
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * Strategy for process match files. This strategy considers all fields equals.
	 * @author Rodrigues Targino 
	 * 
	 * @param List<Record>
	 * @param List<Record>
	 * @return Integer 
	 */
	private void processUploadFileUnMatch(List<Record> list1, List<Record> list2, List<Record> mached) throws IOException {
		
		response = new ArrayList<ResponseMessageRegister>();
		
		list1.forEach(item1 -> list2.stream().forEach(item2 -> {
			
			if(item1.getLineNumber() == item2.getLineNumber()) {
				
				if(!hasReconciliation(item1, item2)) {
					countRecordsUnMached1 = countRecordsUnMached1+1;
					countRecordsUnMached2 = countRecordsUnMached2+1;
					
					ResponseMessageRegister responseRegister = new ResponseMessageRegister();
					
					responseRegister.setProfileName(item1.getProfileName());
					responseRegister.setProfileName1(item2.getProfileName());
					
					responseRegister.setTransactionAmount(item1.getTransactionAmount());
					responseRegister.setTransactionAmount1(item2.getTransactionAmount());
					
					responseRegister.setTransactionDate(item1.getTransactionDate());
					responseRegister.setTransactionDate1(item2.getTransactionDate());
					
					responseRegister.setTransactionID(item1.getTransactionID());
					responseRegister.setTransactionID1(item2.getTransactionID());
					
					responseRegister.setTransactionType(item1.getTransactionType());
					responseRegister.setTransactionType1(item2.getTransactionType());
					
					responseRegister.setWalletReference(item1.getWalletReference());
					responseRegister.setWalletReference1(item2.getWalletReference());
					
					responseRegister.setTransactionNarrative(item1.getTransactionNarrative());
					responseRegister.setTransactionNarrative(item2.getTransactionNarrative());
					
					responseRegister.setTransactionDescription(item1.getTransactionDescription());
					responseRegister.setTransactionDescription(item2.getTransactionDescription());
					
					response.add(responseRegister);
				}
			}
			
		}));
		
	}

	private boolean hasReconciliation(Record item1, Record item2) {
		return (item1.getTransactionDate().equals(item2.getTransactionDate()) &&
		item1.getTransactionAmount().equals(item2.getTransactionAmount()) &&
		item1.getTransactionID().equals(item2.getTransactionID()) &&
		item1.getWalletReference().equals(item2.getWalletReference()));
	}

}
