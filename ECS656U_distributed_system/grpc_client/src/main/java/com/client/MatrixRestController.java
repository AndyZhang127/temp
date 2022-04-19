package com.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@RestController
public class MatrixRestController {

	GRPCClientService grpcClientService;

	@Autowired
	public MatrixRestController(GRPCClientService grpcClientService) {
		this.grpcClientService = grpcClientService;
	}


	@PostMapping("/add")
	public String addMultiply(@RequestParam("matrixFile1") MultipartFile matrixFile1, @RequestParam("matrixFile2") MultipartFile matrixFile2, @RequestParam("deadline") String deadline) {
		try {
			String matrix1String = new String(matrixFile1.getBytes(), StandardCharsets.UTF_8);
			String matrix2String = new String(matrixFile2.getBytes(), StandardCharsets.UTF_8);
			return grpcClientService.addMatrixFiles(matrix1String, matrix2String, Long.parseLong(deadline));
		} catch(IOException | InvalidMatrixException | ExecutionException | InterruptedException e) {
			e.printStackTrace();
			return e.getLocalizedMessage();
		}
	}

	@PostMapping("/matrixMultiply")
	public String matrixMultiply(@RequestParam("matrixFile1") MultipartFile matrixFile1, @RequestParam("matrixFile2") MultipartFile matrixFile2, @RequestParam("deadline") String deadline) {
	    try {
			String matrix1String = new String(matrixFile1.getBytes(), StandardCharsets.UTF_8);
			String matrix2String = new String(matrixFile2.getBytes(), StandardCharsets.UTF_8);
			return grpcClientService.multiplyMatrixFiles(matrix1String, matrix2String, Long.parseLong(deadline));
	    } catch(IOException | InvalidMatrixException | ExecutionException | InterruptedException e) {
	    	e.printStackTrace();
	    	return e.getLocalizedMessage();
		}
	}
}
