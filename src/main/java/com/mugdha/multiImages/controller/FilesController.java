package com.mugdha.multiImages.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.mugdha.multiImages.data.ProductRepository;
import com.mugdha.multiImages.message.ResponseMessage;
import com.mugdha.multiImages.model.FileInfo;
import com.mugdha.multiImages.model.Product;
import com.mugdha.multiImages.model.ProductImages;
import com.mugdha.multiImages.service.FilesStorageService;

@Controller
@CrossOrigin("http://localhost:8888")
public class FilesController {

	@Autowired
	FilesStorageService storageService;

	@Autowired
	private ProductRepository fileJpaRepository;


	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files,

			@RequestParam("productName") String productName) {
		String message = "";
		System.out.println("in post");
		try {
			List<String> fileNames = new ArrayList<>();
			Product product = new Product();

			product.setName(productName);
			Set<ProductImages> prodImagesSet = new HashSet();
			final Path root = Paths.get("uploads/" + productName);
			Files.createDirectory(root);
			Arrays.asList(files).stream().forEach(file -> {
				ProductImages imagesInfo = new ProductImages();
				imagesInfo.setName(file.getOriginalFilename());
				prodImagesSet.add(imagesInfo);
				storageService.save(file, root);
				imagesInfo.setProduct(product);

				fileNames.add(file.getOriginalFilename());
			});
			product.setProductIamges(prodImagesSet);
			fileJpaRepository.save(product);
			message = "Uploaded the files successfully: " + fileNames;
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Fail to upload files!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/files")
	public ResponseEntity<List<FileInfo>> getListFiles() {
		List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

			return new FileInfo(filename, url);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	@GetMapping("/files/listAllImagesOfProduct")
	public ResponseEntity<List<FileInfo>> getListFilesOfProduct(@RequestParam("productName") String productName) {
		final Path root = Paths.get("uploads/" + productName);
		List<FileInfo> fileInfos = storageService.loadAllImagesOfProduct(root).map(path -> {
			String filename = path.getFileName().toString();
			String url = MvcUriComponentsBuilder
					.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

			return new FileInfo(filename, url);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
