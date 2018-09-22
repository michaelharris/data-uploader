package mh.data.uploader.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mh.data.uploader.model.FileUpload;
import mh.data.uploader.service.FileUploadService;

@RestController
public class UploadController {

	@Autowired
	FileUploadService fileUploadService;

	static Logger logger = LoggerFactory.getLogger(UploadController.class);

	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResponseEntity uploadFileHandler(@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String name = file.getOriginalFilename();
				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Written file to {}", serverFile.getAbsolutePath());

				return new ResponseEntity<>("{}", HttpStatus.OK);
			} catch (Exception e) {

				return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			System.err.println("File was empty");
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
