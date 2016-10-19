package mh.data.uploader.service;

import org.springframework.stereotype.Service;

import mh.data.uploader.model.FileUpload;

@Service
public class FileUploadService {

	// Upload the file
	public void uploadFile(FileUpload doc) {
		System.out.println("Uploaded file " + doc.getFilename());
	}
}
